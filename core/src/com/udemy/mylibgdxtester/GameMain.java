package com.udemy.mylibgdxtester;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GameInfo;
import scenes.MainMenu;

// changed ApplicationAdapter to Game (extends)
public class GameMain extends Game {
	private SpriteBatch batch;	// SpriteBatch is the main component for drawing things onto the screen. YOU SHOULD ONLY HAVE 1 SPRITEBATCH. PASS IT AS PARAMETER

	@Override
	public void create () {
	batch = new SpriteBatch();
	setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
	super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}
}
