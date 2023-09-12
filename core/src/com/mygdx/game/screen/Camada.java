package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Camada
{

	private Texture objetosCamadas[][];
	private float imageSize;
	public int gridSnap;
	public Camada(int xSize)
	{
		this.imageSize =(int) (Gdx.graphics.getWidth() / xSize);
		this.gridSnap = (int) (Gdx.graphics.getWidth() /this.imageSize);
		this.objetosCamadas = new Texture[this.gridSnap][this.gridSnap];
	}
	public float getImageSize()
	{
		return this.imageSize;
	}
	public Texture getTexture(int posX, int posY)
	{
		/*if(posX < this.objetosCamadas.length && posX > 0 && posY < this.objetosCamadas.length && posY > 0)
		{
			return objetosCamadas[posX][posY];
		}*/
		return objetosCamadas[posX][posY];
		//return null;
	}
	public void setTexture(Texture texture,int posX,int posY)
	{
		this.objetosCamadas[posX][posY] = texture;
	}
	public int getGridSnap()
	{
		return this.gridSnap;
	}
}
