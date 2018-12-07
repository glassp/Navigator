#!/usr/bin/env bash
echo -n "Checking for dependencies..."
for name in java
do
    [[ $(which ${name} 2>/dev/null) ]] || { echo -en "\n${name} missing. Try 'sudo apt-get install ${name}'."; deps = 1; }
done
[[ ${deps} -ne 1 ]] && echo "OK" || { echo -en "\n Install the above  and rerun this script\n"; exit 1; }

#chech if bin exists
if [[ -d "./bin" ]];
then
    #previous compiled files exist => remove them
    rm -r  ./bin
fi

#create bin dir
mkdir ./bin

#compile code
javac ./src/main/*.java -d ./bin/

#start main()
#TODO: ERROR: does not run: NOClassDefFoundError
java -cp ./bin/main/CLI

