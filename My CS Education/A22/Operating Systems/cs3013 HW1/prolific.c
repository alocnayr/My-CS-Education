#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

//main function that uses a parent process to spawn child processes
//they introduce themselves (displays their pid), waits and then exits
int main(){
    //reading the seed value
    FILE *file = fopen("seed.txt", "r");
    char buffer[1000];
    fgets(buffer, 1000, file);

    int seed = atoi(buffer);
    srand((unsigned)seed);
    //get number between 5 and 11
    int num_children = 5 + (rand()%7);
    
    printf("Read seed value (converted to integer): %d\n", seed);
    printf("Random child Count: %d\n", num_children);
    printf("I'm feeling prolific!\n");

    //generate array of random numbers
    int arr[num_children];
    for (int i = 0; i < num_children; i++){
        arr[i] = rand();
    }
    //counter for keeping track position of array
    int counter = 0;
    int exit_code = 0;
    //random value that is used for producing exit code and wait time for child process
    int random_val = 0;
    //time that a child process should wait
    int wait_time = 0;
    int status;
    int exit_status;
    //generate a specific number of children with fork()
    //rc<0 indicates a failure in creating a child process
    //rc == 0 indicates a successful child process creation
    for (int i=0; i < num_children; i++){
        int rc = fork();
        if (rc < 0){
            fprintf(stderr, "fork failed\n");
            exit(1);
        }else if (rc == 0){
            rc = getpid();
            random_val = arr[counter];
            exit_code = (random_val % 50) + 1;
            wait_time = (random_val % 3) + 1;
            printf("    [Child, PID: %d]: I am the child and I will wait %d seconds and exit with code %d\n", rc, wait_time, exit_code);
            sleep(wait_time);
            printf("    [Child, PID: %d]: Now exiting ...\n", rc);
            exit(exit_code);
        } else {
            printf("[Parent]: I am waiting for PID %d to finish.\n", rc);
            int rc_wait = waitpid(rc, &status, WUNTRACED);
            exit_status = WEXITSTATUS(status);
            printf("[Parent]: Child %d finished with status code %d. Onward!\n", rc, exit_status);
        }
        counter = counter + 1;
    }
    return 0;
}
