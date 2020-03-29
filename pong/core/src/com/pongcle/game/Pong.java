package com.pongcle.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pongcle.screens.SinglePlayer;
import com.pongcle.screens.SinglePlayerVersusAI;

public class Pong extends Game {
	public SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new SinglePlayerVersusAI(this));
//		this.setScreen(new SinglePlayer(this)); //uncomment this and comment out the line above to test use the singleplayer screen

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
