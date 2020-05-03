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
    @org.junit.Test
    public void testResetBall() {
        Ball ball = new Ball();
        ball.resetBall(true);
        assertEquals(true, (ball.body.getPosition().x>0 && ball.body.getPosition().x<128)); //128 represents width of physics simulation
        assertEquals(true, (ball.body.getLinearVelocity().x < 0)); //since ball velocity is set left, it should be negative
        assertEquals(true, (ball.body.getPosition().y>0 && ball.body.getPosition().y<72)); //72 represents height of physics simulation
    }

    @org.junit.Test
    public void testCheckForGoals() {
        Ball ball = new Ball();
        assertEquals(0, ball.checkForGoals());
    }

    @org.junit.Test
    public void testSyncSpriteBody() {
        Ball ball = new Ball();
        ball.syncSpriteBody();
        assertEquals(ball.body.getPosition().x*10, ball.sprite.getX(), ball.ballRadius);
    }

}