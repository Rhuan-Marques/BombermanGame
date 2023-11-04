package com.mygdx.game.fase;
import com.badlogic.gdx.graphics.Texture;
public class Drop_BombaVerm extends ObjetoDoJogo implements Item{
    private static Texture itemBombaV = new Texture("Drop_BombaVermelha.png");
    private String tipo;
    public static Texture getTexture()
    {
        return itemBombaV;
    }
    public Drop_BombaVerm(int posX, int posY, String tipo)
    {
        this.setPosX(posX);
        this.setPosY(posY);
        this.setTexture(itemBombaV);
        this.tipo = new String;
    }

    @Override
    public void recebido(Player player) {
        player.recebeBombaEspecial(this.tipo);
    }

}
