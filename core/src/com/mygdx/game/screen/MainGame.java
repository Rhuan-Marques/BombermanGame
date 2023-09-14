package com.mygdx.game.screen;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Grid;

/*"implements" é usado para garantir que a classe MainGameScreen cumpra todos os métodos 
 * definidos na interface Screen. Isso significa que a classe deve fornecer implementações para todos 
 * os métodos declarados na interface*/
public class MainGame implements Screen
{
	Player player;
	int x = 0,y = 0;
	Camada camadas[] = new Camada[5];//make grid snap Static
	Texture chaoTexture = new Texture("box.jpg");
	Grid game;
	
	public MainGame(Grid game)
	{
		player = new Player(0, 0, new Texture("badlogic.jpg"));
		for(int i = 0;i<camadas.length;i++)
		{
			camadas[i] = new Camada(20);
		}
		this.game = game;
		for(int i = 0;i<camadas[0].getGridSnap();i++)
		{
			for(int j = 0;j<camadas[0].getGridSnap();j++)
			{
				camadas[0].setTexture(chaoTexture, i, j);
			}
		}
		for(int i = 1;i<camadas[1].getGridSnap();i+=2)
		{
			for(int j = 1;j<camadas[1].getGridSnap();j+=2)
			{
				camadas[1].setTexture(new Texture("tile.jpg"), i, j);
			}
		}
		
	}
	
	@Override
	public void show() 
	{
		//player = ;
		
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//float deltaTime = Gdx.graphics.getDeltaTime();
	    //apagando player da posicao atual 
	    int playerPos[] = player.getCurrentPos();
		camadas[1].setTexture(null, playerPos[0], playerPos[1]);
		//escrevendo player na nova posicao
		int posAdj[][] = player.getAdjacentPositions(camadas[1].getGridSnap());
		Boolean posOcupadas[] = new Boolean[4];
		//pegando posicoes adjacentes ao player
		for(int i = 0;i<posAdj.length;i++)
		{
			if(posAdj[i]!= null && camadas[1].getTexture(posAdj[i][0], posAdj[i][1]) !=null)
			{
				posOcupadas[i] = true;
			}
			else 
			{
				posOcupadas[i] = false;
			}
		}
		player.handleInput(Gdx.input, camadas[1].getGridSnap(),posOcupadas);
		
		//Verificando bombas na scene
		if (player.bombas != null) 
		{
		    for (int i = 0; i < player.bombas.length; i++) 
		    {
		    	int[] pos = player.bombas[i].getPosicao();
		        List<int[]> localExplosao = player.updateBombasTime(delta, camadas[1].getGridSnap());
		        if (localExplosao != null) 
		        {
		            for (int j = 0; j < localExplosao.size(); j++) 
		            {
		                int[] explosionCoords = localExplosao.get(j);
		                int x = explosionCoords[0];
		                int y = explosionCoords[1];
		                System.out.println(x + "," + y);
		                if(camadas[1].getTexture(x, y) == player.geTexture())
		                {
		                	//player perde vida
		                }
		                else 
		                {
		                	camadas[1].setTexture(Bomba.getExplosaTexture(), x, y);
						}
		                
		            }
		            camadas[1].setTexture(null, pos[0], pos[1]);
		        }
		        else
		        {
		        	camadas[1].setTexture(Bomba.getBombaTexture(), pos[0], pos[1]);
		        }
		    }
		}
		camadas[1].updateCamada(delta);
		
			
		playerPos = player.getCurrentPos();
		camadas[1].setTexture(player.geTexture(), playerPos[0], playerPos[1]);
		game.batch.begin();
		for(int camada = 0;camada<camadas.length;camada++)
		{
			for(int i = 0;i<camadas[camada].getGridSnap();i++)
			{
				for(int j = 0;j<camadas[camada].getGridSnap();j++)
				{
					if(camadas[camada].getTexture(i,j) != null)
					{
						game.batch.draw(camadas[camada].getTexture(i,j), i * camadas[camada].getImageSize(), j * camadas[camada].getImageSize(), camadas[camada].getImageSize(), camadas[camada].getImageSize());	
					}
				}
			}
		}
		
		game.batch.end();
	}
	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
