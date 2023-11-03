package com.mygdx.game.fase;
import com.badlogic.gdx.graphics.Texture;

public class BombaVermelha extends Bomba{
    private static Texture bombaVermTexture = new Texture("bombaVermelha.png");
    public BombaVermelha(int posX, int posY)
    {
        super(posX, posY);
        this.setTamanhoExplosao(5);
        this.setTexture(bombaVermTexture);
    }
}
