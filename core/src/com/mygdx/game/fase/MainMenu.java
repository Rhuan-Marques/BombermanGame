package com.mygdx.game.fase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Bomberman;


public class MainMenu implements Screen {

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int TUTORIAL_BUTTON_WIDTH = 300;
    private static final int TUTORIAL_BUTTON_HEIGHT = 80;
    private static final int PLAY_BUTTON_WIDTH = 330;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_Y = 350;
    private static final int TUTORIAL_BUTTON_Y = 280;

    private final Bomberman game;
    private final Texture playButtonActive;
    private final Texture playButtonInactive;
    private final Texture tutorialButtonInactive;
    private final Texture tutorialButtonActive;
    private final Texture exitButtonActive;
    private final Texture exitButtonInactive;

    public MainMenu(Bomberman game) 
    {
        this.game = game;
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        tutorialButtonActive = new Texture("tutorial_button_active.png");
        tutorialButtonInactive = new Texture("tutorial_button_inactive.png");
    }

    @Override
    public void show() 
    {
        // Método não implementado
    }

    @Override
    public void render(float delta) 
    {
        // Configuração do fundo
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        int x = Bomberman.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;

        // Verifica se o mouse está sobre o botão de saída
        if (isMouseOverButton(x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT)) 
        {
            game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            // Fecha o jogo se o botão é clicado
            if (Gdx.input.isTouched()) 
            {
                Gdx.app.exit();
            }
        }
        else 
        {
            game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        x = Bomberman.WIDTH / 2 - TUTORIAL_BUTTON_WIDTH / 2;
        if (isMouseOverButton(x, TUTORIAL_BUTTON_Y, TUTORIAL_BUTTON_WIDTH, TUTORIAL_BUTTON_HEIGHT))
        {
            game.batch.draw(tutorialButtonActive, x, TUTORIAL_BUTTON_Y, TUTORIAL_BUTTON_WIDTH, TUTORIAL_BUTTON_HEIGHT);
            // Fecha o jogo se o botão é clicado
            if (Gdx.input.isTouched())
            {
                this.dispose();
                game.setScreen(new TutorialMenu(game));
            }
        }
        else
        {
            game.batch.draw(tutorialButtonInactive, x-00, TUTORIAL_BUTTON_Y, TUTORIAL_BUTTON_WIDTH, TUTORIAL_BUTTON_HEIGHT);
        }
        x = Bomberman.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
            // Verifica se o mouse está sobre o botão de jogar
        if (isMouseOverButton(x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT)) 
        {
            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            // Inicia o jogo principal se o botão é clicado
            if (Gdx.input.isTouched()) 
            {
                this.dispose();
                game.setScreen(new MainGame(game));
            }
        } 
        else 
        {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    // Verifica se o mouse está sobre um botão
    private boolean isMouseOverButton(int buttonX, int buttonY, int buttonWidth, int buttonHeight) 
    {
        return Gdx.input.getX() < buttonX + buttonWidth &&
               Gdx.input.getX() > buttonX &&
               Bomberman.HEIGHT - Gdx.input.getY() < buttonY + buttonHeight &&
               Bomberman.HEIGHT - Gdx.input.getY() > buttonY;
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
