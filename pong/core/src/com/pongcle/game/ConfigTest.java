package com.pongcle.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {

    @Test
    public void setScoreToWin() {
        Config config = new Config();
        config.setScoreToWin(9);
        assertEquals(9, config.getScoreToWin());
    }

    @Test
    public void setGameMode() {
        Config config = new Config();
        config.setGameMode("2");
        assertEquals("2", config.getGameMode());
    }
    @Test
    public void setDifficulty() {
        Config config = new Config();
        config.setDifficulty(1);
        assertEquals(1, config.getDifficulty());
    }
    @Test
    public void getScoreToWin() {
        Config config = new Config();
        assertEquals(3, config.getScoreToWin());
    }

    @Test
    public void getGameMode() {
        Config config = new Config();
        assertEquals("1", config.getGameMode());
    }

    @Test
    public void getDifficulty() {
        Config config = new Config();
        assertEquals(1, config.getDifficulty());
    }

}