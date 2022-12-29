#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct job{
	int id;
	int length;
	int waitingTime;
	int turnaroundTime;
	int responseTime;
	int timeLeft;
	struct job *next;
} job;

void fifo(job* headJob);
void sjf(job* headJob);
void roundRobin(job* headJob, int timeSlice);
void policyAnalysis(job* headJob);

int main(int argc, char **argv){

	fflush(stdout);
	int timeSlice = atoi(argv[3]);
	char buffer[10];
	int lengthOfJob = atoi(buffer);
	FILE *file = fopen(argv[2], "r");
	//number of lines in workload file
	int numberOfLines = 0;
	job *headJob = malloc(sizeof(job));
	job *currentJob = headJob;



	while(fgets(buffer, 10, file)){
		int lengthOfJob = atoi(buffer);
		if(numberOfLines == 0){
			headJob->id = numberOfLines;
			headJob->length = lengthOfJob;
			headJob->timeLeft = lengthOfJob;
		}
		else{
			//allocating space for next job
			currentJob->next = malloc(sizeof(job));
			currentJob = currentJob->next;
			currentJob->id = numberOfLines;
			currentJob->length = lengthOfJob;
			currentJob->timeLeft=lengthOfJob;
		}
		numberOfLines++;
	}
	if(strcmp(argv[1], "FIFO") == 0){
		printf("Execution trace with FIFO:\n");
		fifo(headJob);
	}
	else if(strcmp(argv[1], "SJF")==0){
		printf("Execution trace with SJF:\n");
		sjf(headJob);
	}
	else if(strcmp(argv[1], "RR")==0){
		printf("Execution trace with RR:\n");
		roundRobin(headJob, timeSlice);
	}
	else {
		printf("Improper job algorithm input\n");
	}
	
//garbage collection
	currentJob = headJob;
	while(currentJob!=NULL){
		job* thisJob = currentJob;
		currentJob = currentJob->next;
		free(thisJob);	
	}
	return 0;
}

//fifo
void fifo(job* headJob){

	job* currentJob = headJob;
	int timeElapsed = 0;

	while(currentJob != NULL){

		printf("Job %d ran for: %d\n", currentJob->id, currentJob->length);
		
		currentJob->responseTime = timeElapsed;
		currentJob->waitingTime = timeElapsed;
		timeElapsed+=currentJob-> length;
		currentJob->turnaroundTime = timeElapsed;
		currentJob=currentJob-> next;
	}
	printf("End of execution with FIFO.\n");
	printf("Begin analyzing wiht FIFO:\n");
	policyAnalysis(headJob);
	printf("End analyzing FIFO.\n");
}

//sjf
void sjf(job* head){

	job *sortedJob = NULL;
	int lengthOfJob = 0;
	job* currentJob = head;
	while(currentJob != NULL){
		job* nextJob = currentJob->next;
		
		if(sortedJob == NULL || sortedJob-> length >= currentJob->length) {
			currentJob-> next = sortedJob;
			sortedJob = currentJob;
		}
		else{
			job* sortedCurrentJob = sortedJob;
			while(sortedCurrentJob->next !=NULL && sortedCurrentJob->next->length <= currentJob->length){
				sortedCurrentJob = sortedCurrentJob->next;
			}
			currentJob->next = sortedCurrentJob-> next;
			sortedCurrentJob-> next = currentJob;
		}
		currentJob=nextJob;
	}
	currentJob = sortedJob;
	int elapsed = 0;
	while(currentJob!= NULL){
		printf("Job %d ran for: %d\n", currentJob->id, currentJob->length);
		currentJob->responseTime = elapsed;
		currentJob->waitingTime = elapsed;
		elapsed+= currentJob-> length;
		currentJob->turnaroundTime = elapsed;
		currentJob = currentJob-> next;
	}
	printf("End of execution with SJF.\n");
	printf("Begin analyzing SJF:\n");
	policyAnalysis(sortedJob);
	printf("End analyzing SJF.\n");

}

//round robin
void roundRobin(job* head, int timeSlice){
	
	int terminate = 0;
	int timeElapsed = 0;
	while(terminate == 0){
		job* currentJob = head;
		terminate =1;
		while(currentJob != NULL){
			if(currentJob->timeLeft != 0 && (currentJob->timeLeft == currentJob->length)){
				currentJob->responseTime = timeElapsed;
			}
			if(currentJob->timeLeft==0){
			}
			else if(currentJob->timeLeft > timeSlice){
				printf("Job %d ran for: %d\n", currentJob->id, timeSlice);
				currentJob->timeLeft-=timeSlice;
				timeElapsed +=timeSlice;
				terminate = 0;
			} 
			else{

				timeElapsed+=currentJob->timeLeft;
				printf("Job %d ran for: %d\n", currentJob->id, currentJob->timeLeft);
				currentJob->timeLeft = 0;
				currentJob-> turnaroundTime = timeElapsed;
				currentJob->waitingTime = timeElapsed -currentJob->length;				
			}
			currentJob = currentJob->next;
		}
	}
	printf("End of execution with RR.\n");
	printf("Begin analyzing of RR:\n");
	policyAnalysis(head);
	printf("End analyzing RR.\n");
}

//outputting data
void policyAnalysis(job* headJob){

	job* currentJob = headJob;
	double responseTime, turnaroundTime, waitTime, total = 0;

	while(currentJob!=NULL){
		total++;
		printf("Job %d -- Response time: %d Turnaround: %d Wait: %d\n", currentJob->id, currentJob->responseTime, currentJob->turnaroundTime, currentJob->waitingTime);
		responseTime+=currentJob->responseTime;
		turnaroundTime+=currentJob->turnaroundTime;
		waitTime+=currentJob->waitingTime;
		currentJob = currentJob->next;
	}
	printf("Average -- Response: %.2f Turnaround %.2f Wait %.2f\n", responseTime/total, turnaroundTime/total, waitTime/total); 
}