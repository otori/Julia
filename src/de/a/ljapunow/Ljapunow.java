/**
 * 
 */
package de.a.ljapunow;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.otori.engine.FraktalProgram;
import de.otori.engine.Point2F;


/**
 * @author arndt http://de.wikipedia.org/wiki/Ljapunow-Fraktal
 */
public class Ljapunow extends FraktalProgram {
	public static final Ljapunow LjFraktal = new Ljapunow();

	/**
	 * The Maximum number of iterations determines the Quality of the
	 * computation: A high number results in longer computation time, a low in
	 * less quality
	 */
	public static int MAX_ITER = 50;
	public static int PSEUDO_INFINITY = 7;
	boolean[] Folge = { true, true, true, true, true, true, false, false,
			false, false, false, false };
	double startValue;

	public Ljapunow() {
		startValue = 0.5;
		center = new Point2F(2, 3.5);
		zoom = 1.;
	}

	/**
	 * setter to change MAX_ITER A high number results in longer computation
	 * time, a low in less quality
	 * 
	 * @param iter
	 *            Maximun number of iterations
	 */
	public static void setIteration(int iter) {
		MAX_ITER = iter;
	}

	/**
	 * the logistic equation creates some deterministic chaos
	 * 
	 * @param sequence
	 *            a rather small one dimensonial array of booleans
	 * @param position
	 *            Pixel to be calculated
	 * @return a chaotic number
	 */
	double ljaunowCalculation(boolean[] sequence, Point2F position) {
		double logisticFactor = startValue;
		double a = position.x;
		double b = position.y;
		int length = sequence.length;
		for (int i = 0; i <= MAX_ITER; i++) {
			if (sequence[(i % length)]) {
				logisticFactor = (logisticFactor * a * (1 - logisticFactor));
			} else {

				logisticFactor = (logisticFactor * b * (1 - logisticFactor));

			}
		}
		if (sequence[(MAX_ITER % length)]) {
			return ljapunowExponent(logisticFactor, a);
		} else {
			return ljapunowExponent(logisticFactor, b);
		}
	}

	/**
	 * is part of the ljaponow Calculation
	 * 
	 * @return ljaounow exponent
	 */
	private double ljapunowExponent(double Xn, double Rn) {
		double Sum = 0;
	for (int n = 1; n <= PSEUDO_INFINITY; n++) {
			Sum = Sum + Math.log(Math.abs(Rn * (1 - Xn)));
		}
		double lambda = Sum / PSEUDO_INFINITY;
		return lambda;
	}

	@Override
	public Color calcPixel(Point2F coordinate) {

		double temp = 0;
	   temp = ljaunowCalculation(Folge, coordinate);

		int colVal = 0;
		int colVal2 = 0;
		int colVal3 = 0;
		if (temp < 0) {
			colVal2 = ((int) Math.abs((temp * -1) * 100)) % 255;
			colVal =  Math.abs(((int) (temp * 255))) % 255;
		}

		else {
			colVal = ((int) (temp)) % 255;
		}
		if (0 < temp && temp < 10) {
			colVal3 = Math.abs(((int) (temp * 255))) % 255;
		}

		return new Color(colVal, colVal2, colVal3);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyPressed(e); // We will do the parent events, too.

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			startValue = startValue + 0.001 * (1 / (zoom * 1.8));
			break;
		case KeyEvent.VK_DOWN:
			startValue = startValue - 0.001 * (1 / (zoom * 1.8));
			break;
		case KeyEvent.VK_RIGHT:
			Folge[0] = !Folge[0];
			break;
		case KeyEvent.VK_LEFT:
			Folge[1] = !Folge[1];
			break;
		case KeyEvent.VK_ESCAPE:
			startValue = 0.5;
			zoom = 1;
			center.x = 2;
			center.y = 3.5;
		}
	}

}
