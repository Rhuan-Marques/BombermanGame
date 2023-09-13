package com.mygdx.game.screen;

import java.util.List;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class Player 
{
	private Texture playerTexture;
	private int posX = 0;
	private int posY = 0;
	private int bombPos;
	public Bomba bombas[];
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
			this.posY += 1;
			this.bombPos = 0;
		}
		if(input.isKeyJustPressed(Keys.RIGHT)&& posX < gridLenght-1)
		{
			this.posX +=  1;
			this.bombPos = 1;
		}
		if(input.isKeyJustPressed(Keys.DOWN)&& posY > 0)
		{
			this.bombPos = 3;
			this.posY -= 1;
		}
		if(input.isKeyJustPressed(Keys.LEFT)&& posX > 0)
		{
			this.bombPos = 2;
			this.posX -=  1;
		}
		if(input.isKeyJustPressed(Keys.D))
		{
			spawnBomb(gridLenght);
		}
	}
	public List<int[]> updateBombasTime(float delta,int gridLength)
	{
		for(int i =0;i<this.bombas.length;i++)
		{
			if(this.bombas[i]!=null)
			{
				if(this.bombas[i].addToContador(delta))
				{
					List<int[]> matriz =this.bombas[i].obterIndicesDaExplosao(gridLength);
					Bomba newBombasBomba[] = new Bomba[this.bombas.length-1];
					for(int j =1;j<this.bombas.length;j++)
					{
						newBombasBomba[j-1] = this.bombas[j];
					}
					return matriz;
				}
			}
		}
		return null;
	}
	public void spawnBomb(int gridLenght)
	{
		Bomba bomba = null;
		switch(this.bombPos)
		{
			case 1:
			{
				if(this.posX + 1 < gridLenght)
				{
					bomba = new Bomba(this.posX+1, this.posY);
					break;
				}
			}
			case 2:
			{
				if(this.posX - 1 >=0)
				{
					bomba = new Bomba(this.posX-1, this.posY);
					break;
				}
			}
			case 3:
			{
				if(this.posY - 1 >=0)
				{
					bomba = new Bomba(this.posX, this.posY-1);
					break;
				}
			}
			default:
			{
				if(this.posY+ 1 < gridLenght)
				{
					bomba = new Bomba(this.posX, this.posY+1);
					break;
				}
				else 
				{
					if(this.posX + 1 < gridLenght)
					{
						bomba = new Bomba(this.posX+1, this.posY);
						break;
					}
					else if(this.posY - 1 >=0)
					{
						bomba = new Bomba(this.posX, this.posY-1);
						break;
					}
					else if(this.posX - 1 >= 0)
					{
						bomba = new Bomba(this.posX-1, this.posY);
						break;
					}
				}
			}
			
		}
		if(bomba != null)
		{
			
			Bomba newBombas[];
			if(this.bombas == null)
			{
				newBombas = new Bomba[1];
			}
			else 
			{
				newBombas = new Bomba[this.bombas.length+1];
			}
			for(int i =0;i<newBombas.length-1;i++)
			{
				newBombas[i] = this.bombas[i];
			}
			newBombas[newBombas.length-1] = bomba;
			bombas = newBombas;
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
