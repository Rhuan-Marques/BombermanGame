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


public abstract class TutorialScreen implements Screen {
    Bomberman game;
    protected static BotaoClicavel play;
    protected static final int INITIAL_X = 50;
    protected static int INITIAL_Y;
    protected static int SIZE_ICON;
    protected static int GAP;
    protected static final String DESC_PATH = ".\\assets\\Tutorial";
    protected String DESC_TYPE;
    protected static HashMap<String, Texture> sprites;
    protected static HashMap<String, String> descriptions;

    protected ArrayList <String> items;
    public TutorialScreen(Bomberman game)
    {
        this.game = game;
        this.DESC_TYPE = "";
        INITIAL_Y = game.HEIGHT-40;
        GAP = 20;

        play = new BotaoClicavel(-1, 5, 330, 150,
                new Texture("Botoes\\play_button_active.png"),
                new Texture("Botoes\\play_button_inactive.png"));
        play.centerPosX(game);

        game.font.setColor(0.8f, 0.8f, 0.4f, 1);
        game.font.getData().setScale(1.5f);

        sprites = new HashMap<>();
        descriptions = new HashMap<>();
        this.items = new ArrayList<>();
    }

    @Override
    public abstract void render(float delta);
    protected void loadAssets(){
        this.items = listItemsFromAssetsFolder(DESC_PATH);
        for(String item : items){
            sprites.put(item, getSprite(item));
            descriptions.put(item, getDescription(item));
        }

        this.SIZE_ICON = (game.HEIGHT - play.getHeight()-10 - (game.HEIGHT-INITIAL_Y) - GAP * (items.size()-1))/ items.size();
        if(this.items.size() <= 3)
            this.SIZE_ICON -= 50;
    }


    protected void addItemDescription(String itemName, int yCoord){
        game.batch.draw(sprites.get(itemName), INITIAL_X, yCoord, SIZE_ICON, SIZE_ICON);
        int x = INITIAL_X + SIZE_ICON + 10;
        int y =  yCoord + (int)(SIZE_ICON * 0.8);
        game.font.draw(game.batch, descriptions.get(itemName),x ,y, game.WIDTH - x, -1, true);
    }

    protected abstract Texture getSprite(String itemName);

    protected Texture spriteFromPath(String path){
        Texture texture;
        try{
            texture = new Texture(path);
        }catch (Exception e){
            texture = new Texture("TestingImage.png");
        }
        return texture;
    }

    protected String getDescription(String itemName) {
        String fileName = DESC_PATH + "\\" + DESC_TYPE + "_" + itemName + ".txt";
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            content.append("DESCRICAO NAO ENCONTRADA");
        }

        return content.toString();
    }

    protected ArrayList<String> listItemsFromAssetsFolder(String folderPath) {
        ArrayList<String> itemList = new ArrayList<>();
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if(file.getName().startsWith(DESC_TYPE)){
                        String itemName = file.getName();
                        itemName = itemName.replaceFirst(DESC_TYPE, "").replaceFirst("_", "").replace(".txt", "").replace(".TXT", "");
                        itemList.add(itemName);
                    }
                }
            }
        }

        return itemList;
    }

    protected String removeOrder(String itemName){
        return itemName.replaceAll("[0-9]", "").replace(".", "");
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
