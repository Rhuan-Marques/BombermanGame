package com.mygdx.game.fase;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class Bomba extends ObjetoDoJogo
{
	private static float tempoDeExplosao = 0.5f;
    private float contador;
    private static Texture bombaTexture = new Texture("bomba.png");
    private int tamanhoDaExplosao;

    public Bomba(int posX, int posY)
    {
    	this.tamanhoDaExplosao = 3;
    	this.contador = -1;
        this.posX = posX;
        this.posY = posY;
        this.setTexture(bombaTexture);
    }
	public static Texture getBombaTexture()
	{
		return Bomba.bombaTexture;
	}
	public int getTamanhoExplosao()
	{
		return this.tamanhoDaExplosao;
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
		int deslocamento = this.tamanhoDaExplosao;
        List<int[]> indices = new ArrayList<>();
        for(int i = this.posX+1;i<=this.posX+deslocamento;i++)//cima
        {
            if(i > gridLength-1)
            {
            	indices.add(new int[]{-1, -1});
            }
            else 
            {
            	indices.add(new int[]{i, this.posY});
			}
            
        }
        for(int i = this.posX-1;i>=this.posX-deslocamento;i--)//baixo
        {
        	if(i < 0)
            {
            	indices.add(new int[]{-1, -1});
            }
            else 
            {
            	indices.add(new int[]{i, this.posY});
			}
        }
        
        
        for(int i = this.posY+1;i<=this.posY+deslocamento;i++)//direita
        {
        	if(i > gridLength-1)
            {
            	indices.add(new int[]{-1, -1});
            }
            else 
            {
            	 indices.add(new int[]{posX, i});
			}
        }
        for(int i = this.posY-1;i>=this.posY-deslocamento;i--)//esquerda
        {
        	if(i < 0)
            {
            	indices.add(new int[]{-1, -1});
            }
            else 
            {
            	 indices.add(new int[]{posX, i});
			}
        }
        return indices;
    }
	public String toString() {
        return "Bomba na posicao ("+this.getPosX()+", "+this.getPosY()+")";
    }
	
	
}
