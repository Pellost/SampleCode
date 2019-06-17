/*
Family Name: Lau
Given Name: Andrew
Section: A
Student Number: 212905253
CS Login: pellost
*/

/*
The program does create a log and a files that suppose to contain the copied bytes from the source. 
However to program does not seem to be reconstructing the file correctly
The program also current runs forever. The problem lies after the main wakes up from sleep where it 
moves on to join the threads. pthread_join doesn't kill the threads and to program stops at the first 
join of the loop, letting the threads run forever. I have commented out this loop so that the program 
terminates. 


*/

#include <stdlib.h>
#include <pthread.h>
#include <stdio.h> 
#include <string.h>
#include <unistd.h>
#define RAND_RANGE 10000000 


typedef struct {

     char  data ;
     off_t offset ;

} BufferItem;

int bufIN = 0;
int bufOUT = 0;
int nIN;
int nOUT;
int bufSize;
int counter = 0;
char fileOUT[100];
char Log[100];

BufferItem *Buffer;

FILE *readFile;


pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

pthread_attr_t attr;


void *producer(void *);
void *consumer(void *);
BufferItem consume(int, int);
void produce(int, BufferItem);
void write_byte(int, BufferItem);
BufferItem read_byte(int);
void toLog(char *, char *, int, off_t, int, int);


int main(int argc, char *argv[]){
   
    if (argc == 7){
       
        nIN = atoi(argv[1]);
        nOUT = atoi(argv[2]);
        readFile = fopen(argv[3], "rb");
        strcpy(fileOUT,argv[4]);
        bufSize = atoi(argv[5]);
        strcpy(Log,argv[6]);
   
    }else{
    	printf("To use cpy, please use this form: cpy <nIN> <nOUT> <file> <copy> <bufSize> <Log>\n");
    	return -1;	    
    }
    
     if (nIN < 1){
        printf("<nIN> must be at least 1\n");
        return -1;
    }else if (nOUT < 1){
        printf("<nOUT> must be at least 1\n");
        return -1;
    }else if (bufSize < 1){
        printf("<bufSize> must be at least 1\n");
       return -1;
    }

    
    pthread_t tidIN[nIN];
    pthread_t tidOUT[nOUT];
   
    pthread_attr_init(&attr);
    
    Buffer = malloc(sizeof(BufferItem) * bufSize);

    int x;
    int y;
    int *p = malloc(sizeof(*p));
   
    printf("RUNNING...\n");
    
    for(x=0; x<nIN; x++){
        
        *p = x;
        pthread_create(&tidIN[x],&attr,producer,p);
        
    }
    
    for(y=0; y<nOUT; y++){

        *p = y;
        pthread_create(&tidOUT[y],&attr,consumer,p);
        
    }
    
    

	
/*	
    int i;
   
      for(i=0; i<nIN; i++){
      	      
        pthread_join(tidIN[i],NULL);
        	
    }  
   
    for(i=0; i<nOUT; i++){

        pthread_join(tidOUT[i],NULL);
        
    }  
    
   fclose(readFile);
   
 */
    
  sleep(10);
  printf("DONE\n");
  return 0;	  
	
}




void *producer(void *param){
     

    struct timespec t;
    
    t.tv_nsec = 0;
    BufferItem currentItem;
    int tnum = *(int *) param;
    

  
    while(fgetc(readFile) != EOF){
    	    
        t.tv_nsec = rand()%(RAND_RANGE+1);
        nanosleep(&t,NULL);
        
        currentItem = read_byte(tnum);
        
        t.tv_nsec = rand()%(RAND_RANGE+1);
        nanosleep(&t,NULL);

        
        while(counter == bufSize){};
        pthread_mutex_lock(&mutex);
      
        produce(tnum,currentItem);
        bufIN = (bufIN + 1) % bufSize;
        
        pthread_mutex_unlock(&mutex);
        counter++;
        
    
    }
    
    
    pthread_exit(0);
}

void produce(int thread, BufferItem item){

    toLog("produce","PT",thread,item.offset,item.data,bufIN);
    
  
    Buffer[bufIN] = item;
    
    


    
}

BufferItem read_byte(int thread){
    BufferItem retval;

    retval.data = fgetc(readFile);
    retval.offset = ftell(readFile);
    
    toLog("read_byte","CT",thread,retval.offset,retval.data, -1);
    
    return retval;
}



void *consumer(void *param){
   

    struct timespec t;
    t.tv_nsec = 0;
    
    int tnum = *(int *) param;

 

    BufferItem nextOUT;
    
    while(1){
           
        t.tv_nsec = rand()%(RAND_RANGE+1);
        nanosleep(&t,NULL);
           
         
        
        while(counter == 0){};
        pthread_mutex_lock(&mutex);
        
        nextOUT = consume(tnum, bufOUT);
        bufOUT = (bufOUT+1) % bufSize;
        
        pthread_mutex_unlock(&mutex);
        
        counter--;
        
        
       
        t.tv_nsec = rand()%(RAND_RANGE+1);
        nanosleep(&t,NULL);
        
        write_byte(tnum, nextOUT);
        
    }

    pthread_exit(0);
}

BufferItem consume(int thread, int nextNum){
    

    BufferItem retval = Buffer[nextNum];
    toLog("consume","CT",thread,retval.offset,retval.data,bufOUT);
  
  
    return retval;

    
}



void write_byte(int thread, BufferItem writeVal){
	FILE *writefile;

    if (!(writefile = fopen(fileOUT, "a"))) {
        printf("could not open output file for writing");
        exit(-1);
    }
    writefile = fopen(fileOUT, "a");

    if (fseek(writefile, writeVal.offset, SEEK_SET) == -1) {
        fprintf(stderr, "error setting output file position to %u\n",(unsigned int) writeVal.offset);
        exit(-1);
    }
    if (fputc(writeVal.data, writefile) == EOF) {
        fprintf(stderr, "error writing byte %d to output file\n", writeVal.data);
        exit(-1);
    }
    
   
    toLog("write_byte","CT",thread,writeVal.offset,writeVal.data, -1);
    
    fseek(writefile, writeVal.offset, SEEK_SET);
    fputc(writeVal.data,writefile);

    fclose(writefile);

     
}



void toLog(char *op, char *tt, int tnum, off_t offset, int b, int i){
    
    FILE *Logfile;
    Logfile = fopen(Log, "a");
    
    char string[100];
    snprintf(string,sizeof(string),"%s %s%d O%ld B%d I%d\n",op, tt, tnum, offset, b, i);

    fputs(string,Logfile); 
    fclose(Logfile);
}



















