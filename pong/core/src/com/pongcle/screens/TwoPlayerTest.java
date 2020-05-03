package com.pongcle.screens;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TwoPlayerTest {

    @Test
    public void setCenterString() {
        TwoPlayer twoPlayer = new TwoPlayer();
        twoPlayer.setCenterString("test");
        assertEquals("test", twoPlayer.getCenterString());
    }

    @Test
    public void getCenterString() {
        TwoPlayer twoPlayer = new TwoPlayer();
        assertEquals("", twoPlayer.getCenterString());
    }

    @Test
    public void setDifficulty() {
        TwoPlayer twoPlayer = new TwoPlayer();
        twoPlayer.setDifficulty(3);
        assertEquals(3, twoPlayer.getDifficulty());
    }

    @Test
    public void getDifficulty() {
    	TwoPlayer twoPlayer = new TwoPlayer();
        assertEquals(1, twoPlayer.getDifficulty());
    }
    
    @Test
    public void setBallVelocity() {
        TwoPlayer twoPlayer = new TwoPlayer();
        twoPlayer.setBallVelocity(50);
        assertEquals(50, twoPlayer.getBallVelocity());
    }

    @Test
    public void getBallVelocity() {
    	TwoPlayer twoPlayer = new TwoPlayer();
        assertEquals(40, twoPlayer.getBallVelocity());
    }

    @Test
    public void setBallRadius() {
        TwoPlayer twoPlayer = new TwoPlayer();
        twoPlayer.setBallRadius(50);
        assertEquals(50, twoPlayer.getBallRadius());
    }

    @Test
    public void getBallRadius() {
        TwoPlayer twoPlayer = new TwoPlayer();
        assertEquals(40, twoPlayer.getBallRadius());
    }

    @Test
    public void setPaddleWidth() {
        TwoPlayer twoPlayer = new TwoPlayer();
        twoPlayer.setPaddleWidth(90);
        assertEquals(90, twoPlayer.getPaddleWidth());
    }

    @Test
    public void getPaddleWidth() {
        TwoPlayer twoPlayer = new TwoPlayer();
        assertEquals(80, twoPlayer.getPaddleWidth());
    }
}
