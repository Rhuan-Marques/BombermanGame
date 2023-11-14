package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Bomberman;
import com.mygdx.game.fase.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class MainTutorialMenu extends TutorialScreen {

    private static BotaoClicavel bombasTut;
    private static BotaoClicavel itemTut;
    public MainTutorialMenu(Bomberman game)
    {
        super(game);
        this.DESC_TYPE = "Main";
        this.GAP = 40;
        this.loadAssets();

        int yPos;
        yPos = INITIAL_Y - SIZE_ICON - SIZE_ICON - 60;
        bombasTut = new BotaoClicavel(INITIAL_X-50, yPos, game.WIDTH-40, SIZE_ICON+40,
                new Texture("Botoes\\blue_button_active.png"),
                new Texture("Botoes\\blue_button_inactive.png"));

        yPos-= SIZE_ICON+GAP+5;
        itemTut = new BotaoClicavel(INITIAL_X-50, yPos, game.WIDTH-40, SIZE_ICON+60,
                new Texture("Botoes\\blue_button_active.png"),
                new Texture("Botoes\\blue_button_inactive.png"));

    }

    @Override
    public void render(float delta)
    {
        // Configuração do fundo
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        int yCoord= INITIAL_Y;



        // Verifica se o mouse está sobre o botão de jogar
        if(play.buttonFunction(game, delta)){
            this.dispose();
            game.setScreen(new MainGame(game));
            game.batch.end();
            return;
        }

        if(bombasTut.buttonFunction(game, delta)){
            this.dispose();
            game.setScreen(new BombTutorial(game));
            game.batch.end();
            return;
        }

        if(itemTut.buttonFunction(game, delta)){
            this.dispose();
            game.setScreen(new ItemTutorial(game));
            game.batch.end();
            return;
        }

        for(String item : items){
            yCoord-=SIZE_ICON;
            addItemDescription(item, yCoord);
            yCoord-=GAP;
        }

        game.batch.end();
    }
    @Override
    public Texture getSprite(String itemName) {
        String path = "";
        itemName = this.removeOrder(itemName);
        if(itemName.equals("Players"))
            path = "player1\\DOWN.png";
        else if(itemName.equals("Bombas"))
            path = "Bombas\\bomba.png";
        else if(itemName.equals("Items"))
            path = "Items\\Item_Asa.png";
        return spriteFromPath(path);
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
