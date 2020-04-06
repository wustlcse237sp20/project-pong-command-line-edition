#!/bin/bash 


#README: this file is intended to create the config.txt file which contains the users's command line prefferences based on the flags they input and begins the pong game. 


#check for old config file, if it exists delete so old settings are not re-used 
if test -f pong/core/assets/config.txt ; then 
	rm pong/core/assets/config.txt
fi


#loop through argument flags and add to config file, if any 
for var in "$@"
do 	
	echo "$var" >> pong/core/assets/config.txt
done


#begin the pong game with user flag settings 
cd pong
gradle wrapper
./gradlew desktop:run
