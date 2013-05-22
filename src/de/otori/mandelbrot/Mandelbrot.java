package de.otori.mandelbrot;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import de.otori.engine.FraktalProgram;
import de.otori.engine.Point2F;
import de.otori.misc.ColorFun;
import de.otori.misc.FasterSinus;

/**
 *  offers methods to compute whether a point is colored or black
 */
public class Mandelbrot extends FraktalProgram {
	
	private static enum ColorMode{RAINBOW, GREENTORED, PSYCHO};
	
	public static final Mandelbrot MBFraktal = new Mandelbrot();
	private ColorMode cMode;
	private Color[] colors;
	
	private static Color rainbow(double v) {
		  double angle = 2.0 * Math.PI * v;
		  Color rgb = new Color((int)((0.5 - 0.5 * Math.cos(2.0 * angle)) * 255), (int)((0.5 - 0.5 * Math.cos(3.0 * angle)) * 255), (int)((0.5 - 0.5 * Math.cos(4.0 * angle)) * 255));//vec3(0.5) - vec3(0.5) * cos(vec3(2.0, 3.0, 5.0) * vec3(angle));
		  return rgb;
	}
	
	private void initColors()
	{		
		colors = new Color[MAX_ITER + 1];
		for(int i = 0; i < MAX_ITER; ++i)
		{
			switch (cMode)
			{
			case RAINBOW: 	  colors[i] = rainbow(i / (double)MAX_ITER);
							  break;
			case GREENTORED:  int colVal = (int)((Math.sqrt(i) / Math.sqrt((double)MAX_ITER)) * 255);
							  colors[i] = new Color(colVal, 255-colVal, 0);
							  break;
			case PSYCHO:	  break;
			}
		}
		colors[MAX_ITER] = Color.BLACK;
	}
	
	/**
	 * The Maximum number of iterations determines the Quality of the computation:
	 * A high number results in longer computation time, a low in less quality
	 */
	private int MAX_ITER = 50;
	
	private ComplexNumber startValue;
	
	public Mandelbrot()
	{
		cMode = ColorMode.RAINBOW;
		startValue = new ComplexNumber(0, 0); 
		center = new Point2F(-.5, 0);
		zoom = 1.;
		initColors();		
	}
	
	/**
	 * setter to change MAX_ITER 
	 * A high number results in longer computation time, a low in less quality
	 * @param iter Maximun number of iterations
	 */
	public void setIteration(int iter)
	{
		MAX_ITER = iter;
		initColors();
	}
	
	@Override
	public void preRendering() {
		super.preRendering();
		if(System.currentTimeMillis()%30000<15000)startValue.setReal(startValue.getReal() + (0.01*(1/(zoom*1.8))));
		else startValue.setReal(startValue.getReal() - (0.01*(1/(zoom*1.8))));
	}
	
	/**
	 * computes the behavior of an complex Number
	 * if the behavior is still unclear at MAX_ITER computation breaks
	 * 
	 * @param cn a complex Number
	 * @param startValue
	 * @return an int representing the numbers of iterations done
	 */
	public int isInMandel(final ComplexNumber cn, ComplexNumber startValue)
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
		
	@Override
	public Color calcPixel(Point2F point) {
		// TODO Auto-generated method stub
						
		int iter = MAX_ITER;
		
		iter = isInMandel(new ComplexNumber(point.x, point.y), startValue);			
							
		if(cMode != ColorMode.PSYCHO)
			return colors[iter];
		else
		{
			if(iter == MAX_ITER)
			{
				return Color.getHSBColor((float) (((point.x / 3) + 0.5f) % 1.f), 1.f, 1.f);
			}
			else
			{
				double near = Math.sqrt(iter) / Math.sqrt((double)MAX_ITER);
				return ColorFun.farbVerlauf(Color.getHSBColor((float) (((point.x / 3) + 0.5f) % 1.f), 1.f, 1.f), Color.getHSBColor((float) (point.x / 3), 1.f, 1.f), near);
			}
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
		case KeyEvent.VK_ADD:
		case KeyEvent.VK_PLUS: 
			int mIter = MAX_ITER + 10;
			setIteration(mIter);
			break;
		case KeyEvent.VK_SUBTRACT:
		case KeyEvent.VK_MINUS:
			mIter = MAX_ITER - 10;
			while(mIter < 1) mIter += 10;
			setIteration(mIter);
			break;
		case KeyEvent.VK_ESCAPE:
			startValue.setImag(0);
			startValue.setReal(0);
			zoom = 1.;
			center.x = -.5;
			center.y =  .0;	
			break;
		}		
	}
	
}
