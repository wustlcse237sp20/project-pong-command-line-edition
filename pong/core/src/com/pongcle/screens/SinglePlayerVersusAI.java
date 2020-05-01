package com.pongcle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pongcle.game.Pong;

/**
 * This is the level that one player plays pong against an AI.
 *
 * NOTE TO GRADERS: In game development, using a game engine like this,
 * we cannot encapsulate as much as other projects will be able to.
 * This is because in LibGDX (Game engine), objects must be created and defined in SHOW
 * Then those same objects must be used in RENDER, and there is no way to pass them to render.
 */
public class SinglePlayerVersusAI implements Screen {

    Pong game;
    AIPaddle aiPaddle;
    Paddle paddle;
    World world;

    Ball ball;

    float simulationScale = 10f;

    BitmapFont playerScoreText;
    BitmapFont aiScoreText;
    BitmapFont centerScreenText;

    int ballRadius = 40;
    int paddleWidth = 80;

    private String centerScreenString = "";

    int playerScore = 0;
    int aiScore = 0;

    int playUntilScore = 3;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    Matrix4 debugMatrix;
    boolean isGameOver = false;

    private int difficulty = 1;
    private int ballVelocity = 40;

    public SinglePlayerVersusAI(Pong game, int scoreToWin, int difficulty, int ballRadius, int paddleWidth){
        this.game = game;
        this.playUntilScore = scoreToWin;
        setBallVelocity(40);
        if(difficulty == 2){
            setBallVelocity(50);
        }
        if(difficulty == 3){
            setBallVelocity(60);
        }
        setDifficulty(difficulty);
        setBallRadius(ballRadius);
        setPaddleWidth(paddleWidth);

    }
    public SinglePlayerVersusAI(){}


        public void setCenterString(String str){
        centerScreenString = str;
    }
    public String getCenterString(){
        return centerScreenString;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public int getBallVelocity() {
        return ballVelocity;
    }
    public void setBallVelocity(int ballVelocity) {
        this.ballVelocity = ballVelocity;
    }
    public void setBallRadius(int rad){
        this.ballRadius = rad;
    }
    public int getBallRadius(){
        return this.ballRadius;
    }
    public void setPaddleWidth(int w){
        this.paddleWidth = w;
    }
    public int getPaddleWidth(){
        return this.paddleWidth;
    }
    /**
     * Default LibGDX function,
     * Creates all of the game objects: ball, paddles, text
     *
     */
    @Override
    public void show() {
        cam = new OrthographicCamera(1280, 720);
        cam.position.set(-200,-200,0);
        this.world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        debugMatrix=new Matrix4(cam.combined);
        debugMatrix.scale(3, 3, 1f);
        ball = new Ball(world, ballRadius, ballVelocity, simulationScale);
        paddle = new Paddle(world, paddleWidth, simulationScale);
        aiPaddle = new AIPaddle(world, paddleWidth, simulationScale);
        createGameText();
    }

    /**
     * Creates all of the text objects that draw text for this level.
     */
    public void createGameText(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Minecrafter.Reg.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 38;
        playerScoreText = generator.generateFont(parameter);
        centerScreenText = generator.generateFont(parameter);
        aiScoreText = generator.generateFont(parameter);
        aiScoreText.setColor(new Color(255,255,255,1));
        playerScoreText.setColor(new Color(255,255,255,1));
        centerScreenText.setColor(new Color(255,255,255,1));
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
    }

    /**
     * Gets called from checkBallBounds when the AI scores a point
     * Gives AI a point and resets ball to middle of screen.
     */
    public void aiScored(){
        aiScore++;
        ball.resetBall(true);
    }

    /**
     * Gets called from checkBallBounds when the player scores a point
     * Gives player a point and resets ball to middle of screen.
     */
    public void playerScored(){
        playerScore++;
        ball.resetBall(false);
    }

    /**
     * Gets called 60 times a second
     * Checks if a player has scored enough to be declared the winner.
     * Sets the text to say who won.
     * Ends the game
     * Lets player press enter to reset game.
     */
    public void checkScoresForWinner(){
        if(aiScore == playUntilScore){
            setCenterString("AI won!\nPress ENTER to play again");
            ball.body.setLinearVelocity(0,0);
            isGameOver = true;
        }
        if(playerScore == playUntilScore){
            setCenterString("You won!\nPress ENTER to play again");
            ball.body.setLinearVelocity(0,0);
            isGameOver = true;
        }
    }

    /**
     * Resets the game variables and restarts the game.
     */
    public void resetGame(){
        if(!isGameOver){ //only let them reset game when it is over.
            return;
        }
        setCenterString("");
        aiScore = 0;
        playerScore = 0;
        ball.resetBall(true);
        isGameOver = false;
    }

    /**
     * LibGDX engine function - is the game loop
     * Gets called 60 times a second
     * This handles everything dynamic about the game.
     * It moves the AI, it renders everything on the screen.
     * It updates the positioning of the objects.
     * @param delta value that represents your FPS.
     * You can use delta to compensate for some user's lagging.
     */
    @Override
    public void render(float delta) {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateObjects();
        drawObjects();
//        debugRenderer.render(world, debugMatrix); UNCOMMENT TO DEBUG PHYSICS ENGINE
    }

    /**
     * Gets called 60 times a second, updates the game objects.
     */
    public void updateObjects(){
        ball.syncSpriteBody();
        paddle.syncSpriteBody();
        aiPaddle.syncSpriteBody();
        int goals = ball.checkForGoals();
        if(goals==1){
            aiScored();
        }
        if(goals==2){
            playerScored();
        }
        ball.makeBallBounceOffWalls();
        aiPaddle.moveAI(ball.body, ball.sprite);
        paddle.movePaddle(Input.Keys.UP, Input.Keys.DOWN);
        checkScoresForWinner();
        ball.checkBallVelocitySlope();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            resetGame();
        }
    }
    /**
     * Draws items on screen
     */
    public void drawObjects(){
        game.batch.begin();
        playerScoreText.draw(game.batch, String.valueOf(playerScore), Gdx.graphics.getWidth()/2-100,playerScoreText.getXHeight()+10);
        aiScoreText.draw(game.batch, String.valueOf(aiScore), Gdx.graphics.getWidth()/2+100,aiScoreText.getXHeight()+10);
        centerScreenText.draw(game.batch, getCenterString(), Gdx.graphics.getWidth()/2-24*centerScreenString.length()/2,Gdx.graphics.getHeight()/2+38);
        ball.sprite.draw(game.batch);
        paddle.sprite.draw(game.batch);
        aiPaddle.sprite.draw(game.batch);
        game.batch.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
    }

}
