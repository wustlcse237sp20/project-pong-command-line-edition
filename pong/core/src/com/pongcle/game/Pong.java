package com.pongcle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pongcle.screens.SinglePlayer;
import com.pongcle.screens.SinglePlayerVersusAI;
import com.pongcle.screens.TwoPlayer;
import java.io.*; //added by JA
import java.util.*; //added by JA

public class Pong extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		Dictionary game_modes = new Hashtable();

		game_modes.put("1", "Single Player");
		game_modes.put("2", "Two Player");
		game_modes.put("3", "Single Player VS AI");

		//set defaults if none given
		String playerMode = "1";

		//check for user input flag's to change game settings
		try {
			File config_file = new File("config.txt");
			Scanner scanner = new Scanner(config_file);
			while(scanner.hasNext()){
				if(scanner.nextLine().equals("-m")) {
					playerMode = scanner.nextLine();
					System.out.println("Game mode changed to: " + game_modes.get(playerMode)); //may need some try / catch logic here if not found in dictionary
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The Config File was not found.. please try again");
		}

		//set game mode as selected
		if(playerMode.equals("1")){
			this.setScreen(new SinglePlayer(this));
		}
		else if(playerMode.equals("2")){
			this.setScreen(new TwoPlayer(this));
		}
		else if(playerMode.equals("3")){
			this.setScreen(new SinglePlayerVersusAI(this));
		}else {
			System.out.println("given game mode is not valid => Usage: ./pong -m <game mode> where <game mode> is one of these options: (1=single player, 2=two player, 3=player vs AI)");
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
