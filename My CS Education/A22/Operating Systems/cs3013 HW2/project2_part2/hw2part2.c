#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/time.h>
#include <pthread.h>
#include <semaphore.h>
#include <stdbool.h>

#define RUNWAY_SEGMENTS 6
#define BUFFERSIZE 1000
#define NUM_SMALL_PLANES 30
#define NUM_LARGE_PLANES 15
#define TOTAL_NUM_PLANES 45

typedef struct runwaySegment
{
	int runwaySegment; // runway number
} runwaySegment;

typedef struct plane
{
	int planeID;   // planeID
	char *state;	   // state of plane
	int planeSize; // size of plane (0 = small, 1 = large)
} plane;

// put each set of states in an array and then call a random numer
// rand()%(numStates) to get its random starting state
char *states[6] = {"idle at the terminal", "awaiting takeoff", "taking off", "flying", "awaiting landing", "landing"};

int allRunwaySegments[] = {1, 2, 3, 4, 5, 6};

int smallPlaneRunwayCombinations[6][2] = {{1, 4}, {4, 6}, {5, 3}, {3, 2}, {1, 2}, {3, 4}};
int largePlaneRunWayCombination[2][3] = {{5, 3, 2}, {1, 4, 6}};

void initPlane(plane planes[], int numberPlanes);
void createPlane(int id, bool size, plane* plane);
void* planeLifeCycle(void *ptr);
void idleAtTerminal(plane aPlane);
void awaitingTakeoff(plane aPlane, int* takeOffPath);
void takingOff(plane aPlane, int *path);
void flying(plane aPlane);
void awaitingLanding(plane aPlane, int* landingPath);
void landing(plane aPlane, int* path);
void selectRandomRunwayPath(int size, int* path);
double getCurrTime(int startingTime);

runwaySegment *runwaySegments[RUNWAY_SEGMENTS];
// runwaySegment consecutiveRunwaySegmentPairs[];
plane planes[TOTAL_NUM_PLANES];
sem_t semaphores[RUNWAY_SEGMENTS];

// keep track of plane numbers in array
// binary semphaore, only one person can hold one at a time, mutual exclusion of data region
// need this to update the array of occupied runways
// wake up people waiting on the runway
// thread wake up - condition variable
// awaiting on region 5 and then sleep

// dont keep grabbing at spots otherwise youre going to run out of spots
// and everyone will get stuck

// bool runwayOccupency[] = {false, false, false, false, false, false}; //keeping track of runway sections occupations

// account for airplane size corresponding to how many segments they take up
// large plane has to use 3 runway segments
// small plane has to use 2 runway segments

// they must travel in a straight line

//# planes = # threads generated, so generate 30+15 threads for small and large planes
// threads run continuously
// threads can only be in  idle at the terminal, awaiting
// takeoff, taking off, flying, awaiting landing, and landing states
// when landing they go back to idle at terminal state

int main(int argc, char *argv[])
{

	// read in seed for random number
	FILE *file = fopen("seed.txt", "r");
	char buffer[BUFFERSIZE];
	fgets(buffer, BUFFERSIZE, file);
	int seed = atoi(buffer);
	srand((unsigned)seed);

	pthread_t threads[TOTAL_NUM_PLANES];

	// giving each runway segment a semaphore
	for (int i = 0; i < RUNWAY_SEGMENTS; i++)
	{
		// semaphore corresponding to runway segment
		// middle parameter indicating that the semaphore is shared between the thread
		// giving an initial value of 1
		sem_init(&semaphores[i], 0, 1);
		//printf("initializing semaphore %d\n", i);
	}
	//printf("all semaphores initialized\n");
	//counter to give an accurate plane ID based on the order in which
	//they were created
	int counterPlaneID = 0;
	//creating all small planes
	for (int i = 0; i < NUM_SMALL_PLANES; i++)
	{
		//allocate memory for each plane
		plane* planePTR = (plane*)malloc(1*sizeof(plane));
		//create plane helper function
		createPlane(counterPlaneID, 0, planePTR);
		//printf("creating plane small plane %d\n", planes[i].planeID);
		//creating the plane and giving it memory
		planes[counterPlaneID] = *planePTR;
		//incrementing the counter for correct planeID
		counterPlaneID++;
	}
	//creating all large planes
	for (int i = 0; i < NUM_LARGE_PLANES; i++)
	{
		plane* planePTR = (plane*)malloc(1*sizeof(plane));
		createPlane(counterPlaneID, 1, planePTR);
		//printf("creating large plane %d\n", planes[i].planeID);
		planes[counterPlaneID] = *planePTR;
		counterPlaneID++;
	}
	//creating threads for all planes
	for (int i = 0; i < TOTAL_NUM_PLANES; i++)
	{
		//passing in the thread array, what function the thread is going to
		//run and the plane array
		pthread_create(&threads[i], NULL, planeLifeCycle, &planes[i]);
		//printf("creating thread # %d\n", i);
	}
	// if this is inside the loop above, then it is going to create and the keep waiting
	for (int i = 0; i < TOTAL_NUM_PLANES; i++)
	{
		pthread_join(threads[i], NULL);
		//printf("pthread_join %d\n", i);
	}
}

//creates the plane and gives it its attributes
void createPlane(int id, bool size, plane* aPlane)
{

	aPlane->planeID = id;
	aPlane->planeSize = size;
	aPlane->state = states[0];

}

//where the magic happens
void* planeLifeCycle(void *ptr)
{

	plane thePlane = *(plane *)ptr;

//for time purposes
	// struct timespec s_time;
	// double time_elapsed;
	// clock_gettime(CLOCK_MONOTONIC, &s_time);

//allocating space for both paths
	int* takeOffPath = (int*)malloc(3*sizeof(int));
	int *landingPath = (int*)malloc(3*sizeof(int));

	// first phase
	if (strcmp(thePlane.state, "idle at the terminal"))
	{
		idleAtTerminal(thePlane);
	}
	//second phase
	if (strcmp(thePlane.state, "awaiting takeoff"))
	{
		awaitingTakeoff(thePlane, takeOffPath);
	}
	//third phase
	if (strcmp(thePlane.state, "taking off"))
	{
		takingOff(thePlane, takeOffPath);
		free(takeOffPath);
	}
	//forth phase
	if (strcmp(thePlane.state, "flying"))
	{
		flying(thePlane);
	}
	//fifth phase
	if (strcmp(thePlane.state, "awaiting landing"))
	{
		awaitingLanding(thePlane, landingPath);
	}
	//sixth phase
	if (strcmp(thePlane.state, "landing"))
	{
		landing(thePlane, landingPath);
		free(landingPath);
	}

	return NULL; 
}

void idleAtTerminal(plane aPlane)
{

	// gettingCurrentTime
	//double currentTime = getCurrTime(clock_gettime(CLOCK_MONOTONIC, &s_time), s_time);

	// get random amount of sleep time
	int randSleepTime = rand() % 100;

	// announce their presence
	printf("I am plane thread %d %d.\n I am in the %s state and "
		   " I am going to sleep for %d seconds.\n",
		   aPlane.planeID, aPlane.planeSize, aPlane.state, randSleepTime);
	// go to sleep for rand amount of time
	usleep(randSleepTime);
	// setting the state to awaiting takeoff after being idle at terminal
	// and waiting rand amount of time

	// entering state 2
	aPlane.state = states[1];
	printf("I am plane thread %d %d.\n I had a nice nap and am now entering"
		   " the %s state.\n",
		  aPlane.planeID, aPlane.planeSize, aPlane.state);
}

void awaitingTakeoff(plane aPlane, int* takeOffPath)
{
	// gettingCurrentTime
	//double currentTime = getCurrTime(clock_gettime(CLOCK_MONOTONIC, &s_time), s_time);

	// decide which runway path its going to take using enum

	selectRandomRunwayPath(aPlane.planeSize, takeOffPath);
	// create condition variable and check to see if runway avaiable

	if (aPlane.planeSize == 0)
	{
		printf("I am plane %d %d and I want to eventually take off on runways %d and %d\n", aPlane.planeID, aPlane.planeSize, takeOffPath[0], takeOffPath[1]);
	}

	if (aPlane.planeSize == 1)
	{
		printf("I am plane %d %d and I want to eventually take off on runways %d, %d, and %d.\n", aPlane.planeID, aPlane.planeSize, takeOffPath[0], takeOffPath[1], takeOffPath[2]);
	}

	if (aPlane.planeSize == 0)
	{
		//intend to use this semaphore
		sem_wait(&semaphores[takeOffPath[0] - 1]);
		printf("I am plane %d %d, waiting on runway %d.\n", aPlane.planeID, aPlane.planeSize, takeOffPath[0]);
		//if block then you're waiting
		//claimed this spot
		sem_wait(&semaphores[takeOffPath[1] - 1]);
		printf("I am plane %d %d, waiting on runway %d.\n", aPlane.planeID, aPlane.planeSize, takeOffPath[1]);
	}
	if (aPlane.planeSize == 1)
	{
		sem_wait(&semaphores[takeOffPath[0] - 1]);
		printf("I am plane %d %d, waiting on runway %d.\n", aPlane.planeID, aPlane.planeSize, takeOffPath[0]);
		sem_wait(&semaphores[takeOffPath[1] - 1]);
		printf("I am plane %d %d, waiting on runway %d.\n", aPlane.planeID, aPlane.planeSize, takeOffPath[1]);
		sem_wait(&semaphores[takeOffPath[2] - 1]);
		printf("I am plane %d %d, waiting on runway %d.\n", aPlane.planeID, aPlane.planeSize, takeOffPath[2]);
	}
//i have waited on this semaphore

	// entering state 3
	aPlane.state = states[2];
	printf("I am plane thread %d %d. Now entering the %s phase."
		   " Ready for takeoff.\n",
		   aPlane.planeID, aPlane.planeSize, aPlane.state);
}

void selectRandomRunwayPath(int size, int* path)
{

	if (size == 0)
	{
		// small plane
		int randCombinationSmallRunways = rand() % 6;
		int *smallRunway = smallPlaneRunwayCombinations[randCombinationSmallRunways];
		// flip a coin to see if reverse the array or not
		int reverse = rand() % 2; // returns 0 then don't reverse, else then reverse
		if (reverse == 1)
		{
			// reverse the array
			//you need to alloc memory here to not rewrite memory until i free it
			path[0] = smallRunway[1];
			path[1] = smallRunway[0];
		}
		else
		{
			path[0] = smallRunway[0];
			path[1] = smallRunway[1];
		}
	}
	if (size == 1)
	{
		// large plane
		int randCombinationLargeRunways = rand() % 2;
		int *largeRunway = largePlaneRunWayCombination[randCombinationLargeRunways];
		// flip a coin to see if reverse the array or not
		int reverse = rand() % 2;
		if (reverse == 1)
		{
			// reverse the array
			path[0] = largeRunway[2];
			path[1] = largeRunway[1];
			path[2] = largeRunway[0];
		}
		else
		{
			path[0] = largeRunway[0];
			path[1] = largeRunway[1];
			path[2] = largeRunway[2];;
		}
	}
}

void takingOff(plane aPlane, int *path)
{

	// gettingCurrentTime
	//double currentTime = getCurrTime(clock_gettime(CLOCK_MONOTONIC, &s_time), s_time);

	// if small plane
	if (aPlane.planeSize == 0)
	{
		// sleep everytime it enters a new region
		int randSleepTimeRegion1 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while taking off from runway # %d\n",
			   aPlane.planeID, aPlane.planeSize, randSleepTimeRegion1, path[0]);
		usleep(randSleepTimeRegion1);
		printf("I am plane thread %d %d. I took off from runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[0]);
		sem_post(&semaphores[path[0] - 1]);
		printf("I am plane thread %d %d. I took off from runway %d and RELEASED SEMAPHORE \n",
			   aPlane.planeID, aPlane.planeSize, path[0]);
		
		int randSleepTimeRegion2 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while taking off from runway # %d\n",
			    aPlane.planeID, aPlane.planeSize, randSleepTimeRegion2, path[1]);
		usleep(randSleepTimeRegion2);
		printf("I am plane thread %d %d. I took off from runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);
		sem_post(&semaphores[path[1] - 1]);
		printf("I am plane thread %d %d. I took off from runway %d and released my semaphore \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);
	}

	// if large plane
	if (aPlane.planeSize == 1)
	{

		// gettingCurrentTime
		//double currentTime = getCurrTime(clock_gettime(CLOCK_MONOTONIC, &s_time), s_time);

		// sleep everytime it enters a new region
		int randSleepTimeRegion1 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while taking off from runway # %d\n",
			   aPlane.planeID, aPlane.planeSize, randSleepTimeRegion1, path[0]);
		usleep(randSleepTimeRegion1);
		printf("I am plane thread %d %d. I took off from runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[0]);
		sem_post(&semaphores[path[0] - 1]);
		printf("I am plane thread %d %d. I took off from runway %d and RELEASED SEMAPHORE \n",
			   aPlane.planeID, aPlane.planeSize, path[0]);

		int randSleepTimeRegion2 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while taking off from runway # %d\n",
			   aPlane.planeID, aPlane.planeSize, randSleepTimeRegion2, path[1]);
		usleep(randSleepTimeRegion2);
		printf("I am plane thread %d %d. I took off from runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);
		sem_post(&semaphores[path[1] - 1]);
		printf("I am plane thread %d %d. I took off from runway %d and RELEASED SEMAPHORE \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);

		int randSleepTimeRegion3 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while taking off from runway # %d\n",
			    aPlane.planeID, aPlane.planeSize, randSleepTimeRegion3, path[2]);
		usleep(randSleepTimeRegion3);
		printf("I am plane thread %d %d. I took off from runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);
		sem_post(&semaphores[path[2] - 1]);
		printf("I am plane thread %d %d. I took off from runway %d and RELEASED SEMAPHORE \n",
			   aPlane.planeID, aPlane.planeSize, path[2]);
	}

	aPlane.state = states[3];
	printf("I am plane thread %d %d. Take off sucessful. Now entering the %s state.\n", aPlane.planeID, aPlane.planeSize, aPlane.state);

	return;
}

void flying(plane aPlane)
{
	// gettingCurrentTime
	//double currentTime = getCurrTime(clock_gettime(CLOCK_MONOTONIC, &s_time), s_time);

	// randsleep for flying state
	int randSleepAutopilot = rand() % 100;
	printf("I am plane thread %d %d. We've entered the %s state."
		   " Time to put on autopilot and take a nap for %d seconds\n",
		   aPlane.planeID, aPlane.planeSize, aPlane.state, randSleepAutopilot);
	usleep(randSleepAutopilot);

	// entering state 5
	aPlane.state = states[4];
	printf("I am plane thread %d %d. Entering the %s state."
		   " Almost to destination.\n",
		    aPlane.planeID, aPlane.planeSize, aPlane.state);
	return;
}

void awaitingLanding(plane aPlane, int* landingPath)
{

	//double currentTime = getCurrTime(clock_gettime(CLOCK_MONOTONIC, &s_time), s_time);

	selectRandomRunwayPath(aPlane.planeSize, landingPath);

	if (aPlane.planeSize == 0)
	{
		printf("I am plane %d %d and I want to eventually land on runways %d and %d\n", aPlane.planeID, aPlane.planeSize, landingPath[0], landingPath[1]);
	}

	if (aPlane.planeSize == 1)
	{
		printf("I am plane %d %d and I want to eventually land on runways %d, %d, and %d.\n", aPlane.planeID, aPlane.planeSize, landingPath[0], landingPath[1], landingPath[2]);
	}

	if (aPlane.planeSize == 0)
	{
		sem_wait(&semaphores[landingPath[0] - 1]);
		printf("I am plane %d %d, waiting on runway %d for landing.\n", aPlane.planeID, aPlane.planeSize, landingPath[0]);
		sem_wait(&semaphores[landingPath[1] - 1]);
		printf("I am plane %d %d, waiting on runway %d for landing.\n", aPlane.planeID, aPlane.planeSize, landingPath[1]);
	}
	if (aPlane.planeSize == 1)
	{
		sem_wait(&semaphores[landingPath[0] - 1]);
		printf("I am plane %d %d, waiting on runway %d for landing.\n", aPlane.planeID, aPlane.planeSize, landingPath[0]);
		sem_wait(&semaphores[landingPath[1] - 1]);
		printf("I am plane %d %d, waiting on runway %d for landing.\n", aPlane.planeID, aPlane.planeSize, landingPath[1]);
		sem_wait(&semaphores[landingPath[2] - 1]);
		printf("I am plane %d %d, waiting on runway %d for landing.\n", aPlane.planeID, aPlane.planeSize, landingPath[2]);
	}

	// entering state 6
	aPlane.state = states[5];
	printf("I am plane thread %d %d. Entering the %s state."
		   " Ready for landing.\n",
		  aPlane.planeID, aPlane.planeSize, aPlane.state);
}

void landing(plane aPlane, int* path)
{
	// gettingCurrentTime
	//double currentTime = getCurrTime(clock_gettime(CLOCK_MONOTONIC, &s_time), s_time);

	// if small plane
	if (aPlane.planeSize == 0)
	{
		// sleep everytime it enters a new region
		int randSleepTimeRegion1 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while landing on runway # %d\n",
			    aPlane.planeID, aPlane.planeSize, randSleepTimeRegion1, path[0]);
		usleep(randSleepTimeRegion1);
		printf("I am plane thread %d %d. I landed on runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[0]);
		sem_post(&semaphores[path[0] - 1]);
		printf("I am plane thread %d %d. I landed on runway %d. SEMAPHORE RELEASED. \n",
			   aPlane.planeID, aPlane.planeSize, path[0]);

		int randSleepTimeRegion2 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " landing on runway # %d\n",
			    aPlane.planeID, aPlane.planeSize, randSleepTimeRegion2, path[1]);
		usleep(randSleepTimeRegion2);
		printf("I am plane thread %d %d. I landed on runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);
		sem_post(&semaphores[path[1] - 1]);
		printf("I am plane thread %d %d. I landed on runway %d. SEMAPHORE RELEASED. \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);
	}

	// if large plane
	if (aPlane.planeSize == 1)
	{

		// gettingCurrentTime
		//double currentTime = getCurrTime(clock_gettime(CLOCK_MONOTONIC, &s_time), s_time);

		// sleep everytime it enters a new region
		int randSleepTimeRegion1 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while landing on runway # %d\n",
			    aPlane.planeID, aPlane.planeSize, randSleepTimeRegion1, path[0]);
		usleep(randSleepTimeRegion1);
		printf("I am plane thread %d %d. I landed on runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[0]);
		sem_post(&semaphores[path[0] - 1]);
		printf("I am plane thread %d %d. I landed on runway %d. SEMAPHORE RELEASED. \n",
			   aPlane.planeID, aPlane.planeSize, path[0]);

		int randSleepTimeRegion2 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while landing on runway # %d\n",
			    aPlane.planeID, aPlane.planeSize, randSleepTimeRegion2, path[1]);
		usleep(randSleepTimeRegion2);
		printf("I am plane thread %d %d. I landed on runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);
		sem_post(&semaphores[path[1] - 1]);
		printf("I am plane thread %d %d. I landed on runway %d. SEMAPHORE RELEASED. \n",
			   aPlane.planeID, aPlane.planeSize, path[1]);

		int randSleepTimeRegion3 = rand() % 100;
		printf("I am plane thread %d %d. I am sleeping for %d seconds"
			   " while landing on runway # %d\n",
			   aPlane.planeID, aPlane.planeSize, randSleepTimeRegion3, path[2]);
		usleep(randSleepTimeRegion3);
		printf("I am plane thread %d %d. I landed on runway %d. Releasing my semaphore now. \n",
			   aPlane.planeID, aPlane.planeSize, path[2]);
		sem_post(&semaphores[path[2] - 1]);
		printf("I am plane thread %d %d. I landed on runway %d. SEMAPHORE RELEASED. \n",
			   aPlane.planeID, aPlane.planeSize, path[2]);
	}

	// back to state 0
	aPlane.state = states[0];
	printf("I am plane thread %d %d and have succesfully landed!!!"
		   " Taxing back to the terminal and entering %s state.\n",
		    aPlane.planeID, aPlane.planeSize, aPlane.state);
	return;
}

// double getCurrTime(int startingTime, struct timespec s_time)
// {
// 	double time_elapsed;
// 	struct timespec curr_time;
// 	clock_gettime(CLOCK_MONOTONIC, &curr_time);
// 	time_elapsed = (double)curr_time.tv_sec - (double)s_time.tv_sec;
// 	double n_time_elapsed = (double)curr_time.tv_nsec - (double)s_time.tv_nsec;
// 	n_time_elapsed = n_time_elapsed / 1e9;
// 	time_elapsed = time_elapsed + n_time_elapsed;
// 	return time_elapsed;
// }
