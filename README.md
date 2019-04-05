## Tree-Based Quorum

Implementation of a Tree-Based Quorum. 
***
### Technical Specs
    * Java-8
    * Ubuntu
    * Shell script
  
  Client & Server Folders contain the source code for Client & Server Nodes respectively
***   
### Prerequisites
* Java-8
* MultiThreading and Socket Programming
* Basic Data Structures and Protocol understanding

## Performance Statistics:

Time b/w Client exiting CS and issuing new request | Time Spent in CS | Time taken to enter CS after requesting ( Latency ) | Deadlock
--- | --- | --- | ---
500 to 1000ms | 350ms | 792.82 | No
500 to 1000ms | 300ms | 581.03 | No
500 to 1000ms | 200ms | 347.44 | No
300 to 500ms | 200ms | 506.7 | No
200 to 300ms | 200ms | 584.67 | No
200 to 300ms | 2ms | 87.76 | No
100 to 200ms | 200ms | N/A | Yes
100 to 200ms | 2ms | N/A | Yes
50 to 100ms | 200ms | N/A | Yes
5 to 10ms | 200ms | N/A | Yes
5 to 10ms | 3ms | N/A | Yes

Provided that time between successive requests is sufficiently large so that no deadlock occurs and maintained a constant, time spent in CS is directly proportional to latency.

Provided that Time spent in CS is constant, Time between succesive requests is inversely proportional to Latency, if successive request time is large enough so that no deadlock occurs. 

## **Description**:

####  Requirements

* There is a file (you can choose any name for that file) that each client node can write to. Initially, the file is
empty.
* There are seven server nodes in the system, numbered from S1 to S7. The servers are logically arranged as a
binary tree with S1 as the root. The children of server Si are nodes S2i and S2i+1.
* There are five client nodes in the system, numbered from C1 to C5. Each client independently generates its
requests to enter the critical section by executing the  sequence of operations until twenty of its requests
have been satisfied
* Note that the deadlock can happen because there are no Failed and Yield messages like Maekawa's algorithm
* Multiple quorums are possible in this system of seven servers. Each time a client makes a REQUEST,
it should randomly select one of the quorums to send the REQUEST to. The goal is to select different
quorums over the course of this project.

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

### **Execution** 
1) Unzip or clone the project.
2) Double Click on the RunServer.bat file in Server directory
3) Double Click on the RunClient.bat file in Client directory
4) At the end Data Collection Statistics are displayed in the Console

## Important Note

1) If you run directly after Unzipping the code, the code should run fine. Check the filePaths in readFile.txt and in the ParseConfigFile.java and also check if you have password less login setup. 

   <h4><a href="#password-less-login">Passwordless login</a></h4> automates spawning of terminals without physically entering them
   
2) The project is created in Eclipse, therefore launch.sh and launchServer.sh after execution compiles the code to /bin directory. Change the shell scripts if you don't want it to compile the source code. 
   
3) If you change the UID's, consider also changing the quorums' arrangement. Clients UID range from 11 to 15, while Servers are from
   1 to 7.
      
4) The Given code will not work in LocalHost, even in Linux. With some minor changes you can make it to run on Linux localhost.  Checkout the other branch in GitHub to test it on the Windows LocalMachine
   
5) configClient.txt and configServer.txt should have unique DC Machine as their hostname. Code will not work if 2 UIDs have same hostname
   
6) Quorum's are provided in the readFile.txt of Client.
   
7) ParseConfigFile.java reads lines from readFile.txt ignores empty lines and the lines that start with '#'. Check the code for more understanding.
***
### **Execution** 
1) Unzip or clone the project.
2) Update the respective paths in configClient.txt, configServer.txt and configCleanup.txt. To select the paths for your Servers to host the files (Path should be inside the DC Machine). No need to create directories, code takes care of it. *Config files guide the shell script to the locations of each node in the network*.
3) As per the project description Executing the code on DC machines is mandatory. Make sure you have password less login setup before you run the Shell script. Details can be found below. ( To access DC Machines you have to be a UTD Student )
   <br/> <h4><a href="#password-less-login">Passwordless login ( Simple 12 steps )</a></h4>
4) Update the paths inside ParseConfigFile.java, readFile.txt.
5) **Config files and shell scripts will only be in your local machine**
   All the code will reside in the Server. Copying the files in csgrads1 will update the code in all DC machines as DC Machine implements Distributed Shared File System
6) Push the code in csgrads1.utdallas.edu, by using winscp or sftp
7) Give executable permission to the scripts
   ```shell
    $chmod +x *.sh

8) Compiles the java code ( You can ignore this step, if you are using launch.sh and launchServer.sh.<br> The shell scripts will compile the code and execute )
 
    ```shell
    $ cd Client
    $ javac -cp "./bin" -d "./bin" ./src/*.java
    $ cd ../Server
    $ javac -cp "./bin" -d "./bin" ./src/*.java
    ```

9)  "launchServer.sh" will setup the terminals for Server<br>
    "launch.sh" sets up the terminals for Clients<br>
    "cleanup.sh" closes all the ssh connections of the terminals <br>

10) Open a terminal and go into the directory of shell scripts. Obviously, always launch the Servers first.
    ```shell
    $ ./launchServer.sh 
    ```
11) Open a terminal and go into the directory of shell scripts
    ```shell
    $ ./launch.sh
    ```
12) Terminals will pop up and start executing the code.
    In the end, view your files in the Server. All the respective files will have the same values.

13) At the end Data Collection Statistics are displayed in the terminals

14) Open a terminal and go into the directory of shell scripts
    ```shell
    $ ./cleanup.sh
    ```
This will terminate all the spawned terminals.
* The Details of the Project Description can be found in Project_2 Description.pdf
***
## [Password Less Login](#password-less-login)
Note: $ is a prompt in the terminal: Ignore $ while copying these commands into the terminal

1) Open the terminal in your local machine, type
```shell
  $ cd
  $ cd .ssh
``` 
Now you'll be in the .ssh directory

2) Type
```shell
  $ ssh netid@dc01.utdallas.edu
```
3) if prompted, 
```shell
  Are you sure you want to continue connecting (yes/no)? 
```
Type yes, then enter

4) enter password 

dc01: will be your current system. 

5) Type
```shell
  $ cd .ssh
```
Now you'll be in dc01/.ssh directory

6) Type
```shell
  $ ssh-keygen -t rsa
```
 Just press enter for passphrase … … <br>
The key fingerprint is: … something … , hit enter <br>
The keys randomart image is: … … (special image), hit enter <br>

7) type
```shell
  $ cat id_rsa.pub >> authorized_keys
```
8) type
```shell
  $ exit
```
Now you'll be in .ssh directory in your local Machine.

9) type
```shell
  $ sftp netid@dc01.utdallas.edu
```
Enter your password

10) Type
```shell
  sftp> cd .ssh
```
Now you'll be in dc01/.ssh directory

11) Type
```shell
  sftp> get id_rsa
```
id_rsa file will be copied into your .ssh directory in local machine

12) Type
```shell
  $ exit
```
Now you'll be in your local machine
* You dont have to enter password to enter to any dc machine from your local machine now.
***


#### Feedback:

If you have any doubts / suggestions / feedback please send a mail to kiranck18@gmail.com or open an issue in the GitHub.