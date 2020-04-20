#!/bin/bash


#README: this file is intended to create the config.txt file which contains the users's command line prefferences based on the flags they input and begins the pong game.


#check for old config file, if it exists delete so old settings are not re-used
if test -f pong/core/assets/config.txt ; then
	rm pong/core/assets/config.txt
fi


#loop through argument flags and add to config file, if any
for var in "$@"
do
	if [ $var == "-h" ]; then

		echo "PONG COMMAND LINE EDITION: (-h)"
		echo "Usage Message:"
		echo ". The format for including flags in the command line is as follows: ./pong.sh -[flag name] [flag value]"
		echo ". Users can change the game mode settings from the command line with the flags listed below:"
		echo ". For example, to run the command line pong game with two player mode on, run: ./pong.sh -m 2, where '-m' is the [flag name], and '2' is the [flag value] to use."
		echo "... (Optional) Flags:"
		echo "..... -m [game mode] : change the current game mode [game mode] options : 1 : single player mode 2 : two player mode 3 : single player vs. AI"
		echo "..... -h : display usage message & helpful info "
		echo "..... -d [difficulty]: change the difficulty to 1 (easy), 2 (medium), 3 (hard) "
		echo "..... -s [winning score]: change the score that it takes to win, to any number. "
		echo "..... -ballRadius | change the game ball radius (default is 20) "
		echo "..... -paddleWidth | change the size of the paddle (default is 80) "

		sleep 10s
		echo "Starting game in 20 seconds "
		sleep 20s

	else
		echo "$var" >> pong/desktop/build/libs/config.txt
	fi
done


#begin the pong game with user flag settings
cd pong/desktop/build/libs

java -jar desktop-1.0.jar
#gradle wrapper
#./gradlew desktop:run
