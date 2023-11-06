package com.mygdx.game.fase;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class BombaDourada extends Bomba{
    private static Texture bombaDouradaTexture = new Texture("bombaDourada.png");
    public BombaDourada(int posX, int posY, Player criador){
        super(posX, posY, criador);
        this.setTexture(bombaDouradaTexture);
        this.setPiercing(true);
        this.setDamage(3);
        this.setTamanhoExplosao(1);
    }

    @Override
    public List<int[]> obterIndicesDaExplosao(int gridLength) {
        Bomba bomba;
        if(this.getPolvoraLvl() < 3){
            bomba = new Bomba(posX, posY, null);
        }
        else{
            bomba = new BombaAzul(posX, posY, null);
        }
        return bomba.obterIndicesDaExplosao(gridLength);
    }


}
