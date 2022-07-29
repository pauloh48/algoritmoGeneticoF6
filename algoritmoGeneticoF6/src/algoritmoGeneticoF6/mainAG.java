package algoritmoGeneticoF6;

public class mainAG {

	public static void main(String[] args) {
		int iGeracao = 0;
		int maxGeracao = 40;
		float paradaAptidao = (float) 0.0;
		int posMelhorPai = 0;
		
		AlgoritmoGenetico AG = new AlgoritmoGenetico(); 
		Cromossoma[] cArray;
		
		AG.equilibraPopulacao();//equlibra populcao filhos se 1 pai, entao 2 filhos
		
		cArray = new Cromossoma[AG.getSizePopulcaoEquilibrada()];//atualiza para ficar par caso seja um unico pai

		AG.geraPopulacao(cArray, AG.getSizePopulacao()); // cria a qtd de pai passado.getSizePopulacao());
		AG.alocaFilhos();
		
		/*
		 *  A flag define o que será selecionado na roleta
		 *  Se sizePopulacao == impar, entao 0 (para pai) 
		 *  como houve correção, na segunda iteração em diante a flag usa a
		 *  quantidade par de filhos, assim no roleta há um equilibrio 
		 *  e seleciona a populacaoEquilibrada
		 */
		AG.setFlagSelecionaRoletaEquilibrada(0);
		while((paradaAptidao < 1) || (iGeracao < maxGeracao)) {
			//correcao
			if(iGeracao!=0 && AG.getSizePopulacao()%2==1)
				AG.setFlagSelecionaRoletaEquilibrada(1);
				
			AG.novaPopulacao(cArray, AG.getSizePopulacao());			
			AG.elitismo(cArray, AG.getSizePopulacao());
			
			iGeracao++;
			paradaAptidao = (float) cArray[AG.posicaoMelhorPai(cArray,AG.getSizePopulacao())].getAptidao();
			posMelhorPai = AG.posicaoMelhorPai(cArray,AG.getSizePopulacao());
			
			System.out.println("Geracao: " + iGeracao + " : " + cArray[posMelhorPai].getCromossoma() 
				+ " F6: " + cArray[posMelhorPai].getAptidao()
				+ "\n\t\tx:" + cArray[posMelhorPai].getxBase2() + " y:" + cArray[posMelhorPai].getyBase2() 
				+ "\n\t\tx10: " + cArray[posMelhorPai].getxBase10() + " \t\ty10: " + cArray[posMelhorPai].getyBase10()
				+ "\n\t\txMul: " + cArray[posMelhorPai].getxBase10Mult() + " yMul: " + cArray[posMelhorPai].getyBase10Mult()
				+ "\n\t\txMin: " + cArray[posMelhorPai].getxBase10Min() + " yMin: " + cArray[posMelhorPai].getyBase10Min()
				);
			//if(paradaAptidao < 1)
			//	System.out.println("\n");
		}
		
		System.out.println("Total populacao: " + AG.getContTotalIndividuo());
		/*
		Cromossoma c = new Cromossoma();
		c.splitCromossomaXY();
		c.xyBase2ToBase10();
		c.multiplicaXYBase10();
		c.somaMuliComMinimos();
		c.F6();
		
		System.out.println("Geracao: " + iGeracao + " : " + cArray[posMelhorPai].getCromossoma() 
				+ " F6: " + cArray[posMelhorPai].getAptidao()
				+ "\n\t\tx:" + cArray[posMelhorPai].getxBase2() + " y:" + cArray[posMelhorPai].getyBase2() 
				+ "\n\t\tx10: " + cArray[posMelhorPai].getxBase10() + " \t\ty10: " + cArray[posMelhorPai].getyBase10()
				+ "\n\t\txMul: " + cArray[posMelhorPai].getxBase10Mult() + " yMul: " + cArray[posMelhorPai].getyBase10Mult()
				+ "\n\t\txMin: " + cArray[posMelhorPai].getxBase10Min() + " yMin: " + cArray[posMelhorPai].getyBase10Min()
				);

		*/
		
		/*
		01111111111111111111000111111111111111111111 F6: 1.0
		x:0111111111111111111100 y:0111111111111111111111
		x10: 2097148 		y10: 2097151
		xMul: 99.99983215332031 yMul: 99.9999771118164
		xMin: -1.678466796875E-4 yMin: -2.288818359375E-5
		*/
	}
}