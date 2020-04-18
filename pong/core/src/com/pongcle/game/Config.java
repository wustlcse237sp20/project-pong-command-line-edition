package com.pongcle.game;

public class Config {
    private int scoreToWin = 3;
    private int difficulty = 1;
    private String gameMode = "1";

    Config(){

    }
    public void setScoreToWin(int score){
        this.scoreToWin = score;
    }
    public void setGameMode(String gameMode){
        this.gameMode = gameMode;
    }
    public int getScoreToWin(){
        return this.scoreToWin;
    }
    public String getGameMode(){
        return this.gameMode;
    }
    public void setDifficulty(int dif){
        this.difficulty = dif;
    }
    public int getDifficulty(){
        return this.difficulty;
    }


}
