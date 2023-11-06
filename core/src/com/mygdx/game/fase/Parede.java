package com.mygdx.game.fase;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class Parede extends ObjetoDoJogo implements Explodivel{
    private Integer vida;
    public Parede(int posX, int posY, int vida){
        this.posX = posX;
        this.posY = posY;
        this.vida = vida;
        this.updateTexture();
    }
    @Override
    public ObjetoDoJogo recebeExplosao(int dano){
        this.vida-=dano;
        if(this.vida>0){
            this.updateTexture();
            return this;
        }
        else
            return this.acabaVida();

    }

    @Override
    public ObjetoDoJogo acabaVida() {
        Random random = new Random();
        int chance = random.nextInt(100)+1;
        ObjetoDoJogo drop;
        if(chance<=10){
            drop = new Item(this.posX, this.posY, 1);
        }
        else if(chance<=20){
            drop = new Item(this.posX, this.posY, 2);
        }
        else if(chance<=25){
            drop = new Item(this.posX, this.posY, 3);
        }
        else if(chance<=35){
            drop = new Item(this.posX, this.posY, 11);
        }
        else if(chance<=40){
            drop = new Item(this.posX, this.posY, 12);
        }
        else if(chance<=42){
            drop = new Item(this.posX, this.posY, 13);
        }
        else if(chance<=60){
            drop = new Item(this.posX, this.posY, 14);
        }
        else{
            drop = null;
        }
        return drop;
    }

    private void updateTexture(){
        String path = "Parede\\hp" + this.vida.toString() + ".png";
        this.texture = new Texture(path);
    }
}
