package com.pongcle.game;

import com.badlogic.gdx.utils.TimeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Config {
    private int scoreToWin = 3;
    private int difficulty = 1;
    private String gameMode = "1";
    private int ballRadius = 20;
    private int paddleWidth = 80;

    Config(){

        readConfigFile();
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
    public void setBallRadius(int rad){
        this.ballRadius = rad;
    }
    public int getBallRadius(){
        return this.ballRadius;
    }
    public void setPaddleWidth(int w){
        this.paddleWidth = w;
    }
    public int getPaddleWidth(){
        return this.paddleWidth;
    }

    /**
     * Reads the argument variables from the config file
     * @return 1 if file found and 0 if there was an exception (likely no file found)
     */
    public int readConfigFile(){

        try {
            File config_file = new File("config.txt");
            Scanner scanner = new Scanner(config_file);
            while(scanner.hasNext()){
                String nextLine = scanner.nextLine();
                if(nextLine.equals("-m")) {
                    this.setGameMode(scanner.nextLine());
                }
                if(nextLine.equals("-s")) {
                    this.setScoreToWin(Integer.valueOf(scanner.nextLine()));
                }
                if(nextLine.equals("-d")) {
                    this.setDifficulty(Integer.valueOf(scanner.nextLine()));
                }
                if(nextLine.equals("-ballRadius")) {
                    this.setBallRadius(Integer.valueOf(scanner.nextLine()));
                }
                if(nextLine.equals("-paddleWidth")) {
                    this.setPaddleWidth(Integer.valueOf(scanner.nextLine()));
                }
            }
            scanner.close();
            return 1;
        } catch (FileNotFoundException e) {
            return 0;
        }
    }


}
