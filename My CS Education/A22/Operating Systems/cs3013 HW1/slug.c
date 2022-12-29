#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

//main function for slug that moves around the file system and creates child processes to execute commands
//also makes use of command line arguments through argv and argc
int main(int argc, char *argv[]){

    //if number of arguments are too little (less than 2)
    if (argc < 2){
        fprintf(stderr, "No argument has been passed in. \n");
        return -1;
    }
    int num = atoi(argv[1]);
    if (num < 1 || num > 4){
        fprintf(stderr, "Wrong argument!\n");
        return -1;
    }
    
    //storing the seed text files in an array containing various seed values
    //for easy access
    char *file_names[4] = {"seed_slug_1.txt", "seed_slug_2.txt", "seed_slug_3.txt", "seed_slug_4.txt"};

    FILE *file = fopen(file_names[num-1], "r");
    char buffer[1000];
    fgets(buffer, 1000, file);

    int seed = atoi(buffer);
    srand((unsigned)seed);

    int pid = getpid();
    printf("[Slug PID: %d]: Read seed value: %d\n\n", pid, seed);
    printf("[Slug PID: %d]: Read seed value (converted to integer): %d\n", pid, seed);

    int num_seconds = 4 + rand()%6;
    int coinflip = rand() % 2;
    printf("[Slug PID: %d]: Delay time is %d seconds. Coin flip: %d\n", pid, num_seconds, coinflip);
    printf("[Slug PID: %d]: I'll get the job done. Eventually...\n", pid);
    sleep(num_seconds);
    
    //char* int array for acessing various commands
    char* systemCommand_one[] = {"last", "-i", "-x", NULL};
    char* systemCommand_two[] = {"id", "--group", NULL};

    if(coinflip == 0){
        printf("[Slug PID: %d]: Break time is over! I am running the 'last -i -x' command.\n", pid);
        execvp("last", systemCommand_one);
        exit(0);
    } else{
        printf("[Slug PID: %d]: Break time is over! I am running the 'id -group' command.\n", pid);
        execvp("id", systemCommand_two);
        exit(0);
    }
    return 0;
}
