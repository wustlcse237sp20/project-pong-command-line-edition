# Pong command line edition
This is mini pong game written in Java. There are 3 game modes namely single player, single player vs AI and two players. Difficulties can be adjusted using command lines.

## Install
***1. Window***

- To start game, go to '/pong' and type 'gradlew desktop:run'

***2. Mac***
- First install gradle. To do that, simply run 'brew install gradle'
- Go to '/pong', then type 'gradle wrapper'
- To start game, run './gradlew desktop:run'

***3. Notes***
- If that doesn't work, it's likely something to do with your Java install (JRE or JDK).
- If it doesn't work on Windows, and you have java installed, check your environment variables.
- To develop with an IDE, LibGDX highly recommends InteliJ, I tried to use Eclipse for a couple hours and couldn't get it to import and build. I got InteliJ working using the Desktop instructions here: https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA

## Iteration
***1. Iteration 1***
- User story 1: Users are getting bored, especially during the quarantine due to COVID-19. They want to play a fun game to improve their mood as well kill some time. Therefore, we come up with a mini pong game that users can control a paddle to hit the ball against a wall. (finished)
- User story 2: The game is fun at first, but the users started to get bored of bouncing the ball into a steady wall. So, we create another mode for them to play against an AI. (finished)

***2. Iteration 2***
- User story 1: An user told his friends about the game, then they tried and fell in love with it. After knowing how to play the game, the group want to challenge each other to see who is the Pong master. So, we add another mode for two players to compete.

***3. Iteration 3***
