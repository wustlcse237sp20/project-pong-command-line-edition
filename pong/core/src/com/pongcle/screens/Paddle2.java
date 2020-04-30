package com.pongcle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Paddle2 extends Paddle{

    Paddle2(World world, int paddleWidth, float simulationScale) {
        super(world, paddleWidth, simulationScale);
        sprite = createSprite(paddleWidth, "pinkrect.png");
        body = createBody(paddleWidth);
        body.setTransform(Gdx.graphics.getWidth()/10-5, 50, 0);
    }

}
