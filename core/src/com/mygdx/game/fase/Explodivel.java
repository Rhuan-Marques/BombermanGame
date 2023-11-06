package com.mygdx.game.fase;

public interface Explodivel {
    Integer vida = 1;
    //Funcao que retorna um ObjetoDoJogo ao receber dano
    //Retorna a si mesma se nada acontecer
    //Retorna um objeto para substiruir a si caso contrario (null ou items)
    ObjetoDoJogo recebeExplosao(int dano);
    ObjetoDoJogo acabaVida();

}
