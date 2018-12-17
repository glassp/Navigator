# Navigator
A Simple Dijkstra Navigator
## Requirements

- JRE ( Java Runtime Environment)

## Installation
### Automatic
Just download and run the makefile.sh or run.sh

- Will check if JRE is installed
- Will compile the Java Source code
- Will run the Programm with a CLI or as terminal script

### Manual
Just follow these steps
1. Open Terminal and cd into Project Folder
2. Run ``javac ./src/main/*.java -d ./bin``
3.
 - Run ``java -cp ./bin/ main/Main`` for CLI
 - Run ``java -cp ./bin/ main/Main arg1 arg2 arg3 `` for Terminal Script,  
 where `arg1` is the path to the graph data  
 `arg2` the path to the query file  
 and `arg3` the path to the .sol file


## Running  

### As CLI  

Use the makefile.sh file and run in in Terminal

#### Import the Graph  

-  Enter ``0``
-  Enter the Path to the .fmi file

#### Run the Dijkstra Algorithm

- Import the Graph
- Enter ``1``
- Enter the Start Node

#### Run a Query

- Import the Graph
- Enter ``2``
- Enter the file to the .que file

#### Check for Differences

- Import the Graph
- Enter ``3``
- Enter the file to the .que file
- Enter the file to the .sol file

#### Logging output
#####Verbose Logging
Enter ``v`` to toogle verbose Logging

#####Debug Logging
Enter ``d`` to toogle debug Logging

#####Info Logging
Enter ``i`` to toogle info Logging

###From Terminal  

Use the run.sh from terminal with parameter  
- `arg1` The Path to the Graph data
- `arg2` The path to the Query file
- `arg3` The path to the .sol file
  
The Script will import the graph, run the query and compare it to the solution.