package com.pongcle.screens;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SinglePlayerVersusAITest {

    @Test
    public void setCenterString() {
        SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        singlePlayerVersusAI.setCenterString("test");
        assertEquals("test", singlePlayerVersusAI.getCenterString());
    }

    @Test
    public void getCenterString() {
        SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        assertEquals("", singlePlayerVersusAI.getCenterString());
    }

    @Test
    public void setDifficulty() {
        SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        singlePlayerVersusAI.setDifficulty(3);
        assertEquals(3, singlePlayerVersusAI.getDifficulty());
    }

    @Test
    public void getDifficulty() {
    	SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        assertEquals(1, singlePlayerVersusAI.getDifficulty());
    }
    
    @Test
    public void setBallVelocity() {
        SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        singlePlayerVersusAI.setBallVelocity(50);
        assertEquals(50, singlePlayerVersusAI.getBallVelocity());
    }

    @Test
    public void getBallVelocity() {
    	SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        assertEquals(40, singlePlayerVersusAI.getBallVelocity());
    }

    @Test
    public void setBallRadius() {
        SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        singlePlayerVersusAI.setBallRadius(50);
        assertEquals(50, singlePlayerVersusAI.getBallRadius());
    }

    @Test
    public void getBallRadius() {
        SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        assertEquals(40, singlePlayerVersusAI.getBallRadius());
    }

    @Test
    public void setPaddleWidth() {
        SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        singlePlayerVersusAI.setPaddleWidth(90);
        assertEquals(90, singlePlayerVersusAI.getPaddleWidth());
    }

    @Test
    public void getPaddleWidth() {
        SinglePlayerVersusAI singlePlayerVersusAI = new SinglePlayerVersusAI();
        assertEquals(80, singlePlayerVersusAI.getPaddleWidth());
    }
}
