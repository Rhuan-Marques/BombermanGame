package com.mygdx.game.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class Player2 extends Player 
{
	public Player2(int posX, int posY, Texture playerTexture,int vida) 
	{
		super(posX, posY, playerTexture,vida);
	}
	public int handleInput(Input input, int gridLenght, Boolean posOcupadas[]) 
	{
	    int pos = -1;
	    if (input.isKeyJustPressed(Keys.F) && !posOcupadas[this.bombPos]) 
	    {
	        spawnBomb(gridLenght);
	    }
	    if (input.isKeyJustPressed(Keys.W) && posY < gridLenght - 1) 
	    {
	        if (!posOcupadas[3]) 
	        {
	            this.addToPosY(1);
	            this.setBombPos(3);
	        } else 
	        {
	            pos = 3;
	        }
	    } else if (input.isKeyJustPressed(Keys.D) && posX < gridLenght - 1) 
	    {
	        if (!posOcupadas[1]) 
	        {
	            this.addToPosX(1);
	            this.setBombPos(1);
	        } else {
	            pos = 1;
	        }
	    } else if (input.isKeyJustPressed(Keys.S) && posY > 0) 
	    {
	        if (!posOcupadas[2]) 
	        {
	            this.setBombPos(2);
	            this.addToPosY(-1);
	        } else 
	        {
	            pos = 2;
	        }
	    } else if (input.isKeyJustPressed(Keys.A) && posX > 0) 
	    {
	        if (!posOcupadas[0]) 
	        {
	            this.setBombPos(0);
	            this.addToPosX(-1);
	        } else 
	        {
	            pos = 0;
	        }
	    }
	    return pos;
	}
}
