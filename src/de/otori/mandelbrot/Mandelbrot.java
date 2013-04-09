package de.otori.mandelbrot;
public class Mandelbrot {

	/**
	 * @param args
	 */
	
	public static final int MAX_ITER = 40;
	
	public static int isInMandel(final ComplexNumber cn)
	{
		int iteration = 1;
		
		ComplexNumber mandelFolge = new ComplexNumber(cn);
		
		while(iteration < MAX_ITER)
		{
			mandelFolge.quad();
			mandelFolge.add(cn);
			
			iteration++;
			if(mandelFolge.absSqr() > 4.0)
				break;
		}
		
		return iteration;
	}
	
	public static ComplexNumber cnFromPixel(final int x, final int y, final int winWidth, final int winHeight)//, double zoom, double xZoom, double yZoom)
	{		
		return new ComplexNumber((x * 3.0 / (double)winWidth) - 2, (y * 2.0 / (double)winHeight) - 1);
	}
	
	public static double sqrtFakeAprox(int iter) //THis is not really an aproximation of sqrt, it just grows faster at the beginning (and we didnt make a better name :D)
	{
		if(iter < (MAX_ITER / 2))
			return (4./3.) * (iter/(double)MAX_ITER);
		return (2./3.) * (iter/(double)MAX_ITER) + (1./3.);
	}
}
