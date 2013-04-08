
public class ComplexNumber {

	private double real, imag;

	public double getReal() {
		return real;
	}

	public void setReal(double real) {
		this.real = real;
	}

	public double getImag() {
		return imag;
	}

	public void setImag(double imag) {
		this.imag = imag;
	}

	public ComplexNumber(double re, double im)
	{
		real = re;
		imag = im;		
	}
	
	public ComplexNumber(ComplexNumber cn)
	{
		real = cn.getReal();
		imag = cn.getImag();		
	}
	
	public void add(ComplexNumber cn)
	{
		real += cn.real;
		imag += cn.imag;
	}
	
	public void sub(ComplexNumber cn)
	{
		real -= cn.real;
		imag -= cn.imag;
	}
	
	public void quad()
	{
		final double tmpReal = real * real - imag * imag;
		imag = 2 * real * imag;
		real = tmpReal;
	}
	
	public double abs()
	{
		return real*real + imag*imag;
	}
	
}
