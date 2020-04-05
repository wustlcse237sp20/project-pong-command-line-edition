package com.pongcle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pongcle.screens.SinglePlayer;
import com.pongcle.screens.SinglePlayerVersusAI;
import com.pongcle.screens.TwoPlayer;

public class Pong extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
//		 this.setScreen(new SinglePlayerVersusAI(this));
		 this.setScreen(new SinglePlayer(this)); //uncomment this and comment out the line above to test and use the singleplayer screen
//		this.setScreen(new TwoPlayer(this));
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
