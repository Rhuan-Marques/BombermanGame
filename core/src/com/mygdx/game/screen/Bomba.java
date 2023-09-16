package com.mygdx.game.screen;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class Bomba extends ObjetoDoJogo
{
	private static float tempoDeExplosao = 0.5f;
    private float contador = -1;
    private static Texture bombaTexture = new Texture("bomba.png");
    private static int tamanhoDaExplosao = 3;

    public Bomba(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
        this.setTexture(bombaTexture);
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
