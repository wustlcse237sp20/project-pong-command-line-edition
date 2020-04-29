package com.pongcle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Paddle {
    Sprite sprite;
    Body body;
    World world;
    float simulationScale = 10f;
    private float velocity = 50f;

    Paddle(World world, int paddleWidth) {
        this.world = world;
        sprite = createSprite(paddleWidth);
        body = createBody(paddleWidth);
    }

    public Sprite createSprite(int paddleWidth) {
        Sprite sprite = new Sprite(new Texture("bluerect.png"));
        sprite.setPosition(50, 50);
        sprite.setSize(20, paddleWidth);
        return sprite;
    }

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

    public void syncSpriteBody(){
        sprite.setPosition(body.getPosition().x*simulationScale, body.getPosition().y*simulationScale);
    }

    /**
     * Moves the paddle UP and DOWN
     * When the user presses the up or down arrow.
     */
    public void movePaddle() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.setLinearVelocity(0, velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            body.setLinearVelocity(0, -velocity);
        } else {
            body.setLinearVelocity(0, 0);
        }
    }

}
