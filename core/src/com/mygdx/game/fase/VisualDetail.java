package com.mygdx.game.fase;

import com.badlogic.gdx.graphics.Texture;

public class VisualDetail extends ObjetoDoJogo{
    public VisualDetail(int posX, int posY, Texture texture){
        super();
        this.setPosX(posX);
        this.setPosY(posY);
        this.setTexture(texture);
    }
}
