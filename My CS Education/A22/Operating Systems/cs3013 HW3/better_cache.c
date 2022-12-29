#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

// size of memory
#define MEM_SIZE 64
// size of page
#define PAGE_SIZE 16
// number of pages
#define PAGE_NUM 4
// size of a page table entry
#define ENTRY_SIZE 4
// protection bits
#define WRITABLE 1
#define READABLE_ONLY 0
// PFN offset in page table entry
#define PFN_OFF 1
// protection bit offset in page table entry
// protection bit: 1 = writable and readable
#define PROTECTION_OFF 2
// valid bit offset in page table entry
// valid bit: 2 = valid in memory, 1 = valid in disk, 0 = not valid yet
#define VAL_OFF 3

struct hardware_register{
    // address of the page
    int address;
    // where does it point to: 1 = memory, 0 = virtual address
    int location; 
};

// memory
unsigned char memory[MEM_SIZE];
// Whether corresponding page is available; 1 = available, 0 = not
int freelist[4];
// hardware register pointing to page table
struct hardware_register hp[4];
// page information, which process is holding it
// -1 = no process currently holding
int pages[4];
// 1 = pagetable, 0 = otherwise
int structure[4];
// round robin
int rr;
// line number in swap file
int lc;

// takes in a pid and a virtual address, get which page table entry it is
int get_pte(unsigned char pid, unsigned char vaddress){
    int page_num = 0;
    int loc = 0;
    while (vaddress >= 16){
        vaddress = vaddress - 16;
        page_num += 1;
    }
    // currently in memory
    if (hp[pid].location == 1){
        // return location in memory
        loc = hp[pid].address + ENTRY_SIZE*page_num;
        return loc;
    } 
    // currently in disk
    else if (hp[pid].location == 0){
        return -1;
    }
    // not found
    return -2;
}

// check whether a physical page is available
int is_available(){
    // return physical address of start of page if page is available
    for(int i = 0; i < PAGE_NUM; i++){
        if(freelist[i] == 1){
            freelist[i] = 0;
            return i * PAGE_SIZE;
        }
    }
    return -1;
}

// update the round robin
void update_rr(){
    rr = (rr + 1) % 4;
}

// get physical address from virtual address and pid
int translate(unsigned char pid, unsigned char vaddress){
    // address is in memory
    if (hp[pid].location == 1){
        int num = vaddress;
        int page_num = 0;
        // get page number
        while (num >= 16){
            num = num - 16;
            page_num += 1;
        }
        // page not valid
        if (memory[hp[pid].address + (page_num * ENTRY_SIZE) + VAL_OFF] == 0){   
            return -1;
        }
        int paddress = memory[hp[pid].address + (page_num * ENTRY_SIZE) + PFN_OFF];
        int offset = vaddress - (16 * page_num);
        paddress += offset;
        return paddress;
    }
    return -1;
}

// copy characters to memory
void to_memory(unsigned char* buffer, int page){
    for(int i =0; i < PAGE_SIZE; i++){
		memory[page + i] = buffer[i];
	}
}

// get buffer from file (disk)
void get_file(unsigned char* buffer, int line){
    char str[64];
    FILE *f = fopen("swapspace.txt", "r");
    fseek(f, 0, SEEK_SET);
    fgets(str, 64, f);
    char *t = strtok(str, "");
    int num = atoi(t);
    // find the line
    while(num != line){
        fgets(str, 64, f);
        t = strtok(str, " ");
        num = atoi(t);
    }
    // get first number
    t = strtok(NULL, " ");
    int count;
    while(t!=NULL){
        // load char into buffer
        count += 1;
        buffer[count] = (unsigned char) atoi(t);
        t = strtok(NULL, " ");
    }
    fclose(f);
}

// write page to disk
int write_file(unsigned char *buffer, int l, int base){
    FILE* f = fopen("swapspace.txt", "a");
    // write line number to file
    fprintf(f, "%d ", l);
    // write data to file
    for (int i = 0; i < PAGE_SIZE; i++){
        fprintf(f, "%u ",buffer[base+i]);
    }
    // get a new line
    fprintf(f, "%c ", '\n');
    fclose(f);
    return l;
}

// get the previously evicted page or page table
// identifier: 1=page, 0=page table
int get_page(int pid, int vaddress, int identifier){
    unsigned char buf[16];
    int free_pg;
    if (identifier == 1){
        int pte = get_pte(pid, vaddress);        
        int line = memory[pte+PFN_OFF];
        free_pg = is_available();
        get_file(buf, line);
        to_memory(buf, free_pg);
        pages[free_pg/PAGE_SIZE] = pid;
        memory[pte + PFN_OFF] = free_pg;
        memory[pte + VAL_OFF] = 2;
        printf("Swapped disk slot %d into frame %d\n", lc, free_pg/PAGE_SIZE);
    } else {
        int line = hp[pid].address;
        free_pg = is_available();
        get_file(buf, line);
        to_memory(buf, free_pg);
        pages[free_pg/PAGE_SIZE] = pid;
        hp[pid].address = free_pg;
        hp[pid].location = 1;
        structure[free_pg/PAGE_SIZE] = 1;
        printf("Swapped page table for PID %d into physical frame %d", pid, free_pg/PAGE_SIZE);
    }
    return free_pg;
}

// move the page to disk
void move_page(int pid, int paddress){
    int pte = get_pte(pid,0);
    int pt = 0;
    for (int i = 0; i < PAGE_SIZE; i++){
        int loc = pte + i;
        if(memory[loc] == paddress){
            pt = memory[loc-1];
        }
    }
    int a = write_file(memory, lc++, paddress);
    memory[pte+(PAGE_NUM*pt) + PFN_OFF] = a;
    memory[pte+(PAGE_NUM*pt) + VAL_OFF] = 1;
    // erase data from memory
    for(int i = 0; i < PAGE_SIZE; i++){
        int loc = paddress+i;
        memory[loc] = 0;
    }
    // set the freelist and page information
    freelist[paddress/PAGE_SIZE] = 1;
    pages[paddress/PAGE_SIZE] = -1;
}

// move page table to disk
void move_page_table(int pid){
    int pte = get_pte(pid,0);
    int b = write_file(memory, lc++, pte);
    hp[pid].address = b;
    hp[pid].location = 0;
    // erase page table from memory
    for(int i = 0; i < PAGE_SIZE; i++){
        int loc = +i;
        memory[loc] = 0;
    }
    freelist[pte/PAGE_SIZE] = 1;
    structure[pte/PAGE_SIZE] = 0;
    pages[pte/PAGE_SIZE] = -1;
}

// evict a page to get space for other pages
void evict(int num, int pid){
    update_rr();
    // get the number of free pages available
    int fpcount;
    for (int i = 0; i<PAGE_NUM; i++){
        if(freelist[i] == 1){
            fpcount++;
        }
    }
    // keep track of the ones that are removed
    int ev[4] = {0, 0, 0, 0};
    // move page
    while (fpcount < num){
        int count = 0;
        if(hp[pid].address == rr*PAGE_NUM && hp[pid].location==1){
            update_rr();
            count++;
        }

        // check if all four are page table
        for (int i = 0; i < PAGE_NUM; i++){
            if(structure[rr] == 1){
                count++;
            }
            // if all four are page tables, move on to the next loop
            if (count == PAGE_NUM){
                break;
            }
        }
        
        // if not evicted
        if (ev[rr] == 0){
            move_page(pages[rr], rr*PAGE_SIZE);
            ev[rr] = 1;
            fpcount++;
            count++;
            printf("Swapped frame %d to disk at swap slot %d\n", rr, lc);
            update_rr();
            if (count == PAGE_NUM){
                break;
            }
        } else {
            update_rr();
            count++;
            if (count == PAGE_NUM){
                break;
            }
        }
    }

    // move page table
    while(fpcount < num){
        if(hp[pid].address == rr*PAGE_NUM && hp[pid].location==1){
            update_rr();
        }
        // if not evicted
        if (ev[rr] == 0){
            // if is pagetable
            if(structure[rr] == 1){
                move_page_table(pages[rr]);
                printf("Swapped page table for pid %d into disk.\n", pid);
                update_rr();
                fpcount++;
            } 
            // if is a physical page
            else {
                move_page(pages[rr], rr*PAGE_SIZE);
                int slot = lc-1;
                printf("Swapped frame %d to disk at swap slot %d\n", rr, slot);
                fpcount++;
            }
        } else {
            update_rr();
        }   
    }
}

// creates a mapping in the page table between a virtual and physical address
void map(unsigned char pid, unsigned char vaddress, unsigned char value){
    // get location of page table entry in memory
    int pte = get_pte(pid, vaddress);
    // if pte currently in disk
    if (pte == -1){
        evict(1, pid);
        int free_pg = get_page(pid, vaddress, 0);
        // address of start of a free page
        pages[free_pg/PAGE_SIZE] = pid;
        printf("Load page table for PID %d into physical frame %d\n", pid, free_pg/PAGE_SIZE);
        structure[free_pg/PAGE_SIZE] = 1;
    }
    // if pte not found
    else if (pte == -2){
        evict(2, pid);
        int free_pg = is_available();
        hp[pid].address = free_pg;
        hp[pid].location = 1;
        // which page should it be put on
        int page_num = free_pg/PAGE_SIZE;
        pages[page_num] = pid;
        // create page table on demand
        for (int i = 0; i < PAGE_NUM; i++){
            memory[hp[pid].address + (ENTRY_SIZE * i)] = i;
            memory[hp[pid].address + (ENTRY_SIZE * i) + VAL_OFF] = 0;
        }
        printf("Put page table for PID %d into physical frame %d\n", pid, free_pg/PAGE_SIZE);
        structure[free_pg/PAGE_SIZE] = 1;
    }
    // update pte
    pte = get_pte(pid, vaddress);

    // rewrite the protection bit if needed
    if(memory[pte+VAL_OFF] == 2){
        if(memory[pte+PROTECTION_OFF] == value){
            printf("Error: virtual page %d is already mapped with rw_bit=%d\n", pid, value);
        } else {
            memory[pte+PROTECTION_OFF] = value;
            printf("Updating permissions for virtual page %d\n", pid);
        }
        return;
    }

    // update the status value for the page table entry
    int free_pg = is_available();
    // just in case no free page available
    if (free_pg < 0){
        evict(1, pid);
        free_pg = is_available();
    }
    pages[free_pg/PAGE_SIZE] = pid;
    memory[pte + PFN_OFF] = free_pg;
    memory[pte + PROTECTION_OFF] = value;
    memory[pte + VAL_OFF] = 2;
    printf("Mapped virtual address %d (page %d) into physical frame %d\n", vaddress, vaddress/PAGE_SIZE, free_pg/PAGE_SIZE);
}

// instructs the memory manager to write the supplied value into the physical memory location 
// associated with the provided virtual address, swapping if needed
void store(unsigned char pid, unsigned char vaddress, unsigned char value){
    int pte = get_pte(pid, vaddress);
    // page in memory
    if(pte >= 0){
        // get physical address
        int paddress = translate(pid, vaddress);
        if (paddress < 0){
            printf("Error: Segmentation fault! Go get a page! \n");
            return;
        }
        if (memory[pte + PROTECTION_OFF] == WRITABLE){
            memory[paddress] = value;
            printf("Stored value %d at virtual address %d (physical address %d)\n", value, vaddress, paddress);
            return;
        } else {
            printf("Error: writes are not allowed to this page\n");
            return;
        }
    } 
    // page in disk, with swapping action
    else if (pte == -1){
        int free_pg;
        for (int i = 0; i < PAGE_NUM; i++){
            if(freelist[i]==1){
                free_pg++;
            }
        }
        // check with hp if on disk
        if (hp[pid].location==0){
            // if no free page
            if(free_pg == 0){
                evict(1,pid);
            }
            get_page(pid, vaddress, 0);
            pte = get_pte(pid, vaddress);
            // check validity
            if(memory[pte+VAL_OFF] == 1){
                free_pg--;
                if(free_pg == 0){
                    evict(1, pid);
                }
                get_page(pid, vaddress, 1);
            }
        }
        pte = get_pte(pid, vaddress);
        // check if writable
        if(memory[pte+PROTECTION_OFF] == WRITABLE){
            int paddress = translate(vaddress, pid);
            memory[paddress] = value;
            printf("Stored value %d at virtual address %d (physical address %d)\n", value, vaddress, paddress);
        } else {
            printf("Error: writes are not allowed to this page\n");
        }
        return;
    } 
    // page does not exist
    else if (pte == -2){
        printf("Error: Segmentation fault! Go get a page! \n");
        return;
    }
}

// instructs the memory manager to return the byte stored at the memory location specified by 
// virtual address, swapping if needed
void load(unsigned char pid, unsigned char vaddress, unsigned char value){
    int pte = get_pte(pid, vaddress);
    // page in memory
    if (pte >= 0){
        // if in memory
        if(memory[pte+VAL_OFF] == 2){
            int paddress = translate(pid, vaddress);
            int val = memory[paddress];
            printf("The value %d is virtual address %d (physical address %d)\n", memory[paddress], vaddress, paddress);
            return;
        } 
        // if in disk
        else if (memory[pte+VAL_OFF] == 1){
            int free_pg_c;
            for (int i = 0; i < PAGE_NUM; i++){
                if(freelist[i] == 1){
                    free_pg_c = 1;
                }
            }
            if(free_pg_c == 1){
                get_page(pid, vaddress,1);
            } else {
                evict(1, pid);
                get_page(pid, vaddress,1);
            }
            
            int paddress = translate(pid, vaddress);
            if (paddress > 0){
                printf("The value %d is virtual address %d (physical address %d)\n", memory[paddress], vaddress, paddress);
                return;
            }
        } else {
            printf("Error: No value to load yet! Need to store value first\n");
            return;
        }

    }
    // page in disk, swapping may occur
    else if (pte == -1){
        int free_pg;
        for (int i = 0; i < PAGE_NUM; i++){
            if(freelist[i]==1){
                free_pg++;
            }
        }
        // if on disk
        if (hp[pid].location==0){
            // if no free page
            if(free_pg == 0){
                evict(1,pid);
            }
            get_page(pid, vaddress, 0);
            if(memory[pte+VAL_OFF] == 1){
                free_pg--;
                if(free_pg == 0){
                    evict(1, pid);
                }
                get_page(pid, vaddress, 1);
            }
        }
        int paddress = translate(pid, vaddress);
        if(paddress>0){
            printf("The value %d is virtual address %d (physical address %d)\n", memory[paddress], vaddress, paddress);
        }
        return;
    }
    else if (pte == -2){
        printf("Error: Segmentation fault! Go get a page! \n");
        return;
    }

}

int main(){
    // list of instructions: 1=map, 2=store, 3=load
    int instruction = 0;
    char str[16];
    unsigned char pid,vaddress,value;
    for (int i = 0; i < PAGE_NUM; i++){
        hp[i].location = -1;
        freelist[i] = 1;
        pages[i] = -1;
    }
    rr=0;
    while(1){
        printf("Instruction?: ");
        fgets(str, 16, stdin);
        char *token = strtok(str, ",");
        char *token_array[4];
        int i = 0;
        if(str==NULL || str == "\n"){
            printf("Please provide an input!\n");
            return 0;
        }
        // put each part into an array
        while (token != NULL){
            token_array[i++] = token;
            token = strtok (NULL, ",");
        }
        pid = atoi(token_array[0]);
        // check if pid is in the range of [0, 3]
        if (pid < 0 || pid > 3){
            printf("Wrong PID!\n");
            return -1;
        }
        // look at the instruction of each tuple
        if (strcmp(token_array[1], "map") == 0){    
            instruction = 1;
        } else if (strcmp(token_array[1], "store") == 0){
            instruction = 2;
        } else if (strcmp(token_array[1], "load") == 0){
            instruction = 3;
        } else {
            printf("Wrong instruction!\n");
            return -1;
        }
        vaddress = atoi(token_array[2]);
        if (vaddress < 0 || vaddress > 63){
            printf("Wrong virtual address!\n");
            return -1;
        }
        value = atoi(token_array[3]);
        if (value < 0|| value > 256){
            printf("Wrong value!\n");
            return -1;
        }

        switch(instruction){
            case 1:
                map(pid, vaddress, value);
                break;
            case 2:
                store(pid, vaddress, value);
                break;
            case 3:
                load(pid, vaddress, value);
                break;
        }
    }
    return 0;
}