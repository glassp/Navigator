# Navigator
A Simple Dijkstra Navigator

![](https://img.shields.io/github/license/otakupasi/Navigator.svg) 
![](https://img.shields.io/coveralls/github/otakupasi/Navigator.svg) 
![](https://img.shields.io/github/status/s/pulls/badges/shields/1110.svg)  
[goto](https://drive.google.com/drive/folders/18Yi9jL2jzjAOlSKQE7uswRldwCoxtzc1?usp=sharing)

## TODO
Rearange dir structure
```
root
| + public (Server, should be listed to a port and accessible by browser)
| + protected (Backend, inaccessible to browser)
```

## Requirements

- JRE ( Java Runtime Environment)

## Installation
### Automatic
Just download and run the makefile.sh or run.sh

- Will check if JRE is installed
- Will compile the Java source code
- Will run the program with a CLI or as terminal script

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
-  Enter the path to the .fmi file

#### Run single Query

- Import the Graph
- Enter ``1``
- Enter the start node
- Enter any target node

If you want to make multiple inquiries from the same starting point in a row, Dijkstra's algorithm will only be running once.

#### Run multiple Queries

- Import the Graph
- Enter ``2``
- Enter the path to the .que file

#### Check for Differences

- Import the Graph
- Enter ``3``
- Enter the path to the .out file
- Enter the path to the .sol file

NOTE: After having run a query, the path to its .out file is automatically used for this Graph. 

#### Logging output  
NOTE: This will increase the runtime
##### Verbose Logging
Enter ``v`` to toggle verbose logging

##### Debug Logging
Enter ``d`` to toggle debug logging

### From Terminal  

Use the run.sh from terminal with parameter  
- `arg1` The Path to the Graph data
- `arg2` The path to the Query file
- `arg3` The path to the .sol file
  
The Script will import the graph, run the query and compare it to the solution.
