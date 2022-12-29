#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>

int main(){
    //timespec types for storing the time before running the processes and getting the time after all processes are ran
    struct timespec s_time, e_time;
    double time_elapsed;
    //gets the starting time before running any of the processes
    clock_gettime(CLOCK_MONOTONIC, &s_time);
    int rc;
    //count for identifying child processes
    int count = 0;
    //array for getting pid of children
    int list_cpid[4];
    int status;
    for (int i = 0; i < 4; i++){
        rc = fork();
        if (rc < 0){
            fprintf(stderr, "fork failed\n");
            exit(1);
        } else if (rc == 0){
            rc = getpid();
            printf("    [Child, PID, %d]: Executing './slug %d' command...\n", rc, i+1);
            //arrays for executing slug.c and which random value to pass in as argument
            char *num_str[4] = {"1", "2", "3", "4"};
            char* systemCommand[] = {"./slug", num_str[i], NULL};
            execvp("./slug", systemCommand);
            exit(0);
        } else{
            count++;
            //array for identifying child pid
            list_cpid[i] = rc;
            printf("[Parent]: I forked off a child %d.\n", rc); 
        }
    }

    while (count > 0){
        // if check = 0 -> on going
        // else a child has returned
        int check = waitpid(-1, &status, WNOHANG);
        int cur_rc = 0;
        if (check == -1){
            // error handeling
            fprintf(stderr, "Child process error.\n");
            exit(1);
        } else if (check == 0){
            usleep(200000);
            printf("The race is ongoing. The following children are still racing ");
            for (int i = 0; i < count; i++){
                printf("%d ", list_cpid[i]);
            }
            printf("\n");
        } else {  
            //changing the order within array to see which children are still racing
            cur_rc = check;
            int ph = 0;
            for (int i = 0; i < 4; i++){
                int cur = list_cpid[i];
                if (cur != cur_rc){
                    list_cpid[ph] = list_cpid[i];
                    ph++;
                }
            }
            count = count-1;
            clock_gettime(CLOCK_MONOTONIC, &e_time);
            //getting time and casting it to double to get more precision
            //then converting from nsec to sec
            time_elapsed = (double) e_time.tv_sec - (double) s_time.tv_sec;
            double n_time_elapsed = (double)e_time.tv_nsec - (double)s_time.tv_nsec;
            n_time_elapsed = n_time_elapsed / 1e9;
            time_elapsed = time_elapsed + n_time_elapsed;
            printf("Child %d has crossed the finish line! It took %lf seconds\n", check, time_elapsed);  
        }       
    }
    printf("The race is over! It took %lf seconds\n", time_elapsed);
    return 0;
}
