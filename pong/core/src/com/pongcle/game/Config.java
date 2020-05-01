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
    private String gameMode = "2";
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
        if (dif == 1 || dif == 2 || dif == 3) {
            this.difficulty = dif;
        } else {
            System.out.println("Invalid difficulty, set it to default value");
        }
    }
    public int getDifficulty(){
        return this.difficulty;
    }
    public void setBallRadius(int rad){
        if (rad >= 10 && rad <= 30) {
            this.ballRadius = rad;
        } else {
            System.out.println("Invalid ball radius, set it to default value");
        }     
    }
    public int getBallRadius(){
        return this.ballRadius;
    }
    public void setPaddleWidth(int w){
        if (w >= 80 && w <= 200) {
            this.paddleWidth = w;
        } else {
            System.out.println("Invalid paddle width, set it to default value");
        }
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
                    try {this.setScoreToWin(Integer.valueOf(scanner.nextLine()));}
                    catch (NumberFormatException nfe) {
                        System.out.println("Invalid winning score");
                        System.exit(0);
                    }
                }
                if(nextLine.equals("-d")) {
                    try {this.setDifficulty(Integer.valueOf(scanner.nextLine()));}
                    catch (NumberFormatException nfe) {
                        System.out.println("Invalid difficulty");
                        System.exit(0);
                    }
                }
                if(nextLine.equals("-ballRadius")) {
                    try {this.setBallRadius(Integer.valueOf(scanner.nextLine()));}
                    catch (NumberFormatException nfe) {
                        System.out.println("Invalid ball radius");
                        System.exit(0);
                    }
                }
                if(nextLine.equals("-paddleWidth")) {
                    try {this.setPaddleWidth(Integer.valueOf(scanner.nextLine()));}
                    catch (NumberFormatException nfe) {
                        System.out.println("Invalid paddle width");
                        System.exit(0);
                    }
                }
            }
            scanner.close();
            return 1;
        } catch (FileNotFoundException e) {
            return 0;
        }
    }


}
