package com.mygdx.game.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Camada
{

	private Texture objetosCamadas[][];
	private float imageSize;
	public int gridSnap;
	private List<Float> counters;
	private List<List<int[]>> listacoodList ;
	private float tempoDaExplosao = Bomba.tempoDaExplosao;
	public Camada(int xSize)
	{
		this.listacoodList = new ArrayList<>();
		this.counters = new  ArrayList<>();;
		this.imageSize =(int) (Gdx.graphics.getWidth() / xSize);
		this.gridSnap = (int) (Gdx.graphics.getWidth() /this.imageSize);
		this.objetosCamadas = new Texture[this.gridSnap][this.gridSnap];
	}
	public Boolean[] posAdjOcupadas(Player player)
	{
		 int[][] posAdj = player.getAdjacentPositions(this.getGridSnap());
         Boolean[] posOcupadas = new Boolean[4];
         
         // Verifique as posições adjacentes ocupadas
         
         for (int h = 0; h < posAdj.length; h++) 
         {
             if (posAdj[h] != null && this.getTexture(posAdj[h][0], posAdj[h][1]) != null) {
                 posOcupadas[h] = true;
             } else 
             {
                 posOcupadas[h] = false;
             }
         }
         return posOcupadas;
	}
	public void verificaBombasNaCamada(Player currentPlayer, Player players[],float delta)
	{
		Bomba bombas[] = currentPlayer.bombas;
		  if (bombas != null) 
          {
              for (int h = 0; h < bombas.length; h++) {
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
                          if(x ==-1 || y == -1)
                          {
                          	//j = (((int)j/3)+1) *3;
                          	//j--;
                          	continue;
                          }
                          //System.out.println(x + "," + y);
                          System.out.println("j:"+j+" len>"+localExplosao.size());
                          Texture player1Texture = players[0].getTexture();
                          Texture player2Texture = players[1].getTexture();
                          Texture layer1Texture = this.getTexture(x, y);
                          if (layer1Texture != null && (layer1Texture.equals(player1Texture) || layer1Texture.equals(player2Texture))) 
                          {
                              if (layer1Texture.equals(player2Texture)) 
                              {
                                  players[1].recebeDano(1);
                              } else 
                              {
                                  players[0].recebeDano(1);
                              }
                          } else 
                          {
                              this.setTexture(Bomba.getExplosaTexture(), x, y);
                          }
                        //if j % 3 != 0 then j = proximo mutiplo de 3 > j
                          //0 1 2 -> 3 4 5-> 6 7 8->
                          if (layer1Texture != null ) 
                          {
                          	//j+=3;
                          	j = (((int)j/3)+1) *3;
                          	j--;
                          }
                      }
                      this.setTexture(null, pos[0], pos[1]);
                  } else 
                  {
                      this.setTexture(Bomba.getBombaTexture(), pos[0], pos[1]);
                  }
              }
          }
	}
	public void updateCamada(float delta) {
	    List<int[]> lista = new ArrayList<>();
	    for (int x = 0; x < this.gridSnap; x++) 
	    {
	        for (int y = 0; y < this.gridSnap; y++)
	        {
	            boolean inListCoord = false;
	            for (int i = 0; i < this.listacoodList.size(); i++) 
	            {
	                for (int j = 0; j < this.listacoodList.get(i).size(); j++) 
	                {
	                    // Check if listacoodList is not empty
	                    if (!this.listacoodList.isEmpty()) 
	                    {
	                        // Check bounds before accessing elements
	                        if (i < this.listacoodList.size() && j < this.listacoodList.get(i).size()) 
	                        {
	                            if (this.listacoodList.get(i).get(j)[0] == x && this.listacoodList.get(i).get(j)[1] == y) 
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
	            if (objetosCamadas[x][y] == Bomba.getExplosaTexture() && !inListCoord) 
	            {
	                int[] arr = { x, y };
	                lista.add(arr);
	            }
	        }
	    }
	    if (!lista.isEmpty()) {
	        this.listacoodList.add(lista);
            this.counters.add(0.0f);
	    }
	    if (!this.counters.isEmpty() && this.listacoodList.size()>0) 
	    {
	        for (int i = 0; i < this.listacoodList.size(); i++) 
	        {
	            float time = this.counters.get(i) + delta;
	            if (time >= this.tempoDaExplosao) 
	            {
	            	
	            	for (int j = 0; j < this.listacoodList.get(i).size(); j++) 
	                {
	            		System.out.println(this.listacoodList.get(i).get(j)[0]+" "+this.listacoodList.get(i).get(j)[1]);
	                }
	            	
		                for (int j = 0; j < this.listacoodList.get(i).size(); j++) 
		                {
		                    int arr[] = this.listacoodList.get(i).get(j);
		                    // Check bounds before accessing elements
		                    if (arr[0] >= 0 && arr[0] < objetosCamadas.length && arr[1] >= 0 && arr[1] < objetosCamadas[0].length) 
		                    {
		                        this.objetosCamadas[arr[0]][arr[1]] = null;
		                    }
		                }


	                this.counters.remove(i);
	                this.listacoodList.remove(i);
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
	public Texture getTexture(int posX, int posY)
	{
		/*if(posX < this.objetosCamadas.length && posX > 0 && posY < this.objetosCamadas.length && posY > 0)
		{
			return objetosCamadas[posX][posY];
		}*/
		return objetosCamadas[posX][posY];
		//return null;
	}
	public void setTexture(Texture texture,int posX,int posY)
	{
		this.objetosCamadas[posX][posY] = texture;
	}
	public int getGridSnap()
	{
		return this.gridSnap;
	}
}
