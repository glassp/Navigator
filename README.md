# Navigator
A Simple Dijkstra Navigator


![GitHub](https://img.shields.io/github/license/otakupasi/Navigator.svg?style=popout) 

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
- Enter the start node ![](https://img.shields.io/badge/Datatype-Integer-important.svg)
- Enter any target node ![](https://img.shields.io/badge/Datatype-Integer-important.svg)

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

Use the webrunner.sh with following parameter
- `arg1` The path to the .fmi Graph file
- `arg2` ![](https://img.shields.io/badge/Optional-True-green.svg) The path to the webroot dir
- `arg3` ![](https://img.shields.io/badge/Optional-True-green.svg) ![](https://img.shields.io/badge/Datatype-Integer-important.svg) The port you want the server to run on

The second argument should always be used if the server runs from a directory that is not called "Navigator" or the server may not find the default webroot dir.
 
After running the terminal should output a message like the following  
```
[2019-02-13 19:39:22] [SYS] Starting server...
#################################
### HTTP-Server               ###
### http://YOUR_LOCAL_IP:8080 ###
#################################
```

After that you can go to the provided link and use the files provided.

To Terminate the server you just need to close the Terminal window.

#### Running Queries on the Server

When using the Web GUI, you can freely choose start and destination coordinates on the map.
Clicking "Find Route" will run Dijkstra and display the route on the map.

If the closest node to start and destination is identical, a pin icon will mark the coordinates as the only point on the route.

An appropriately labeled button will toggle a popup showing info on
coordinates and the next node from the graph wherever you click on the map.
