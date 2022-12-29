#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

//helper recursive function for forking and figuring out descendant count
void life(int count, int lifespan_count){
    if (count == lifespan_count){
        return;
    }
    int rc_i = fork();
    int status;
    if (rc_i < 0){
        fprintf(stderr, "fork failed\n");
        exit(1);
    } else if (rc_i == 0){
        rc_i = getpid();
        printf("    [Child, PID: %d]: I was called with descendant count=%d. I'll have %d descendant(s)\n", rc_i, lifespan_count-count, lifespan_count-count-1);
        count++;
        life(count, lifespan_count);
        exit(count);
    }else{
        int rc_p = getpid();
        printf("[Parent, PID: %d]: I am waiting for PID %d to finish.\n", rc_p, rc_i);
        int rc_wait = waitpid(rc_i, &status, 0);
        printf("[Parent, PID: %d]: Child %d finished with status code %d. It's now my turn to exit.\n", rc_i, rc_p, lifespan_count-count-1);
    }
    exit(0);
}

//main function that generates a random lifecount number and spawns child processes
//until that lifecount number gets down to 0
int main(){
//reading the seed value
    FILE *file = fopen("seed.txt", "r");
    char buffer[1000];
    fgets(buffer, 1000, file);

    int seed = atoi(buffer);
    srand((unsigned)seed);
    //get random number between 8 and 14
    int lifespan_count = 8 + (rand()%7);
    
    printf("Read seed value (converted to integer): %d\n", seed);
    printf("Random Descendant Count: %d\n", lifespan_count);
    printf("Time to meet the kids/grandkids/great grand kid/...\n");

    //counter for figuring out which child is which
    int count = 0;
    //generate a specific number of children with fork()
    int rc = fork();
    if (rc < 0){
        fprintf(stderr, "fork failed\n");
        exit(1);
    }else if (rc == 0){
        life(count, lifespan_count);
    } else {
        int rc_wait = wait(NULL);
    }

    return 0;
}
