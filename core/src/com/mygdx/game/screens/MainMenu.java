package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Bomberman;
import com.mygdx.game.screens.BotaoClicavel;


public class MainMenu implements Screen {

    private static BotaoClicavel play;
    private static BotaoClicavel exit;
    private static BotaoClicavel tutorial;

    private final Bomberman game;

    public MainMenu(Bomberman game) 
    {
        this.game = game;
        play = new BotaoClicavel(-1, 350, 330, 150,
                new Texture("Botoes\\play_button_active.png"),
                new Texture("Botoes\\play_button_inactive.png"));

        play.setPosX(game.WIDTH/2 - play.getWidth()/2);

        exit = new BotaoClicavel(-1, 100, 300, 150,
                new Texture("Botoes\\exit_button_active.png"),
                new Texture("Botoes\\exit_button_inactive.png"));
        exit.setPosX(game.WIDTH/2 - exit.getWidth()/2);

        tutorial = new BotaoClicavel(-1, 280, 300, 80,
                new Texture("Botoes\\tutorial_button_active.png"),
                new Texture("Botoes\\tutorial_button_inactive.png"));
        tutorial.setPosX(game.WIDTH/2 - tutorial.getWidth()/2);
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


        if(play.buttonFunction(game, delta)){
            this.dispose();
            game.setScreen(new MainGame(game));
        }

        else if (exit.buttonFunction(game, delta)){
                Gdx.app.exit();
        }

        else if(tutorial.buttonFunction(game, delta)){
                this.dispose();
                game.setScreen(new MainTutorialMenu(game));
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
