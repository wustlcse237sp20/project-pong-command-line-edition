# Pong command line edition
This is a pong game written in Java using the LibGDX game engine. There are 3 gamemodes namely single player, single player vs AI, and two players. 




## Usage 
- Users can change the game mode settings from the command line with the flags listed below 
- The format for including flags in the command line is as follows: ./pong.sh -[flag name] [flag value]
- For example, to run the command line pong game with two player mode on, run: ./pong.sh -m 2, where '-m' is the [flag name], and '2' is the [flag value] to use. 
- **Run ./pong.sh -h For a help message that explains all of the flags.**

***3. Notes***
- If that doesn't work, it's likely something to do with your Java install (JRE or JDK).
    - To determine if this is the problem, run: java -version
    - If the java version is not java 1.8.x, or 1.7.x, please upgrade to this version and try again.
    - If you are still having issued, run: 'java -version' again to ensure the correct version of java is being used.
- If it doesn't work on Windows, and you have java installed, check your environment variables, ensure the JAVA_HOME variable is set to a jdk-1.8.x or jdk-1.7.x distribution. 
- **You only need Java to run our game. If, somehow, the script isn't working. Go to 'pong\desktop\build\libs' and run desktop-1.0.jar to launch the game. Running this way will not allow you to enter in config variables like gamemode or difficulty.** 
            
## Testing
- Since our project is a series of games, we cannot fully use Unit Tests. This is because Unit Tests are designed to tests functions once.
- Our tests need to continously run to ensure things are valid throughout the duration of the game where things are constantly moving around changing values. 
- Our tests constantly run while you play the game and print out how many tests pass to the command line. 
- Our DesktopLauncher doesn't have a test because it is LibGDX generated code. We didn't write it. 
- Prof Shook mentions that UI classes don't need tests, are game is basically all UI but we wrote the tests mentioned above. 
- *We have JUnit tests for our Config class. This is because it isn't Game Code, so it is JUnit testable.*

## Iteration / User Stories
***1. Iteration 1***
- As a bored quarantined person, I want to play a fun infinite game of pong. (Complete)
- As a player, I want to play a game of pong against an AI to make the game more interesting. (Complete)
- As a player, I want to be able to play against (and beat my friends), in some head to head two player pong. (Complete)
- As a player, I want to be able to launch any of the three gamemodes from command line. (Complete)


***2. Iteration 2***
- As a player, I want to be able to customize the ball and paddle sizes to change the game experience. (Complete)
- As a player, I want to be able to set the difficulty of the AI and change the speed of the ball in all games. (Complete)
- As a player, I want want the ball to never get a very steep vertical slope because I want the ball to get across the screen quickly. (Complete)
- As a player, I want to be able to launch the game without installing anything. (Complete) 
- As a player, I want a useful help message on the open game script. (Complete)
- As a player, I want to be able to customize how long the game will last. (Complete)
- As a developer, I want to run JUnit tests on the config to ensure it works properly. (Complete)

***3. Iteration 3***
TBD
