#!/bin/bash 


#README: this file is intended to create the config.txt file which contains the users's command line prefferences based on the flags they input and begins the pong game. 


#check for old config file, if it exists delete so old settings are not re-used 
if test -f pong/core/assets/config.txt ; then 
	rm pong/core/assets/config.txt
fi


#loop through argument flags and add to config file, if any. Print usage message
for var in "$@"
do 
	if [ $var == "-h" ]; then 
		
		echo "  "
		echo "  "
		echo "---------------------------------------------"
		echo "| PONG COMMAND LINE EDITION: HELP FLAG (-h) |"
		echo "---------------------------------------------"

		echo ". Users can change the game mode settings from the command line with the flags listed below:"
		echo ". For example, to run the command line pong game with two player mode on, run: ./pong.sh -m 2, where '-m' is the [flag name], and '2' is the [flag value] to use."
		echo "... (Optional) Flags:"
		echo "..... -m [game mode] : change the current game mode [game mode] options : 1 : single player mode 2 : two player mode 3 : single player vs. AI"
		echo "..... -h : display usage message & helpful info "
		echo "..... -s [winning score]: change the current score to win the game. Default is (3)"
		echo "..... -d [difficulty level] : set the games difficulty (velocity of the ball, 1 : slow, 2 : med, 3 : fast)"
		echo "  "
		echo "  "
	
	else
		echo "$var" >> pong/desktop/build/libs/config.txt
	fi
done

#change to the correct directory and run the compiled java code
cd pong/desktop/build/libs
java -jar desktop-1.0.jar


