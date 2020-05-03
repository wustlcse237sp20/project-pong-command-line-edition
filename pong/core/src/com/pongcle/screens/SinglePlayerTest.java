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
        SinglePlayer singlePlayer = new SinglePlayer();
        assertEquals("", singlePlayer.getCenterString());
    }

    @Test
    public void setDifficulty() {
        SinglePlayer singlePlayer = new SinglePlayer();
        singlePlayer.setDifficulty(3);
        assertEquals(3, singlePlayer.getDifficulty());
    }

    @Test
    public void getDifficulty() {
    	SinglePlayer singlePlayer = new SinglePlayer();
        assertEquals(1, singlePlayer.getDifficulty());
    }
    
    @Test
    public void setBallVelocity() {
        SinglePlayer singlePlayer = new SinglePlayer();
        singlePlayer.setBallVelocity(50);
        assertEquals(50, singlePlayer.getBallVelocity());
    }

    @Test
    public void getBallVelocity() {
    	SinglePlayer singlePlayer = new SinglePlayer();
        assertEquals(40, singlePlayer.getBallVelocity());
    }

    @Test
    public void setBallRadius() {
        SinglePlayer singlePlayer = new SinglePlayer();
        singlePlayer.setBallRadius(50);
        assertEquals(50, singlePlayer.getBallRadius());
    }

    @Test
    public void getBallRadius() {
        SinglePlayer singlePlayer = new SinglePlayer();
        assertEquals(40, singlePlayer.getBallRadius());
    }

    @Test
    public void setPaddleWidth() {
        SinglePlayer singlePlayer = new SinglePlayer();
        singlePlayer.setPaddleWidth(90);
        assertEquals(90, singlePlayer.getPaddleWidth());
    }

    @Test
    public void getPaddleWidth() {
        SinglePlayer singlePlayer = new SinglePlayer();
        assertEquals(80, singlePlayer.getPaddleWidth());
    }
}
