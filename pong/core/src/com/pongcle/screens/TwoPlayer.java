package com.pongcle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pongcle.game.Pong;

public class TwoPlayer implements Screen {

    Pong game;
    World world;

    Sprite ballSprite;
    Body ballBody;

    Sprite player1Sprite;
    Body player1Body;

    Sprite player2Sprite;
    Body player2Body;

    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    Matrix4 debugMatrix;

    public TwoPlayer(Pong game){
        this.game = game;
    }

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
        ballShape.setRadius(20/10);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 1f;
        fixtureDef.density = 1f;
        Fixture fixture = ballBody.createFixture(fixtureDef);
        ballShape.dispose();
    }
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
    
    public void makeBallBounceOffWalls(){
        if(ballBody.getPosition().y < 0){
            ballBody.setLinearVelocity(ballBody.getLinearVelocity().x, Math.abs(ballBody.getLinearVelocity().y));
        }
//        if(ballBody.getPosition().x < 0){
//            ballBody.setLinearVelocity(-ballBody.getLinearVelocity().x, ballBody.getLinearVelocity().y);
//        }
        if(ballSprite.getY() > 720-ballSprite.getHeight()){
            ballBody.setLinearVelocity(ballBody.getLinearVelocity().x, -Math.abs(ballBody.getLinearVelocity().y));
        }
    }

    public void movePlayer1(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player1Body.setLinearVelocity(0, 30);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player1Body.setLinearVelocity(0, -30);
        } else {
            player1Body.setLinearVelocity(0, 0);
        }
    }
    public void movePlayer2() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player2Body.setLinearVelocity(0, 30);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player2Body.setLinearVelocity(0, -30);
        } else {
            player2Body.setLinearVelocity(0, 0);
        }
    }
    
    @Override
    public void render(float delta) {
        makeBallBounceOffWalls();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        System.out.println(player1Body.getLinearVelocity().y);
        ballSprite.setPosition(ballBody.getPosition().x*10, ballBody.getPosition().y*10);
        player1Sprite.setPosition(player1Body.getPosition().x*10, player1Body.getPosition().y*10);
        player2Sprite.setPosition(player2Body.getPosition().x*10, player2Body.getPosition().y*10);
        movePlayer1();
        movePlayer2();
        game.batch.begin();
        ballSprite.draw(game.batch);
        player1Sprite.draw(game.batch);
        player2Sprite.draw(game.batch);
        game.batch.end();
        debugRenderer.render(world, debugMatrix);
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
