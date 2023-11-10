package com.mygdx.game.fase;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class Bomba extends ObjetoDoJogo implements Explodivel
{
	private float tempoDeExplosao = 0.5f;
    protected float contador;
    private  Texture bombaTexture = new Texture("bomba.png");
    private int tamanhoDaExplosao;
	private int vida; 
	private boolean piercing;
	private int damage;
	private Boolean exploded;
	private int polvoraLvl;
	private Player criador;
    public Bomba(int posX, int posY, Player criador)
    {
		this.posX = posX;
		this.posY = posY;
		this.criador = criador;
		if(this.criador != null){
			this.polvoraLvl = this.criador.getPolvora();
			this.piercing = this.criador.getPierceEffect();
		}
		else{
			this.polvoraLvl = 0;
			this.piercing = false;
		}
		this.tamanhoDaExplosao = 1;
		if(polvoraLvl>=1)
			this.tamanhoDaExplosao+=2;
		this.vida = 1;
    	this.contador = -1;
		this.damage=1;
        this.posX = posX;
        this.posY = posY;
        this.exploded = false;
        this.setTexture(bombaTexture);
    }
	@Override
	public ObjetoDoJogo recebeExplosao(int dano){
		vida-=dano;
		if(vida>0)
			return this;
		else
			return this.acabaVida();
	}
	@Override
	public ObjetoDoJogo acabaVida(){
		this.contador=this.tempoDeExplosao;
		return this;
	}
	public float getTempoDeExplosao()
	{
		return this.tempoDeExplosao;
	}
	public void setExploded(Boolean b)
	{
		this.exploded = b;
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
			this.exploded = true;
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
	public boolean getExploded()
	{
		return this.exploded;
	}
	public boolean getPiercieng(){
		return piercing;
	}

	protected void setPiercing(boolean piercing){
		this.piercing = piercing;
	}

	protected void setDamage(int damage){
		this.damage = damage;
	}

	public int getDamage(){
		return this.damage;
	}

	protected void setTamanhoExplosao(int tam){
		tamanhoDaExplosao = tam;
	}

	protected int getPolvoraLvl(){
		return this.polvoraLvl;
	}

	protected Player getCriador(){
		return this.criador;
	}
	public String toString() {
        return "Bomba na posicao ("+this.getPosX()+", "+this.getPosY()+")";
    }
}
