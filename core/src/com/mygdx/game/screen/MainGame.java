package com.mygdx.game.screen;

import java.util.List;
import java.util.Random;

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
	Player players[];
	int x = 0,y = 0;
	Camada camadas[] = new Camada[5];//make grid snap Static
	Texture chaoTexture = new Texture("grass.png");
	Grid game;
	Boolean gameOver;
	Texture detailTextures[][] = {{ new Texture("grassSpriteb.png"), new Texture("grassSprite.png")},{new Texture("redFlower.png"),new Texture("yellowFlower.png"),new Texture("whiteFlower.png")}};
	public MainGame(Grid game)
	{
		gameOver =false;
		for(int i = 0;i<camadas.length;i++)
		{
			camadas[i] = new Camada(20);
		}
		this.game = game;
		//Camada de detalhes do mapa PLACEHOLDER
		for(int i = 0;i<camadas[1].getGridSnap();i+=2)
		{
			for(int j = 0;j<camadas[1].getGridSnap();j+=2)
			{
				Random random = new Random();		       
		        int indexi = random.nextInt(detailTextures.length);
		        int indexii = random.nextInt(detailTextures[indexi].length);
				camadas[1].setTexture(detailTextures[indexi][indexii], i, j);
			}
		}
		
		//gereando chao tile de grama
		for(int i = 0;i<camadas[0].getGridSnap();i++)
		{
			for(int j = 0;j<camadas[0].getGridSnap();j++)
			{
				camadas[0].setTexture(chaoTexture, i, j);
			}
		}
		//gerando areia abaixo do tile quebravel
		for(int i = 1;i<camadas[0].getGridSnap();i+=2)
		{
			for(int j = 1;j<camadas[0].getGridSnap();j+=2)
			{
				camadas[0].setTexture(new Texture("chao.png"), i, j);
			}
		}
		for(int i = 1;i<camadas[3].getGridSnap();i+=2)
		{
			for(int j = 1;j<camadas[3].getGridSnap();j+=2)
			{
				camadas[3].setTexture(new Texture("tile.jpg"), i, j);
			}
		}
		players = new Player[2];
		players[0] = new Player(0, 0, new Texture("badlogic.jpg"));
		players[1] = new Player2(camadas[3].getGridSnap()-2, camadas[3].getGridSnap()-1, new Texture("player2.png"));
		//Player
	}
	
	@Override
	public void show() 
	{
		//players[i] = ;
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.6f, 0.4f, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    // Verifique o estado do jogo
	    gameOver = (!players[0].taVivo() || !players[1].taVivo());
	    
	    if (!gameOver) 
	    {
	        for (int i = 0; i < players.length; i++) 
	        {
	            int[] playersPos = players[i].getCurrentPos();
	            int[][] posAdj = players[i].getAdjacentPositions(camadas[3].getGridSnap());
	            Boolean[] posOcupadas = new Boolean[4];
	            
	            // Verifique as posições adjacentes
	            for (int h = 0; h < posAdj.length; h++) 
	            {
	                if (posAdj[h] != null && camadas[3].getTexture(posAdj[h][0], posAdj[h][1]) != null) {
	                    posOcupadas[h] = true;
	                } else 
	                {
	                    posOcupadas[h] = false;
	                }
	            }
	            
	            players[i].handleInput(Gdx.input, camadas[3].getGridSnap(), posOcupadas);
	            
	            // Verifique as bombas na cena
	            if (players[i].bombas != null) 
	            {
	                for (int h = 0; h < players[i].bombas.length; h++) {
	                    int[] pos = players[i].bombas[h].getPosicao();
	                    List<int[]> localExplosao = players[i].updateBombasTime(delta, camadas[3].getGridSnap());
	                    
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
	                            Texture layer1Texture = camadas[3].getTexture(x, y);
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
	                                camadas[3].setTexture(Bomba.getExplosaTexture(), x, y);
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
	                        camadas[3].setTexture(null, pos[0], pos[1]);
	                    } else 
	                    {
	                        camadas[3].setTexture(Bomba.getBombaTexture(), pos[0], pos[1]);
	                    }
	                }
	            }
	            
	            // Atualize a camada 1
	            camadas[3].updateCamada(delta);
	            
	            // Atualize as posições dos jogadores na camada
	            camadas[3].setTexture(null, playersPos[0], playersPos[1]);
	            playersPos = players[i].getCurrentPos();
	            camadas[3].setTexture(players[i].geTexture(), playersPos[0], playersPos[1]);
	            
	            // Renderize as camadas
	            game.batch.begin();
	            for (int camada = 0; camada < camadas.length; camada++) 
	            {
	                for (int h = 0; h < camadas[camada].getGridSnap(); h++) 
	                {
	                    for (int j = 0; j < camadas[camada].getGridSnap(); j++) 
	                    {
	                        if (camadas[camada].getTexture(h, j) != null) 
	                        {
	                        	if(camada != 1)
	                        	{
	                        		game.batch.draw(camadas[camada].getTexture(h, j), h * camadas[camada].getImageSize(), j * camadas[camada].getImageSize(), camadas[camada].getImageSize(), camadas[camada].getImageSize());
	                        	}
	                        	else 
	                        	{//Camada de detalhes do mapa PLACEHOLDER
	                        		
	                        		// Usando uma funcao hash para gerar numeros pseudo-aleatorios
	                        		long seed = (long)h * 2654435761L + (long)j * 2654435789L;
	                        		Random random = new Random(seed);
	                        		//Posicao na tela
	                        		float offsetX = random.nextFloat() * camadas[camada].getImageSize();
	                        		float offsetY = random.nextFloat() * camadas[camada].getImageSize();
	                        		game.batch.draw( camadas[camada].getTexture(h, j),h * camadas[camada].getImageSize() + offsetX,j * camadas[camada].getImageSize() + offsetY,camadas[camada].getImageSize() / 4,camadas[camada].getImageSize() / 4);
								}
	                            
	                        }
	      
	                    }
	                }
	            }
	            game.batch.end();
	        }
	    } else 
	    {
	        game.setScreen(new MainMenu(game));
	    }
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
