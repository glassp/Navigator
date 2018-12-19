#!/bin/bash
echo -n "Checking for dependencies..."
for name in java
do
    [[ $(which ${name} 2>/dev/null) ]] || { echo -en "\n${name} missing. Try 'sudo apt-get install ${name}'."; DEPS=1; }
done

if [[ ${DEPS} -ne 1 ]];
then
    echo -en "OK \n\n"
else
	echo -en "\n Install the above  and rerun this script\n"; exit 1;
fi



#chech if bin exists
if [[ -d "./bin" ]];
then
    #previous compiled files exist => remove them
    rm -r  ./bin
fi

#create bin dir
mkdir ./bin
#create dir for dijkstra outputs
mkdir ./bin/out

#compile code
javac ./src/main/*.java -d ./bin/

#start main()
java -cp ./bin/ main/Main

sleep 5m