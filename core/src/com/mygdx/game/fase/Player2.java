package com.mygdx.game.fase;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class Player2 extends Player 
{
	public Player2(int posX, int posY, Texture playerTexture,int vida) 
	{
		super(posX, posY, playerTexture,vida);
	}
	/**
	 * Manipula a entrada do jogador, movendo-o na grade e permitindo-o colocar bombas.
	 * 
	 * @param input         Objeto de entrada do jogador.
	 * @param gridLength    Tamanho da grade do jogo.
	 * @param posOcupadas   Array de Boolean indicando se as posições adjacentes estão ocupadas.
	 * @return Retorna a direcao na qual o jogador foi ou -1 se o movimento foi nao foi bem-sucedido.
	 */
	public int handleInput(Input input, int gridLength, Boolean posOcupadas[]) 
	{
	    int pos = -1; // Direção bloqueada (padrão -1, indicando movimento bem-sucedido)

	    // Verifica se a tecla de bomba foi pressionada e a posição da bomba não está ocupada
	    if (input.isKeyJustPressed(Keys.F) && !posOcupadas[this.bombPos]) 
	    {
	        spawnBomb(gridLength); // Coloca uma bomba na posição atual
	    }

	    // Verifica se as teclas de movimento foram pressionadas para mover o jogador
	    if (input.isKeyJustPressed(Keys.W) && posY < gridLength - 1) 
	    {
	        if (!posOcupadas[3]) 
	        {
	            this.addToPosY(1); // Move para cima
	            this.setBombPos(3); // Define a posição da bomba para cima
	        } 
	        else 
	        {
	            pos = 3;
	        }
	    } 
	    else if (input.isKeyJustPressed(Keys.D) && posX < gridLength - 1) 
	    {
	        if (!posOcupadas[1]) 
	        {
	            this.addToPosX(1); // Move para a direita
	            this.setBombPos(1); // Define a posição da bomba para a direita
	        } 
	        else 
	        {
	            pos = 1;
	        }
	    } 
	    else if (input.isKeyJustPressed(Keys.S) && posY > 0) 
	    {
	        if (!posOcupadas[2]) 
	        {
	            this.setBombPos(2); // Define a posição da bomba para baixo
	            this.addToPosY(-1); // Move para baixo
	        } 
	        else 
	        {
	            pos = 2;
	        }
	    } else if (input.isKeyJustPressed(Keys.A) && posX > 0) 
	    {
	        if (!posOcupadas[0]) 
	        {
	            this.setBombPos(0); // Define a posição da bomba para a esquerda
	            this.addToPosX(-1); // Move para a esquerda
	        } 
	        else 
	        {
	            pos = 0;
	        }
	    }
	    return pos;
	}

}
