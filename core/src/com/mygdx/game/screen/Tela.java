/*package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Grid;

/*"implements" é usado para garantir que a classe MainGameScreen cumpra todos os métodos 
 * definidos na interface Screen. Isso significa que a classe deve fornecer implementações para todos 
 * os métodos declarados na interface*//*
public class Tela implements Screen
{
	//private int IMG_SIZE = 100;
	private int GRID_SNAP_X = Gdx.graphics.getWidth() / IMG_SIZE;
	private int GRID_SNAP_Y = Gdx.graphics.getHeight() / IMG_SIZE;
	public static float VELOCIDADE = 250f;
	Texture img;
	int x = 0,y = 0;
	Texture objetos[][] = new Texture[(int)GRID_SNAP_X][(int)GRID_SNAP_Y];
	Texture chaoTexture = new Texture("box.jpg");
	Grid game;
	
	public MainGame(Grid game)
	{
		this.game = game;
		for(int i = 0;i<GRID_SNAP_X;i++)
		{
			for(int j = 0;j<GRID_SNAP_Y;j++)
			{
				objetos[i][j] = chaoTexture;
			}
		}
		
	}
	
	@Override
	public void show() 
	{
		img = new Texture("badlogic.jpg");
		
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//float deltaTime = Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyJustPressed(Keys.UP)&& y < GRID_SNAP_Y-1)
		{
			objetos[x][y] = chaoTexture;
			y += 1;
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)&& x < GRID_SNAP_X-1)
		{
			objetos[x][y] = chaoTexture;
			x +=  1;
		}
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)&& y > 0)
		{
			objetos[x][y] = chaoTexture;
			y -= 1;
		}
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)&& x > 0)
		{
			
			objetos[x][y] = chaoTexture;
			x -=  1;
		}
		objetos[x][y] = img;
		game.batch.begin();
		for(int i = 0;i<GRID_SNAP_X;i++)
		{
			for(int j = 0;j<GRID_SNAP_Y;j++)
			{
				if(objetos[i][j] != null)
				{
					game.batch.draw(objetos[i][j], i * IMG_SIZE, j * IMG_SIZE, IMG_SIZE, IMG_SIZE);	
					if(objetos[i][j] == img)
					{
						game.batch.draw(new Texture("Bomberman.png"), i * IMG_SIZE, j * IMG_SIZE, IMG_SIZE-20, IMG_SIZE-20);
					}
				}
			}
		}
		game.batch.end();
	}
	public void drawCamadas()
	{
		for(int h = 0;h<this.texturas.length;h++)
		{
			for(int i = 0;i<GRID_SNAP_X;i++)
			{
				for(int j = 0;j<GRID_SNAP_Y;j++)
				{
					if(objetos[i][j] != null)
					{
						game.batch.draw(objetos[i][j], i * IMG_SIZE, j * IMG_SIZE, IMG_SIZE, IMG_SIZE);	
						if(objetos[i][j] == img)
						{
							game.batch.draw(new Texture("Bomberman.png"), i * IMG_SIZE, j * IMG_SIZE, IMG_SIZE-20, IMG_SIZE-20);
						}
					}
				}
			}
		}
		return;
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	private 

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

}*/
