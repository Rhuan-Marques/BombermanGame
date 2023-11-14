package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Bomberman;
import java.util.HashMap;


public class BombTutorial extends TutorialScreen {
    private static BotaoClicavel swap;
    private static HashMap<String, Integer> tiles;
    private static int tileNumber;
    private static int totalSize;
    private static int highestSize;
    public BombTutorial(Bomberman game)
    {
        super(game);
        this.DESC_TYPE = "Bomb";
        this.game = game;
        this.highestSize=0;
        this.GAP = 50;
        this.INITIAL_Y=game.HEIGHT-5;
        this.loadAssets();

        swap = new BotaoClicavel(50, 30, 150, 80,
                new Texture("Botoes\\back_button_active.png"),
                new Texture("Botoes\\back_button_inactive.png"));
    }

    @Override
    protected void loadAssets(){
        super.loadAssets();
        totalSize = SIZE_ICON * items.size();
        tiles = new HashMap<String, Integer>();
        for(String item :items){
            String clearName = removeOrder(item);
            int tileSize;
            if(clearName.equals("BombaPadrao")){
                tileSize=7;
            }
            else if(clearName.equals("BombaVermelha")) {
                tileSize=11;
            }
            else if(clearName.equals("BombaAzul")){
                tileSize=5;
            }
            else {
                tileSize=3;
            }
            tiles.put(item, tileSize);
            tileNumber+=tileSize;
            if(tileSize > highestSize)
                highestSize = tileSize;
        }
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
            this.SIZE_ICON = (totalSize/tileNumber) * tiles.get(item);
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
        path = "Tutorial\\ImgBomb_" + itemName + ".png";

        return spriteFromPath(path);
    }

    @Override
    protected void addItemDescription(String itemName, int yCoord){
        int difference = (totalSize / tileNumber)*(highestSize- tiles.get(itemName))/2;
        game.batch.draw(sprites.get(itemName), INITIAL_X+difference, yCoord, SIZE_ICON, SIZE_ICON);
        int x = INITIAL_X + difference + difference + SIZE_ICON + 10;
        int y =  yCoord + (int)(SIZE_ICON * 0.8) + 40;
        game.font.draw(game.batch, descriptions.get(itemName),x ,y, game.WIDTH - x, -1, true);
    }
}
