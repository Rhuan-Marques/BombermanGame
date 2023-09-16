package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;

public class ObjetoDoJogo 
{
	protected Texture texture;
	protected int posX = 0;
	protected int posY = 0;
	public int getPosY()
	{
		return this.posY;
	}
	public int getPosX()
	{
		return this.posX;
	}
	public void setPosY(int posX)
	{
		this.posY = posX;
	}
	public void setPosX(int posY)
	{
		this.posX = posY;
	}
	public void addToPosX(int i)
	{
		this.setPosX(this.getPosX()+i);
	}
	public void addToPosY(int i)
	{
		this.setPosY(this.getPosY()+i);
	}
	public Texture geTexture()
	{
		return this.texture;
	}
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
}
