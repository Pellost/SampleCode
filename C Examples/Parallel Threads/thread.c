/*
Family Name: Lau
Given Name: Andrew
Student Number: 212905253
CS Login: Pellost
*/

#include <stdlib.h> 
#include <pthread.h> 
#include <stdio.h>

typedef struct {

    float max;
    float min;
    float sum;
    float dif;
    FILE * fr;

}Array;

void * GetData(void * );

Array * Result;		//struct array to hold all the values for each thread

pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;		//a mutex lock to protext the global struct array

static int threadnum = 0;		//counter for the number of threads 

int main(int argc, char * argv[]) {

    int FileNum;

    if (argc < 2) {
        fprintf(stderr, "Please input a file\n");
        exit(-1);
    }

    if (atoi(argv[1]) < 0) {
        fprintf(stderr, "%d must be >= 0\n", atoi(argv[1]));
        exit(-1);
    }

    pthread_t tid[argc];		//array of threads
    pthread_attr_t attr;
    Result = (Array * ) malloc(sizeof(Array) * argc);


    for (FileNum = 1; FileNum < argc; FileNum++) {

        char * FileName = argv[FileNum];

        pthread_attr_init( & attr);			

        pthread_create( & tid[FileNum - 1], & attr, GetData, FileName);		//creation of the threads
    }

    for (FileNum = 1; FileNum < argc; FileNum++) {

        pthread_join(tid[FileNum - 1], NULL);		//join the threads after completions

    }

    float Maximum = 0;
    float Minimum = 0;

    for (FileNum = 1; FileNum < argc; FileNum++) {

        if (Maximum == 0) {								//calculation to find the maximum variable
            Maximum = Result[FileNum - 1].max;
        } else if (Result[FileNum - 1].max > Maximum) {
            Maximum = Result[FileNum - 1].max;

        }

        if (Minimum == 0) {								//calculation to find the minimum variable
            Minimum = Result[FileNum - 1].min;
        } else if (Result[FileNum - 1].min < Minimum) {
            Minimum = Result[FileNum - 1].min;

        }
        printf("%s SUM=%0.5f DIF=%0.5f MIN=%0.5f MAX=%0.5f\n", Result[FileNum-1].fr, Result[FileNum - 1].sum, Result[FileNum - 1].dif, Result[FileNum - 1].min, Result[FileNum - 1].max);
    }

    printf("MINIMUM=%0.5f MAXIMUM=%0.5f\n", Minimum, Maximum);
    return 0;

}

void * GetData(void * Name) {		//thread runner

    float num = 0;
    float tmpmax = 0;
    float tmpmin = 0;
    float tmpsum = 0;
    float tmpdif = 0;

    FILE * file = fopen(Name, "r");		//open the file

    if (Name == NULL) {
        printf("%d\n", "No File");
        exit(-1);
    }

    while (fscanf(file, "%f", & num) != EOF) {		//get the values from the file 

        if (tmpmax == 0) {					//find the max value for that file
            tmpmax = num;
        } else if (num > tmpmax) {
            tmpmax = num;

        }

        if (tmpmin == 0) {					//fine the min value for that file
            tmpmin = num;
        } else if (num < tmpmin) {
            tmpmin = num;

        }

    }

    tmpsum = tmpmax + tmpmin;			//calculation for the sum of the file 
    tmpdif = tmpmin - tmpmax;			//calculation for the difference of the file

    pthread_mutex_lock( & lock);		//lock the critical section

    Result[threadnum].max = tmpmax;		//set the values of the array 
    Result[threadnum].min = tmpmin;
    Result[threadnum].sum = tmpsum;
    Result[threadnum].dif = tmpdif;
    Result[threadnum].fr = Name;

    threadnum++;

    pthread_mutex_unlock( & lock);		//unlock the critical section

    fclose(file);						//close the file

    pthread_exit(0);					//end the thread
}
