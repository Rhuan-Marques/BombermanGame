package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Bomberman;

public class BotaoClicavel {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Texture active;
    private Texture inactive;
    private static float timeSinceLastButton;
    private static final float MAX_TIME = 1f;
    public BotaoClicavel(int posX, int posY, int width, int height, Texture active, Texture inactive){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.active = active;
        this.inactive = inactive;
    }

    public boolean buttonFunction(Bomberman game, float delta){
        timeSinceLastButton+=delta;
        if (isMouseOverButton(posX, posY, width, height))
        {
            game.batch.draw(active, posX, posY, width, height);
            // Inicia o jogo principal se o botão é clicado
            if (Gdx.input.isTouched() && timeSinceLastButton>MAX_TIME)
            {
                timeSinceLastButton = 0.0f;
                return true;
            }
        }
        else
        {
            game.batch.draw(inactive, posX, posY, width, height);
        }
        return false;
    }

    private boolean isMouseOverButton(int buttonX, int buttonY, int buttonWidth, int buttonHeight)
    {
        return Gdx.input.getX() < buttonX + buttonWidth &&
                Gdx.input.getX() > buttonX &&
                Bomberman.HEIGHT - Gdx.input.getY() < buttonY + buttonHeight &&
                Bomberman.HEIGHT - Gdx.input.getY() > buttonY;
    }

    public int getHeight() {
        return height;
    }
    public int getWidth(){
        return width;
    }

    public int getPosX(){
        return this.posX;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void swapTextures(Texture active, Texture inactive){
        this.active = active;
        this.inactive = inactive;
    }

    public void centerPosX(Bomberman game){
        this.posX = game.WIDTH/2 - this.width/2;
    }
}
