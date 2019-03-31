## Tree-Based Quorum

Implementation of a Tree-Based Quorum. 
***
### Technical Specs
    * Java-8
    * Windows
    * Batch File
  
  Client & Server Folders contain the source code for Client & Server Nodes respectively
***   
### Prerequisites
* Java-8
* MultiThreading and Socket Programming
* Basic Data Structures and Protocol understanding

## **Description**:

####  Requirements

* There is a file (you can choose any name for that file) that each client node can write to. Initially, the file is
empty.
* There are seven server nodes in the system, numbered from S1 to S7. The servers are logically arranged as a
binary tree with S1 as the root. The children of server Si are nodes S2i and S2i+1.
* There are five client nodes in the system, numbered from C1 to C5. Each client independently generates its
requests to enter the critical section by executing the  sequence of operations until twenty of its requests
have been satisfied

#### Data Collection
For your implementation of the mutual exclusion algorithm report the following (either show it on the screen or write
it to a file):
1. The total number of messages sent by each node from the beginning until it sends the completion notification.
2. The total number of messages received by each node from the beginning until it sends the completion notification.
3. For each node, report the following for each of its attempts to enter the critical section:
(a) The number of messages exchanged.
(b) The elapsed time between making a request and being able to enter the critical section (latency)

For more details read Project_2 Description.pdf
***
## Important Note

1) If you run directly after Unzipping the code, the code should run fine. Check the filePaths in readFile.txt and in the source code.
   <h4><a > The Batch Files</a></h4> automates spawning of terminals without physically entering them
   
2) The project is created in Eclipse
   
3) The Given code will not work in Linux. Check the other branch to test it on the DC Machines in UTD. 
    
4) ParseConfigFile.java reads lines from readFile.txt in a specified format. Check the code for more understanding.
***

### **Execution** 
1) Unzip or clone the project.
2) Double Click on the RunServer.bat file in Server directory
3) Double Click on the RunClient.bat file in Client directory
4) At the end Data Collection Statistics are displayed in the Console
