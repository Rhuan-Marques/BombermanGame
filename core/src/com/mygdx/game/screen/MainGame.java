package com.mygdx.game.screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Grid;

/*"implements" é usado para garantir que a classe MainGameScreen cumpra todos os métodos 
 * definidos na interface Screen. Isso significa que a classe deve fornecer implementações para todos 
 * os métodos declarados na interface*/
public class MainGame implements Screen
{
	  Player players[];
	    int x = 0, y = 0;
	    Camada camadas[] = new Camada[5];
	    Texture chaoTexture = new Texture("grass.png");
	    Grid game;
	    Boolean gameOver;
	    Texture spriteDeCoracaoTexture = new Texture("spriteDeCoracao.png");
	    Texture detailTextures[][] = {{new Texture("grassSpriteb.png"), new Texture("grassSprite.png")},
	            {new Texture("redFlower.png"), new Texture("yellowFlower.png"), new Texture("whiteFlower.png")}};

	    public MainGame(Grid game) 
	    {
	        gameOver = false;
	        this.game = game;
	        this.generateCamadaTextures();
	        players = new Player[2];
	        players[0] = new Player(0, 0, new Texture("badlogic.jpg"),2);
	        players[1] = new Player2(camadas[3].getGridSnap() - 2, camadas[3].getGridSnap() - 1, new Texture("player2.png"),2);
	    }

	    @Override
	    public void show() 
	    {
	        // players[i] = ;
	    }

	    public void render(float delta) 
	    {
	        Gdx.gl.glClearColor(0.6f, 0.4f, 0.2f, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
	        gameOver = (!players[0].taVivo() || !players[1].taVivo());

	        if (!gameOver) 
	        {
	            for (int i = 0; i < players.length; i++) 
	            {
	                int[] playersPos = players[i].getCurrentPos();
	                Boolean[] posOcupadas = camadas[3].posAdjOcupadas(players[i]);

	                int pos = players[i].handleInput(Gdx.input, camadas[3].getGridSnap(), posOcupadas);

	                camadas[3].verificaBombasNaCamada(players[i], players, delta);

	                camadas[3].updateCamada(delta);

	                camadas[3].setObjetoDoJogo(null, playersPos[0], playersPos[1]);
	                playersPos = players[i].getCurrentPos();
	                camadas[3].setObjetoDoJogo(players[i], playersPos[0], playersPos[1]);
	                camadas[3].handleColission(pos,players[i].getAdjacentPositions(camadas[3].getGridSnap()),players);
	                for(int y =0;players[0].bombas!= null&&y<players[0].bombas.length;y++)
	                {
	                	System.out.println(players[0].bombas[y].getPosicao()[0]+" "+players[0].bombas[y].getPosicao()[1]);
	                }
	                game.batch.begin();

	                this.renderizaCamadasdoGame();

	                game.batch.end();
	            }
	        } else 
	        {
	            game.setScreen(new MainMenu(game));
	        }
	    }

	    public void renderizaCamadasdoGame() 
	    {

	    	
	        for (int camada = 0; camada < camadas.length; camada++) 
	        {
	            for (int h = 0; h < camadas[camada].getGridSnap(); h++) 
	            {
	                for (int j = 0; j < camadas[camada].getGridSnap(); j++) 
	                {
	                    ObjetoDoJogo objeto = camadas[camada].getObjetoDoJogo(h, j);
	                    if (objeto != null) 
	                    {
	                        if (camada != 1) 
	                        {
	                            game.batch.draw(objeto.geTexture(), h * camadas[camada].getImageSize(),
	                                    j * camadas[camada].getImageSize(), camadas[camada].getImageSize(),
	                                    camadas[camada].getImageSize());
	                        } else 
	                        {
	                            long seed = (long) h * 2654435761L + (long) j * 2654435789L;
	                            Random random = new Random(seed);
	                            float offsetX = random.nextFloat() * camadas[camada].getImageSize();
	                            float offsetY = random.nextFloat() * camadas[camada].getImageSize();
	                            game.batch.draw(objeto.geTexture(), h * camadas[camada].getImageSize() + offsetX,
	                                    j * camadas[camada].getImageSize() + offsetY,
	                                    camadas[camada].getImageSize() / 4, camadas[camada].getImageSize() / 4);
	                        }
	                    }
	                }
	            }
	        }
	        for (int i = 0; i < players.length; i++) 
            {
	        	float offset = 0f;
	        	if(i ==1)
	        	{
	        		offset = Grid.WIDTH - (players[i].getVida() *(2*spriteDeCoracaoTexture.getWidth()));
	        	}
	        	for(int j =0;j<players[i].getVida();j++)
	        	{
	        		game.batch.draw(spriteDeCoracaoTexture,(j * (2*spriteDeCoracaoTexture.getWidth()) ) + offset,
                            Grid.HEIGTH - (spriteDeCoracaoTexture.getHeight()*2),(2*spriteDeCoracaoTexture.getWidth()),spriteDeCoracaoTexture.getHeight()*2);
	        	}
            }
	    }
	public void generateCamadaTextures() 
	{
	    for (int i = 0; i < camadas.length; i++) 
	    {
	        camadas[i] = new Camada(10);
	    }
	    // Camada de detalhes do mapa PLACEHOLDER
	    this.generateDetailTextures(camadas[1]);

	    // Gerando chão (tile de grama)
	    this.generateGroundTextures(camadas[0]);

	    // Gerando areia abaixo do tile quebrável
	    this.generateSandTextures(camadas[0]);
	    // Gerando tile quebraveis 
	    this.generateBreakableTileTextures(camadas[3]);
	}
	private void generateDetailTextures(Camada camada) 
	{
	    for (int i = 0; i < camada.getGridSnap(); i += 2) 
	    {
	        for (int j = 0; j < camada.getGridSnap(); j += 2) 
	        {
	            Random random = new Random();
	            int indexi = random.nextInt(detailTextures.length);
	            int indexii = random.nextInt(detailTextures[indexi].length);

	            ObjetoDoJogo objeto = new ObjetoDoJogo();
	            objeto.setTexture(detailTextures[indexi][indexii]);
	            objeto.setPosX(i);
	            objeto.setPosY(j);

	            camada.setObjetoDoJogo(objeto, i, j);
	        }
	    }
	}

	private void generateBreakableTileTextures(Camada camada)
	{
	    for(int i = 1; i < camada.getGridSnap(); i += 2)
	    {
	        for(int j = 1; j < camada.getGridSnap(); j += 2)
	        {
	            ObjetoDoJogo objeto = new ObjetoDoJogo();
	            objeto.setTexture(new Texture("tile.jpg"));
	            objeto.setPosX(i);
	            objeto.setPosY(j);

	            camada.setObjetoDoJogo(objeto, i, j);
	        }
	    }
	}

	private void generateGroundTextures(Camada camada) 
	{
	    for (int i = 0; i < camada.getGridSnap(); i++) 
	    {
	        for (int j = 0; j < camada.getGridSnap(); j++) 
	        {
	            ObjetoDoJogo objeto = new ObjetoDoJogo();
	            objeto.setTexture(chaoTexture);
	            objeto.setPosX(i);
	            objeto.setPosY(j);

	            camada.setObjetoDoJogo(objeto, i, j);
	        }
	    }
	}

	private void generateSandTextures(Camada camada) 
	{
	    for (int i = 1; i < camada.getGridSnap(); i += 2) 
	    {
	        for (int j = 1; j < camada.getGridSnap(); j += 2) 
	        {
	            ObjetoDoJogo objeto = new ObjetoDoJogo();
	            objeto.setTexture(new Texture("chao.png"));
	            objeto.setPosX(i);
	            objeto.setPosY(j);

	            camada.setObjetoDoJogo(objeto, i, j);
	        }
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
