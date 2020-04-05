# Pong command line edition
This is mini pong game written in Java. There are 3 game modes namely single player, single player vs AI and two players. Difficulties can be adjusted using command lines.

## Install
***1. Window***

- First, open the project and install gradle. To do that, use the command: 'apt install gradle'
- Next, run the pong.sh bash script using the command: ./pong.sh

***2. Mac***
- First, open the project and install gradle. To do that, simply run 'brew install gradle'
- Next, run the pong.sh bash script using the command: ./pong.sh

***3. Notes***
- If that doesn't work, it's likely something to do with your Java install (JRE or JDK).
    - To determine if this is the problem, run: java -version
    - If the java version is not java 1.8.x, or 1.9.x, please upgrade to this version and try again. 
    - If you are still having issued, run: 'java -version' again to ensure the correct version of java is being used.
- If it doesn't work on Windows, and you have java installed, check your environment variables, ensure the JAVA_HOME variable is set to a jdk-1.8.x or jdk-1.9.x distribution. 
- To develop with an IDE, LibGDX highly recommends InteliJ, I tried to use Eclipse for a couple hours and couldn't get it to import and build. I got InteliJ working using the Desktop instructions here: https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA

## Usage 
- Users can change the game mode settings from the command line with the flags listed below 
- The format for including flags in the command line is as follows: ./pong.sh -<flag name> <flag value>
- For example, to run the command line pong game with two player mode on, run: ./pong.sh -m 2, where '-m' is the <flag name>, and '2' is the <flag value> to use. 
- (Optional) Flags: 
    -m <game mode> : change the current game mode
        <game mode> options : 
            1 : single player mode 
            2 : two player mode 
            3 : single player vs. AI 

## Iteration
***1. Iteration 1***
- User story 1: Users are getting bored, especially during the quarantine due to COVID-19. They want to play a fun game to improve their mood as well kill some time. Therefore, we come up with a mini pong game that users can control a paddle to hit the ball against a wall. (finished)
- User story 2: The game is fun at first, but the users started to get bored of bouncing the ball into a steady wall. So, we create another mode for them to play against an AI. (finished)
- User story 3: the user is having lots of fun, but finds it annoying that he must go into the code to manually change the game mode. So, we created a command line flag functionality to accept flags from the user to change game mode settings. 

***2. Iteration 2***
- User story 1: An user told his friends about the game, then they tried and fell in love with it. After knowing how to play the game, the group want to challenge each other to see who is the Pong master. So, we add another mode for two players to compete.

***3. Iteration 3***
