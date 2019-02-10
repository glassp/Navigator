# Navigator
A Simple Dijkstra Navigator

![](https://img.shields.io/github/license/otakupasi/Navigator.svg) 
![](https://img.shields.io/coveralls/github/otakupasi/Navigator.svg) 
![](https://img.shields.io/github/status/s/pulls/badges/shields/1110.svg)  


## Requirements

- JRE ( Java Runtime Environment)

## Installation
Just download and run the makefile.sh, run.sh or webrunner.sh

- Will check if JRE is installed
- Will compile the Java source code
- Will run the program

## Running  

### Via Makefile.sh

makefile.sh will run the programm as a CLI within the Terminal

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


### Via Run.sh 

Use the run.sh from terminal with parameter  
- `arg1` The Path to the Graph data
- `arg2` The path to the Query file
- `arg3` The path to the .sol file
  
The Script will import the graph, run the query and compare it to the solution.

### Via Webrunner.sh

TODO