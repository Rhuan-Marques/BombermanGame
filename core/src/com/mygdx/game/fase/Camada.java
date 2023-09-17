package com.mygdx.game.fase;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Camada {
    private ObjetoDoJogo objetosCamadas[][];
    private float imageSize;
    public int gridSnap;
    private List<Float> counters;
    private List<List<ObjetoDoJogo>> listaCoordenadas;
    private float tempoDaExplosao = Explosao.getTempoDaExplosao();

    public Camada(int xSize) {
        this.listaCoordenadas = new ArrayList<>();
        this.counters = new ArrayList<>();
        this.imageSize = (int) (Gdx.graphics.getWidth() / xSize);
        this.gridSnap = (int) (Gdx.graphics.getWidth() / this.imageSize);
        this.objetosCamadas = new ObjetoDoJogo[this.gridSnap][this.gridSnap];
    }

    public Boolean[] posAdjOcupadas(Player player) 
    {
        int[][] posAdj = player.getAdjacentPositions(this.getGridSnap());
        Boolean[] posOcupadas = new Boolean[4];

        // Verifique as posições adjacentes ocupadas

        for (int h = 0; h < posAdj.length; h++) 
        {
            if (posAdj[h] != null && this.getObjetoDoJogo(posAdj[h][0], posAdj[h][1]) != null) 
            {
            	
                posOcupadas[h] = true;
                
            } else {
                posOcupadas[h] = false;
            }
        }
        return posOcupadas;
    }
    public void handleColission(int pos,int posAdj[][],Player players[])
    {
    	if(pos != -1 &&this.getObjetoDoJogo(posAdj[pos][0], posAdj[pos][1])!=null)
    	{
    		ObjetoDoJogo objetoDoJogo = this.getObjetoDoJogo(posAdj[pos][0], posAdj[pos][1]);
    		if(objetoDoJogo.geTexture().equals(Bomba.getBombaTexture()))
            {
            	/* {posX - 1, posY}, // Posição à esquerda
                {posX + 1, posY}, // Posição à direita
                {posX, posY - 1}, // Posição abaixo
                {posX, posY + 1}  // Posição acima*/
            	this.setObjetoDoJogo(null,posAdj[pos][0], posAdj[pos][1]);
            	//objetoDoJogo.setPosX(posAdj[h][0]);
            	int newPosX = posAdj[pos][0];
            	int newPosY = posAdj[pos][1];
            	switch (pos) 
            	{
    			case 0:
    			{
    				int i = 0;
    				while(i < 3 && this.getObjetoDoJogo(newPosX-1, newPosY) == null)
    				{
    					newPosX -= 1;
        				int newPos[] = this.inBoundPos(newPosX, newPosY);
        				newPosX = newPos[0];
        				i++;
        				
    				}
    				break;
    			}
    			case 1:
    			{

    				int i = 0;
    				while(i < 3 && this.getObjetoDoJogo(newPosX+1, newPosY) == null)
    				{
    					newPosX += 1;
        				int newPos[] = this.inBoundPos(newPosX, newPosY);
        				newPosX = newPos[0];
        				i++;
        				
    				}
    				break;
    			}
    			case 2:
    			{

    				int i = 0;
    				while(i < 3 && this.getObjetoDoJogo(newPosX, newPosY-1) == null)
    				{
    					newPosY -= 1;
        				int newPos[] = this.inBoundPos(newPosX, newPosY);
        				newPosY = newPos[1];
        				i++;
        				
    				}
    				break;
    			}
    			case 3:
    			{
    				int i = 0;
    				while(i < 3 && this.getObjetoDoJogo(newPosX, newPosY+1) == null)
    				{
    					newPosY += 1;
        				int newPos[] = this.inBoundPos(newPosX, newPosY);
        				newPosY = newPos[1];
        				i++;
        				
    				}
    				break;
    			}
    			default:
    				break;
    			}
            	
            	for(int j =0;j<players.length;j++)
            	{
            		if(players[j].bombas != null)
            		{
            			for(int i =0;i<players[j].bombas.length;i++)
                    	{
                    		if(players[j].bombas[i].getPosX() == objetoDoJogo.getPosX() && players[j].bombas[i].getPosY() == objetoDoJogo.getPosY())
                    		{
                    			players[j].bombas[i].setPosX(newPosX);
                    			players[j].bombas[i].setPosY(newPosY);
                    		}
                    	}
            		}
            	}
            	objetoDoJogo.setPosX(newPosX);
            	objetoDoJogo.setPosY(newPosY);
            	this.setObjetoDoJogo(objetoDoJogo, newPosX, newPosY);
            }

    	}
    }
    public int[] inBoundPos(int newPosX,int newPosY)
    {
    	if(newPosX < 0)
    	{
    		newPosX = 0;
    	}
    	else if(newPosX > this.getGridSnap()-1)
    	{
    		newPosX = this.getGridSnap()-1;
    	}
    	else if(newPosY < 0)
    	{
    		newPosY = 0;
    	}
    	else if(newPosY > this.getGridSnap()-1)
    	{
    		newPosY = this.getGridSnap()-1;
    	}
    	int[] arr ={newPosX, newPosY};
    	return arr;
    }
    public void verificaBombasNaCamada(Player currentPlayer, Player players[], float delta) 
    {
        Bomba bombas[] = currentPlayer.bombas;
        if (bombas != null) {
            for (int h = 0; h < bombas.length; h++) 
            {
                int[] pos = bombas[h].getPosicao();
                List<int[]> localExplosao = currentPlayer.updateBombasTime(delta, this.getGridSnap());

                if (localExplosao != null) 
                {
                    // Processar explosões
                    for (int j = 0; j < localExplosao.size(); j++) 
                    {
                        int[] explosionCoords = localExplosao.get(j);
                        int x = explosionCoords[0];
                        int y = explosionCoords[1];
                        if (x == -1 || y == -1) {
                            // j = (((int)j/3)+1) *3;
                            // j--;
                            continue;
                        }
                        // System.out.println(x + "," + y);
                        System.out.println("j:" + j + " len>" + localExplosao.size());
                        Texture player1Texture = players[0].geTexture();
                        Texture player2Texture = players[1].geTexture();
                        ObjetoDoJogo layer1Objeto = this.getObjetoDoJogo(x, y);
                        if (layer1Objeto != null && (layer1Objeto.geTexture().equals(player1Texture)
                                || layer1Objeto.geTexture().equals(player2Texture))) 
                        {
                            if (layer1Objeto.geTexture().equals(player2Texture)) 
                            {
                                players[1].recebeDano(1);
                            } else 
                            {
                                players[0].recebeDano(1);
                            }
                        } else 
                        {
                        	ObjetoDoJogo objEx = new ObjetoDoJogo();
                        	objEx.setPosX(x);
                        	objEx.setPosY(y);
                        	objEx.setTexture(Explosao.getExplosaTexture());
                            this.setObjetoDoJogo(objEx, x, y);
                        }
                        // if j % 3 != 0 then j = proximo mutiplo de 3 > j
                        // 0 1 2 -> 3 4 5-> 6 7 8->
                        if (layer1Objeto != null) 
                        {
                            // j+=3;
                            j = (((int) j / 3) + 1) * 3;
                            j--;
                        }
                    }
                    this.setObjetoDoJogo(null, pos[0], pos[1]);
                } else 
                {
                    this.setObjetoDoJogo(bombas[h], pos[0], pos[1]);
                }
            }
        }
    }

    public void updateCamada(float delta) 
    {
        List<ObjetoDoJogo> lista = new ArrayList<>();
        for (int x = 0; x < this.gridSnap; x++) 
        {
            for (int y = 0; y < this.gridSnap; y++) 
            {
                boolean inListCoord = false;
                for (int i = 0; i < this.listaCoordenadas.size(); i++) 
                {
                    for (int j = 0; j < this.listaCoordenadas.get(i).size(); j++) 
                    {
                        // Check if listaCoordenadas is not empty
                        if (!this.listaCoordenadas.isEmpty()) 
                        {
                            // Check bounds before accessing elements
                            if (i < this.listaCoordenadas.size()
                                    && j < this.listaCoordenadas.get(i).size()) 
                            {
                                if (this.listaCoordenadas.get(i).get(j).getPosX() == x && this.listaCoordenadas.get(i).get(j).getPosY() == y) 
                                {
                                    inListCoord = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (inListCoord) 
                    {
                        break;
                    }
                }
                if (objetosCamadas[x][y] != null && objetosCamadas[x][y].geTexture() == Explosao.getExplosaTexture() && !inListCoord) 
                {
                    lista.add(objetosCamadas[x][y]);
                }
            }
        }
        if (!lista.isEmpty()) 
        {
            this.listaCoordenadas.add(lista);
            this.counters.add(0.0f);
        }
        if (!this.counters.isEmpty() && this.listaCoordenadas.size() > 0) 
        {
            for (int i = 0; i < this.listaCoordenadas.size(); i++) 
            {
                float time = this.counters.get(i) + delta;
                if (time >= this.tempoDaExplosao) 
                {

                    for (int j = 0; j < this.listaCoordenadas.get(i).size(); j++) 
                    {
                        int x = this.listaCoordenadas.get(i).get(j).getPosX();
                        int y = this.listaCoordenadas.get(i).get(j).getPosY();
                        // Check bounds before accessing elements
                        if (x >= 0 && x < objetosCamadas.length && y >= 0 && y < objetosCamadas[0].length) 
                        {
                            this.objetosCamadas[x][y] = null;
                        }
                    }

                    this.counters.remove(i);
                    this.listaCoordenadas.remove(i);
                    i--; // Adjust the index after removing an element
                } else 
                {
                    this.counters.set(i, time);
                }
            }
        }
    }

    public float getImageSize() 
    {
        return this.imageSize;
    }

    public ObjetoDoJogo getObjetoDoJogo(int posX, int posY) 
    {
        if (posX >= 0 && posX < objetosCamadas.length && posY >= 0 && posY < objetosCamadas[0].length) 
        {
            return objetosCamadas[posX][posY];
        }
        return null;
    }

    public void setObjetoDoJogo(ObjetoDoJogo objeto, int posX, int posY) 
    {
        if (posX >= 0 && posX < objetosCamadas.length && posY >= 0 && posY < objetosCamadas[0].length) 
        {
            objetosCamadas[posX][posY] = objeto;
        }
    }

    public int getGridSnap() 
    {
        return this.gridSnap;
    }
}

