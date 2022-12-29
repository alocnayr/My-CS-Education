// !!! how to make sure not deprive

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>

#define MAX_BASEBALL_PLAYER 36
#define MAX_FOOTBALL_PLAYER 44
#define MAX_SOCCER_PLAYER 44
#define MAX_NUM_PLAYER 124

// define player type struct
typedef enum{
    baseball,
    football,
    soccer
} player_type;

// define each player
typedef struct{
    // 0~35 = baseball player, 36~79 = football player, 80~123 = soccer player
    int player_id;
    player_type player_sport;
    int field_slot;
    // status of the player: 0 = not on field, 1 = on field
    int status;
} player_about;

// define park struct
typedef struct{
    pthread_mutex_t mutex;
    // which type of sport player is using the park: 0 = none, 1 = baseball, 2 = football, 3 = soccer
    int current_sport;
    // how many players are on the field now
    int current_num_player;
    // how many soccer player is in field now
    int current_soccer_player_num;
} park;

// declare park
park omniPark;

// declare locks and condition variables
pthread_mutex_t gmutex;
pthread_cond_t bcond, fcond, scond, cond;

// current ready player count
int baseball_player_count;
int football_player_count;
int soccer_player_count;
int wait_soccer_player_count;

// Array of pointers of who is waiting
player_about *baseball_player[18];
player_about *football_player[22];
player_about *soccer_player[22];

// initialize all players
player_about all_players[MAX_NUM_PLAYER];

// initialize the park
void init(park *omniPark){
    pthread_mutex_init(&omniPark->mutex, NULL);
    omniPark->current_sport = 0;
}

// let the players who are ready enter the field
void go_in(park *omniPark, player_about *p){
    player_about *player = (player_about*) p;
    // lock the field
    pthread_mutex_lock(&omniPark->mutex);
    // special case: soccer players are in field and other pairs join, cannot exceed 22 players
    if (omniPark->current_sport == 3 && player->player_sport == soccer && omniPark->current_soccer_player_num < 22) {
        omniPark->current_num_player += 2;
        omniPark->current_soccer_player_num += 2;
        printf("Park: new soccer players appeared!\n");
        printf("Park: current soccer player count: %d\n", omniPark->current_soccer_player_num);
    }
    //if no team is currently on field, switch to the new sport
    else if (omniPark->current_sport==0){
        if (player->player_sport == baseball){
            omniPark->current_sport = 1;
            omniPark->current_num_player += 18;
            printf("Park: Now playing to baseball!\n");
        } else if (player->player_sport == football){
            omniPark->current_sport = 2;
            omniPark->current_num_player += 22;
            printf("Park: Now playing to football!\n");
        } else {
            omniPark->current_sport = 3;
            omniPark->current_num_player += 2;
            omniPark->current_soccer_player_num += 2;
            printf("Park: Now playing to soccer!\n");
        }
    } else {
        // switch player on field, fail-safe system
        switch(player->player_sport){
            case baseball:
                omniPark->current_sport = 1;
                omniPark->current_num_player = 18;
            case football:
                omniPark->current_sport = 2;
                omniPark->current_num_player = 22;
            case soccer:
                omniPark->current_sport = 3;
                omniPark->current_num_player = 2;
                omniPark->current_soccer_player_num = 2;
        }
    }
    // unlock the field
    pthread_mutex_unlock(&omniPark->mutex);
}

// clear the park to let the next set of players enter
void get_out(park *omniPark){
    // lock the field
    pthread_mutex_lock(&omniPark->mutex);
    omniPark->current_num_player = 0;
    omniPark->current_soccer_player_num = 0;
    omniPark->current_sport = 0;
    printf("Park: A team has now left the field.\n");
    // unlock the field
    pthread_mutex_unlock(&omniPark->mutex);
}

// destroy the lock and end the park
void end(park *omniPark){
    pthread_mutex_destroy(&omniPark->mutex);
}

// imitating the playing process
void *player_init(void *player){
    // run each thread five times
    for (int i = 0; i < 5; i++){
        player_about *p = (player_about*) player;
        // time for players to emerge
        int sleeptime = 1+rand()%3;
        // time for a game
        int gametime = 7+rand()%5;
        sleep(sleeptime);
        printf("I'm player %d, I'm sleeping for %d seconds.\n", p->player_id, sleeptime);

        pthread_mutex_lock(&gmutex);
        // get players ready (not on field yet)
        if (p->player_sport == baseball){
            baseball_player[baseball_player_count] = p;
            baseball_player_count += 1;
            printf("Ready baseball player count %d\n", baseball_player_count);
            p->status = 0;
        } else if (p->player_sport == football){
            football_player[football_player_count] = p;
            football_player_count += 1;
            printf("Ready football player count %d\n", football_player_count);
            p->status = 0;
        } else {
            soccer_player[soccer_player_count] = p;
            soccer_player_count += 1;
            wait_soccer_player_count += 1;
            printf("Ready soccer player count %d\n", soccer_player_count);
            p->status = 0;
        }
        pthread_mutex_unlock(&gmutex);

        pthread_mutex_lock(&gmutex);
        // wait for other players to join
        if (p->player_sport == baseball){
            // once 18 baseball players are ready, wake them up
            while (baseball_player_count < 18){
                pthread_cond_wait(&bcond, &gmutex);
            }
            pthread_cond_broadcast(&bcond);
        } else if(p->player_sport == football){
            // once 22 football players are ready, wake them up
            while (football_player_count < 22){
                pthread_cond_wait(&fcond, &gmutex);
            }
            pthread_cond_broadcast(&fcond);
        } else{
            // once 2 baseball players are ready, wake the first one up
            while (wait_soccer_player_count < 2 || wait_soccer_player_count %2 == 1){
                pthread_cond_wait(&scond, &gmutex);
            }
            // make sure it does not exceed 22
            if (soccer_player_count <= 22){
                for (int j=soccer_player_count-2; j<soccer_player_count; j++){
                    player_about *cur_player = soccer_player[j];
                    cur_player->status = 1;
                    cur_player->field_slot = j+1;
                    printf("I'm player %d, I'm a soccer player. I'm going in field right now.\n", cur_player->player_id);
                }
            }
            pthread_cond_broadcast(&scond);
            wait_soccer_player_count = 0;
        }

        // check which team is ready; if both is ready, randomly pick a team to wake up
        switch (p->player_sport){
            case baseball:
                if (football_player_count == 22 && soccer_player_count >= 2){
                    // since it is easier for soccer player to form a team, give a greater chance for football player
                    int rand_num = rand()%3;
                    if (rand_num < 2){
                        pthread_cond_broadcast(&fcond);
                        for (int j = 0; j < 22; j++){
                            player_about *cur_player = football_player[j];
                            cur_player->status = 1;
                            cur_player->field_slot = j+1;
                            printf("I'm player %d, I'm a football player. I'm going in field right now.\n", cur_player->player_id);
                        }    
                    } else {
                        pthread_cond_broadcast(&scond);
                        for (int j = 0; j < soccer_player_count; j++){
                            player_about *cur_player = soccer_player[j];
                            cur_player->status = 1;
                            cur_player->field_slot = j+1;
                            printf("I'm player %d, I'm a soccer player. I'm going in field right now.\n", cur_player->player_id);
                        }      
                    }
                // if only football is ready, let football play
                } else if (football_player_count == 22){
                    pthread_cond_broadcast(&fcond);
                    for (int j = 0; j < 22; j++){
                        player_about *cur_player = football_player[j];
                        cur_player->status = 1;
                        cur_player->field_slot = j+1;
                        printf("I'm player %d, I'm a football player. I'm going in field right now.\n", cur_player->player_id);
                    } 
                // if only soccer is ready, let soccer play
                } else if (soccer_player_count >= 2){
                    pthread_cond_broadcast(&scond);
                        for (int j = 0; j < soccer_player_count; j++){
                            player_about *cur_player = soccer_player[j];
                            cur_player->status = 1;
                            cur_player->field_slot = j+1;
                            printf("I'm player %d, I'm a soccer player. I'm going in field right now.\n", cur_player->player_id);
                        } 
                } 
            case football:
                if (baseball_player_count == 22 && soccer_player_count >= 2){
                    // since it is easier for soccer player to form a team, give a greater chance for baseball player
                    int rand_num = rand()%3;
                    if (rand_num < 2){
                        pthread_cond_broadcast(&bcond);
                        for (int j = 0; j < 18; j++){
                            player_about *cur_player = baseball_player[j];
                            cur_player->status = 1;
                            cur_player->field_slot = j+1;
                            printf("I'm player %d, I'm a baseball player. I'm going in field right now.\n", cur_player->player_id);
                        }    
                    } else {
                        pthread_cond_broadcast(&scond);
                        for (int j = 0; j < soccer_player_count; j++){
                            player_about *cur_player = soccer_player[j];
                            cur_player->status = 1;
                            cur_player->field_slot = j+1;
                            printf("I'm player %d, I'm a soccer player. I'm going in field right now.\n", cur_player->player_id);
                        }      
                    }
                // if only baseball is ready, let baseball play
                } else if (baseball_player_count == 22){
                    pthread_cond_broadcast(&bcond);
                    for (int j = 0; j < 18; j++){
                        player_about *cur_player = baseball_player[j];
                        cur_player->status = 1;
                        cur_player->field_slot = j+1;
                        printf("I'm player %d, I'm a baseball player. I'm going in field right now.\n", cur_player->player_id);
                    } 
                // if only soccer is ready, let soccer play
                } else if (soccer_player_count >= 2){
                    pthread_cond_broadcast(&scond);
                    for (int j = 0; j < soccer_player_count; j++){
                        player_about *cur_player = soccer_player[j];
                        cur_player->status = 1;
                        cur_player->field_slot = j+1;
                        printf("I'm player %d, I'm a soccer player. I'm going in field right now.\n", cur_player->player_id);
                    } 
                } 
            case soccer:
                if (football_player_count == 22 && baseball_player_count == 18){
                    int rand_num = rand()%2;
                    if (rand_num < 1){
                        pthread_cond_broadcast(&bcond);
                        for (int j = 0; j < 18; j++){
                            player_about *cur_player = baseball_player[j];
                            cur_player->status = 1;
                            cur_player->field_slot = j+1;
                            printf("I'm player %d, I'm a baseball player. I'm going in field right now.\n", cur_player->player_id);
                        }    
                    } else {
                        pthread_cond_broadcast(&fcond);
                        for (int j = 0; j < 22; j++){
                            player_about *cur_player = football_player[j];
                            cur_player->status = 1;
                            cur_player->field_slot = j+1;
                            printf("I'm player %d, I'm a football player. I'm going in field right now.\n", cur_player->player_id);
                        }       
                    }
                // if only baseball is ready, let baseball play
                } else if (baseball_player_count == 18){
                    pthread_cond_broadcast(&bcond);
                    for (int j = 0; j < 18; j++){
                        player_about *cur_player = baseball_player[j];
                        cur_player->status = 1;
                        cur_player->field_slot = j+1;
                        printf("I'm player %d, I'm a baseball player. I'm going in field right now.\n", cur_player->player_id);
                    } 
                // if only football is ready, let football play
                } else if (football_player_count == 22){
                    pthread_cond_broadcast(&fcond);
                    for (int j = 0; j < 22; j++){
                        player_about *cur_player = football_player[j];
                        cur_player->status = 1;
                        cur_player->field_slot = j+1;
                        printf("I'm player %d, I'm a football player. I'm going in field right now.\n", cur_player->player_id);
                    } 
                } 
        }

        // team going into the field
        go_in(&omniPark, p);
        
        // team playing
        sleep(gametime);

        // get the players back to sleep
        if (p->player_sport == baseball){
            for (int j = 0; j < baseball_player_count; j++){
                player_about *cur_player = baseball_player[j];
                cur_player->status = 0;
                cur_player->field_slot = 0;
                printf("I'm player %d, I'm a baseball player. I'm leaving the field right now.\n", cur_player->player_id);
            }
            baseball_player_count = 0;
        } else if(p->player_sport == football){
            for (int j = 0; j < football_player_count; j++){
                player_about *cur_player = football_player[j];
                cur_player->status = 0;
                cur_player->field_slot = 0;
                printf("I'm player %d, I'm a football player. I'm leaving the field right now.\n", cur_player->player_id);
            }
            football_player_count = 0;
        } else{
            for (int j = 0; j < soccer_player_count; j++){
                player_about *cur_player = soccer_player[j];
                cur_player->status = 0;
                cur_player->field_slot = 0;
                printf("I'm player %d, I'm a soccer player. I'm leaving the field right now.\n", cur_player->player_id);
            }
            soccer_player_count = 0;
        }

        // clear the field to get ready for the next set of players
        get_out(&omniPark);
        
        pthread_mutex_unlock(&gmutex);
    }
}

int main(){
    //reading and setting the seed value
    FILE *file = fopen("seed.txt", "r");
    char buffer[100];
    fgets(buffer, 100, file);
    int seed = atoi(buffer);
    srand((unsigned)seed);
    
    // create a specific number of players
    pthread_t Player[MAX_NUM_PLAYER];
    // initiate omniPark
    init(&omniPark);

    // create all the players
    for (int i = 0; i < MAX_NUM_PLAYER; i++){
        player_about *player;
        // assign player ID
        player = &(all_players[i]);
        player->player_id = i;
        // assign player's sport based on their ID
        if(player->player_id >= (MAX_BASEBALL_PLAYER+MAX_FOOTBALL_PLAYER)){   //80
            player->player_sport = soccer;
        } else if (player->player_id >= MAX_BASEBALL_PLAYER){   //36
            player->player_sport = football;
        } else {
            player->player_sport = baseball;
        }
        // create players
        pthread_create(&Player[i], NULL, player_init, (void *)player);
    }

    // wait for other baseball players to join
    for (int i = 0; i<MAX_BASEBALL_PLAYER; i++){
        pthread_join(Player[i], NULL);
    }

    // wait for other foot players to join
    for (int i = MAX_BASEBALL_PLAYER-1; i<MAX_FOOTBALL_PLAYER + MAX_BASEBALL_PLAYER; i++){
        pthread_join(Player[i], NULL);
    }

    // wait for other soccer players to join
    for (int i = MAX_FOOTBALL_PLAYER + MAX_BASEBALL_PLAYER-1; i<MAX_NUM_PLAYER; i++){
        pthread_join(Player[i], NULL);
    }

    end(&omniPark);

    return 0;
}