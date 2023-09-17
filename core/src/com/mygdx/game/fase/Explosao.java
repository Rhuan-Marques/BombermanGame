package com.mygdx.game.fase;

import com.badlogic.gdx.graphics.Texture;

public class Explosao extends ObjetoDoJogo
{
	private static Texture explosaoTexture = new Texture("explosao.png");
	public static float tempoDaExplosao = 0.5f;
	public static Texture getExplosaTexture()
	{
		return Explosao.explosaoTexture;
	}
	public static float getTempoDaExplosao()
	{
		return Explosao.tempoDaExplosao;
	} 
}
