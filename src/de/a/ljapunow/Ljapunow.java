/**
 * 
 */
package de.a.ljapunow;

import java.awt.Color;

import de.otori.engine.FraktalProgram;
import de.otori.engine.Point2F;
import de.otori.mandelbrot.ComplexNumber;
import de.otori.mandelbrot.Mandelbrot;

/**
 * @author arndt
 *http://de.wikipedia.org/wiki/Ljapunow-Fraktal
 */
public class Ljapunow extends FraktalProgram {
	public static final Ljapunow LjFraktal = new Ljapunow();


	/**
	 * The Maximum number of iterations determines the Quality of the computation:
	 * A high number results in longer computation time, a low in less quality
	 */
	public static int MAX_ITER = 50;
	public static int PSEUDO_INFINITY = 50;

	double startValue;

	public Ljapunow() {
		startValue = 0.5; 
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

	 public void leTest(){
		double test = logisticEquation(new boolean[] {true,false,true}, new Point2F(2,3));
	 System.out.println(test);
	 }
	 
	double  logisticEquation(boolean[] sequence, Point2F position){
		double logisticFactor = startValue;
		double a = position.x ;
		double b = position.y;
		for(int i = 0; i<=MAX_ITER ;i++){
			if(sequence[(i%sequence.length)]){
				logisticFactor = (logisticFactor * a*(1-logisticFactor) );
			}
			else{
				
				logisticFactor = (logisticFactor * b*(1-logisticFactor) );
				
			}
		}
		return logisticFactor;
	}
	 
	public double ljapunowExponent  (){return 0;}
	
	@Override
	public Color calcPixel(Point2F coordinate) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
