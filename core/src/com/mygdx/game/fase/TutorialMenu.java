package com.mygdx.game.fase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Bomberman;

import java.io.*;
import java.util.ArrayList;


public class TutorialMenu implements Screen {
    Bomberman game;
    private final Texture playButtonActive;
    private final Texture playButtonInactive;
    private static final int INITIAL_X = 15;
    private static final int PLAY_BUTTON_WIDTH = 330;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private int SIZE_ICON;
    private static final String ITEM_PATH = ".\\assets\\Items";
    private static final String DESC_PATH = ".\\assets\\Tutorial_Descriptions";
    public TutorialMenu(Bomberman game)
    {
        this.game = game;
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        game.font.getData().setScale((float)1.8);
    }

    @Override
    public void render(float delta)
    {
        // Configuração do fundo
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        ArrayList <String> items = listItemsFromAssetsFolder(DESC_PATH);
        SIZE_ICON = (game.HEIGHT-150)/ items.size();
        int yCoord=150;

        for(String item : items){
            addItemDescription(item, yCoord);
            yCoord+=SIZE_ICON;
        }

        int x = Bomberman.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        // Verifica se o mouse está sobre o botão de jogar
        if (isMouseOverButton(x, 0, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT))
        {
            game.batch.draw(playButtonActive, x, 0, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            // Inicia o jogo principal se o botão é clicado
            if (Gdx.input.isTouched())
            {
                this.dispose();
                game.setScreen(new MainGame(game));
            }
        }
        else
        {
            game.batch.draw(playButtonInactive, x, 0, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }


        game.batch.end();
    }

    void addItemDescription(String itemName, int yCoord){
        Texture texture = getSprite(itemName);
        game.batch.draw(texture, INITIAL_X, yCoord, SIZE_ICON, SIZE_ICON);
        int x = INITIAL_X + SIZE_ICON + 10;
        int y =  yCoord + (int)(SIZE_ICON * 0.8);
        game.font.draw(game.batch, getDescription(itemName),x ,y, game.WIDTH - x, -1, true);
        //game.font.draw
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

    @Override
    public void show()
    {
        // Método não implementado
    }

}
