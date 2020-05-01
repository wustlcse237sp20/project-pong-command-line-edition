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
 * This is two-player level pong.
 *
 */

public class TwoPlayer implements Screen {

    Pong game;
    Paddle player1Paddle;
    Paddle player2Paddle;
    World world;

    Ball ball;

    float simulationScale = 10f;

    BitmapFont player1ScoreText;
    BitmapFont player2ScoreText;
    BitmapFont centerScreenText;

    int ballRadius = 40;
    int paddleWidth = 80;

    private String centerScreenString = "";

    int player1Score = 0;
    int player2Score = 0;

    int playUntilScore = 3;

    boolean isGameOver = false;

    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    Matrix4 debugMatrix;

    private int difficulty = 1;
    private int ballVelocity = 40;

    public TwoPlayer(Pong game, int scoreToWin, int difficulty, int ballRadius, int paddleWidth){
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

    public void setCenterString(String str){
        centerScreenString = str;
    }
    public String getCenterString(){
        return centerScreenString;
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
        debugMatrix.scale(2, 2, 1f);
        ball = new Ball(world, ballRadius, ballVelocity, simulationScale);
        player1Paddle = new Paddle(world, paddleWidth, simulationScale);
        player2Paddle = new Paddle(world, paddleWidth, simulationScale);
        player2Paddle.body.setTransform(Gdx.graphics.getWidth()/10-5, 50, 0);
        player2Paddle.sprite.setTexture(new Texture("pinkrect.png"));
        createGameText();
    }

    /**
     * Creates all of the text objects that draw text for this level.
     */
    public void createGameText(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Minecrafter.Reg.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 38;
        player1ScoreText = generator.generateFont(parameter);
        centerScreenText = generator.generateFont(parameter);
        player2ScoreText = generator.generateFont(parameter);
        player1ScoreText.setColor(new Color(255,255,255,1));
        player2ScoreText.setColor(new Color(255,255,255,1));
        centerScreenText.setColor(new Color(255,255,255,1));
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
    }


    /**
     * Gets called from checkBallBounds when the AI scores a point
     * Gives AI a point and resets ball to middle of screen.
     */
    public void player1Scored(){
        player1Score++;
        ball.resetBall(true);
    }

    /**
     * Gets called from checkBallBounds when the player scores a point
     * Gives player a point and resets ball to middle of screen.
     */
    public void player2Scored(){
        player2Score++;
        ball.resetBall(true);
    }

    /**
     * Gets called 60 times a second
     * Checks if a player has scored enough to be declared the winner.
     * Sets the text to say who won.
     * Ends the game
     * Lets player press enter to reset game.
     */
    public void checkScoresForWinner(){
        if(player1Score == playUntilScore){
            setCenterString("Player 1 won!\nPress ENTER to play again");
            ball.body.setLinearVelocity(0,0);
            isGameOver = true;
        }
        if(player2Score == playUntilScore){
            setCenterString("Player 2 won!\nPress ENTER to play again");
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
        player1Score = 0;
        player2Score = 0;
        ball.resetBall(true);
        isGameOver = false;
    }
    
    @Override
    public void render(float delta) {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateObjects();
        drawObjects();
    }

    /**
     * Gets called 60 times a second, updates the game objects.
     */
    public void updateObjects(){
        ball.syncSpriteBody();
        player1Paddle.syncSpriteBody();
        player2Paddle.syncSpriteBody();
        int goals = ball.checkForGoals();
        if(goals==1){
            player2Scored();
        }
        if(goals==2){
            player1Scored();
        }
        ball.makeBallBounceOffWalls();
        player1Paddle.movePaddle(Input.Keys.W, Input.Keys.S);
        player2Paddle.movePaddle(Input.Keys.UP, Input.Keys.DOWN);
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
        player1ScoreText.draw(game.batch, String.valueOf(player1Score), Gdx.graphics.getWidth()/2-100,player1ScoreText.getXHeight()+10);
        player2ScoreText.draw(game.batch, String.valueOf(player2Score), Gdx.graphics.getWidth()/2+100,player2ScoreText.getXHeight()+10);
        centerScreenText.draw(game.batch, getCenterString(), Gdx.graphics.getWidth()/2-24*centerScreenString.length()/2,Gdx.graphics.getHeight()/2+38);
        ball.sprite.draw(game.batch);
        player1Paddle.sprite.draw(game.batch);
        player2Paddle.sprite.draw(game.batch);
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
