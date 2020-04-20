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

		Config config = getConfigFile();

		if(config.getGameMode().equals("1")){
			this.setScreen(new SinglePlayer(this, config.getDifficulty()));
		}
		else if(config.getGameMode().equals("2")){
			this.setScreen(new TwoPlayer(this, config.getScoreToWin(), config.getDifficulty()));
		}
		else if(config.getGameMode().equals("3")){
			this.setScreen(new SinglePlayerVersusAI(this, config.getScoreToWin(), config.getDifficulty()));
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
	
	/**
	 * 
	 * @return a String containing the selected game mode. 
	 * If no game mode provided, single player will used as default. 
	 */
	public Config getConfigFile(){
		Config config = new Config();
		Dictionary game_modes = new Hashtable();
		game_modes.put("1", "Single Player");
		game_modes.put("2", "Two Player");
		game_modes.put("3", "Single Player VS AI");

		try {
			File config_file = new File("config.txt");
			Scanner scanner = new Scanner(config_file);
			while(scanner.hasNext()){
				String nextLine = scanner.nextLine();
				if(nextLine.equals("-m")) {
					config.setGameMode(scanner.nextLine());
				}
				if(nextLine.equals("-s")) {
					config.setScoreToWin(Integer.valueOf(scanner.nextLine()));
				}
				if(nextLine.equals("-d")) {
					config.setDifficulty(Integer.valueOf(scanner.nextLine()));
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The Config File was not found.. defaulting to single player");
		}
		return config;
	}
}
