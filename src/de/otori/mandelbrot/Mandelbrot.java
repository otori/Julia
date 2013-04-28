package de.otori.mandelbrot;
/**
 *  offers methods to compute whether a point is colored or black
 */
public class Mandelbrot {

/**
 * The Maximum number of iterations determines the Quality of the computation:
 * A high number results in longer computation time, a low in less quality
 */
	public static int MAX_ITER = 50;
	
	/**
	 * setter to change MAX_ITER 
	 * A high number results in longer computation time, a low in less quality
	 * @param iter Maximun number of iterations
	 */
	public static void setIteration(int iter)
	{
		MAX_ITER = iter;
	}
	
	/**
	 * computes the behavior of an complex Number
	 * if the behavior is still unclear at MAX_ITER computation breaks
	 * 
	 * @param cn a complex Number
	 * @param startValue
	 * @return an int representing the numbers of iterations done
	 */
	public static int isInMandel(final ComplexNumber cn, ComplexNumber startValue)
	{
		int iteration = 1;
		
		ComplexNumber mandelFolge = new ComplexNumber(startValue);
		
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
	
	
	/**
	 * assigns a pixel to a complex number(based on position in complex plane)
	 * @param x: x of pixel	
	 * @param y: y of pixel
	 * @param winWidth: width from the target pixel plane	
	 * @param winHeight: height of the target pixel plane
	 * @return
	 */
	public static ComplexNumber cnFromPixel(final int x, final int y, final int winWidth, final int winHeight)//, double zoom, double xCenter, double yCenter)
	{		
		return new ComplexNumber((x * 3.0 / (double)winWidth) - 2, (y * 2.0 / (double)winHeight) - 1);
	}
	
	public static ComplexNumber cnFromPixelZoom(final int x, final int y, final int winWidth, final int winHeight, final double zoom, final double xCenter, final double yCenter)
	{		
		return new ComplexNumber(xCenter + (x / (double)winWidth - 0.5)*(3/zoom) , yCenter + (y / (double)winHeight - 0.5)*(2/zoom));
	}
	
	//This is not really an aproximation of sqrt, it just grows faster at the beginning (and we didnt make a better name :D)
	public static double sqrtFakeAprox(int iter) 
	{
		if(iter < (MAX_ITER / 2))
			return (4./3.) * (iter/(double)MAX_ITER);
		return (2./3.) * (iter/(double)MAX_ITER) + (1./3.);
	}
}
