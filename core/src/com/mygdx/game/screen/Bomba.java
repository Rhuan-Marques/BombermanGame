package com.mygdx.game.screen;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class Bomba 
{
	private static float tempoDeExplosao = 0.5f;
	private float contador = -1;
	private int posX;
	private int posY;
	private static Texture bombaTexture =  new Texture("bomba.png");
	private static int tamanhoDaExplosao = 3;
	private static Texture explosaoTexture = new Texture("ship.png");
	public Bomba(int posX,int posY)
	{
		this.posX = posX;
		this.posY = posY;
	}
	public static Texture getExplosaTexture()
	{
		return Bomba.explosaoTexture;
	}
	public static Texture getBombaTexture()
	{
		return Bomba.bombaTexture;
	}
	public static int getTamanhoExplosao()
	{
		return Bomba.tamanhoDaExplosao;
	}
	public int[] getPosicao()
	{
		int arr [] ={this.posX,this.posY};
		return arr;
	}
	public Boolean addToContador(float num)
	{
		this.contador += num;
		if(this.contador >= tempoDeExplosao)
		{
			this.contador = 0;
			return true;
		}
		return false;
	}
	public List<int[]> obterIndicesDaExplosao(int gridLength) 
	{
		int deslocamento = 3;
        List<int[]> indices = new ArrayList<>();
        int instanceOfXlower = this.posX - deslocamento;
        int instanceOfXUpper = this.posX + deslocamento;
        int instanceOfY = this.posY - deslocamento;
        int instanceOfYUpper = this.posY + deslocamento;
        if(instanceOfXlower < 0)
        {
            instanceOfXlower = 0;
        }
        if(instanceOfXUpper > gridLength-1)
        {
            instanceOfXUpper = gridLength-1;
        }
        if(instanceOfY < 0)
        {
            instanceOfY = 0;
        }
        if(instanceOfYUpper > gridLength-1)
        {
            instanceOfYUpper = gridLength-1;
        }
        for(int i = instanceOfXlower;i<=instanceOfXUpper;i++)
        {
            if(i == this.posX)
            {
                continue;
            }
            indices.add(new int[]{i, posY});
        }
        for(int i = instanceOfY;i<=instanceOfYUpper;i++)
        {
            if(i == this.posY)
            {
                continue;
            }
            indices.add(new int[]{posX, i});
        }
        return indices;
    }
	
	
	
}
