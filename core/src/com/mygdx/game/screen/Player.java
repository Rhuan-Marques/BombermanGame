package com.mygdx.game.screen;

import java.io.ObjectInputStream.GetField;
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
	private int vida;
	public Player(int posX,int posY,Texture playerTexture)
	{
		this.vida = 1;
		this.playerTexture = playerTexture;
		this.posX = posX;
		this.posY = posY;
	}
	public Texture getTexture()
	{
		return this.playerTexture;
	}
	public void recebeDano(int i)
	{
		this.vida -= i;
	}
	public Boolean taVivo()
	{
		if(this.vida >= 1)
		{
			return true;
		}
		return false;
	}
	public void handleInput(Input input,int gridLenght,Boolean posOcupadas[])
	{
		if(input.isKeyJustPressed(Keys.UP)&& posY < gridLenght-1 && !posOcupadas[3])
		{
			this.posY += 1;
			this.bombPos = 0;
		}
		if(input.isKeyJustPressed(Keys.RIGHT)&& posX < gridLenght-1 && !posOcupadas[1])
		{
			this.posX +=  1;
			this.bombPos = 1;
		}
		if(input.isKeyJustPressed(Keys.DOWN)&& posY > 0 && !posOcupadas[2])
		{
			this.bombPos = 3;
			this.posY -= 1;
		}
		if(input.isKeyJustPressed(Keys.LEFT)&& posX > 0 && !posOcupadas[0])
		{
			this.bombPos = 2;
			this.posX -=  1;
		}
		if(input.isKeyJustPressed(Keys.SHIFT_RIGHT))
		{
			spawnBomb(gridLenght);
		}
	}
	 public int[][] getAdjacentPositions(int gridLength) 
	 {
	        int[][] adjacentPositions = new int[][] 
	        		{
	            {posX - 1, posY}, // Posição à esquerda
	            {posX + 1, posY}, // Posição à direita
	            {posX, posY - 1}, // Posição acima
	            {posX, posY + 1}  // Posição abaixo
	        		};
	        for(int i=0;i<adjacentPositions.length;i++)
	        {
	        	if(adjacentPositions[i][0] < 0 || adjacentPositions[i][0] >= gridLength || adjacentPositions[i][1] < 0 || adjacentPositions[i][1] >= gridLength)
	        	{
	        		adjacentPositions[i] = null;
	        	}
	        }
	        return adjacentPositions;
	    }
	public List<int[]> updateBombasTime(float delta,int gridLength)
	{
		if(bombas !=null)
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
						this.bombas = newBombasBomba;
						return matriz;
					}
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
	public int getPosY()
	{
		return this.posY;
	}
	public int getPosX()
	{
		return this.posX;
	}
	public int getBomPos()
	{
		return this.bombPos;
	}
	public void addToPosX(int i)
	{
		this.posX+=i;
	}
	public void addToPosY(int i)
	{
		this.posY+=i;
	}
	public Texture geTexture()
	{
		return this.playerTexture;
	}
	public void setBombPos(int pos)
	{
		this.bombPos = pos;
	}
}
