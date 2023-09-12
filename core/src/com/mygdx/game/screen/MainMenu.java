package com.mygdx.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Grid;


public class MainMenu implements Screen
{
	private static int EXIT_BUTTON_WIDTH = 300;
	private static int EXIT_BUTTON_HEIGTH= 150;
	private static int PLAY_BUTTON_WIDTH = 330;
	private static int PLAY_BUTTON_HEIGTH= 150;
	private static int EXIT_BUTTON_Y = 100;
	private static int PLAY_BUTTON_Y = 300;
	Grid game;
	Texture playButtonActive;
	Texture playButtonInactive;
	Texture exitButtonActive;
	Texture exitButtonInactive;
	public MainMenu(Grid game)
	{
		this.game = game;
		playButtonActive = new Texture("play_button_active.png");
		playButtonInactive = new Texture("play_button_inactive.png");
		exitButtonActive = new Texture("exit_button_active.png");
		exitButtonInactive = new Texture("exit_button_inactive.png");
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		int x = Grid.WIDTH/2 - EXIT_BUTTON_WIDTH/2;
		if((Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX()> x && Grid.HEIGTH - Gdx.input.getY()< EXIT_BUTTON_Y+EXIT_BUTTON_HEIGTH &&  Grid.HEIGTH - Gdx.input.getY()> EXIT_BUTTON_Y) )
		{
			game.batch.draw(exitButtonActive,x,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH ,EXIT_BUTTON_HEIGTH);
			if(Gdx.input.isTouched())
			{
				Gdx.app.exit();
			}
		}
		else
		{
			game.batch.draw(exitButtonInactive,x,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH ,EXIT_BUTTON_HEIGTH);
		}
		
		if((Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX()> x && Grid.HEIGTH - Gdx.input.getY()< PLAY_BUTTON_Y+PLAY_BUTTON_HEIGTH &&  Grid.HEIGTH - Gdx.input.getY()> PLAY_BUTTON_Y) )
		{
			game.batch.draw(playButtonActive,x,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH ,PLAY_BUTTON_HEIGTH);
			if(Gdx.input.isTouched())
			{
				this.dispose();
				game.setScreen(new MainGame(game));
			}
		}
		else
		{
			game.batch.draw(playButtonInactive,x,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH ,PLAY_BUTTON_HEIGTH);
		}
		
		
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
