/*
Family Name: Lau
Given Name: Andrew
Student Number: 212905253
CS Login: Pellost
*/

#include <stdlib.h>
#include <stdio.h> 
#include <sys/types.h>

#define READ 0
#define WRITE 1

int main(int argc, char *argv[]){
	
	FILE *fr;				//contains the file that is to be read
	pid_t pid;				//the process id 
	int FileNum = 0;		//Counter for the number of files
	float Maximum = 0;		//maximum number in all datasets
	float Minimum = 0;		//minimum number in all datasets
	
	/*Pipes*/
	int sumpipe[argc-1][2];			//a integer array that represents the pipe that holds the sum
	int diffpipe[argc-1][2];		//a integer array that represents the pipe that holds the difference
	int namepipe[argc-1][2];		//a integer array to represent the pipe that holds the name of the processes 

	pid_t *pids = malloc(argc * sizeof(pid_t));		//an array of process id's of concurrently running processes
	
			
	  if (argc < 2) {
        fprintf(stderr, "Please input a file\n");
        exit(-1);
    }

	for (FileNum = 1; FileNum < argc; FileNum++){/*File loop*/	
		
		if (pipe(sumpipe[FileNum-1]) == -1 || pipe(diffpipe[FileNum-1]) == -1) {/*Pipe creation*/
			fprintf(stderr,"Pipe failed");
			exit(-1);
		}

		pid = fork();
		
		
		if (pid < 0){ 
			
			fprintf(stderr, "Fork Failed");
			exit(-1);
			
		}else if (pid == 0){/*Child pricess*/
			
			float num;
			char FileName[100];
			float tmpmax = 0;
			float tmpmin = 0;
			float tmpsum;
			float tmpdiff;

			fr = fopen (argv[FileNum], "r");

			

			if (fr == NULL) { //if file doesnt exist
				printf("%d\n", argv[FileNum]);
				continue;
			}
			
			while(fscanf(fr, "%f", &num) > 0){
				
				if (tmpmax == 0){
					tmpmax = num;
				}else if (num > tmpmax){
					tmpmax = num;
					
				}
				
				if (tmpmin == 0){
					tmpmin = num;
				}else if (num < tmpmin){
					tmpmin = num;
					
				}
				
			}
			
			tmpsum = tmpmax + tmpmin;
			tmpdiff = tmpmin - tmpmax;
			
			close(sumpipe[FileNum-1][READ]);							//recond values to the pipes
			write(sumpipe[FileNum-1][WRITE],&tmpsum,sizeof(tmpsum));
			close(sumpipe[FileNum-1][WRITE]);
			
			close(diffpipe[FileNum-1][READ]);
			write(diffpipe[FileNum-1][WRITE],&tmpdiff,sizeof(tmpdiff));
			close(diffpipe[FileNum-1][WRITE]);
			
			fclose(fr);		//close file

			
			exit(1);
			
			}else{/*Parent process*/
				
				pids[FileNum-1] = pid;		//set the process ids to the process id array
				
			}

	}
	
	
	for (FileNum = 1; FileNum < argc; FileNum++){
		
		int returnStatus;
		waitpid(pids[FileNum-1], &returnStatus, 0);		//wait for the completion
		
		float max;
		float min;
		float sum;
		float diff;
		
		close(sumpipe[FileNum-1][WRITE]);					//read values from the pipes
		read(sumpipe[FileNum-1][READ],&sum,sizeof(sum));
		close(sumpipe[FileNum-1][READ]);
		
		close(diffpipe[FileNum-1][WRITE]);
		read(diffpipe[FileNum-1][READ],&diff,sizeof(diff));
		close(diffpipe[FileNum-1][READ]);

		min = (sum + diff)/2;
		max = (sum - diff)/2;
		
		
		if (Maximum == 0){			//calculate the Maximum value
			Maximum = max;
		}else if (max > Maximum){
			Maximum = max;
			
		}
		
		if (Minimum == 0){			//calculate the Minimum value
			Minimum = min;
		}else if (min < Minimum){
			Minimum = min;
			
		}
		printf("%s SUM=%0.5f DIF=%0.5f MIN=%0.5f MAX=%0.5f\n",argv[FileNum], sum, diff, min, max);	//print values per dataset

	}
	

	printf("MINIMUM=%0.5f MAXIMUM=%0.5f\n", Minimum, Maximum);		//print Minimum and Maximum values
	return 0;
}

