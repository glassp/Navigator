# Navigator
A Simple Dijkstra Navigator
## Requirements

- JRE ( Java Runtime Environment)

## Installation
### Automatic
Just download and run the makefile.sh

- Will check if JRE is installed
- Will compile the Java Source code
- Will run the Programm with a CLI

### Manual
Just follow these steps
1. Open Terminal and cd into Project Folder
2. Run ``javac ./src/main/*.java -d ./bin``
3. Run ``java -cp ./bin/ main/Main``

This will start the Programms CLI
## Running  

### Import the Graph  

-  Enter ``0``
-  Enter the Path to the .fmi file

### Run the Dijkstra Algorithm

- Import the Graph
- Enter ``1``
- Enter the Start Node

### Run a Query

- Import the Graph
- Enter ``2``
- Enter the file to the .que file

### Check for Differences

- Import the Graph
- Enter ``3``
- Enter the file to the .que file
- Enter the file to the .sol file

## Logging output
###Verbose Logging
Enter ``v`` to toogle verbose Logging

###Debug Logging
Enter ``d`` to toogle debug Logging

###Info Logging
Enter ``i`` to toogle info Logging
