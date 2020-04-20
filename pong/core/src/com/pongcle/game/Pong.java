package com.pongcle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pongcle.screens.SinglePlayer;
import com.pongcle.screens.SinglePlayerVersusAI;
import com.pongcle.screens.TwoPlayer;
import java.io.*; 
import java.util.*; 

public class Pong extends Game {
	public SpriteBatch batch;
	public Config config;

	/**
	 * Creates new pong game mode based on command line arguments 
	 */
	@Override
	public void create () {

		batch = new SpriteBatch();

		Config config = new Config();

		if(config.getGameMode().equals("1")){
			this.setScreen(new SinglePlayer(this, config.getDifficulty(), config.getBallRadius(), config.getPaddleWidth()));
		}
		else if(config.getGameMode().equals("2")){
			this.setScreen(new TwoPlayer(this, config.getScoreToWin(), config.getDifficulty(), config.getBallRadius(), config.getPaddleWidth()));
		}
		else if(config.getGameMode().equals("3")){
			this.setScreen(new SinglePlayerVersusAI(this, config.getScoreToWin(), config.getDifficulty(), config.getBallRadius(), config.getPaddleWidth()));
		}else {
			System.out.println("given player mode is not valid => Usage: ./pong -m <game mode> where <game mode> is one of these options: (1=single player, 2=two player, 3=player vs AI)");
		}
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
	

}
