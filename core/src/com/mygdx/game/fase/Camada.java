package com.mygdx.game.fase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Camada {
    private ObjetoDoJogo objetosCamadas[][];
    private float imageSize;
    public int gridSnap;
    private List<Float> counters;
    private List<List<ObjetoDoJogo>> listaCoordenadas;
    private float tempoDaExplosao;
    private static Texture detailTextures[][] = {{new Texture("grassSpriteb.png"), new Texture("grassSprite.png")},
            {new Texture("redFlower.png"), new Texture("yellowFlower.png"), new Texture("whiteFlower.png")}};
    private static Texture chaoTexture = new Texture("grass.png");
    private static Texture sandTexture = new Texture("chao.png");
    public Camada(int xSize) 
    {
    	this.tempoDaExplosao = Explosao.getTempoDaExplosao();
        this.listaCoordenadas = new ArrayList<>();
        this.counters = new ArrayList<>();
        this.imageSize = (int) (Gdx.graphics.getWidth() / xSize);
        this.gridSnap = (int) (Gdx.graphics.getWidth() / this.imageSize);
        this.objetosCamadas = new ObjetoDoJogo[this.gridSnap][this.gridSnap];
    }

    /**
     * Gera texturas de chão em todas as posições do grid.
     */
    public void generateGroundTextures() 
    {
        for (int i = 0; i < this.getGridSnap(); i++) 
        {
            for (int j = 0; j < this.getGridSnap(); j++) 
            {
                // Cria um objeto do jogo com textura de chão e define sua posição no grid.
                ObjetoDoJogo objeto = new ObjetoDoJogo();
                objeto.setTexture(Camada.chaoTexture);
                objeto.setPosX(i);
                objeto.setPosY(j);

                // Define o objeto do jogo no grid na posição atual.
                this.setObjetoDoJogo(objeto, i, j);
            }
        }
    }
    public ArrayList<Inimigo> instanciarInimigosAleatoriamente(int n) 
    {
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i < this.getGridSnap() - 1 && inimigos.size() < n; i++) 
        {
            int posX = random.nextInt(this.getGridSnap() - 1) + 1; // Gera posição X aleatória
            int posY = i; // Posição Y é a linha atual (não na primeira ou última linha)
            int direcao = random.nextInt(4); // Gera uma direção aleatória (0 a 3)

            Inimigo inimigo = new Inimigo(posX, posY, direcao); // Crie um novo inimigo com direção aleatória
            inimigos.add(inimigo); // Adicione o inimigo à lista
        }

        return inimigos;
    }

    /**
     * Gera texturas detalhadas em posições específicas do grid.
     */
    public void generateDetailTextures() 
    {
        for (int i = 0; i < this.getGridSnap(); i += 2) 
        {
            for (int j = 0; j < this.getGridSnap(); j += 2) 
            {
                // Seleciona aleatoriamente uma textura detalhada da matriz de texturas detalhadas.
                Random random = new Random();
                int indexi = random.nextInt(Camada.detailTextures.length);
                int indexii = random.nextInt(Camada.detailTextures[indexi].length);

                // Cria um objeto do jogo com a textura detalhada selecionada e define sua posição no grid.
                ObjetoDoJogo objeto = new ObjetoDoJogo();
                objeto.setTexture(Camada.detailTextures[indexi][indexii]);
                objeto.setPosX(i);
                objeto.setPosY(j);

                // Define o objeto do jogo no grid na posição atual.
                this.setObjetoDoJogo(objeto, i, j);
            }
        }
    }

    /**
     * Gera texturas de areia em posições específicas do grid.
     */
    public void generateSandTextures() 
    {
        for (int i = 1; i < this.getGridSnap(); i += 2) 
        {
            for (int j = 1; j < this.getGridSnap(); j += 2) 
            {
                // Cria um objeto do jogo com textura de areia e define sua posição no grid.
                ObjetoDoJogo objeto = new ObjetoDoJogo();
                objeto.setTexture(Camada.sandTexture);
                objeto.setPosX(i);
                objeto.setPosY(j);

                // Define o objeto do jogo no grid na posição atual.
                this.setObjetoDoJogo(objeto, i, j);
            }
        }
    }

    /**
     * Gera o Layout de blocos baseado em um arquivo.txt
     */
    public void setBlockLayout(int layout[][])
    {
        for(int i = 0; i < this.getGridSnap(); i+=1)
        {
            for(int j = 0; j < this.getGridSnap(); j+=1)
            {
                // Cria um objeto do jogo com textura de telha quebrável e define sua posição no grid.
                if(layout[i][j] > 0) {
                    ObjetoDoJogo objeto = new Parede(i, j, layout[i][j]);
                    // Define o objeto do jogo no grid na posição atual.
                    this.setObjetoDoJogo(objeto, i, j);
                }
            }
        }
    }

    /**
     * Verifica se as posições adjacentes ao jogador estão ocupadas por objetos no mapa.
     * 
     * @param obj Objeto cujas posições adjacentes estão sendo verificadas.
     * @return Um array de Boolean indicando se as posições adjacentes estão ocupadas (true) ou não (false).
     */
    public Boolean[] posAdjOcupadas(ObjetoDoJogo obj) 
    {
        // Obtém as posições adjacentes do jogador
        int[][] posAdj = obj.getAdjacentPositions(this.getGridSnap());
        
        // Array para armazenar se as posições adjacentes estão ocupadas ou não
        Boolean[] posOcupadas = new Boolean[4];

        // Verifica as posições adjacentes ocupadas
        for (int h = 0; h < posAdj.length; h++) 
        {
            // Verifica se a posição adjacente não é nula e se há um objeto na posição
            if (posAdj[h] != null && this.getObjetoDoJogo(posAdj[h][0], posAdj[h][1]) != null)
            {
                posOcupadas[h] = true; // Posição ocupada
            } else 
            {
                posOcupadas[h] = false; // Posição não ocupada
            }
        }
        
        // Retorna o array indicando se as posições adjacentes estão ocupadas ou não
        return posOcupadas;
    }
    public Boolean playerOnPosAdj(Inimigo inimigo)
    {
    	// Obtém as posições adjacentes do inimigo
        int[][] posAdj = inimigo.getAdjacentPositions(this.getGridSnap());
        
        // Array para armazenar se as posições adjacentes estão ocupadas ou não
        Boolean playerOnAdj = false;

        // Verifica as posições adjacentes ocupadas
        for (int h = 0; h < posAdj.length; h++) 
        {
            // Verifica se a posição adjacente não é nula e se há um player na posição
            if (posAdj[h] != null && this.getObjetoDoJogo(posAdj[h][0], posAdj[h][1]) instanceof Player)
            {
            	playerOnAdj = true; // Posição ocupada
            	break;
            }
        }
        
        return playerOnAdj;
    }

    /**
     * Gerencia colisões entre objetos no jogo, especialmente quando uma bomba colide com outro objeto.
     * Atualiza as posições dos objetos conforme necessário.
     * 
     * @param pos       Índice da posição de colisão em posAdj[][].
     * @param posAdj    Matriz de posições adjacentes à posição da colisão.
     * @param players   Array de jogadores no jogo.
     */
    public void manejaColisao(int pos, int posAdj[][], Player players[], Player current_player)
    {
        // Verifica se a posição de colisão não é inválida e se há um objeto na posição
        if (pos != -1 && this.getObjetoDoJogo(posAdj[pos][0], posAdj[pos][1]) != null) 
        {
            ObjetoDoJogo objetoDoJogo = this.getObjetoDoJogo(posAdj[pos][0], posAdj[pos][1]);

            // Verifica se o objeto na posição é uma bomba
            if (objetoDoJogo instanceof Bomba)
            {
                // Calcula quantas posicoes o player pode chutar bomba
                int distLimit = current_player.getKickPower();

                // Remove a bomba da posição de colisão
                this.setObjetoDoJogo(null, posAdj[pos][0], posAdj[pos][1]);

                // Calcula a nova posição da bomba após a colisão, considerando as direções
                int newPosX = posAdj[pos][0];
                int newPosY = posAdj[pos][1];
                switch (pos) {
                    case 0: // Posição à esquerda
                        // Move a bomba para a esquerda até encontrar um obstáculo ou atingir o limite de posições
                        int i = 0;
                        while (i < distLimit && this.getObjetoDoJogo(newPosX - 1, newPosY) == null)
                        {
                            newPosX -= 1;
                            int newPos[] = this.limitarPosicaoNoGrid(newPosX, newPosY);
                            newPosX = newPos[0];
                            i++;
                        }
                        break;
                    case 1: // Posição à direita
                        // Move a bomba para a direita até encontrar um obstáculo ou atingir o limite de posições
                        i = 0;
                        while (i < distLimit && this.getObjetoDoJogo(newPosX + 1, newPosY) == null)
                        {
                            newPosX += 1;
                            int newPos[] = this.limitarPosicaoNoGrid(newPosX, newPosY);
                            newPosX = newPos[0];
                            i++;
                        }
                        break;
                    case 2: // Posição abaixo
                        // Move a bomba para baixo até encontrar um obstáculo ou atingir o limite de posições
                        i = 0;
                        while (i < distLimit && this.getObjetoDoJogo(newPosX, newPosY - 1) == null)
                        {
                            newPosY -= 1;
                            int newPos[] = this.limitarPosicaoNoGrid(newPosX, newPosY);
                            newPosY = newPos[1];
                            i++;
                        }
                        break;
                    case 3: // Posição acima
                        // Move a bomba para cima até encontrar um obstáculo ou atingir o limite de posições
                        i = 0;
                        while (i < distLimit && this.getObjetoDoJogo(newPosX, newPosY + 1) == null)
                        {
                            newPosY += 1;
                            int newPos[] = this.limitarPosicaoNoGrid(newPosX, newPosY);
                            newPosY = newPos[1];
                            i++;
                        }
                        break;
                    default:
                        break;
                }

                // Atualiza as posições das bombas dos jogadores, se necessário
                for (int j = 0; j < players.length; j++) 
                {
                    if (players[j].bombas != null) 
                    {
                        for (int i = 0; i < players[j].bombas.length; i++) 
                        {
                            if (players[j].bombas[i].getPosX() == objetoDoJogo.getPosX() && players[j].bombas[i].getPosY() == objetoDoJogo.getPosY()) 
                            {
                                players[j].bombas[i].setPosX(newPosX);
                                players[j].bombas[i].setPosY(newPosY);
                            }
                        }
                    }
                }

                // Atualiza a posição da bomba no mapa
                objetoDoJogo.setPosX(newPosX);
                objetoDoJogo.setPosY(newPosY);
                this.setObjetoDoJogo(objetoDoJogo, newPosX, newPosY);
            }
            //Checa se o Player colediu com um item
            if (objetoDoJogo instanceof Item){
                current_player.recebeItem(((Item)objetoDoJogo).getTipo());
                this.setObjetoDoJogo(null, posAdj[pos][0], posAdj[pos][1]);
            }

            else if(objetoDoJogo instanceof Parede &&
                    current_player.getJumping()) {

                int[][] adjPosObjeto = objetoDoJogo.getAdjacentPositions(this.getGridSnap());
                int[] newPos = adjPosObjeto[pos];
                if(newPos != null && this.objetosCamadas[newPos[0]][newPos[1]] == null){
                    int[] oldPos = current_player.getCurrentPos();
                    current_player.setPosX(newPos[0]);
                    current_player.setPosY(newPos[1]);
                    current_player.setDirection(pos);
                    current_player.transform("Passaro", (float)0.3);
                    this.setObjetoDoJogo(current_player, newPos[0], newPos[1]);
                    this.setObjetoDoJogo(null, oldPos[0], oldPos[1]);
                }
            }

        }
    }

    public int[] limitarPosicaoNoGrid(int newPosX,int newPosY)
    {
    	if(newPosX < 0)
    	{
    		newPosX = 0;
    	}
    	else if(newPosX > this.getGridSnap()-1)
    	{
    		newPosX = this.getGridSnap()-1;
    	}
    	else if(newPosY < 0)
    	{
    		newPosY = 0;
    	}
    	else if(newPosY > this.getGridSnap()-1)
    	{
    		newPosY = this.getGridSnap()-1;
    	}
    	int[] arr ={newPosX, newPosY};
    	return arr;
    }
    /**
     * Verifica as bombas na camada do jogador atual, processa suas explosões e
     * aplica danos aos jogadores ou cria explosões no mapa conforme necessário.
     * 
     * @param currentPlayer Jogador atual cujas bombas estão sendo verificadas.
     * @param delta         O tempo decorrido desde o último frame.
     */
    @SuppressWarnings("unused")
	public void verificaBombasNaCamada(Player currentPlayer,ArrayList<Inimigo> inimigos, float delta) {
        // Obtém a lista de bombas do jogador atual
		Bomba bombas[] = currentPlayer.bombas;
            
        if (bombas != null) 
        {
            // Itera sobre cada bomba
            for (int h = 0; h < bombas.length; h++) 
            {
                // Obtém as coordenadas das explosões causadas pela bomba
                currentPlayer.updateBombaTime(delta, this.getGridSnap(), bombas[h]);
                this.InstanciaExplosao(bombas[h]);
            }
        }
        if(inimigos!=null)
        {
        	for (Inimigo i: inimigos) 
            {
        		if(i!= null)
        			this.InstanciaExplosao(i);
            }
        }
    }
    public void InstanciaExplosao(Bomba bombh)
    {
    	// Obtém a posição da bomba no mapa
        int[] pos = bombh.getPosicao();
        
        // Obtém as coordenadas das explosões causadas pela bomba
        List<int[]> localExplosao = null;
        if(bombh.getExploded())
        {
        	if(bombh instanceof Inimigo && ((Inimigo) bombh).getExpl() == true )
        	{
        		return;
        	}
        	else if(bombh instanceof Inimigo)
        	{
        		((Inimigo) bombh).setExpl(true);
        	}
        	localExplosao = bombh.obterIndicesDaExplosao(this.getGridSnap());
        }
        //Verifica se bomba explodiu
        if (localExplosao != null) 
        {
            // Processa explosões
            for (int j = 0; j < localExplosao.size(); j++) 
            {
                int[] explosionCoords = localExplosao.get(j);
                int x = explosionCoords[0];
                int y = explosionCoords[1];
                if (x == -1 || y == -1) 
                {
                    // Ignora coordenadas inválidas
                    continue;
                }

                ObjetoDoJogo layer1Objeto = this.getObjetoDoJogo(x, y);
                if (layer1Objeto != null && layer1Objeto instanceof Explodivel)
                {
                    // Aplica dano ao objeto atingido
                    ObjetoDoJogo resultado = ((Explodivel) layer1Objeto).recebeExplosao(bombh.getDamage());
                    if(resultado != layer1Objeto){
                        this.setObjetoDoJogo(resultado, x, y);
                    }
                    //PULA AS PROXIMAS EXPLOSOES PARA BOMBAS QUE AS EXPLOSOES SAO CONECTADAS
                    if(bombh.getPiercieng() == false){
                        j = (((int) j / bombh.getTamanhoExplosao()) + 1) * bombh.getTamanhoExplosao();
                        j--;
                    }

                } 
                else 
                {
                    // Cria uma explosão no mapa se não houver objeto explodivel
                    ObjetoDoJogo objEx = new Explosao(x, y);
                    this.setObjetoDoJogo(objEx, x, y);
                }
            }
            // Remove a bomba após a explosão
            this.setObjetoDoJogo(null, pos[0], pos[1]);
            bombh = null;
        }
        else 
        {
            // Atualiza a posição da bomba no mapa
            this.setObjetoDoJogo(bombh, pos[0], pos[1]);
        }
    }

    /**
     * Gerencia a lógica de explosões no jogo. Monitora e atualiza as explosões no mapa
     * baseado no tempo decorrido desde a última atualização.
     * 
     * @param delta O tempo decorrido desde o último frame.
     */
    public void explosaoManager(float delta) 
    {
        // Lista para armazenar objetos de explosão
        List<ObjetoDoJogo> lista = new ArrayList<>();
        
        // Itera sobre todas as posições no grid do jogo
        for (int x = 0; x < this.gridSnap; x++) 
        {
            for (int y = 0; y < this.gridSnap; y++) 
            {
                boolean inListCoord = false;
                
                // Verifica se a coordenada está na lista de coordenadas de explosões
                for (int i = 0; i < this.listaCoordenadas.size(); i++) 
                {
                    for (int j = 0; j < this.listaCoordenadas.get(i).size(); j++) 
                    {
                        // Verifica se a lista de coordenadas não está vazia e dentro dos limites
                        if (!this.listaCoordenadas.isEmpty() && i < this.listaCoordenadas.size()
                                && j < this.listaCoordenadas.get(i).size()) 
                        {
                            if (this.listaCoordenadas.get(i).get(j).getPosX() == x && this.listaCoordenadas.get(i).get(j).getPosY() == y) 
                            {
                                inListCoord = true;
                                break;
                            }
                        }
                    }
                    if (inListCoord) 
                    {
                        break;
                    }
                }
                
                // Verifica se há uma explosão na posição atual
                if (objetosCamadas[x][y] != null && objetosCamadas[x][y].geTexture() == Explosao.getExplosaTexture() && !inListCoord) 
                {
                    lista.add(objetosCamadas[x][y]);
                }
            }
        }
        
        // Adiciona a lista de explosões à lista de coordenadas e inicializa o contador
        if (!lista.isEmpty()) 
        {
            this.listaCoordenadas.add(lista);
            this.counters.add(0.0f);
        }
        
        // Atualiza o tempo das explosões e remove as explosões concluídas
        if (!this.counters.isEmpty() && this.listaCoordenadas.size() > 0) 
        {
            for (int i = 0; i < this.listaCoordenadas.size(); i++) 
            {
                float time = this.counters.get(i) + delta;
                if (time >= this.tempoDaExplosao) 
                {
                    // Remove explosões do mapa após o término do tempo de explosão
                    for (int j = 0; j < this.listaCoordenadas.get(i).size(); j++) 
                    {
                        int x = this.listaCoordenadas.get(i).get(j).getPosX();
                        int y = this.listaCoordenadas.get(i).get(j).getPosY();
                        // Verifica se as coordenadas estão dentro dos limites antes de acessar os elementos
                        if (x >= 0 && x < objetosCamadas.length && y >= 0 && y < objetosCamadas[0].length) 
                        {
                            this.objetosCamadas[x][y] = null;
                        }
                    }
                    // Remove a explosão da lista e ajusta o índice após a remoção do elemento
                    this.counters.remove(i);
                    this.listaCoordenadas.remove(i);
                    i--;
                } else 
                {
                    // Atualiza o tempo restante da explosão
                    this.counters.set(i, time);
                }
            }
        }
    }


    public float getImageSize() 
    {
        return this.imageSize;
    }

    public ObjetoDoJogo getObjetoDoJogo(int posX, int posY) 
    {
        if (posX >= 0 && posX < objetosCamadas.length && posY >= 0 && posY < objetosCamadas[0].length) 
        {
            return objetosCamadas[posX][posY];
        }
        return null;
    }

    public void setObjetoDoJogo(ObjetoDoJogo objeto, int posX, int posY) 
    {
        if (posX >= 0 && posX < objetosCamadas.length && posY >= 0 && posY < objetosCamadas[0].length) 
        {
            objetosCamadas[posX][posY] = objeto;
        }
    }

    public int getGridSnap() 
    {
        return this.gridSnap;
    }
}

