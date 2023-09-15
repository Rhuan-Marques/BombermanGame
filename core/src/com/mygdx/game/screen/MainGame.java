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
		this.game = game;
		//Camada de detalhes do mapa PLACEHOLDER
		
		this.generateCamadaTextures();
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
	            Boolean[] posOcupadas = camadas[3].posAdjOcupadas(players[i]);
	            
	            players[i].handleInput(Gdx.input, camadas[3].getGridSnap(), posOcupadas);
	            
	            // Verifique as bombas na cena
	            camadas[3].verificaBombasNaCamada(players[i], players, delta);
	            
	            // Atualize a camada 3
	            camadas[3].updateCamada(delta);
	            
	            // Atualize as posições dos jogadores na camada
	            camadas[3].setTexture(null, playersPos[0], playersPos[1]);
	            playersPos = players[i].getCurrentPos();
	            camadas[3].setTexture(players[i].geTexture(), playersPos[0], playersPos[1]);
	            
	            // Renderize as camadas
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
	}
	public void generateCamadaTextures() 
	{
	    for (int i = 0; i < camadas.length; i++) 
	    {
	        camadas[i] = new Camada(20);
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
	            camada.setTexture(detailTextures[indexi][indexii], i, j);
	        }
	    }
	}
	private void generateBreakableTileTextures(Camada camada)
	{
		for(int i = 1;i<camada.getGridSnap();i+=2)
		{
			for(int j = 1;j<camada.getGridSnap();j+=2)
			{
				camadas[3].setTexture(new Texture("tile.jpg"), i, j);
			}
		}
	}

	private void generateGroundTextures(Camada camada) 
	{
	    for (int i = 0; i < camada.getGridSnap(); i++) 
	    {
	        for (int j = 0; j < camada.getGridSnap(); j++) 
	        {
	            camada.setTexture(chaoTexture, i, j);
	        }
	    }
	}

	private void generateSandTextures(Camada camada) 
	{
	    for (int i = 1; i < camada.getGridSnap(); i += 2) 
	    {
	        for (int j = 1; j < camada.getGridSnap(); j += 2) 
	        {
	            camada.setTexture(new Texture("chao.png"), i, j);
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
