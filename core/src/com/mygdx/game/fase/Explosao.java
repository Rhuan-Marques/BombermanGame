package com.mygdx.game.fase;

import com.badlogic.gdx.graphics.Texture;

public class Explosao extends ObjetoDoJogo
{
	private static Texture explosaoTexture = new Texture("explosao.png");
	public static float tempoDaExplosao = 0.5f;
	public Explosao(int posX, int posY)
	{
		this.setPosX(posX);
		this.setPosY(posY);
		this.setTexture(getExplosaTexture());
	}
	public static Texture getExplosaTexture()
	{
		return Explosao.explosaoTexture;
	}
	public static float getTempoDaExplosao()
	{
		return Explosao.tempoDaExplosao;
	} 
}
