package com.pongcle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class AIPaddle extends Paddle {


    AIPaddle(World world, int paddleWidth, float simulationScale) {
        super(world, paddleWidth, simulationScale);
        sprite = createSprite(paddleWidth, "pinkrect.png", false);
        body = createBody(paddleWidth);
        body.setTransform(Gdx.graphics.getWidth()/10-2, 50, 0);

    }
    int aiFrames = 0;
    public void moveAI(Body ballBody, Sprite ballSprite){
        aiFrames++;
        aiFrames %= 128;
        if(ballBody.getLinearVelocity().x < 0 || ballSprite.getX() < Gdx.graphics.getWidth()/2){
            body.setLinearVelocity(0, 0);
            return;
        }
        if(aiFrames %  7 != 0){ //make ai only change directions or speed a max of ~10 times a second
            return;
        }
        if(ballBody.getPosition().y < body.getPosition().y){
            body.setLinearVelocity(0, (float) -Math.abs(ballBody.getLinearVelocity().y * (0.75+0.25*Math.random())));
        }else{
            body.setLinearVelocity(0, (float) Math.abs(ballBody.getLinearVelocity().y*(0.75+0.25*Math.random())));
        }
    }

}
