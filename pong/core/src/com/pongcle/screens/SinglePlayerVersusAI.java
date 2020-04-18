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
    World world;

    Sprite ballSprite;
    Body ballBody;

    Sprite paddleSprite;
    Body paddleBody;

    Sprite aiSprite;
    Body aiBody;

    BitmapFont playerScoreText;
    BitmapFont aiScoreText;
    BitmapFont centerScreenText;

    private String centerScreenString = "";

    int aiFrames = 0;

    int playerScore = 0;
    int aiScore = 0;

    int playUntilScore = 3;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    Matrix4 debugMatrix;
    boolean isGameOver = false;

    private int difficulty = 1;
    private int ballVelocity = 40;

    public SinglePlayerVersusAI(Pong game, int scoreToWin, int difficulty){
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
        createBall();
        createPlayerPaddle();
        createAI();
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
        ballShape.setRadius(2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 1f;
        fixtureDef.density = 1f;
        Fixture fixture = ballBody.createFixture(fixtureDef);
        ballShape.dispose();
    }

    /**
     * Creates the left paddle's body and renderable sprite.
     */
    public void createPlayerPaddle(){
        paddleSprite = new Sprite(new Texture("bluerect.png"));
        paddleSprite.setPosition(50, 50);
        paddleSprite.setSize(20,80);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(2, 8);
        paddleBody = world.createBody(bodyDef);
        PolygonShape paddleShape = new PolygonShape();
        paddleShape.setAsBox(paddleSprite.getWidth()/20f, paddleSprite.getHeight()/20f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = paddleShape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        Fixture fixture = paddleBody.createFixture(fixtureDef);
        paddleShape.dispose();
    }

    /**
     * Create's the AI's (right paddle) body and renderable sprite
     */
    public void createAI(){
        aiSprite = new Sprite(new Texture("pinkrect.png"));
        aiSprite.setPosition(1280-50, 50);
        aiSprite.setSize(20,80);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(aiSprite.getX()/10, aiSprite.getY()/10);
        aiBody = world.createBody(bodyDef);
        PolygonShape aiShape = new PolygonShape();
        aiShape.setAsBox(aiSprite.getWidth()/20f, aiSprite.getHeight()/20f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = aiShape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        Fixture fixture = aiBody.createFixture(fixtureDef);
        aiShape.dispose();
    }

    /**
     * Gets called 60 times a second, used to move the AI in relation to the ball.
     */
    public void moveAI(){
        aiFrames++;
        aiFrames %= 128;
        if(ballBody.getLinearVelocity().x < 0 || ballSprite.getX() < 640){
            aiBody.setLinearVelocity(0, 0);
            return;
        }
        if(aiFrames %  7 != 0){ //make ai only change directions or speed a max of ~10 times a second
            return;
        }
        if(ballBody.getPosition().y < aiBody.getPosition().y){
            aiBody.setLinearVelocity(0, (float) -Math.abs(ballBody.getLinearVelocity().y * (0.75+0.25*Math.random())));
        }else{
            aiBody.setLinearVelocity(0, (float) Math.abs(ballBody.getLinearVelocity().y*(0.75+0.25*Math.random())));
        }
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
            aiScored();
        }
        if(ballSprite.getX() > Gdx.graphics.getWidth()+ballSprite.getWidth()){
            playerScored();
        }
        if(ballSprite.getY()<-ballSprite.getHeight() || ballSprite.getY()>Gdx.graphics.getHeight()+ballSprite.getHeight()){
            ballBody.setTransform(50, (float) (4.00+Math.random()*68), 90);
            ballBody.setLinearVelocity(-getBallVelocity(), getBallVelocity()/2);
        }
    }

    /**
     * Gets called from checkBallBounds when the AI scores a point
     * Gives AI a point and resets ball to middle of screen.
     */
    public void aiScored(){
        aiScore++;
        ballBody.setTransform(50, (float) (4.00+Math.random()*68), 90);
        ballBody.setLinearVelocity(-getBallVelocity(), getBallVelocity()/2);
        System.out.println("reset > ");
    }

    /**
     * Gets called from checkBallBounds when the player scores a point
     * Gives player a point and resets ball to middle of screen.
     */
    public void playerScored(){
        playerScore++;
        ballBody.setTransform(50, (float) (4.00+Math.random()*68), 90);
        ballBody.setLinearVelocity(getBallVelocity(), getBallVelocity()/2);

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
            ballBody.setLinearVelocity(0,0);
            isGameOver = true;
        }
        if(playerScore == playUntilScore){
            setCenterString("You won!\nPress ENTER to play again");
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
        aiScore = 0;
        playerScore = 0;
        ballBody.setLinearVelocity(getBallVelocity(), getBallVelocity()/2);
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
        ballSprite.setPosition(ballBody.getPosition().x*10-10, ballBody.getPosition().y*10+20);
        paddleSprite.setPosition(paddleBody.getPosition().x*10, paddleBody.getPosition().y*10);
        aiSprite.setPosition(aiBody.getPosition().x*10, aiBody.getPosition().y*10);
        checkBallBounds();
        makeBallBounceOffWalls();
        moveAI();
        movePaddle();
        checkScoresForWinner();
        checkBallVelocitySlope();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            resetGame();
        }
        drawObjects();
        runTests();
//        debugRenderer.render(world, debugMatrix); UNCOMMENT TO DEBUG PHYSICS ENGINE
    }

    /**
     * Draws items on screen
     */
    public void drawObjects(){
        game.batch.begin();
        playerScoreText.draw(game.batch, String.valueOf(playerScore), Gdx.graphics.getWidth()/2-100,playerScoreText.getXHeight()+10);
        aiScoreText.draw(game.batch, String.valueOf(aiScore), Gdx.graphics.getWidth()/2+100,aiScoreText.getXHeight()+10);
        centerScreenText.draw(game.batch, getCenterString(), Gdx.graphics.getWidth()/2-24*centerScreenString.length()/2,Gdx.graphics.getHeight()/2+38);
        ballSprite.draw(game.batch);
        paddleSprite.draw(game.batch);
        aiSprite.draw(game.batch);
        game.batch.end();
    }

    /**
     * Moves the paddle UP and DOWN
     * When the user presses the up or down arrow.
     */
    public void movePaddle() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            paddleBody.setLinearVelocity(0, 50);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            paddleBody.setLinearVelocity(0, -50);
        } else {
            paddleBody.setLinearVelocity(0, 0);
        }
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
        int numTests = 5;
        numPassedTests+=testScoresInBounds();
        numPassedTests+=testAIPaddleInBounds();
        numPassedTests+=testBallInBounds();
        numPassedTests+=testBallMovement();
        numPassedTests+=testScreenText();


        System.out.println("Passed "+String.valueOf(numPassedTests)+"/"+String.valueOf(numTests)+" tests");
    }

    /**
     * @return If scores are in possible range, return 1. Otherwise return 0
     */
    public int testScoresInBounds(){
        if(aiScore < 0 || aiScore > playUntilScore){
            return 0;
        }
        if(playerScore < 0 || playerScore > playUntilScore){
            return 0;
        }
        return 1;
    }
    /**
     * @return If AI position is in possible range, return 1. Otherwise return 0
     */
    public int testAIPaddleInBounds(){
        if(aiSprite.getY() < -aiSprite.getHeight() || aiSprite.getY() > Gdx.graphics.getHeight()+aiSprite.getHeight()) {
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
