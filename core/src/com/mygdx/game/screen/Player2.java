package com.mygdx.game.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class Player2 extends Player 
{
	public Player2(int posX, int posY, Texture playerTexture) 
	{
		super(posX, posY, playerTexture);
	}

	public void handleInput(Input input,int gridLenght,Boolean posOcupadas[])
	{
		if(input.isKeyJustPressed(Keys.W)&& this.getPosY()< gridLenght-1 && !posOcupadas[3])
		{
			this.addToPosY(1);
			this.setBombPos(0);
		}
		if(input.isKeyJustPressed(Keys.D)&& this.getPosX() < gridLenght-1 && !posOcupadas[1])
		{
			this.addToPosX(1);
			this.setBombPos(1);
		}
		if(input.isKeyJustPressed(Keys.S)&& this.getPosY() > 0 && !posOcupadas[2])
		{
			this.setBombPos(3);
			this.addToPosY(-1);;
		}
		if(input.isKeyJustPressed(Keys.A)&& this.getPosX() > 0 && !posOcupadas[0])
		{
			this.setBombPos(2);
			this.addToPosX(-1);
		}
		if(input.isKeyJustPressed(Keys.F))
		{
			spawnBomb(gridLenght);
		}
	}
}
