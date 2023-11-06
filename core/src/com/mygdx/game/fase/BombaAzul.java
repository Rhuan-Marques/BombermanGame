package com.mygdx.game.fase;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class BombaAzul extends Bomba{
    private static Texture bombaAzulTexture = new Texture("bombaAzul.png");
    public BombaAzul(int posX, int posY, Player criador)
    {
        super(posX, posY, criador);
        this.setTamanhoExplosao(3);
        this.setTexture(bombaAzulTexture);
        this.setPiercing(true);
        if(this.getPolvoraLvl() >= 3){
            this.setTamanhoExplosao(5);
        }

    }
    @Override
    public List<int[]> obterIndicesDaExplosao(int gridLength){
        int distancia = this.getTamanhoExplosao();
        int deslocamento = (distancia-1)/2;
        int startX = posX-deslocamento;
        int startY = posY-deslocamento;
        List<int[]> indices = new ArrayList<>();
        for(int i = startX; i<startX+distancia; i++){
            for(int j = startY; j<startY+distancia; j++){
                if(i<0 || i>gridLength-1 || j<0 || j>gridLength-1)
                    indices.add(new int[]{-1, -1});
                else indices.add(new int[]{i, j});
            }
        }
        return indices;
    }
}
