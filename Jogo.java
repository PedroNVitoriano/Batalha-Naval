import java.util.Scanner;
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;

public class Jogo{
    private Jogador player1, player2;
    private Mapa mapaP1, mapaP2;
    private boolean mapasInicializados;
    private int numNavios;
    public static final String resetaCor = "\u001B[0m";
    public static final String amarelaLetra = "\u001B[33m";
    public static final String vermelhoLetra = "\u001B[31m";
    public static final String azulLetra = "\u001B[34m";
    public static final String verdeLetra = "\u001B[32m";
    
    Jogo(Jogador p1, Mapa mapa1, Jogador p2, Mapa mapa2, int navios){
        this.setPlayer1(p1);
        this.setMapaP1(mapa1);
        this.setPlayer2(p2);
        this.setMapaP2(mapa2);
        this.setNumNavios(navios);
    }

    //get e set do Player 1
    public Jogador getPlayer1() {
        return this.player1;
    }

    public void setPlayer1(Jogador player) {
        this.player1 = player;
    }

    //get e set do Player 2
    public Jogador getPlayer2() {
        return this.player2;
    }

    public void setPlayer2(Jogador player) {
        this.player2 = player;
    }
    
    //get e set do mapa1
    public Mapa getMapaP1() {
        return this.mapaP1;
    }

    public void setMapaP1(Mapa map) {
        this.mapaP1 = map;
    }

    //get e set do mapa2
    public Mapa getMapaP2() {
        return this.mapaP2;
    }

    public void setMapaP2(Mapa map) {
        this.mapaP2 = map;
    }

    //get e set do boolean dos mapas inicializados
    private boolean getMapasInicializados(){
        return this.mapasInicializados;
    }

    private void setMapasInicializados(boolean estado){
        this.mapasInicializados = estado;
    }

    //get e set do numero de navios
    private int getNumNavios() {
        return this.numNavios;
    }

    private void setNumNavios(int novoValor) {
        this.numNavios = novoValor;
    }

    public void abertura(){
        this.transicaoJogo("BATALHA NAVAL", 3);
    }

    public void inicializaPerfis(){
        String nome, comemora;
        Scanner ler = new Scanner(System.in);

        this.transicaoJogador("Passe para o Jogador 1");
        this.limpaTela();
        System.out.println("Qual o seu nome?");
        nome = ler.nextLine();

        System.out.println("Seja bem vindo " + nome + "!\nSe voce ganhar, o que voce quer dizer?");
        comemora = ler.nextLine();

        this.getPlayer1().setNome(nome);
        this.getPlayer1().setComemoracao(comemora);

        this.transicaoJogador("Passe para o Jogador 2");
        this.limpaTela();
        System.out.println("Qual o seu nome?");
        nome = ler.nextLine();

        System.out.println("Seja bem vindo " + nome + "!\nSe voce ganhar, o que voce quer dizer?");
        comemora = ler.nextLine();

        this.getPlayer2().setNome(nome);
        this.getPlayer2().setComemoracao(comemora);
    }

    public void inicializarMapas(){
        boolean initOk1, initOk2;

        this.transicaoJogo("Inicializando os mapas...", 3);
        initOk1 = this.inicializaMapaJogador(this.getPlayer1(), this.getMapaP1());
        initOk2 = this.inicializaMapaJogador(this.getPlayer2(), this.getMapaP2());

        this.setMapasInicializados(initOk1 && initOk2);

        if(this.getMapasInicializados()){
            System.out.println("Mapas inicializados, pronto para inicio de jogo..."); 
        }
        else{
            System.out.println("Falha na inicialização dos mapas..."); 
        }
    }

    private boolean inicializaMapaJogador(Jogador player, Mapa mapa){
        Scanner ler = new Scanner(System.in);
        int contNavio, linha, coluna, quantosFaltam, qualNavio;
        Navio navioAtual;
        boolean atingiuLimite;

        this.transicaoJogador("Hora de " + player.getNome() + " colocar seus " + this.getNumNavios() + " navios!");

        qualNavio = 0;
        quantosFaltam = this.getNumNavios();
        contNavio = 0;
        atingiuLimite = false;

        while(!atingiuLimite){
            this.atualizaTela(player, mapa);
            System.out.println();
            System.out.print("Insercao de Navio do Jogador: " + player.getNome());
            System.out.println();

            System.out.println("A posicao se refere ao meio do navio: ");

            if(qualNavio == 0){
                navioAtual = new Navio(0);
                System.out.println("X Ou X X " + amarelaLetra + "X" + resetaCor + " X X");
                System.out.println("X\n" + amarelaLetra + "X" + resetaCor + "\nX\nX");
            }
            else if(qualNavio <= 2){
                navioAtual = new Navio(1);
                System.out.println("X Ou X " + amarelaLetra + "X" + resetaCor + " X X");
                System.out.println(amarelaLetra + "X" + resetaCor + "\nX\nX");
            }
            else if(qualNavio <= 4){
                navioAtual = new Navio(2);
                System.out.println("X Ou X" + amarelaLetra + "X" + resetaCor + " X");
                System.out.println(amarelaLetra + "X" + resetaCor + "\nX");
            }
            else{
                navioAtual = new Navio(3);
                System.out.println(amarelaLetra + "X " + resetaCor + "Ou" + amarelaLetra + " X" + resetaCor + " X");
                System.out.println("X");
            }

            System.out.println();
            System.out.println("O navio que voce vai colocar agora eh um " + navioAtual.getNome() + " (" + navioAtual.getTamanho() + " posicoes)");

            System.out.println("Faltam " + quantosFaltam + " navios para serem colocados");
            System.out.print("Digite a coordenada da linha: ");
            linha = this.leituraValida(ler, 0, mapa.getLinha() - 1);
            System.out.print("Digite a coordenada da coluna: ");
            coluna = this.leituraValida(ler, 0, mapa.getLinha() - 1);

            //visualizaPos
            if(mapa.cabeNavio(linha, coluna, true, navioAtual) || mapa.cabeNavio(linha, coluna, false, navioAtual)){
                mapa.perguntaRotacao(ler, linha, coluna, navioAtual);
            }
            else{
                System.out.println("--Este barco nao cabe nessas coordenadas! Tente outra posicao!--");
                this.delayTela(2);
                quantosFaltam++;
                qualNavio--;
                contNavio--;
            }

            //mudaPos
            quantosFaltam--;
            qualNavio++;
            contNavio++;

            if(contNavio > (this.getNumNavios() - 1)){
                this.limpaTela();
                System.out.println("Voce terminou de posicionar seus barcos!");
                System.out.println();
                System.out.println("Seu mapa ficou assim: ");
                System.out.println();
                mapa.imprime();

                this.delayTela(5);
                atingiuLimite = true;
            }
        }

        return true;
    }

    private int leituraValida(Scanner scan, int lowLimit, int highLimit){
        int entrada;
        entrada = scan.nextInt();

        while((entrada < lowLimit) || (entrada > highLimit)){
            System.out.print("Numero Invalido... digite novamente: ");
            entrada = scan.nextInt();
        }

        return entrada;
    }

    public void iniciaBatalha(){
        boolean continuaBatalha;

        this.transicaoJogo("Inicializando a batalha...",3);
        continuaBatalha = true;

        while(continuaBatalha){
            this.transicaoJogador("Vez do jogador: " + this.getPlayer1().getNome());
            continuaBatalha = this.executarJogada(this.getPlayer1(), this.getMapaP1(), this.getPlayer2(), this.getMapaP2());

            if(continuaBatalha){
                this.transicaoJogador("Vez do jogador: " + this.getPlayer2().getNome());
                continuaBatalha = this.executarJogada(this.getPlayer2(), this.getMapaP2(), this.getPlayer1(), this.getMapaP1());
            }
            //ideia - contar a quantidade de rodadas
        }

        this.imprimeResultado();

        System.out.println("Batalha Finalizada...");
    }

    private boolean executarJogada(Jogador player, Mapa mapaPlayer, Jogador inimigo, Mapa mapaInimigo){
        boolean statusJogo, acertou;
        int linha, coluna;
        String message;
        Scanner ler = new Scanner(System.in);

        statusJogo = true;
        acertou = true;
        while(acertou && statusJogo){
            this.atualizaTela(player, mapaPlayer, inimigo, mapaInimigo);
            System.out.println();
            System.out.println(amarelaLetra + mapaPlayer.getCodigoMapa(3) + resetaCor + " - Seus Navios!");
            System.out.println(vermelhoLetra + mapaPlayer.getCodigoMapa(2) + resetaCor + " - Tiro Errado!");
            System.out.println(azulLetra + mapaPlayer.getCodigoMapa(1) + resetaCor + " - Tiro Acertado!");
            System.out.println();
            System.out.print("Digite a coordenada da linha:");
            linha = this.leituraValida(ler, 0, mapaInimigo.getLinha() - 1);
            System.out.print("Digite a coordenada da coluna:");
            coluna = this.leituraValida(ler, 0, mapaInimigo.getLinha() - 1);
            acertou = mapaInimigo.tiro(linha, coluna);
            if(acertou){
                player.incrementaAcertos();

                if(player.getAcertos() >= 25){
                    statusJogo = false;
                }
            }
            this.delayTela(2);
            this.atualizaTela(player, mapaPlayer, inimigo, mapaInimigo);
        }

        return statusJogo;
    }

    private void transicaoJogo(String mensagem, int segundosDelay){
        int cont;
        this.limpaTela();

        for(cont = 0; cont < 12; cont++){
            System.out.println();
        }
        System.out.println(mensagem);
        for(cont = 0; cont < 12; cont++){
            System.out.println();
        }

        this.delayTela(segundosDelay);
    }

    private void transicaoJogador(String mensagem){
        int cont, espaco;
        Scanner ler = new Scanner(System.in);
        espaco = 12;
        this.limpaTela();

        for(cont = 0; cont < espaco; cont++){
            System.out.println();
        }
        System.out.println(mensagem);
        for(cont = 0; cont < espaco; cont++){
            System.out.println();
        }
        System.out.println("Aperte enter para confirmar:");
        ler.nextLine();
    }

    private void delayTela(int segundos){
        try{
            Thread.sleep(segundos * 1000);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private void limpaTela(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void atualizaTela(Jogador player, Mapa mapaPlayer, Jogador inimigo, Mapa mapaInimigo){
        this.limpaTela();
        System.out.println("Status Adversario: " + inimigo.getNome());
        mapaInimigo.imprime(true);
        System.out.println();
        System.out.println("Status jogador: " + player.getNome());
        mapaPlayer.imprime();
    }

    public void atualizaTela(Jogador player, Mapa mapaPlayer){
        this.limpaTela();
        System.out.println();
        System.out.println("Status jogador: " + player.getNome());
        mapaPlayer.imprime();
    }

    public void imprimeResultado(){
        this.transicaoJogo("TEMOS UM VENCEDOR!!", 5);
        this.limpaTela();

        System.out.println("O resultado da Batalha:");
        System.out.println(azulLetra + "Status: " + resetaCor + this.getPlayer1().getNome() + " - " + verdeLetra + "Acertos: " + resetaCor + this.getPlayer1().getAcertos());
        this.getMapaP1().imprime();
        System.out.println();
        System.out.println(azulLetra + "Status: " + resetaCor + this.getPlayer2().getNome() + " - " + verdeLetra + "Acertos: " + resetaCor + this.getPlayer2().getAcertos());
        this.getMapaP2().imprime();
        System.out.print(verdeLetra + "\nVENCEDOR: " + resetaCor);
        if(this.getPlayer1().getAcertos() > this.getPlayer2().getAcertos()){
            System.out.println(this.getPlayer1().getNome());
            System.out.println(this.getPlayer1().getComemoracao());
        }
        else{
            System.out.println(this.getPlayer2().getNome());
            System.out.println(this.getPlayer2().getComemoracao());
        }
    }

    public static void main(String[] args){
        System.out.println("Batalha Naval");

        Jogo jogo1;
        Jogador player1, player2;
        Mapa mapa1, mapa2;

        player1 = new Jogador();
        player1.setNome("Fulano");
        player2 = new Jogador();
        player2.setNome("Beltrano");

        mapa1 = new Mapa();
        mapa2 = new Mapa();

        jogo1 = new Jogo(player1, mapa1, player2, mapa2, 8);

        jogo1.abertura();
        jogo1.inicializaPerfis();
        jogo1.inicializarMapas();
        jogo1.iniciaBatalha();
        jogo1.imprimeResultado();
    }
}

class Mapa{
    private char[][] matriz;
    private int numLinhas, numColunas;
    private char[] codigoMapa; //Ocean, Hit, Fail, Navy
    public static final String resetaCor = "\u001B[0m";
    public static final String amarelaLetra = "\u001B[33m";
    public static final String vermelhoLetra = "\u001B[31m";
    public static final String azulLetra = "\u001B[34m";

    Mapa(){
        this.setLinha(10);
        this.setColuna(10);
        matriz = new char[this.getLinha()][this.getColuna()];
        codigoMapa = new char[4];
        this.setCodigoMapa(0, 'O');
        this.setCodigoMapa(1, 'A');
        this.setCodigoMapa(2, 'E');
        this.setCodigoMapa(3, 'X');
        this.inicializar(this.getCodigoMapa(0));
    }

    //elemento da matriz
    public char getElemento(int i, int j){
        return this.matriz[i][j];
    }

    public void setElemento(int i, int j, char novoValor){
        this.matriz[i][j] = novoValor;
    }

    //linhas
    public int getLinha(){
        return this.numLinhas;
    }

    private void setLinha(int novoValor){
        this.numLinhas = novoValor;
    }

    //colunas
    public int getColuna(){
        return this.numColunas;
    }

    private void setColuna(int novoValor){
        this.numColunas = novoValor;
    }

    //codigo mapa
    public char getCodigoMapa(int pos) {
        return this.codigoMapa[pos];
    }

    private void setCodigoMapa(int pos, char novoValor) {
        this.codigoMapa[pos] = novoValor;
    }

    public void inicializar(char valor){
        int contI, contJ;

        for(contI = 0; contI < this.getLinha(); contI++){
            for(contJ = 0; contJ < this.getColuna(); contJ++){
                this.setElemento(contI, contJ, valor);
            }
        }
    }

    public void copiarPara(Mapa outro){
        int contI, contJ;
        for(contI = 0; contI < this.getLinha(); contI++){
            for(contJ = 0; contJ < this.getColuna(); contJ++){
                char valor;
                valor = this.getElemento(contI, contJ);
                outro.setElemento(contI, contJ, valor);
            }
        }
    }

    public void imprime(){
        this.imprime(false);
    }

    public void imprime(boolean adversario){
        int contLinhas, contColunas;
        char valor;
        System.out.println("X- 0 1 2 3 4 5 6 7 8 9");
        System.out.println("-");

        for(contLinhas = 0; contLinhas < this.getLinha(); contLinhas++){
            System.out.print(contLinhas + " ");
            for(contColunas = 0; contColunas < this.getColuna(); contColunas++){
                valor = this.getElemento(contLinhas, contColunas);
                if(adversario && (valor == this.getCodigoMapa(3))){
                    valor = this.getCodigoMapa(0);
                }

                if(valor == this.getCodigoMapa(1)){
                    System.out.print(" " + azulLetra + valor + resetaCor);
                }
                else if(valor == this.getCodigoMapa(2)){
                    System.out.print(" " + vermelhoLetra + valor + resetaCor);
                }
                else if(valor == this.getCodigoMapa(3)){
                    System.out.print(" " + amarelaLetra + valor + resetaCor);
                }
                else{
                    System.out.print(" " + valor);
                }
                
            }
            System.out.println();
        }
    }

    public boolean tiro(int linha, int coluna){
        boolean acertou;
        char valorPos;
        String message;
        acertou = true;
        valorPos = this.getElemento(linha, coluna);
        if(valorPos == this.getCodigoMapa(0)){
            message = "---- Errou! -------";
            this.setElemento(linha, coluna, this.getCodigoMapa(2));
            acertou = false;
        }
        else if(valorPos == this.getCodigoMapa(1)){
            message = "---- Posicao Repetida - Acerto Anterior -------";
        }
        else if(valorPos == this.getCodigoMapa(2)){
            message = "---- Posicao Repetida - Agua Anterior -------";
        }
        else{
            message = "--- Acertou um Navio! ---";
            this.setElemento(linha, coluna, this.getCodigoMapa(1));
        }

        System.out.println(message);
        return acertou;
    }

    public void perguntaRotacao(Scanner ler, int linha, int coluna, Navio qualNavio){
        int temDuasOpcoes;
        boolean estado;
        Mapa possibilidade = new Mapa();

        estado = false;

        temDuasOpcoes = 0;
        if(this.cabeNavio(linha, coluna, true, qualNavio)){
            estado = true;
            temDuasOpcoes++;
        }
        if(this.cabeNavio(linha, coluna, false, qualNavio)){
            estado = false;
            temDuasOpcoes++;
        }

        if(temDuasOpcoes == 2){
            int resp;

            this.limpaTela();

            System.out.println("Esta atualmente assim: ");
            this.imprime();

            System.out.println();
            System.out.println("Voce tem duas opcoes para esse barco: ");
            System.out.println("1.");
            possibilidade = this.posicionaNavio(linha, coluna, true, qualNavio);
            possibilidade.imprime();
            System.out.println();

            this.copiarPara(possibilidade);
            System.out.println("2.");
            possibilidade = this.posicionaNavio(linha, coluna, false, qualNavio);
            possibilidade.imprime();

            System.out.println();

            System.out.println("Qual voce vai escolher? 1 ou 2?");
            resp = this.leituraValida(ler, 1, 2);

            if(resp == 1){
                this.posicionaNavio(linha, coluna, true, qualNavio).copiarPara(this);;
            }
            else{
                this.posicionaNavio(linha, coluna, false, qualNavio).copiarPara(this);;
            }
        }
        else if(temDuasOpcoes == 1){
            this.posicionaNavio(linha, coluna, estado, qualNavio).copiarPara(this);;
        }
    }

    public Mapa posicionaNavio(int linha, int coluna, boolean naVertical, Navio qualNavio){
        int tamanhoBarco, oMenor, oMaior;
        Mapa novaPosNavio = new Mapa();
        this.copiarPara(novaPosNavio);

        tamanhoBarco = qualNavio.getTamanho();

        if(naVertical){
            oMenor = linha - this.oMenorLado(tamanhoBarco);
            oMaior = linha + this.oMaiorLado(tamanhoBarco);

            while(oMenor <= linha){
                novaPosNavio.setElemento(oMenor, coluna, novaPosNavio.getCodigoMapa(3));
                oMenor++;
            }
    
            while(oMaior > linha){
                novaPosNavio.setElemento(oMaior, coluna, novaPosNavio.getCodigoMapa(3));
                oMaior--;
            }
        }
        else{
            oMenor = coluna - this.oMenorLado(tamanhoBarco);
            oMaior = coluna + this.oMaiorLado(tamanhoBarco);

            while(oMenor <= coluna){
                novaPosNavio.setElemento(linha, oMenor, novaPosNavio.getCodigoMapa(3));
                oMenor++;
            }
    
            while(oMaior > coluna){
                novaPosNavio.setElemento(linha, oMaior, novaPosNavio.getCodigoMapa(3));
                oMaior--;
            }
        }

        return novaPosNavio;
    }

    public boolean cabeNavio(int linha, int coluna, boolean naVertical, Navio qualNavio){
        boolean podeSerInserido;
        int tamanhoBarco, oMenor, oMaior;

        tamanhoBarco = qualNavio.getTamanho();

        if(naVertical){
            oMenor = linha - this.oMenorLado(tamanhoBarco);
            oMaior = linha + this.oMaiorLado(tamanhoBarco);

            if((oMenor >= 0) && (oMaior < this.getLinha())){
                podeSerInserido = true;

                while(oMenor <= linha){
                    if(this.getElemento(oMenor, coluna) == this.getCodigoMapa(3)){
                        podeSerInserido = false;
                    }
                    oMenor++;
                }
        
                while(oMaior > linha){
                    if(this.getElemento(oMaior, coluna) == this.getCodigoMapa(3)){
                        podeSerInserido = false;
                    }
                    oMaior--;
                }
            }
            else{
                podeSerInserido = false;
            }
        }
        else{
            oMenor = coluna - this.oMenorLado(tamanhoBarco);
            oMaior = coluna + this.oMaiorLado(tamanhoBarco);

            if((oMenor >= 0) && (oMaior < this.getColuna())){
                podeSerInserido = true;
                while((oMenor <= coluna) && podeSerInserido){
                    if(this.getElemento(linha, oMenor) == this.getCodigoMapa(3)){
                        podeSerInserido = false;
                    }
                    oMenor++;
                }
        
                while((oMaior > coluna) && podeSerInserido){
                    if(this.getElemento(linha, oMaior) == this.getCodigoMapa(3)){
                        podeSerInserido = false;
                    }
                    oMaior--;
                }
            }
            else{
                podeSerInserido = false;
            }
        }

        return podeSerInserido;
    }

    private int oMenorLado(int size){
        int lado;
        if((size % 5) == 0){
            lado = 2;
        }
        else if(((size % 3) == 0) || ((size % 4) == 0)){
            lado = 1;
        }
        else{
            lado = 0;
        }

        return lado;
    }

    private int oMaiorLado(int size){
        int lado;
        if(((size % 5) == 0) || ((size % 4) == 0)){
            lado = 2;
        }
        else{
            lado = 1;
        }

        return lado;
    }

    private int leituraValida(Scanner scan, int lowLimit, int highLimit){
        int entrada;
        entrada = scan.nextInt();

        while((entrada < lowLimit) || (entrada > highLimit)){
            System.out.print("Numero Invalido... digite novamente: ");
            entrada = scan.nextInt();
        }

        return entrada;
    }

    private void limpaTela(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

class Navio{
    private int tamanho;
    private String nome;

    Navio(int tipo){
        String seuNome;
        int seuTamanho;

        if(tipo == 0){
            seuNome = "PORTA-AVIOES";
            seuTamanho = 5;
        }
        else if(tipo == 1){
            seuNome = "NAVIO-TANQUE";
            seuTamanho = 4;
        }
        else if(tipo == 2){
            seuNome = "CONTRATORPEDEIROS";
            seuTamanho = 3;
        }
        else{
            seuNome = "SUBMARINO";
            seuTamanho = 2;
        }

        this.setNome(seuNome);
        this.setTamanho(seuTamanho);
    }

    //get e set tamanho
    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int novoValor) {
        this.tamanho = novoValor;
    }

    //get e set do nome
    public String getNome() {
        return this.nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }
}

class Jogador{
    private String nome, comemoracao;
    private int acertos;

    Jogador(){
        this.zerarAcertos();
    }

    //get e set de nome
    public String getNome(){
        return this.nome;
    }

    public void setNome(String novoNome){
        this.nome = novoNome;
    }

    //get e set de comeracao
    public String getComemoracao() {
        return comemoracao;
    }

    public void setComemoracao(String novaString) {
        this.comemoracao = novaString;
    }

    public int getAcertos() {
        return this.acertos;
    }

    private void setAcertos(int novoValor) {
        this.acertos = novoValor;
    }

    public void incrementaAcertos(){
        this.setAcertos(this.getAcertos() + 1);
    }

    public void zerarAcertos(){
        this.setAcertos(0);
    }
}