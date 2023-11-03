package com.mygdx.game.fase;
import com.badlogic.gdx.graphics.Texture;
public class Drop_BombaVerm extends ObjetoDoJogo{
    private static Texture itemBombaV = new Texture("Drop_BombaVermelha.png");

    public static Texture getTexture()
    {
        return itemBombaV;
    }
    public Drop_BombaVerm(int posX, int posY)
    {
        this.setPosX(posX);
        this.setPosY(posY);
        this.setTexture(itemBombaV);
    }
}
