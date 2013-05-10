/**
 * 
 */
package de.a.fakefraktal;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.otori.engine.FraktalProgram;
import de.otori.engine.Point2F;
import de.otori.mandelbrot.Mandelbrot;

/**
 *
 */
public class FakeFraktal extends FraktalProgram {
	private long Time ;
	public static double Horizontal = 1;
	public static double  Vertical =1;



	public static final FakeFraktal FakeFraktal = new FakeFraktal();
	/**
	 * Constructor
	 */
	public FakeFraktal() {
		center = new Point2F(0, 0);
		zoom = 0.01;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.otori.engine.Renderable#calcPixel(de.otori.engine.Point2F)
	 */
	@Override
	public Color calcPixel(Point2F coordinate) {
		double x = coordinate.x;
		double y = coordinate.y;

		return   Color.getHSBColor((float)(((Time)*(Math.sqrt(x*x*Horizontal+y*y*Vertical))/100)%1), 1, 
				(1));
	}

	@Override
	public void preRendering() {
			super.preRendering();
			Time= (System.currentTimeMillis()/10)%10000;
			
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyPressed(e); // We will do the parent events, too.

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			Vertical = Vertical + 0.1 * (1 / (zoom * 1.8));
			break;
		case KeyEvent.VK_DOWN:
			Vertical = Vertical - 0.1 * (1 / (zoom * 1.8));
			break;
		case KeyEvent.VK_RIGHT:
			Horizontal = Horizontal + 0.1 * (1 / (zoom * 1.8));
			break;
		case KeyEvent.VK_LEFT:
			Horizontal =  (Horizontal - 0.1 * (1 / (zoom * 1.8)));
			break;
		case KeyEvent.VK_ESCAPE:
			Vertical = 0.01;
			zoom = 1;
			center.x = 0;
			center.y = 0;
		}
	}
}
