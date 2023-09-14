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
