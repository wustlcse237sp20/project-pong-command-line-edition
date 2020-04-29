package com.pongcle.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pongcle.game.Pong;
import com.pongcle.screens.Ball;

import static org.junit.Assert.*;

public class BallTest extends GdxInitializer {

    @org.junit.Test
    public void testBodyPosition() {
        Ball ball = new Ball();
        ball.body.setTransform(0,0,0);
        assertEquals(ball.body.getPosition().x, 0f, 1f);
    }
    @org.junit.Test
    public void testSpritePosition() {
        Ball ball = new Ball();
        ball.sprite.setPosition(0,0);
        assertEquals(ball.sprite.getX(), 0f, 1f);
    }
}