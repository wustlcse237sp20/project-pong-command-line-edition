package com.pongcle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Paddle {
    public Sprite sprite;
    public Body body;
    World world;
    float simulationScale = 10f;
    private float velocity = 50f;

    Paddle(World world, int paddleWidth, float simulationScale) {
        this.world = world;
        sprite = createSprite(paddleWidth, "bluerect.png", false);
        body = createBody(paddleWidth);
        this.simulationScale = simulationScale;
    }
    public Paddle() {
        world = new World(new Vector2(0, 0), true);
        sprite = createSprite(80, "", true);
        body = createBody(80);
    }

    /**
     * Creates renderable sprite.
     * @param paddleWidth int in pixels that decides paddle width.
     * @param texture string that decides which asset to use for the graphic
     * @param isTest if isTest, don't use a texture.
     * @return renderable sprite object.
     */
    public Sprite createSprite(int paddleWidth, String texture, boolean isTest) {
        Sprite sprite;
        if(isTest){
            sprite = new Sprite();
        }else{
            sprite = new Sprite(new Texture(texture));
        }
        sprite.setPosition(500, 50);
        sprite.setSize(20, paddleWidth);
        return sprite;
    }

    /**
     * Creates the paddle body for the physics simulation
     * @return rectangular body object.
     */
    public Body createBody(int paddleWidth) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(2, paddleWidth/10);
        body = world.createBody(bodyDef);
        PolygonShape paddleShape = new PolygonShape();
        paddleShape.setAsBox(sprite.getWidth()/(2*simulationScale), sprite.getHeight()/(2*simulationScale));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = paddleShape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        Fixture fixture = body.createFixture(fixtureDef);
        paddleShape.dispose();
        return body;
    }

    /**
     * Makes the renderable sprite sync up with the physics engine.
     * The physics engine is smaller than the renderable screen.
     */
    public void syncSpriteBody(){
        sprite.setPosition(body.getPosition().x*simulationScale-sprite.getWidth()/2, body.getPosition().y*simulationScale-sprite.getHeight()/2);
    }

    /**
     * Moves paddle up or down.
     * @param keyCodeUp keyCode of the key you want to move the paddle up
     * @param keyCodeDown keyCode of the key you want to move the paddle down
     */
    public void movePaddle(int keyCodeUp, int keyCodeDown) {
        if (Gdx.input.isKeyPressed(keyCodeUp)) {
            body.setLinearVelocity(0, velocity);
        } else if (Gdx.input.isKeyPressed(keyCodeDown)) {
            body.setLinearVelocity(0, -velocity);
        } else {
            body.setLinearVelocity(0, 0);
        }
    }

}
