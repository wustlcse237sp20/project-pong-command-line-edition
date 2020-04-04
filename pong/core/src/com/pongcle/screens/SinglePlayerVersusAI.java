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

public class SinglePlayerVersusAI implements Screen {

    Pong game;
    World world;

    Sprite ballSprite;
    Body ballBody;

    Sprite paddleSprite;
    Body paddleBody;

    Sprite testpaddleSprite;
    Body testpaddleBody;

    Sprite aiSprite;
    Body aiBody;

    BitmapFont playerScoreText;
    BitmapFont aiScoreText;
    BitmapFont centerScreenText;
    String centerScreenString = "";

    int aiFrames = 0;

    int playerScore = 0;
    int aiScore = 0;

    int playUntilScore = 3;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    Matrix4 debugMatrix;
    boolean isGameOver = false;

    public SinglePlayerVersusAI(Pong game){
        this.game = game;
    }

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
    public void createBall(){
        ballSprite = new Sprite(new Texture("purplecircle.png"));
        ballSprite.setPosition(Gdx.graphics.getWidth() / 2 - ballSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        ballSprite.setSize(40,40);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(ballSprite.getX()/10f, ballSprite.getY()/10f);
        ballBody = world.createBody(bodyDef);
        ballBody.setLinearVelocity(30, 30);
        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 1f;
        fixtureDef.density = 1f;
        Fixture fixture = ballBody.createFixture(fixtureDef);
        ballShape.dispose();
    }
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
    public void makeBallBounceOffWalls(){
        if(ballSprite.getY() < 0){
            ballBody.setLinearVelocity(ballBody.getLinearVelocity().x, Math.abs(ballBody.getLinearVelocity().y));
        }
        if(ballSprite.getY() > 720-ballSprite.getHeight()){
            ballBody.setLinearVelocity(ballBody.getLinearVelocity().x, -Math.abs(ballBody.getLinearVelocity().y));
        }
    }
    public void checkBallBounds(){
        if(ballSprite.getX() < -ballSprite.getWidth()){
            aiScored();
        }
        if(ballSprite.getX() > Gdx.graphics.getWidth()+ballSprite.getWidth()){
            playerScored();
        }
    }
    public void aiScored(){
        aiScore++;
        ballBody.setTransform(50, 50, 90);
        System.out.println("reset > ");
    }
    public void playerScored(){
        playerScore++;
        ballBody.setTransform(50, 50, 90);
        System.out.println("reset < ");
    }

    public void checkScoresForWinner(){
        if(aiScore == playUntilScore){
            centerScreenString = "AI won!\nPress ENTER to play again";
            ballBody.setLinearVelocity(0,0);
            isGameOver = true;
        }
        if(playerScore == playUntilScore){
            centerScreenString = "You won!\nPress ENTER to play again";
            ballBody.setLinearVelocity(0,0);
            isGameOver = true;
        }
    }

    public void resetGame(){
        if(!isGameOver){ //only let them reset game when it is over.
            return;
        }
        centerScreenString = "";
        aiScore = 0;
        playerScore = 0;
        ballBody.setLinearVelocity(30, 30);
        isGameOver = false;

    }
    @Override
    public void render(float delta) {
        makeBallBounceOffWalls();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        checkBallBounds();
        ballSprite.setPosition(ballBody.getPosition().x*10-10, ballBody.getPosition().y*10+20);
        paddleSprite.setPosition(paddleBody.getPosition().x*10, paddleBody.getPosition().y*10);

        aiSprite.setPosition(aiBody.getPosition().x*10, aiBody.getPosition().y*10);

        moveAI();
        movePaddle();
        checkScoresForWinner();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            resetGame();
        }

        game.batch.begin();

        playerScoreText.draw(game.batch, String.valueOf(playerScore), Gdx.graphics.getWidth()/2-100,playerScoreText.getXHeight()+10);
        aiScoreText.draw(game.batch, String.valueOf(aiScore), Gdx.graphics.getWidth()/2+100,playerScoreText.getXHeight()+10);
        aiScoreText.draw(game.batch, centerScreenString, Gdx.graphics.getWidth()/2-24*centerScreenString.length()/2,Gdx.graphics.getHeight()/2+38);

        ballSprite.draw(game.batch);
        paddleSprite.draw(game.batch);
        aiSprite.draw(game.batch);
        game.batch.end();

//        debugRenderer.render(world, debugMatrix); UNCOMMENT TO DEBUG PHYSICS ENGINE
    }
    public void movePaddle() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            paddleBody.setLinearVelocity(0, 30);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            paddleBody.setLinearVelocity(0, -30);
        } else {
            paddleBody.setLinearVelocity(0, 0);
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
}
