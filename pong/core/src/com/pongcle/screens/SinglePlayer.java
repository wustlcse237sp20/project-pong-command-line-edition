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

public class SinglePlayer implements Screen {

    Pong game;
    World world;

    Sprite ballSprite;
    Body ballBody;

    Sprite paddleSprite;
    Body paddleBody;


    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    Matrix4 debugMatrix;


    public SinglePlayer(Pong game){
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
        createPlayerPaddle();
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

//        paddleShape.setAsBox(1, 4);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = paddleShape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        Fixture fixture = paddleBody.createFixture(fixtureDef);
        paddleShape.dispose();
    }

    public void makeBallBounceOffWalls(){
        if(ballBody.getPosition().y < 0){
            ballBody.setLinearVelocity(ballBody.getLinearVelocity().x, Math.abs(ballBody.getLinearVelocity().y));
        }
        if(ballSprite.getX() > 1280-ballSprite.getWidth()){
            ballBody.setLinearVelocity(-Math.abs(ballBody.getLinearVelocity().x), ballBody.getLinearVelocity().y);
        }
        if(ballSprite.getY() > 720-ballSprite.getHeight()){
            ballBody.setLinearVelocity(ballBody.getLinearVelocity().x, -Math.abs(ballBody.getLinearVelocity().y));
        }
    }
    @Override
    public void render(float delta) {
        makeBallBounceOffWalls();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        System.out.println(paddleBody.getLinearVelocity().y);
        ballSprite.setPosition(ballBody.getPosition().x*10, ballBody.getPosition().y*10);
        paddleSprite.setPosition(paddleBody.getPosition().x*10, paddleBody.getPosition().y*10);

        movePaddle();
        System.out.println(ballBody.getPosition().x);
        game.batch.begin();
        ballSprite.draw(game.batch);
        paddleSprite.draw(game.batch);
        game.batch.end();

        debugRenderer.render(world, debugMatrix);

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
