package com.pongcle.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pongcle.game.Pong;
import com.pongcle.screens.Ball;
import com.pongcle.screens.Paddle;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaddleTest extends GdxInitializer {
    @Test
    public void testBodyPosition() {
        Paddle paddle = new Paddle();
        paddle.body.setTransform(0,0,0);
        assertEquals(paddle.body.getPosition().x, 0f, 1f);
    }
    @Test
    public void testSpritePosition() {
        Paddle paddle = new Paddle();
        paddle.sprite.setPosition(0,0);
        assertEquals(paddle.sprite.getX(), 0f, 1f);
    }
    @Test
    public void testSyncSpriteBody() {
        Paddle paddle = new Paddle();
        paddle.syncSpriteBody();
        assertEquals(paddle.body.getPosition().x*10, paddle.sprite.getX(), paddle.sprite.getHeight());
    }
}