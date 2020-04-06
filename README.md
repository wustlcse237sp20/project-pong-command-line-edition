# Pong command line edition
This is a pong game written in Java using the LibGDX game engine. There are 3 gamemodes namely single player, single player vs AI, and two players. 

## Install
***1. Windows***

- First, open the project and install gradle. To do that, use the command: 'apt install gradle'
- Next, run the pong.sh bash script using the command: ./pong.sh

***2. Mac***
- First, open the project and install gradle. To do that, simply run 'brew install gradle'
- Next, run the pong.sh bash script using the command: ./pong.sh

***3. Notes***
- If you don't have gradle installed, still try running'./pong.sh'
- If that doesn't work, it's likely something to do with your Java install (JRE or JDK).
    - To determine if this is the problem, run: java -version
    - If the java version is not java 1.8.x, or 1.7.x, please upgrade to this version and try again.
    - If you are still having issued, run: 'java -version' again to ensure the correct version of java is being used.
- If it doesn't work on Windows, and you have java installed, check your environment variables, ensure the JAVA_HOME variable is set to a jdk-1.8.x or jdk-1.9.x distribution. 


## Usage 
- Users can change the game mode settings from the command line with the flags listed below 
- The format for including flags in the command line is as follows: ./pong.sh -[flag name] [flag value]
- For example, to run the command line pong game with two player mode on, run: ./pong.sh -m 2, where '-m' is the [flag name], and '2' is the [flag value] to use. 
- (Optional) Flags: 
    -m [game mode] : change the current game mode
        [game mode] options : 
            1 : single player mode 
            2 : two player mode 
            3 : single player vs. AI 
            
## Testing
- Since our project is a series of games, we cannot use Unit Tests. This is because Unit Tests are designed to tests functions once.
- Our tests need to continously run to ensure things are valid throughout the duration of the game where things are constantly moving around changing values. 
- Our tests constantly run while you play the game and print out how many tests pass to the command line. 

## Iteration / User Stories
***1. Iteration 1***
- As a bored quarantined person, I want to play a fun infinite game of pong. (Complete)
- As a player, I want to play a game of pong against an AI to make the game more interesting. (Complete)
- As a player, I want to be able to play against (and beat my friends), in some head to head two player pong. (Complete)
- As a player, I want to be able to launch any of the three gamemodes from command line. (Complete)


***2. Iteration 2***
- As a player, I want to be able to customize the pong board, ball, and paddles.
- As a player, I want to be able to set the difficulty of the AI.
- As a player, I want to be able to change the speed of the ball.

***3. Iteration 3***
TBD
