# CS6650_Client

This is the client of the project. It contains a model folder, a part2
folder, two threads class, and a main function.

### How Part1 Works
There is one thread that generate 200k random events data and put them into a blocking queue, and there are 200 threads that each of them will do 1000 post actions and die if it reachs the limit or get an exit msg. 

### Model
In this folder, there is a random skier class that specified the
attributes of a lift event. This class is used in ThreadSkierGeneration
to generate random skier data.

### Part2 Report Generation
In this folder, there is a response summary class that includes the 
required statistic attributes of each run, including mean response time,
max response time, etc. The main function used the result data generated
int part1, create a report, and print out the report in console.

### ThreadDoPost
This is a thread class that would make 1000 post, and end when there is an
'exit' message in the blocking queue, or it reaches the maximum limit.

### ThreadSkierGeneration
This is a thread that would randomly generate 200k lift events.


Please ignore Lab3 and HttpClientTutorial class. I used the template to learn
HttpClient operations. 

## Assignment 1 Result
### ScreenShot of Part1 and Part2
![CorrectedReport](https://media.github.khoury.northeastern.edu/user/8909/files/b226c9d9-e042-4063-adcf-3eb3a93355f3)

### Little's Law Throughput Prediction
Little's Law states that "Work In Progress" = "Throughput" * "Lead Time". In this case, "Work In Progress" means the number of item inside the queueing system. It means the threads amount that is doing the post action, which is 200 in this case. "Lean Time" means the average time an item spends in the system, which is the mean time, 6ms. Therefore, Throughtput predictions should be "Work In Progress" / "Lead Time", which is 33.5 requests per milisecons. This prediction is close to the real result, that is 28 requests per miliseconds. 
