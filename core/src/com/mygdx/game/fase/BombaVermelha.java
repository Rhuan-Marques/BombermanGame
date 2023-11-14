package com.mygdx.game.fase;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class BombaVermelha extends Bomba{
    private static Texture bombaVermTexture = new Texture("Bombas\\bombaVermelha.png");
    public BombaVermelha(int posX, int posY, Player criador)
    {
        super(posX, posY, criador);
        this.setTamanhoExplosao(3);
        if(this.getPolvoraLvl()>0)
            this.setTamanhoExplosao(5);
        this.setTexture(bombaVermTexture);
    }
    @Override
    public List<int[]> obterIndicesDaExplosao(int gridLength) {
        List<int[]> indices = super.obterIndicesDaExplosao(gridLength);
        if (this.getPolvoraLvl() >= 3) {
            int deslocamento = getTamanhoExplosao();
            for (int i = 1; i <= deslocamento; i++) {
                if (this.posX + i < gridLength && this.posY + i < gridLength)
                    indices.add(new int[]{this.posX + i, this.posY + i});
                else
                    indices.add(new int[]{-1, -1});
            }
            for (int i = 1; i <= deslocamento; i++) {
                if (this.posX - i >= 0 && this.posY + i < gridLength)
                    indices.add(new int[]{this.posX - i, this.posY + i});
                else
                    indices.add(new int[]{-1, -1});
            }
            for (int i = 1; i <= deslocamento; i++) {
                if (this.posX + i < gridLength && this.posY - i >= 0)
                    indices.add(new int[]{this.posX + i, this.posY - i});
                else
                    indices.add(new int[]{-1, -1});
            }
            for (int i = 1; i <= deslocamento; i++) {
                if (this.posX - i >= 0 && this.posY - i >= 0)
                    indices.add(new int[]{this.posX - i, this.posY - i});
                else
                    indices.add(new int[]{-1, -1});
            }

        }
        return indices;
    }
}
