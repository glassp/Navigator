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
    rm -r  ./bin/
fi

#create bin dir
mkdir ./bin
#create dir for dijkstra outputs
mkdir ./bin/out

#compile code
javac ./src/main/*.java -d ./bin/

#if param 1,2,3 empty exit
if [[ -z $1 ]]; then
echo -e "\nPlease call $0 <arg1> <arg2> <arg3> to run this command!\n"
sleep 3s
exit 1;
fi
if [[ -z $2 ]]; then
echo -e "\nPlease call $0 <arg1> <arg2> <arg3> to run this command!\n"
sleep 3s
exit 1;
fi
if [[ -z $3 ]]; then
echo -e "\nPlease call $0 <arg1> <arg2> <arg3> to run this command!\n"
sleep 3s
exit 1;
fi

#run main(arg1, arg2, arg3)
java -cp ./bin/ main/Main ${1} ${2} ${3}

