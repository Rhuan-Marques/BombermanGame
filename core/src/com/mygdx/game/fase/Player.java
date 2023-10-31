package com.mygdx.game.fase;
import java.util.List;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class Player extends ObjetoDoJogo
{
	protected int bombPos;
	public Bomba bombas[];
	private int vida;

    public Player(int posX, int posY, Texture playerTexture,int vida) 
    {
        super();
        this.vida = vida;
        this.texture = playerTexture; 
        this.posX = posX;
        this.posY = posY;
    }
	public void recebeDano(int i)
	{
		this.vida -= i;
	}
	public Boolean taVivo()
	{
		if(this.vida >= 1)
		{
			return true;
		}
		return false;
	}
	/**
	 * Manipula a entrada do jogador, movendo-o na grade e permitindo-o colocar bombas.
	 * 
	 * @param input         Objeto de entrada do jogador.
	 * @param gridLength    Tamanho do grid do jogo.
	 * @param posOcupadas   Array de Boolean indicando se as posições adjacentes estão ocupadas.
	 * @return Retorna a direcao na qual o jogador foi ou -1 se o movimento foi nao foi bem-sucedido.
	 */
	public int handleInput(Input input, int gridLength, Boolean posOcupadas[]) 
	{
	    int pos = -1;

	    // Verifica se a tecla de bomba foi pressionada e a posição da bomba não está ocupada
	    if (input.isKeyJustPressed(Keys.SHIFT_RIGHT) && !posOcupadas[this.bombPos]) 
	    {
	        spawnBomb(gridLength); // Coloca uma bomba na posição atual
	    }

	    // Verifica se as teclas de seta foram pressionadas para mover o jogador
	    if (input.isKeyJustPressed(Keys.UP) && posY < gridLength - 1) 
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
	    else if (input.isKeyJustPressed(Keys.RIGHT) && posX < gridLength - 1) 
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
	    else if (input.isKeyJustPressed(Keys.DOWN) && posY > 0) 
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
	    } 
	    else if (input.isKeyJustPressed(Keys.LEFT) && posX > 0) 
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

	/**
	 * Atualiza o temporizador das bombas do jogador e retorna uma lista de índices
	 * onde ocorrerá a explosão após o término do tempo da bomba.
	 * 
	 * @param delta      O tempo decorrido desde o último frame.
	 * @param gridLength O comprimento do grid do jogo.
	 * @return Lista de int[] representando os índices onde a explosão ocorrerá, ou null se não houver explosão.
	 */
	public List<int[]> updateBombasTime(float delta, int gridLength) 
	{
	    // Verifica se o jogador possui bombas
	    if (bombas != null) 
	    {
	        // Itera sobre as bombas do jogador
	        for (int i = 0; i < this.bombas.length; i++) 
	        {
	            // Verifica se a bomba não é nula
	            if (this.bombas[i] != null) 
	            {
	                // Adiciona o delta ao contador da bomba
	                if (this.bombas[i].addToContador(delta)) 
	                {
	                    // Obtém a matriz de índices da explosão
	                    List<int[]> matriz = this.bombas[i].obterIndicesDaExplosao(gridLength);
	                    
	                    // Remove a bomba da lista de bombas do jogador
	                    Bomba newBombasBomba[] = new Bomba[this.bombas.length - 1];
	                    for (int j = 1; j < this.bombas.length; j++) 
	                    {
	                        newBombasBomba[j - 1] = this.bombas[j];
	                    }
	                    this.bombas = newBombasBomba;
	                    
	                    // Retorna a matriz de índices da explosão
	                    return matriz;
	                }
	            }
	        }
	    }
	    // Retorna null se não houver explosão
	    return null;
	}

	/**
	 * Spawna uma nova bomba para o jogador em uma posição adjacente válida no mapa. Definido pelo atributo bombPos
	 * 
	 * @param gridLength O comprimento do grid do jogo.
	 */
	public void spawnBomb(int gridLength) 
	{
	    // Verifica se o jogador já possui uma bomba no mapa
	    if (this.bombas != null && this.bombas.length >= 1) 
	    {
	        return; // Se sim, não spawna uma nova bomba
	    }

	    Bomba bomba = null;
	    // Switch case para determinar a posição adjacente onde a bomba será spawnada
	    switch (this.bombPos) 
	    {
	        case 1: // Direita
	            if (this.posX + 1 < gridLength) 
	            {
	                bomba = new Bomba(this.posX + 1, this.posY);
	            }
	            break;
	        case 2: // Esquerda
	            if (this.posY - 1 >= 0) 
	            {
	                bomba = new Bomba(this.posX, this.posY - 1);
	            }
	            break;
	        case 3: // Baixo
	            if (this.posY + 1 < gridLength) 
	            {
	                bomba = new Bomba(this.posX, this.posY + 1);
	            }
	            break;
	        default: // Cima
	            if (this.posX - 1 >= 0) 
	            {
	                bomba = new Bomba(this.posX - 1, this.posY);
	            } else 
	            {
	                // Se nenhuma posição adjacente for válida, spawnar na posição direita, esquerda ou acima, se possível
	                if (this.posX + 1 < gridLength) 
	                {
	                    bomba = new Bomba(this.posX + 1, this.posY);
	                } 
	                else if (this.posY - 1 >= 0) 
	                {
	                    bomba = new Bomba(this.posX, this.posY - 1);
	                } else if (this.posX - 1 >= 0) 
	                {
	                    bomba = new Bomba(this.posX - 1, this.posY);
	                }
	            }
	            break;
	    }

	    // Adiciona a nova bomba ao array de bombas do jogador
	    if (bomba != null) 
	    {
	        Bomba newBombas[];
	        if (this.bombas == null) 
	        {
	            newBombas = new Bomba[1];
	        } 
	        else 
	        {
	            newBombas = new Bomba[this.bombas.length + 1];
	            System.arraycopy(this.bombas, 0, newBombas, 0, this.bombas.length);
	        }
	        newBombas[newBombas.length - 1] = bomba;
	        this.bombas = newBombas;
	    }
	}

	public int getVida()
	{
		return this.vida;
	}
	public int[] getCurrentPos()
	{
		int arr[] = {this.posX,this.posY};
		return arr;
	}
	public int getBomPos()
	{
		return this.bombPos;
	}
	public void setBombPos(int pos)
	{
		this.bombPos = pos;
	}
}
