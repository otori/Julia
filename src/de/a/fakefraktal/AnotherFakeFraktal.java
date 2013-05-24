/**
 * 
 */
package de.a.fakefraktal;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.otori.engine.FraktalProgram;
import de.otori.engine.Point2F;
import de.otori.mandelbrot.Mandelbrot;
import de.otori.misc.FasterSinus;

/**
 *
 */
public class AnotherFakeFraktal extends FraktalProgram {
	private long Time ;
	public static float Horizontal = 1;
	public static float  Vertical =1;



	public static final AnotherFakeFraktal FakeFraktal = new AnotherFakeFraktal();
	/**
	 * Constructor
	 */
	public AnotherFakeFraktal() {
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
		
		float x = (float) coordinate.x;
		float y = (float)- coordinate.y;
		
		
		
		float funct =  x*(Horizontal);
	
		
		
		
		float funct2 = 10*FasterSinus.sin(x+Vertical+5);
		float funct3 = x*FasterSinus.sin(Horizontal/10);
		if(funct<=(y+0.1) && funct>=(y-0.1))return Color.WHITE;
		if(funct2<=(y+0.1) && funct2>=(y-0.1))return Color.BLUE;
		if (funct3<=(y+10) && funct3>=(y-10))return Color.getHSBColor((float) 0.5, 1, funct3);
		return Color.BLACK;
		//float diff =FasterSinus.sin(x)-y;
		//if(diff<=0  )
		//	return Color.BLACK; 
		 //return   Color.getHSBColor((float)0.5, 1, 1/diff);
	}

	@Override
	public void preRendering() {
			super.preRendering();
			Time= (System.currentTimeMillis()/10)%1000;
			
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyPressed(e); // We will do the parent events, too.

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			Vertical = (float) (Vertical + 0.1 * (1 / (zoom * 1.8)));
			break;
		case KeyEvent.VK_DOWN:
			Vertical = (float) (Vertical - 0.1 * (1 / (zoom * 1.8)));
			break;
		case KeyEvent.VK_RIGHT:
			Horizontal = (float) (Horizontal + 0.01); //* (1 / (zoom * 1.8)));
			break;
		case KeyEvent.VK_LEFT:
			Horizontal =  (float) (Horizontal - 0.01); //* (1 / (zoom * 1.8)));
			break;
		case KeyEvent.VK_ESCAPE:
			Vertical = (float) 0.01;
			zoom = 1;
			center.x = 0;
			center.y = 0;
		}
	}
}
