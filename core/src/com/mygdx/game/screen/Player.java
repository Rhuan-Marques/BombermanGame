package com.mygdx.game.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class Player 
{
	private Texture playerTexture;
	private int posX = 0;
	private int posY = 0;
	public Player(int posX,int posY,Texture playerTexture)
	{
		this.playerTexture = playerTexture;
		this.posX = posX;
		this.posY = posY;
	}
	public void handleInput(Input input,int gridLenght)
	{
		if(input.isKeyJustPressed(Keys.UP)&& posY < gridLenght-1)
		{
			posY += 1;
		}
		if(input.isKeyJustPressed(Keys.RIGHT)&& posX < gridLenght-1)
		{
			posX +=  1;
		}
		if(input.isKeyJustPressed(Keys.DOWN)&& posY > 0)
		{
			
			posY -= 1;
		}
		if(input.isKeyJustPressed(Keys.LEFT)&& posX > 0)
		{
			posX -=  1;
		}
	}
	public int[] getCurrentPos()
	{
		int arr[] = {this.posX,this.posY};
		return arr;
	}
	public Texture geTexture()
	{
		return this.playerTexture;
	}
}
