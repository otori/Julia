package de.otori.mandelbrot;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.otori.engine.FraktalProgram;
import de.otori.engine.Point2F;

/**
 *  offers methods to compute whether a point is colored or black
 */
public class Mandelbrot extends FraktalProgram {
		
	public static final Mandelbrot MBFraktal = new Mandelbrot();
	
	/**
	 * The Maximum number of iterations determines the Quality of the computation:
	 * A high number results in longer computation time, a low in less quality
	 */
	public static int MAX_ITER = 50;
	
	private ComplexNumber startValue;
	
	public Mandelbrot()
	{
		startValue = new ComplexNumber(0, 0); 
		center = new Point2F(-.5, 0);
		zoom = 1.;
	}
	
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
	
	//This is not really an aproximation of sqrt, it just grows faster at the beginning (and we didnt make a better name :D)
	public static double sqrtFakeAprox(int iter) 
	{
		if(iter < (MAX_ITER / 2))
			return (4./3.) * (iter/(double)MAX_ITER);
		return (2./3.) * (iter/(double)MAX_ITER) + (1./3.);
	}

	@Override
	public Color calcPixel(Point2F point) {
		// TODO Auto-generated method stub
						
		int iter = Mandelbrot.MAX_ITER;
		
		iter = isInMandel(new ComplexNumber(point.x, point.y), startValue);			
									
		if(iter == Mandelbrot.MAX_ITER)		
			return Color.BLACK;
		else
		{					
			//Choose either the upper color selection for a psychedelic color experience,
			//Or the lower one for a calm one
								
			//double near = Math.sqrt(iter) / Math.sqrt((double)Mandelbrot.MAX_ITER);
			//pxCol = ColorFun.farbVerlauf(Color.getHSBColor(((iX / (float)winWidth) + 0.5f) % 1.f, 1.f, 1.f), Color.getHSBColor(iX / (float)winWidth, 1.f, 1.f), near);					
			//int colVal = (int)((Math.sqrt(iter) / Math.sqrt((double)Mandelbrot.MAX_ITER)) * 255);
			int colVal = (int)( Mandelbrot.sqrtFakeAprox(iter) * 255);					
			//int colVal = (int)((Math.pow(iter, .28) / Math.pow((double)Mandelbrot.MAX_ITER, .28)) * 255);
			return new Color(colVal, 255-colVal, 0);			
		}				
			
	}	

	long startOfpressingKey = 0 ;
	int lastKey = 0;
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub		
		super.keyPressed(e); // We will do the parent events, too.
		
		switch (e.getKeyCode()) {		
		case KeyEvent.VK_UP:
			startValue.setReal(startValue.getReal() + 0.01*(1/(zoom*1.8)));
			break;
		case KeyEvent.VK_DOWN:
			startValue.setReal(startValue.getReal() - 0.01*(1/(zoom*1.8)));
			break;
		case KeyEvent.VK_RIGHT:
			startValue.setImag(startValue.getImag() + 0.01*(1/(zoom*1.8)));
			break;
		case KeyEvent.VK_LEFT:
			startValue.setImag(startValue.getImag() - 0.01*(1/(zoom*1.8)));
			break;		
		case KeyEvent.VK_ESCAPE:
			startValue.setImag(0);
			startValue.setReal(0);
			zoom = 1.;
			center.x = -.5;
			center.y =  .0;	
		}		
	}
	
}
