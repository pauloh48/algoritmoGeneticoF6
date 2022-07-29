package algoritmoGeneticoF6;

import java.util.Random;

/**
 * Classe Algoritmo Genetico 
 * 
 * @author pauloh48
 *
 */
public class AlgoritmoGenetico {
	private int sizePopulacao = 100;
	private int sizePopulcaoEquilibrada = sizePopulacao;
	private String [] populacaoFilhos;
	private int flagSelecionaRoletaEquilibrada;
	private int contTotalIndividuo = 0;
	
	/**
	 *  Metodo que gera a população de cromossomos de acordo com o tamanho passado,
	 * ja calcula aptidão do cromossomo, e acumula aptidão para roleta;
	 * 
	 * @param cArray Cromossoma[] - cromossomos alocados e null;
	 * @param sizePopulacao int - tamanho da populção;
	 */
	
	public void geraPopulacao(Cromossoma[] cArray, int sizePopulacao) {
		float somaAptidao = 0;
		int i = 0;
		
		while(i < sizePopulacao) {
			cArray[i] = new Cromossoma();
			cArray[i].geraCromossoma();
			cArray[i].splitCromossomaXY();
			cArray[i].xyBase2ToBase10();
			cArray[i].multiplicaXYBase10();
			cArray[i].somaMuliComMinimos();
			cArray[i].F6();

			//acumula para roleta
			somaAptidao += cArray[i].getAptidao();
			cArray[i].setAcumulaAptidaoRoleta(somaAptidao);
			
			i++;
			/*
			 * ---System.out.println(i + "\n\tAptidao: \t" + cArray[i].getAptidao() 
			 * ----		+ "\n\tSoma Aptidao:\t"+ cArray[i].getAcumulaAptidaoRoleta()+"\n");
			 * --System.out.println("Poi: " + i + "  :  " + cArray[i].getCromossoma());
			 *
			 */
		}
	}
	
	
	/**
	 * Metodo que efetua a roleta, entre o intervalo maximo e minimo de aptidão ele gera
	 * uma aptidão aleatoria que será usada como base para selecionar um individuo, 
	 * tal escolha ocorre ao percorrer o array e comparar com a aptidão aleatoria;
	 * 
	 * @param cArray Cromossoma[] - cromossomos ja preenchidos;
	 * @param sizePopulacao int - tamanho da populção;
	 * 
	 * @return Cromossoma String - retorna cromossomo a ser cruzado; 
	 */
	public String roleta(Cromossoma[] cArray, int sizePopulacao) {
		float maxAptidAcum =  (float) cArray[sizePopulacao-1].getAcumulaAptidaoRoleta();//ultima posicao acumulda
		float minAptidAcum = (float) 0.0;
		int i = 0, indiceCromossomaSelecionado = 0;
		Random r = new Random();
		
		// gera aptidão a ser usada de forma aleatorio 
		double randomAptidao = minAptidAcum+(maxAptidAcum-minAptidAcum)*r.nextDouble();
		
		//varre para selecionar um cromossoma menor que o numero sorteado
		//pega primeiro indice, menor posição
		double aptidaoIndividuo= cArray[i].getAcumulaAptidaoRoleta();
		
		while(aptidaoIndividuo <= randomAptidao) {
			i++;
			indiceCromossomaSelecionado = i; //vai para a aptidao que combina
			
			aptidaoIndividuo = cArray[i].getAcumulaAptidaoRoleta();
		}
		
		return cArray[indiceCromossomaSelecionado].getCromossoma();	
	}
	
	
	/**
	 * Metodo que gera 2 filhos a partir do genitor, aplica o ponto de corte se for
	 * menor que a taxa de crossover para cruzar genitor 1 com genitor 2, 
	 * por fim aplica mutação;
	 * 
	 * 
	 * @param genitor1 String - genitor a ser cruzado;
	 * @param genitor2 String - genitor a ser cruzado;
	 * 
	 * @return filho1e2 String - retorna filho 1 e 2 separado por '-';
	 */
	public String crosover(String genitor1, String genitor2) {
		float maxCrossover =  1, minCrossover = 0;
		float taxaCrossover = (float)0.65;
		//float randomCrossover = (float) (Math.random()*(maxCrossover-minCrossover+1)+minCrossover);
		Random r = new Random();

		float randomCrossover = minCrossover+(maxCrossover-minCrossover)*r.nextFloat();
		
		int maxPontoCorte = 43;		//intervalo pra 1 corte
		int minPontoCorte = 1;
		int pontoCorte;
		
		String filho1, filho2;
		
		//aplica cruzamento
		if(randomCrossover < taxaCrossover) {
			pontoCorte = (int) (Math.random()*(maxPontoCorte-minPontoCorte+1)+minPontoCorte);
			
			filho1 = genitor1.substring(0, pontoCorte) + genitor2.substring(pontoCorte,44);
			filho2 = genitor2.substring(0, pontoCorte) + genitor1.substring(pontoCorte,44);
			/*System.out.println(pontoCorte + "\t" + genitor1.substring(0, pontoCorte) + "\t" + genitor2.substring(pontoCorte,44));
			 *System.out.println(pontoCorte + "\t" + genitor2.substring(0, pontoCorte) + "\t" + genitor2.substring(pontoCorte,44));
			*/
		}
		else {
			filho1 = genitor1;
			filho2 = genitor2;
		}
		//mutacao
		filho1 = mutacao(filho1);
		filho2 = mutacao(filho2);
		
		return filho1 + "-" + filho2;
	}
	
	/**
	 * Metodo que insere mutação nos genes do cromossomo se randomMutação for menor que
	 * a taxa definida, assim percorre todo o cromossomo calculando um randomMutação
	 * para cada bit, se cumprir inverte o critério inverte o bit;
	 * 
	 * @param filho String - filho a ser mutado;
	 * 
	 * @return filho String - filho mutado;
	 */
	public String mutacao(String filho) {
		int i = 0;
		double maxMutacao = 1, minMutacao = 0;
		double randomMutacao;
		
		Random r = new Random();
		//int cont=0;
		
		//percorre o crmomossomo inserindo mutação
		while(i < 44) {
			randomMutacao = minMutacao + (maxMutacao-minMutacao) * r.nextDouble();
			if(randomMutacao <= 0.008) {
				if(filho.charAt(i) == '1') 
					filho = filho.substring(0, i) +'0' + filho.substring(i+1); 
				else
					filho = filho.substring(0, i) +'1' + filho.substring(i+1);
				//cont++;
			}
			i++;
		}
		//if(cont>0)
		//	System.out.println("Mutacoes inseridas: " + cont);
		return filho;
	}
	
	/**
	 * Metodo que gera a nova população, para isso utiliza o metodo roleta para selecionar
	 * o genitor 1 e 2, por fim aplica o crossover para gerar os filhos, cada filho gerado
	 * tem seu cromossomo alocado na população de filhos. Possui 2 casos, se a população de
	 * pais for impar então aplica roleta na quantidade de pais impara, caso contrario faz 
	 * o balanceamento que ocorre na segunda iteração do loop na mainAG.java;
	 * 
	 * @param cArray Cromossoma[] - população ja criada;
	 * @param sizePopulacao int - tamanho da população;
	 */
	public void novaPopulacao(Cromossoma[] cArray, int sizePopulacao) {
		int i = 0;
		String pai1, pai2;
		
		while(i < sizePopulacao) { //anda 2 pois cada reprodução gera 2 filhos
			if(flagSelecionaRoletaEquilibrada==0) {
				pai1 = roleta(cArray, sizePopulacao);
				pai2 = roleta(cArray, sizePopulacao);
			}else {
				pai1 = roleta(cArray, getSizePopulcaoEquilibrada());
				pai2 = roleta(cArray, getSizePopulcaoEquilibrada());
			}
			
			String[] filho = crosover(pai1, pai2).split("-");

			populacaoFilhos[i] = filho[0];
			i++;
			
			populacaoFilhos[i] = filho[1];
			i++;
		}
	}
	
	/**
	 * Metodo que aplica elitismo, substituindo a antiga população pela nova, exceto o pai
	 * com melhor F6, por fim calcula o F6 da nova população, se for impar o tamanho da
	 * população, entao é alocado mais um cromossomo, pois a população de filhos é 
	 * balanceada;
	 * 
	 * 
	 * @param cArray Cromossoma[] - população ja criada que será substituida;
	 * @param sizePopulacao int - tamanho da população;
	 */
	public void elitismo(Cromossoma[] cArray, int sizePopulacao) {
		int posicaoMelhorPai = posicaoMelhorPai(cArray, sizePopulacao);
		int i = 0;
		float somaAptidao = 0;
		// elitismo
		
		while(i < getSizePopulcaoEquilibrada()) {
			//System.out.println("P i: " + i + "  -  " + cArray[i].getCromossoma());
			if(i != posicaoMelhorPai) {
				//aloca no caso de impar
				if(cArray[i] == null)
					cArray[i] = new Cromossoma();
				
				cArray[i].setCromossoma(getPopulacaoFilhos()[i]);
				cArray[i].splitCromossomaXY();
				cArray[i].xyBase2ToBase10();
				cArray[i].multiplicaXYBase10();
				cArray[i].somaMuliComMinimos();
				cArray[i].F6();
				
				somaAptidao += cArray[i].getAptidao();
				cArray[i].setAcumulaAptidaoRoleta(somaAptidao);
				
			}else { //pega intervalo aptidao melhor pai
				somaAptidao += cArray[i].getAptidao();
				cArray[i].setAcumulaAptidaoRoleta(somaAptidao);
			}
			i++;
			contTotalIndividuo++;
		}
	}
	
	/**
	 * Metodo que pega o indice do melhor pai;
	 * 
	 * @param cArray Cromossoma[] - população ja criada;
	 * @param sizePopulacao int - tamanho da população;
	 * 
	 * @return posMelhorPai int - posição do melhor pai
	 */
	public int posicaoMelhorPai(Cromossoma[] cArray, int sizePopulacao) {
		int i= 0 , posMelhorPai = 0;
		
		while(i < sizePopulacao) {
			if(cArray[i].getAptidao() > cArray[posMelhorPai].getAptidao())
				posMelhorPai = i;
			i++;
		}
		return posMelhorPai; 
	}
	
	/**
	 * Equlibra a população caso o tamanho seja impar
	 */
	public void equilibraPopulacao() {
		if(sizePopulcaoEquilibrada%2 == 1)
			setSizePopulcaoEquilibrada(sizePopulcaoEquilibrada+1);
		else
			setSizePopulcaoEquilibrada(sizePopulcaoEquilibrada);
	}
	
	public int getSizePopulcaoEquilibrada() {
		return sizePopulcaoEquilibrada;
	}

	public void setSizePopulcaoEquilibrada(int sizePopulcaoEquilibrada) {
		this.sizePopulcaoEquilibrada = sizePopulcaoEquilibrada;
	}

	public void alocaFilhos() {
		populacaoFilhos = new String[getSizePopulcaoEquilibrada()];
	}
	
	public String[] getPopulacaoFilhos() {
		return populacaoFilhos;
	}

	public void setPopulacaoFilhos(String[] populacaoFilhos) {
		this.populacaoFilhos = populacaoFilhos;
	}
	
	public int getSizePopulacao() {
		return sizePopulacao;
	}

	public void setSizePopulacao(int sizePopulacao) {
		this.sizePopulacao = sizePopulacao;
	}
	
	public int getFlagSelecionaRoletaEquilibrada() {
		return flagSelecionaRoletaEquilibrada;
	}

	public void setFlagSelecionaRoletaEquilibrada(int flagSelecionaRoletaEquilibrada) {
		this.flagSelecionaRoletaEquilibrada = flagSelecionaRoletaEquilibrada;
	}

	public int getContTotalIndividuo() {
		return contTotalIndividuo;
	}

	public void setContTotalIndividuo(int contTotalIndividuo) {
		this.contTotalIndividuo = contTotalIndividuo;
	}
}
