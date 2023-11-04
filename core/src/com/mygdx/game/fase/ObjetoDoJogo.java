package com.mygdx.game.fase;

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

	/**
	 * Obtém as posições adjacentes à posição atual do objeto no mapa.
	 * 
	 * @param gridLength O tamanho do grid do mapa.
	 * @return Uma matriz 2D contendo as posições adjacentes: à esquerda, à direita, acima e abaixo.
	 */
	public int[][] getAdjacentPositions(int gridLength) 
	{
	    // Matriz contendo as posições adjacentes
	    int[][] adjacentPositions = new int[][] {
	        {posX - 1, posY}, // Posição à esquerda
	        {posX + 1, posY}, // Posição à direita
	        {posX, posY - 1}, // Posição acima
	        {posX, posY + 1}  // Posição abaixo
	    };

	    // Verifica se as posições adjacentes estão dentro dos limites do grid
	    for (int i = 0; i < adjacentPositions.length; i++) 
	    {
	        if (adjacentPositions[i][0] < 0 || adjacentPositions[i][0] >= gridLength || 
	            adjacentPositions[i][1] < 0 || adjacentPositions[i][1] >= gridLength) 
	        {
	            adjacentPositions[i] = null; // Define como nula se estiver fora dos limites
	        }
	    }

	    // Retorna a matriz contendo as posições adjacentes válidas
	    return adjacentPositions;
	}
}
