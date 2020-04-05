#!/bin/bash 

#loop through argument flags and add to config file, if any 
for var in "$@"
do 	
	echo "$var" >> pong/core/assets/config.txt
done

cd pong
gradle wrapper
./gradlew desktop:run
