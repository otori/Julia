package de.otori.mandelbrot;

/**
 * 
 * a complex Number Z consists of Re(Z) and Imag(Z): 
 * Z = Re(Z)+Imag(Z)*i
 */
public class ComplexNumber {

	private double real, imag;

	/**
	 * Returns Re(Z)of complex number Z 
	 * Returns: double
	 */
	public double getReal() {
		return real;
	}

	/**
	 * setter for Re(Z) 
	 * @param real
	 */
	public void setReal(double real) {
		this.real = real;
	}

	/** 
	 * getter for Im(Z)
	 * @return the Value of Im(Z)
	 */
	public double getImag() {
		return imag;
	}

	/**
	 * setter for Im(Z)
	 * @param imag
	 */
	public void setImag(double imag) {
		this.imag = imag;
	}
/**
 * Constructor for complex Numbers
 * @param Re(Z)
 * @param Im(Z)
 */
	public ComplexNumber(double re, double im)
	{
		real = re;
		imag = im;		
	}
	
	/**
	 * Constructor for complex Numbers
	 * @param cn needs an complex Number
	 */
	public ComplexNumber(ComplexNumber cn)
	{
		real = cn.getReal();
		imag = cn.getImag();		
	}
	
	/**
	 * addition 
	 * @param cn : Summand
	 */
	public void add(ComplexNumber cn)
	{
		real += cn.real;
		imag += cn.imag;
	}
	
	/**
	 * Subtraktion für complex Number
	 * @param cn
	 */
	public void sub(ComplexNumber cn)
	{
		real -= cn.real;
		imag -= cn.imag;
	}
	
	/**
	 * Z-> Z² 
	 */
	public void quad()
	{
		final double tmpReal = real * real - imag * imag;
		imag = 2 * real * imag;
		real = tmpReal;
	}
	
	/**
	 * compute the norm of the complex Number
	 * @return absolute value (or modulus or magnitude)
	 */
	public double abs()
	{
		return Math.sqrt( real*real + imag*imag);
	}
	
	/**
	 * 
	 * @return the absolute value (or modulus or magnitude)
	 */
	public double absSqr()
	{
		return real*real + imag*imag;
	}
	
}
