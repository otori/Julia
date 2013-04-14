package Sinus;

import de.otori.mandelbrot.ComplexNumber; ;

public class Sinustest {
	
	public static int sinustest(final ComplexNumber position, ComplexNumber start) {
		int out = (int) (((2+(Math.sin(position.getReal()))/2*40) +
						(2+(Math.exp(position.getImag())/2*40)))-
						Math.sinh(start.getReal())*
						70*start.getImag())
						% 40;
		return out;
				
			
	}

}
