package com.mygdx.game.fase;
import java.util.Random;
import com.badlogic.gdx.graphics.Texture;

public class Inimigo extends BombaAzul
{
	private Texture sprite[] = {new Texture("inimigo/inimigoBombaIdle.png"),
			new Texture("inimigo/inimigoExplodindo.png"),
			new Texture("inimigo/inimigoExplodindoEfeito0.png"),
			new Texture("inimigo/inimigoExplodindoEfeito1.png")};
	private float countMove;
	private Boolean explosionMode;
	private float timeMove;
	private int moveDirection;
	private Boolean expl;
	 public Inimigo(int posX, int posY,int moveDirection)
    {
        super(posX, posY,null);
        this.setTamanhoExplosao(5);
        this.setTexture(sprite[0]);
        this.setPiercing(true);
        this.setDamage(1);
        this.moveDirection = moveDirection;
        this.explosionMode = false;
        this.countMove = 0;
        this.timeMove = 1f;
        this.expl = false;
    }

	 public void setExpl(boolean b)
	 {
		 this.expl = b;
	 }
	 public Boolean getExpl()
	 {
		 return this.expl;
	 }
	 public void defaultBehavior(Camada camada, float delta)
	 {
		 
		 if(explosionMode)
		 {
			 this.addToContador(delta);
		 }
		 else
		 {
			 if(camada.playerOnPosAdj(this))
			 {
				 this.explosionMode = true;
				 this.addToContador(delta);
			 }
			 else
			 {
				 this.countMove += delta;
				 this.move(camada.getGridSnap(), camada.posAdjOcupadas(this));
			 }
		 }
		 
	 }
	 public int move(int gridLength, Boolean posOcupadas[]) 
	{
	    int pos = -1;
	    if(this.countMove >= this.timeMove)
	    {
	    	this.countMove = 0;
	    	// Verifica se as teclas de seta foram pressionadas para mover o jogador
		    if ( this.moveDirection == 3 && posY < gridLength - 1&&!posOcupadas[3])
		    {

	            this.addToPosY(1); // Move para cima
		    }
		    else if(this.moveDirection == 3 )
		    {
		    	this.moveDirection = 2;
		    	return pos;
		    }
		    if(this.moveDirection == 2 && posY > 0&& !posOcupadas[2])
		    {

	            this.addToPosY(-1); // Move para baixo
		        
		    }
		    else if(this.moveDirection == 2)
		    {
		    	this.moveDirection = 3;
		    	return pos;
		    }
		    
		    if (this.moveDirection==1 && posX < gridLength - 1&& !posOcupadas[1])
		    {
		    	this.addToPosX(1); // Move para a direita
		    } 
		    else if (this.moveDirection==1)
		    {
		    	this.moveDirection = 0;
		    	return pos;
		    }
		    if (this.moveDirection==0 && posX > 0&& !posOcupadas[0])
		    {
	            this.addToPosX(-1); // Move para a esquerda
		    }
		    else if (this.moveDirection==0)
		    {
		    	this.moveDirection = 1;
		    }
		    
			//this.texture = this.textures[moveDirection];
	    }
	    return pos;
	}
	// contador - 100
	 //c - x
	 public Boolean addToContador(float num)
		{
			this.contador += num;
			float porcentagem = (100* this.contador)/this.getTempoDeExplosao();
			if(porcentagem <= 5)
			{
				this.setTexture(this.sprite[1]);
			}
			else if(porcentagem > 10)
			{
				Texture x;
				Random random = new Random();
		        
		        // Gere um número aleatório entre 0 e 3 (inclusive)
		        int randomNumber = random.nextInt(4);   // Gera um número aleatório de 0 a 3
		        
		        // Aplica as probabilidades aos índices do array sprite[]
		        if (randomNumber == 0 || randomNumber == 1) {
		            // 50% de chance para o índice 1
		            x = sprite[1];
		        } else if (randomNumber == 2) {
		            // 25% de chance para o índice 2
		            x = sprite[2];
		        } else {
		            // 25% de chance para o índice 3
		            x = sprite[3];
		        }
		        this.setTexture(x);
			}
			if(this.contador >= this.getTempoDeExplosao())
			{
				this.contador = 0;
				this.setExploded(true);
				return true;
			}
			return false;
		}
	 public int getMoveDirection()
	 {
		 return this.moveDirection;
	 }
	 public float getCount()
	 {
		 return this.countMove;
	 }
	//public void DefaultBehavior()

	public ObjetoDoJogo acabaVida(){
		this.contador=this.getTempoDeExplosao();
		this.setExploded(true);
		return this;
	}

}
