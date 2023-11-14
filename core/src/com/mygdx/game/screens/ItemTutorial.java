package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Bomberman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class ItemTutorial extends TutorialScreen {
    private static BotaoClicavel swap;
    public ItemTutorial(Bomberman game)
    {
        super(game);
        this.DESC_TYPE = "Item";
        this.game = game;
        this.loadAssets();
        swap = new BotaoClicavel(50, 30, 150, 80,
                new Texture("Botoes\\back_button_active.png"),
                new Texture("Botoes\\back_button_inactive.png"));
    }

    @Override
    public void render(float delta)
    {
        // Configuração do fundo
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        int yCoord=INITIAL_Y;

        for(String item : items){
            yCoord-=SIZE_ICON;
            addItemDescription(item, yCoord);
            yCoord-=GAP;
        }

        // Verifica se o mouse está sobre o botão de jogar
        if(play.buttonFunction(game, delta)){
                this.dispose();
                game.setScreen(new MainGame(game));
                game.batch.end();
                return;
        }

        if(swap.buttonFunction(game, delta)){
            this.dispose();
            game.setScreen(new MainTutorialMenu(game));
            game.batch.end();
            return;
        }
        game.batch.end();
    }

    @Override
    public Texture getSprite(String itemName) {

        itemName = itemName.replaceAll("[0-9]", "").replace(".", "");

        String path;
        path = "Items\\Item_" + itemName + ".png";

        return spriteFromPath(path);
    }


}
