package algoritmoGeneticoF6;

/** 
 * Classe Cromossoma
 * 
 * @author pauloh48
 */
public class Cromossoma {
	private String cromossoma = "";
//	private String cromossoma = "00001010000110000000011000101010001110111011"; //professor
//	private String cromossoma = "01111111111111111111000111111111111111111111"; //f6==1
	private String xBase2, yBase2;
	private int xBase10, yBase10;
	private float xBase10Mult, yBase10Mult;
	private float xBase10Min, yBase10Min;
	private int i = 0, sizeCromossoma = 44;
	private float aptidao = 0,  acumulaAptidaoRoleta = 0;
	
	/** 
	 * Metodo que gera um cromossomo composto de 44 bits de forma aleatoria;
	 * 
	 * @return cromossoma String - cromossomo gerado.
	 */
	public String geraCromossoma() {
		while(i < sizeCromossoma) {
			cromossoma += (int) (Math.random() * 2); // 0 ate 1
			i++;
		}
		return cromossoma;
	}
	
	/** 
	 * Metodo que quebra o cromossoma em X e Y usando as funções substring;
	 */
	public void splitCromossomaXY() {
		xBase2 = cromossoma.substring(0, 22);
		yBase2 = cromossoma.substring(22, 44);
	}
	
	/** 
	 * Metodo que converte X e Y da base 2 para a 10;
	 */
	public void xyBase2ToBase10() {
		xBase10 = Integer.parseInt(xBase2, 2);
		yBase10 = Integer.parseInt(yBase2, 2);
	}
	
	/* 
	 * Metodo que multiplica os valores de X e Y na base 10 por (x | y)*200/(2²² - 1);
	 */
	public void multiplicaXYBase10() { //	(x | y)*200/(2²² - 1)
		float multiplicador =  (float) (200.0/(Math.pow(2, 22) - 1));
		xBase10Mult =    		xBase10*multiplicador;
		yBase10Mult =   		yBase10*multiplicador;
	}
	
	/* 
	 * Metodo que soma o valor multiplicado com os minimos de x e y; 
	 */
	public void somaMuliComMinimos() { // [-100, -100]
		xBase10Min = xBase10Mult+(-100);
		yBase10Min = yBase10Mult+(-100);
	}
	
	/* 
	 * Metodo que calcula o F6, que será a aptidão de cada cromossomo;
	 * F(x,y) = 0,5 ((sen(sqrt(x² + y²)² - 0,5)/(1,0 + 0,001*(x² + y¹))²;
	 */
	public void F6() { //formula f6
		aptidao = (float) (0.5 - (Math.pow((Math.sin(Math.sqrt(Math.pow(xBase10Min, 2) + Math.pow(yBase10Min, 2)))), 2)-0.5)/
				(1+0.001*Math.pow(Math.pow(xBase10Min,2) + (Math.pow(yBase10Min,2)),2)));
		//System.out.printf("%f\n", aptidao);
		//System.out.println(aptidao);
	}

	public String getCromossoma() {
		return cromossoma;
	}

	public void setCromossoma(String cromossoma) {
		this.cromossoma = cromossoma;
	}

	public double getAptidao() {
		return aptidao;
	}

	public void setAptidao(float aptidao) {
		this.aptidao = aptidao;
	}

	public double getAcumulaAptidaoRoleta() {
		return acumulaAptidaoRoleta;
	}

	public void setAcumulaAptidaoRoleta(float acumulaAptidaoRoleta) {
		this.acumulaAptidaoRoleta = acumulaAptidaoRoleta;
	}

	public String getxBase2() {
		return xBase2;
	}

	public void setxBase2(String xBase2) {
		this.xBase2 = xBase2;
	}

	public String getyBase2() {
		return yBase2;
	}

	public void setyBase2(String yBase2) {
		this.yBase2 = yBase2;
	}

	public int getxBase10() {
		return xBase10;
	}

	public void setxBase10(int xBase10) {
		this.xBase10 = xBase10;
	}

	public int getyBase10() {
		return yBase10;
	}

	public void setyBase10(int yBase10) {
		this.yBase10 = yBase10;
	}

	public double getxBase10Mult() {
		return xBase10Mult;
	}

	public void setxBase10Mult(float xBase10Mult) {
		this.xBase10Mult = xBase10Mult;
	}

	public double getyBase10Mult() {
		return yBase10Mult;
	}

	public void setyBase10Mult(float yBase10Mult) {
		this.yBase10Mult = yBase10Mult;
	}

	public double getxBase10Min() {
		return xBase10Min;
	}

	public void setxBase10Min(float xBase10Min) {
		this.xBase10Min = xBase10Min;
	}

	public double getyBase10Min() {
		return yBase10Min;
	}

	public void setyBase10Min(float yBase10Min) {
		this.yBase10Min = yBase10Min;
	}
}

