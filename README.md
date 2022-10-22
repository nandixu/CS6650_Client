# CS6650_Client

This is the client of the project. It contains a model folder, a part2
folder, two threads class, and a main function.

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


### ScreenShot of Part1 and Part2
![P1andP2](https://media.github.khoury.northeastern.edu/user/8909/files/c1817003-bfa5-4168-b004-b173a4125f2e)
