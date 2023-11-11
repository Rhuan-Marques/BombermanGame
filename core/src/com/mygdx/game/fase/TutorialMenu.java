package com.mygdx.game.fase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Bomberman;
import jdk.internal.net.http.common.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class TutorialMenu implements Screen {
    Bomberman game;
    private BotaoClicavel play;
    private static final int INITIAL_X = 15;
    private int SIZE_ICON;
    private static final String DESC_PATH = ".\\assets\\Tutorial_Descriptions";

    private static HashMap<String, Texture> sprites;
    private static HashMap<String, String> descriptions;
    ArrayList <String> items;
    public TutorialMenu(Bomberman game)
    {
        this.game = game;
        play = new BotaoClicavel(-1, 5, 330, 150,
                new Texture("play_button_active.png"),
                new Texture("play_button_inactive.png"));
        play.setPosX(game.WIDTH/2 - play.getWidth()/2);
        game.font.getData().setScale((float)1.8);
        game.font.setColor(0.8f, 0.8f, 0.4f, 1);

        this.items = listItemsFromAssetsFolder(DESC_PATH);
        this.SIZE_ICON = (game.HEIGHT-150)/ items.size();

        sprites = new HashMap<>();
        descriptions = new HashMap<>();
        for(String item: items){
            sprites.put(item, getSprite(item));
            descriptions.put(item, getDescription(item));
        }
    }

    @Override
    public void render(float delta)
    {
        // Configuração do fundo
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();


        int yCoord=150;

        for(String item : items){
            addItemDescription(item, yCoord);
            yCoord+=SIZE_ICON;
        }

        // Verifica se o mouse está sobre o botão de jogar
        if(play.buttonFunction(game)){
                this.dispose();
                game.setScreen(new MainGame(game));
        }

        game.batch.end();
    }

    void addItemDescription(String itemName, int yCoord){
        game.batch.draw(sprites.get(itemName), INITIAL_X, yCoord, SIZE_ICON, SIZE_ICON);
        int x = INITIAL_X + SIZE_ICON + 10;
        int y =  yCoord + (int)(SIZE_ICON * 0.8);
        game.font.draw(game.batch, descriptions.get(itemName),x ,y, game.WIDTH - x, -1, true);
    }

    public static Texture getSprite(String itemName) {
        Texture texture;
        String path = "Items\\Item_" + itemName + ".png";
        try{
            texture = new Texture(path);
        }catch (Exception e){
            texture = new Texture("TestingImage.png");
        }
        return texture;
    }


    public static String getDescription(String itemName) {
        String fileName = DESC_PATH + "\\Desc_" + itemName + ".txt";
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

    public static ArrayList<String> listItemsFromAssetsFolder(String folderPath) {
        ArrayList<String> itemList = new ArrayList<>();
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if(file.getName().contains("Desc")){
                        String itemName = file.getName();
                        itemName = itemName.replace("Desc_", "").replace(".txt", "").replace(".PNG", "");
                        itemList.add(itemName);
                    }
                }
            }
        }

        return itemList;
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
