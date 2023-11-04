package com.mygdx.game.fase;

public interface Explodivel {
    Integer vida = 1;
    //Funcao que retorna um ObjetoDoJogo ao receber dano
    //Retorna null se nada acontecer
    //Retorna um objeto pra substuir a si mesma caso contrario
    ObjetoDoJogo recebeExplosao(int dano);
    ObjetoDoJogo acabaVida();

}
