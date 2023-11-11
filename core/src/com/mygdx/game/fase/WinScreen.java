package com.mygdx.game.fase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Bomberman;
import com.badlogic.gdx.Screen;

public class WinScreen implements Screen {
    Bomberman game;
    private BotaoClicavel returnToMenu;
    private String win;
    WinScreen(Bomberman game, String winningPlayer){
        this.game = game;
        this.win = winningPlayer;
        game.font.getData().setScale((float)5);
        if(win == null)
            game.font.setColor(1, 1, 0, 1);
        else
            game.font.setColor(0, 1, 0, 1);

        returnToMenu = new BotaoClicavel(-1, 20, 500, 250,
                new Texture("exit_button_active.png"),
                new Texture("exit_button_inactive.png"));
        returnToMenu.setPosX(game.WIDTH/2 - returnToMenu.getWidth()/2);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        String winText;
        int numChar;

        if(win == null){
            winText = "\nEMPATE\n";
            numChar = 6;
        }
        else{
            winText = "JOGADOR\n" + win + "\nGANHOU";
            numChar =  7;
        }
        float x = game.WIDTH/2;
        float y = game.HEIGHT/2;
        x -= 50 * (numChar/2);
        y += game.font.getLineHeight() * 1.5;
        game.font.draw(game.batch, winText, x, y);

        if(returnToMenu.buttonFunction(game)){
            this.dispose();
            game.setScreen(new MainMenu(game));
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

    @Override
    public void show()
    {
        // Método não implementado
    }
}
