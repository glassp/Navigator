@echo off
IF exist bin/ () ELSE (mkdir bin)
IF exist bin/out/ () ELSE (mkdir bin/out)



javac src/main/*.java -d bin/

java -cp bin/ main/Main
