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
    World world;

    Sprite ballSprite;
    Body ballBody;

    Sprite player1Sprite;
    Body player1Body;

    Sprite player2Sprite;
    Body player2Body;

    BitmapFont player1ScoreText;
    BitmapFont player2ScoreText;
    BitmapFont centerScreenText;

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

    public TwoPlayer(Pong game, int scoreToWin, int difficulty){
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
        createBall();
        createPlayer1Paddle();
        createPlayer2Paddle();
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
     * Creates the game ball's renderable sprite and physics body.
     */
    public void createBall(){
        ballSprite = new Sprite(new Texture("purplecircle.png"));
        ballSprite.setPosition(Gdx.graphics.getWidth() / 2 - ballSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        ballSprite.setSize(40,40);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(ballSprite.getX()/10f, ballSprite.getY()/10f);
        ballBody = world.createBody(bodyDef);
        ballBody.setLinearVelocity(getBallVelocity(), getBallVelocity()/2);
        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(20/10);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 1f;
        fixtureDef.density = 1f;
        Fixture fixture = ballBody.createFixture(fixtureDef);
        ballShape.dispose();
    }

    /**
     * Creates the left (player 1) paddle's body and renderable sprite.
     */
    public void createPlayer1Paddle(){
        player1Sprite = new Sprite(new Texture("bluerect.png"));
        player1Sprite.setPosition(50, 50);
        player1Sprite.setSize(20,80);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(2, 8);
        player1Body = world.createBody(bodyDef);
        PolygonShape player1Shape = new PolygonShape();
        player1Shape.setAsBox(player1Sprite.getWidth()/20f, player1Sprite.getHeight()/20f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = player1Shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        Fixture fixture = player1Body.createFixture(fixtureDef);
        player1Shape.dispose();
    }

    /**
     * Creates the right (player 2) paddle's body and renderable sprite.
     */
    public void createPlayer2Paddle(){
        player2Sprite = new Sprite(new Texture("pinkrect.png"));
        player2Sprite.setPosition(1280-50, 50);
        player2Sprite.setSize(20,80);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(player2Sprite.getX()/10, player2Sprite.getY()/10);
        player2Body = world.createBody(bodyDef);
        PolygonShape player2Shape = new PolygonShape();
        player2Shape.setAsBox(player2Sprite.getWidth()/20f, player2Sprite.getHeight()/20f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = player2Shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        Fixture fixture = player2Body.createFixture(fixtureDef);
        player2Shape.dispose();
    }
    
    /**
     * Makes the ball bounce of the top and bottom of the screen (keeps ball in bounds).
     */
    public void makeBallBounceOffWalls(){
        if(ballSprite.getY() < 0){
            ballBody.setLinearVelocity(ballBody.getLinearVelocity().x, Math.abs(ballBody.getLinearVelocity().y));
        }
        if(ballSprite.getY() > 720-ballSprite.getHeight()){
            ballBody.setLinearVelocity(ballBody.getLinearVelocity().x, -Math.abs(ballBody.getLinearVelocity().y));
        }
    }

    /**
     * Checks is the ball has went off the screen,
     * if the ball went of the screen, someone scored
     * calls a function if the ai scored or player scored.
     */
    public void checkBallBounds(){
        if(ballSprite.getX() < -ballSprite.getWidth()){
            player2Scored();
        }
        if(ballSprite.getX() > Gdx.graphics.getWidth()+ballSprite.getWidth()){
            player1Scored();
        }
        if(ballSprite.getY()<-ballSprite.getHeight() || ballSprite.getY()>Gdx.graphics.getHeight()+ballSprite.getHeight()){
            ballBody.setTransform(50, (float) (4.00+Math.random()*68), 90);
            ballBody.setLinearVelocity(-getBallVelocity(), getBallVelocity()/2);
        }
    }

    /**
     * Moves the paddle UP and DOWN
     * When the user presses the W or S key.
     */
    public void movePlayer1(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player1Body.setLinearVelocity(0, 50);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player1Body.setLinearVelocity(0, -50);
        } else {
            player1Body.setLinearVelocity(0, 0);
        }
    }

    /**
     * Moves the paddle UP and DOWN
     * When the user presses the up or down arrow.
     */
    public void movePlayer2() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player2Body.setLinearVelocity(0, 30);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player2Body.setLinearVelocity(0, -30);
        } else {
            player2Body.setLinearVelocity(0, 0);
        }
    }

    /**
     * Gets called from checkBallBounds when the AI scores a point
     * Gives AI a point and resets ball to middle of screen.
     */
    public void player1Scored(){
        player1Score++;
        ballBody.setTransform(50, (float) (4.00+Math.random()*68), 90);
        ballBody.setLinearVelocity(getBallVelocity(), getBallVelocity()/2);
    }

    /**
     * Gets called from checkBallBounds when the player scores a point
     * Gives player a point and resets ball to middle of screen.
     */
    public void player2Scored(){
        player2Score++;
        ballBody.setTransform(50, (float) (4.00+Math.random()*68), 90);
        ballBody.setLinearVelocity(-getBallVelocity(), getBallVelocity()/2);
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
            ballBody.setLinearVelocity(0,0);
            isGameOver = true;
        }
        if(player2Score == playUntilScore){
            setCenterString("Player 2 won!\nPress ENTER to play again");
            ballBody.setLinearVelocity(0,0);
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
        ballBody.setLinearVelocity(getBallVelocity(), getBallVelocity()/2);
        isGameOver = false;
    }
    
    @Override
    public void render(float delta) {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ballSprite.setPosition(ballBody.getPosition().x*10-10, ballBody.getPosition().y*10+20);
        player1Sprite.setPosition(player1Body.getPosition().x*10, player1Body.getPosition().y*10);
        player2Sprite.setPosition(player2Body.getPosition().x*10, player2Body.getPosition().y*10);
        checkBallBounds();
        makeBallBounceOffWalls();
        movePlayer1();
        movePlayer2();
        checkScoresForWinner();
        checkBallVelocitySlope();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            resetGame();
        }
        drawObjects();
    }

    /**
     * Draws items on screen
     */
    public void drawObjects(){
        game.batch.begin();
        player1ScoreText.draw(game.batch, String.valueOf(player1Score), Gdx.graphics.getWidth()/2-100,player1ScoreText.getXHeight()+10);
        player2ScoreText.draw(game.batch, String.valueOf(player2Score), Gdx.graphics.getWidth()/2+100,player2ScoreText.getXHeight()+10);
        centerScreenText.draw(game.batch, getCenterString(), Gdx.graphics.getWidth()/2-24*centerScreenString.length()/2,Gdx.graphics.getHeight()/2+38);
        ballSprite.draw(game.batch);
        player1Sprite.draw(game.batch);
        player2Sprite.draw(game.batch);
        game.batch.end();
        runTests();
    }
    /**
     * Checks the ball's slope, ensures it's not too steep
     * If a ball has a high slope, it will take forever to get
     * accoss the screen.
     */
    public void checkBallVelocitySlope(){
        float velX = ballBody.getLinearVelocity().x;
        float velY = ballBody.getLinearVelocity().y;
        float slope = Math.abs(velY / velX);
        if(slope > 0.8){
            ballBody.setLinearVelocity((float) (velX*1.20), (float) (velY*0.80));
        }
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

    /**
     * Runs all of the tests and prints out how many passed.
     * Since our project is a game, we cannot use JUnit tests.
     * This is because we need our tests to run 60 times a second.
     * This is because our game is dynamic, things are constantly moving and changing.
     */
    public void runTests(){
        int numPassedTests = 0;
        int numTests = 4;
        numPassedTests+=testScoresInBounds();
        numPassedTests+=testBallInBounds();
        numPassedTests+=testBallMovement();
        numPassedTests+=testScreenText();


        System.out.println("Passed "+String.valueOf(numPassedTests)+"/"+String.valueOf(numTests)+" tests");
    }

    /**
     * @return If scores are in possible range, return 1. Otherwise return 0
     */
    public int testScoresInBounds(){
        if(player1Score < 0 || player1Score > playUntilScore){
            return 0;
        }
        if(player2Score < 0 || player2Score > playUntilScore){
            return 0;
        }
        return 1;
    }
 
    /**
     * @return If ball position is in possible range, return 1. Otherwise return 0
     */
    public int testBallInBounds(){
        if(ballSprite.getY() < -ballSprite.getHeight() || ballSprite.getY() > Gdx.graphics.getHeight()+ballSprite.getHeight()){
            return 0;
        }
        return 1;
    }

    /**
     * @return If ball is not moving without the game being over, return 0, otherwise return 1.
     */
    public int testBallMovement(){
        if(ballBody.getLinearVelocity().x == 0 && !isGameOver){
            return 0;
        }
        if(ballBody.getLinearVelocity().y == 0 && !isGameOver){
            return 0;
        }
        return 1;
    }
    /**
     * If centerString is not empty while game is going, test fails
     * @return if screenText is valid
     */
    public int testScreenText(){
        if(!isGameOver && !getCenterString().equals("")){
            return 0;
        }
        return 1;
    }
}
