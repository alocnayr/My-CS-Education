#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

//helper function for getting the directory info
void get_dir_info(){
    int exit_code = 0;
    char res[1000];
    char *r = getcwd(res, 10000);
    int status;
    int rc = fork();
    if (rc < 0){
        fprintf(stderr, "fork failed\n");
        exit(1);
    }else if (rc == 0){
        rc = getpid();
        // loading system command
        char* systemCommand[] = {"ls", "-tr", NULL};
        printf("    [Child, PID: %d]: Executing 'ls -tr' command...\n'", rc);
        execvp("ls", systemCommand);
        printf("%s\n", r);
        exit(0);
    } else {
        printf("[Parent]: I am waiting for PID %d to finish.\n", rc);
        int rc_wait = waitpid(rc, &status, WUNTRACED);
        printf("[Parent]: Child %d finished with status code %d. Onward!\n", rc, exit_code);
    }
}

int main(){
    FILE *file = fopen("seed.txt", "r");
    char buffer[1000];
    fgets(buffer, 1000, file);

    int seed = atoi(buffer);
    srand((unsigned)seed);

    //all gives directories stored in array
    char *dir_name[6];
    dir_name[0] = "/home";
    dir_name[1] = "/proc";
    dir_name[2] = "/proc/sys";
    dir_name[3] = "/usr";
    dir_name[4] = "/boot";
    dir_name[5] = "/sbin";
    
    printf("Read seed value (converted to integer): %d\n", seed);
    printf("It's time to see the world/file system!\n");

    for (int i = 0; i < 5; i++){
        char *random;
        //accessing the array dir_name between index 0-5
        random = dir_name[rand() % 6];
        int num;
        num = chdir(random);
        printf("Selection #%d: %s [SUCESS]\n", i+1, random);
        if (num < 0){
            fprintf(stderr, "Failed to change current working directory.\n");
            exit(1);
        } else {
            printf("Current reported directory: %s\n", random);
            char res[100];
            char *r = getcwd(res, 1000);
            if(r == NULL){
                printf("Obtaining current directory failed.\n");
                exit(1);
            } else {
                get_dir_info();
            }
                
        } 
    }
}
    
