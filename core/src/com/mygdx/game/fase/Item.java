package com.mygdx.game.fase;
import com.badlogic.gdx.graphics.Texture;
public class Item extends ObjetoDoJogo{
    private int tipo;
    public Item(int posX, int posY, int tipo)
    {
        this.setPosX(posX);
        this.setPosY(posY);
        this.setTextureByType(tipo);
        this.tipo = tipo;
    }

    void setTextureByType(int tipo){
        String path = "Items\\Item_";
        if(tipo==1){
            path+="BombaVermelha";
        }
        else if(tipo==2){
            path+="BombaAzul";
        }
        else if(tipo==11){
            path+="Bota";
        }
        else if(tipo==12){
            path+="SacoBomba";
        }
        this.setTexture(new Texture(path+".png"));
    }

    public int getTipo(){
        return tipo;
    }
}
