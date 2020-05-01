package com.pongcle.screens;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SinglePlayerTest {

    @Test
    public void setCenterString() {
        SinglePlayer singlePlayer = new SinglePlayer();
        singlePlayer.setCenterString("test");
        assertEquals("test", singlePlayer.getCenterString());
    }

    @Test
    public void getCenterString() {
    }

    @Test
    public void setDifficulty() {
    }

    @Test
    public void getDifficulty() {
    }

    @Test
    public void getBallVelocity() {
    }

    @Test
    public void setBallVelocity() {
    }

    @Test
    public void setBallRadius() {
    }

    @Test
    public void getBallRadius() {
    }

    @Test
    public void setPaddleWidth() {
    }

    @Test
    public void getPaddleWidth() {
    }
}
