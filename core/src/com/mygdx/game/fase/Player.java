package com.mygdx.game.fase;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Player extends ObjetoDoJogo implements  Explodivel
{
	protected int direction;
	public Bomba bombas[];
	private Integer vida;
	private int item_Boot;
	private int item_tipoBomba;
	private int item_SacoBomba;
	private int item_Polvora;
    private boolean item_Oleo;
    protected Texture textures[] = new Texture[5];
	private int keyUp;
	private int keyDown;
	private int keyLeft;
	private int keyRight;
	private int keyBomb;

    public Player(int posX, int posY, String playerTextureFolder,int vida,
				  int keyUp, int keyRight, int keyDown, int keyLeft, int keyBomb)
    {
        super();
        this.vida = vida;
		this.direction=0;
        this.carregarTextura(playerTextureFolder);
		this.texture = textures[direction];
        this.posX = posX;
        this.posY = posY;
		this.item_tipoBomba = 0;
		this.item_Boot = 0;
		this.item_SacoBomba = 1;
        this.item_Oleo = false;
		this.item_Polvora = 0;
		this.keyUp=keyUp;
		this.keyDown=keyDown;
		this.keyRight=keyRight;
		this.keyLeft=keyLeft;
		this.keyBomb=keyBomb;
    }

	public void carregarTextura(String pasta){
		textures[0] = new Texture(pasta + "\\LEFT.png");
		textures[1] = new Texture(pasta + "\\RIGHT.png");
		textures[2] = new Texture(pasta + "\\DOWN.png");
		textures[3] = new Texture(pasta + "\\UP.png");
		textures[4] = new Texture(pasta + "\\DEAD.png");
	}
	@Override
	public ObjetoDoJogo recebeExplosao(int dano)
	{
		this.vida -= dano;
		if(this.vida > 0)
			return this;
		else
			return acabaVida();
	}

	@Override
	public ObjetoDoJogo acabaVida() {
		this.texture = textures[4];
		return this;
	}

	public boolean taVivo(){
		if(vida>0)
			return true;
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
	    if (input.isKeyJustPressed(keyBomb) && !posOcupadas[this.direction])
	    {
	        spawnBomb(gridLength); // Coloca uma bomba na posição atual
	    }

	    // Verifica se as teclas de seta foram pressionadas para mover o jogador
	    if (input.isKeyJustPressed(keyUp) && posY < gridLength - 1)
	    {
	        if (!posOcupadas[3]) 
	        {
	            this.addToPosY(1); // Move para cima
	            this.setDirection(3); // Define a posição da bomba para cima
	        } 
	        else 
	        {
	            pos = 3;
	        }
	    } 
	    else if (input.isKeyJustPressed(keyRight) && posX < gridLength - 1)
	    {
	        if (!posOcupadas[1]) 
	        {
	            this.addToPosX(1); // Move para a direita
	            this.setDirection(1); // Define a posição da bomba para a direita
	        } 
	        else 
	        {
	            pos = 1; 
	        }
	    } 
	    else if (input.isKeyJustPressed(keyDown) && posY > 0)
	    {
	        if (!posOcupadas[2]) 
	        {
	            this.setDirection(2); // Define a posição da bomba para baixo
	            this.addToPosY(-1); // Move para baixo
	        } 
	        else 
	        {
	            pos = 2;
	        }
	    } 
	    else if (input.isKeyJustPressed(keyLeft) && posX > 0)
	    {
	        if (!posOcupadas[0]) 
	        {
	            this.setDirection(0); // Define a posição da bomba para a esquerda
	            this.addToPosX(-1); // Move para a esquerda
	        } 
	        else 
	        {
	            pos = 0;
	        }
	    }
		this.texture = this.textures[direction];
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
	public List<int[]> updateBombaTime(float delta, int gridLength, Bomba bomba)
	{
		// Verifica se a bomba não é nula
		if (bomba != null)
		{
			// Adiciona o delta ao contador da bomba
			if (bomba.addToContador(delta))
			{
				// Obtém a matriz de índices da explosão
				List<int[]> matriz = bomba.obterIndicesDaExplosao(gridLength);

				// Remove a bomba da lista de bombas do jogador
				Bomba newBombasBomba[] = new Bomba[this.bombas.length - 1];
				for (int j = 0; j < this.bombas.length; j++)
				{
					if(bombas[j] != bomba)
						newBombasBomba[j - 1] = this.bombas[j];
				}
				this.bombas = newBombasBomba;
				// Retorna a matriz de índices da explosão
				return matriz;
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
	    if (this.bombas != null && this.bombas.length >= item_SacoBomba)
	    {
	        return; // Se sim, não spawna uma nova bomba
	    }

	    Bomba bomba = null;
		int bombPosX, bombPosY;
		Boolean bombPlace= false;
		bombPosX = this.posX;
		bombPosY = this.posY;
	    // Switch case para determinar a posição adjacente onde a bomba será spawnada
	    switch (this.direction)
	    {
	        case 1: // Direita
	            if (this.posX + 1 < gridLength) 
	            {
	                bombPosX+=1;
					bombPlace= true;
	            }
	            break;
	        case 2: // Esquerda
	            if (this.posY - 1 >= 0) 
	            {
	                bombPosY-=1;
					bombPlace= true;

	            }
	            break;
	        case 3: // Baixo
	            if (this.posY + 1 < gridLength) 
	            {
	                bombPosY+=1;
					bombPlace= true;
	            }
	            break;
	        default: // Cima
	            if (this.posX - 1 >= 0) 
	            {
	                bombPosX-=1;
					bombPlace= true;
	            } else 
	            {
	                // Se nenhuma posição adjacente for válida, spawnar na posição direita, esquerda ou acima, se possível
	                if (this.posX + 1 < gridLength) 
	                {
	                    bombPosX+=1;
						bombPlace= true;
	                } 
	                else if (this.posY - 1 >= 0) 
	                {
	                    bombPosY-=1;
						bombPlace= true;
	                } else if (this.posX - 1 >= 0) 
	                {
	                    bombPosX-=1;
						bombPlace= true;
	                }
	            }
	            break;
	    }
		if(bombPlace){
			if(item_tipoBomba==0){
				bomba = new Bomba(bombPosX, bombPosY, this);
			}
			else if(item_tipoBomba==1){
				bomba = new BombaVermelha(bombPosX, bombPosY, this);
			}
			else if(item_tipoBomba==2){
				bomba = new BombaAzul(bombPosX, bombPosY, this);
			}
			else if(item_tipoBomba==3){
				bomba = new BombaDourada(bombPosX, bombPosY, this);
			}
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

	public void recebeItem(int tipo){
		if(tipo<10){
			item_tipoBomba = tipo;
		}
		else if(tipo==11)
			this.item_Boot+=1;
		else if (tipo==12) {
			item_SacoBomba+=1;
		}
		else if(tipo==13){
			item_Oleo = true;
		}
		else if(tipo==14){
			item_Polvora+=1;
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
	public void setDirection(int dir){
		this.direction = dir;
	}

	public int getKickPower(){
		return item_Boot;
	}
    public boolean getPierceEffect(){
        return this.item_Oleo;
    }

	public int getPolvora(){
		return this.item_Polvora;
	}
}
