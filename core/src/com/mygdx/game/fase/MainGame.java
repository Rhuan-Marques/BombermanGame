package com.mygdx.game.fase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Bomberman;
import com.badlogic.gdx.Input.Keys;
import org.w3c.dom.Text;

/*"implements" é usado para garantir que a classe MainGameScreen cumpra todos os métodos 
 * definidos na interface Screen. Isso significa que a classe deve fornecer implementações para todos 
 * os métodos declarados na interface*/
public class MainGame implements Screen {
	private BitmapFont font;
	private OrthographicCamera font_cam;
	private Player[] players;
	private ArrayList<Inimigo> inimigosList;
	private int numeroDeInimigos;
	private Camada[] camadas;
	private Bomberman game;
	private boolean gameOver;
	private Texture spriteDeCoracaoTexture;
	private Texture itemSlot;
	private Texture[] itemUiTextures;

	private static final int ITEM_UI_MAX = 7;

	public MainGame(Bomberman game) {
		// Inicialização
		spriteDeCoracaoTexture = new Texture("spriteDeCoracao.png");
		itemSlot = new Texture("itemSlot.png");
		itemUiTextures = new Texture[ITEM_UI_MAX];
		for(int i=0; i<ITEM_UI_MAX-1; i++){
			itemUiTextures[i] = new Item(-1, -1, i+11).geTexture();
		}
		camadas = new Camada[5];
		gameOver = false;
		this.game = game;
		font = new BitmapFont(Gdx.files.internal("fontLucidaSans.fnt"));
		this.generateCamadaTextures();
		numeroDeInimigos = 3;
		players = new Player[2];
		players[0] = new Player(0, 0, "player1", 3,
				Keys.UP, Keys.RIGHT, Keys.DOWN, Keys.LEFT, Keys.SHIFT_RIGHT);
		players[1] = new Player(camadas[3].getGridSnap() - 2, camadas[3].getGridSnap() - 1, "player2", 3,
				Keys.W, Keys.D, Keys.S, Keys.A, Keys.F);
		inimigosList = new ArrayList<>();
		inimigosList = camadas[3].instanciarInimigosAleatoriamente(numeroDeInimigos);
	}

	@Override
	public void show() {
		// Configuração da câmera
		font_cam = new OrthographicCamera();
		font_cam.setToOrtho(false, Bomberman.WIDTH, Bomberman.HEIGHT);
	}

	public void render(float delta) {
		// Limpa o buffer de tela
		Gdx.gl.glClearColor(0.6f, 0.4f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Verifica se o jogo acabou
		if(!players[0].taVivo() && !players[1].taVivo())
			game.setScreen(new WinScreen(game, null));
		else if(!players[0].taVivo())
			game.setScreen(new WinScreen(game, players[1].getName()));
		else if(!players[1].taVivo())
			game.setScreen(new WinScreen(game, players[0].getName()));
		if (inimigosList != null) {
			for (int i = 0; i < inimigosList.size(); i++) {
				Inimigo inimigo = inimigosList.get(i);
				if (inimigo != null) {
					camadas[3].setObjetoDoJogo(null, inimigo.getPosX(), inimigo.getPosY());
					inimigo.defaultBehavior(camadas[3], delta);
					camadas[3].setObjetoDoJogo(inimigo, inimigo.getPosX(), inimigo.getPosY());

					if (inimigo.getExpl()) {
						camadas[3].setObjetoDoJogo(null, inimigo.getPosX(), inimigo.getPosY());
						inimigosList.remove(i);
						i--; // Atualiza o índice após remover um inimigo
					}
				}
			}
		}
		if (!gameOver) {

			for (int i = 0; i < players.length; i++) {

				// Armazena posicao do player antes do movimento
				int[] playersPos = players[i].getCurrentPos();
				Boolean[] posOcupadas = camadas[3].posAdjOcupadas(players[i]);

				// Checa input do player e guarda a direcao que virou em pos
				int pos = players[i].handleInput(Gdx.input, camadas[3].getGridSnap(), posOcupadas, delta);

				// Manuzeia explosoes de bombas
				camadas[3].verificaBombasNaCamada(players[i], inimigosList, delta);
				camadas[3].explosaoManager(delta);

				// Remove o jogador da posição anterior e o coloca na posição atual
				camadas[3].setObjetoDoJogo(null, playersPos[0], playersPos[1]);
				playersPos = players[i].getCurrentPos();
				camadas[3].setObjetoDoJogo(players[i], playersPos[0], playersPos[1]);

				// Gerencia colisões
				camadas[3].manejaColisao(pos, players[i].getAdjacentPositions(camadas[3].getGridSnap()), players, players[i]);

				game.batch.begin();

				// Renderiza as camadas do jogo
				this.renderizaCamadasdoGame();

				game.batch.end();
			}
		} else {
			// Volta para o menu principal quando o jogo acaba
			game.setScreen(new MainMenu(game));
		}
	}

	/**
	 * Renderiza as camadas do jogo, incluindo objetos, jogadores e informações de vida.
	 */
	public void renderizaCamadasdoGame() {
		// Itera sobre as camadas do jogo
		for (int camada = 0; camada < camadas.length; camada++) {
			// Itera sobre as posições na camada atual
			for (int h = 0; h < camadas[camada].getGridSnap(); h++) {
				for (int j = 0; j < camadas[camada].getGridSnap(); j++) {
					// Obtém o objeto na posição atual
					ObjetoDoJogo objeto = camadas[camada].getObjetoDoJogo(h, j);
					if (objeto != null) {
						if (camada != 1) {
							// Renderiza o objeto na camada, ajustando a posição e o tamanho conforme necessário
							game.batch.draw(objeto.geTexture(), h * camadas[camada].getImageSize(),
									j * camadas[camada].getImageSize(), camadas[camada].getImageSize(),
									camadas[camada].getImageSize());
						} else {
							// Adiciona um deslocamento aleatório para a camada 1 para que os assets de detalhes sejam renderizados de forma mais variada
							long seed = (long) h * 2654435761L + (long) j * 2654435789L;
							Random random = new Random(seed);
							float offsetX = random.nextFloat() * camadas[camada].getImageSize();
							float offsetY = random.nextFloat() * camadas[camada].getImageSize();
							// Renderiza o objeto na camada 1 com o deslocamento aleatório
							game.batch.draw(objeto.geTexture(), h * camadas[camada].getImageSize() + offsetX,
									j * camadas[camada].getImageSize() + offsetY,
									camadas[camada].getImageSize() / 4, camadas[camada].getImageSize() / 4);
						}
					}
				}
			}
		}

		// Define a projeção da câmera para a fonte utilizada para renderizar informações de vida
		game.batch.setProjectionMatrix(font_cam.combined);
		// Itera sobre os jogadores para renderizar as informações de vida
		for (int i = 0; i < players.length; i++) {
			String text = "Player " + (i + 1) + "  ";

			// Calcula a largura do texto para posicionar corretamente as informações de vida
			GlyphLayout layout = new GlyphLayout();
			layout.setText(font, text);
			float textWidth = layout.width;

			float offset = 0f;
			float sizeHearts = 3.5f * spriteDeCoracaoTexture.getWidth();
			float sizeBomb = sizeHearts * 1.3f;
			float posY = game.HEIGHT;
			float posX = 20;

			// Ajusta o deslocamento para o segundo jogador para alinhar as informações à direita da tela
			if (i == 1) {
				offset = Bomberman.WIDTH - (players[i].getMaxVida() * sizeHearts + textWidth) - posX;
			}

			// Renderiza os corações indicando a vida do jogador

			int j;
			posY -= sizeHearts;
			for (j = 0; j < players[i].getVida(); j++) {
				game.batch.draw(spriteDeCoracaoTexture,
						posX + offset + textWidth + (j * sizeHearts),
						posY,
						sizeHearts, sizeHearts);
			}
			// Renderiza o texto indicando o jogador e a posição dos corações
			font.draw(game.batch, layout, posX + offset, Bomberman.HEIGHT - 10);
			posY -= sizeBomb;

			// Renderiza UI do tipo atual de bomba
			game.batch.draw(itemSlot, posX + offset, posY, sizeBomb, sizeBomb);
			Texture currentBomb = players[i].getBombaType(-1, -1).geTexture();

			game.batch.draw(currentBomb, posX + offset, posY,
					sizeBomb, sizeBomb);
			posX += sizeBomb;

			// Renderiza UI de Sacos de Bombas
			addItemToUi(itemUiTextures[1], sizeBomb,1,  posX + offset, posY, players[i].getMaxBombas());
			posX+=sizeBomb;

			// Renderiza UI de Polvora
			addItemToUi(itemUiTextures[3], sizeBomb, 1,
					posX + offset, posY, players[i].getPolvora());
			posX += sizeBomb;

			// Renderiza UI de Bota
			addItemToUi(itemUiTextures[0], sizeBomb, 1.2f, posX + offset, posY,players[i].getKickPower());

			posX = 20;
			posY -= sizeBomb;

			if(addItemToUi(itemUiTextures[2], sizeBomb, posX + offset, posY, players[i].getPierceEffect())){
				posX += sizeBomb;
			}

			if(addItemToUi(itemUiTextures[4], sizeBomb, posX + offset, posY, players[i].getJumping())){
				posX += sizeBomb;
			}

			if(addItemToUi(itemUiTextures[5], sizeBomb, posX + offset, posY, players[i].getDmgResist())){
				posX += sizeBomb;
			}

		}
	}

	private void addItemToUi(Texture itemTexture, float size, float sizeMult, float posX, float posY, Integer playerHas){
		game.batch.draw(itemSlot, posX, posY, size, size);

		game.batch.draw(itemTexture, posX, posY,
				size * sizeMult, size * sizeMult);

		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, playerHas.toString());
		font.draw(game.batch, layout, posX + size / 2, posY + size/ 2);
	}

	private boolean addItemToUi(Texture itemTexture, float size, float posX, float posY, boolean playerHas){
		if(playerHas) {
			game.batch.draw(itemSlot, posX, posY, size, size);
			game.batch.draw(itemTexture, posX, posY, size, size);
			return true;
		}
		return false;
	}
	public void generateCamadaTextures() 
	{
	    int camada_size=10;
		for (int i = 0; i < camadas.length; i++)
	    {
	        camadas[i] = new Camada(camada_size);
	    }
	    // Camada de detalhes do mapa PLACEHOLDER
	    camadas[1].generateDetailTextures();

	    // Gerando chão (tile de grama)
	    camadas[0].generateGroundTextures();

	    // Gerando areia abaixo do tile quebrável
	    camadas[0].generateSandTextures();

	    // Gerando tile quebraveis
		int[][] matrix;
		matrix = randomLayout(camada_size);

		camadas[3].setBlockLayout(matrix);

	}
/*
	public int[][] importLayout(int size, String filepath) throws IOException{
		int[][] matrix = new int[size][size];
		if(filepath != null) {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line;
			int row = 0;
			while ((line = reader.readLine()) != null && row < size) {
				for (int col = 0; col < Math.min(size, line.length()); col++) {
					char c = line.charAt(col);
					if (Character.isDigit(c)) {
						matrix[row][col] = Character.getNumericValue(c);
					} else {
						matrix[row][col] = 0;
					}
				}
				row++;
			}
				reader.close();
				matrix[0][0] = 0;
				matrix[size-1][size-1] = 0;
				return matrix;
		}
		return randomLayout(size);
	}
*/
	public int[][] randomLayout(int size){
		int matrix[][] = new int[size][size];
		for(int i=0; i<size;i++) {
			for(int j=0; j<size;j++) {
				if((i==0 && j<3) ||
					(i<3 && j==0) ||
					(i==size-1 && j>size-4) ||
					(i>size-4 && j==size-1)){
					matrix[i][j] = 0;
					continue;
				}
				Random random = new Random();
				int chance = random.nextInt(100)+1;
				if(chance<=45) matrix[i][j] = 1;
				else if(chance<=55) matrix[i][j] = 2;
				else if(chance<=65) matrix[i][j] = 3;
				else matrix[i][j] = 0;
			}
		}
		matrix[0][0] = 0;
		matrix[size-1][size-1] = 0;
		return matrix;
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
