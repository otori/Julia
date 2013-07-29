package de.otori.mandelbrot;

/**
 * 
 * a complex Number Z consists of Re(Z) and Imag(Z): Z = Re(Z)+Imag(Z)*i
 */
public class ComplexNumber {

	private double real, imag;

	/**
	 * Returns Re(Z)of complex number Z Returns: double
	 */
	public double getReal() {
		return real;
	}

	/**
	 * setter for Re(Z)
	 * 
	 * @param real
	 */
	public void setReal(double real) {
		this.real = real;
	}

	/**
	 * getter for Im(Z)
	 * 
	 * @return the Value of Im(Z)
	 */
	public double getImag() {
		return imag;
	}

	/**
	 * setter for Im(Z)
	 * 
	 * @param imag
	 */
	public void setImag(double imag) {
		this.imag = imag;
	}

	/**
	 * Constructor for complex Numbers
	 * 
	 * @param Re
	 *            (Z)
	 * @param Im
	 *            (Z)
	 */
	public ComplexNumber(double re, double im) {
		real = re;
		imag = im;
	}

	/**
	 * Constructor for complex Numbers
	 * 
	 * @param cn
	 *            needs an complex Number
	 */
	public ComplexNumber(ComplexNumber cn) {
		real = cn.getReal();
		imag = cn.getImag();
	}

	/**
	 * addition
	 * 
	 * @param cn
	 *            : Summand
	 */
	public void add(ComplexNumber cn) {
		real += cn.real;
		imag += cn.imag;
	}  
	
	/**
	 * add an double
	 * @param n
	 */
	public void add(double n) {
		real += n;
	}
	/**
	 * Subtraktion für complex Number
	 * 
	 * @param cn
	 */
	public void sub(ComplexNumber cn) {
		real -= cn.real;
		imag -= cn.imag;
	}
	public void sub(double a) {
		real = real-a; 
	}


	/**
	 * Division
	 */
	public void div(ComplexNumber Divisor){
		
		double tmp =(Divisor.real)*(Divisor.real)+(Divisor.imag*Divisor.imag);
		real = ((real * Divisor.real)+imag*Divisor.imag)/tmp;
		imag = (imag*Divisor.real- real* Divisor.imag)/tmp;}
	
	/**
	 * Multiplikation
	 */
	public void mul(ComplexNumber Faktor){
		final double tmpReal = real * Faktor.real - imag * Faktor.imag;
					imag = Faktor.real * imag + real * Faktor.imag;
					real = tmpReal;
	}
	public void mul(Double Faktor){
		real = real * Faktor;
		imag = imag * Faktor;
	}

	/**
	 * Z -> Z^n
	 */
	public void pot(double n){
		while(n> 1){
			this.mul(this);
			n--;
		}
		if (n==0){
			real=1;imag=0;
		}
	}
	
	/**
	 * Z-> Z²
	 */
	public void quad() {
		final double tmpReal = real * real - imag * imag;
		imag = 2 * real * imag;
		real = tmpReal;
	}

	/**
	 * compute the norm of the complex Number
	 * 
	 * @return absolute value (or modulus or magnitude)
	 */
	public double abs() {
		return Math.sqrt(real * real + imag * imag);
	}

	/**
	 * 
	 * @return the absolute value (or modulus or magnitude)
	 */
	public double absSqr() {
		return real * real + imag * imag;
	}

}
