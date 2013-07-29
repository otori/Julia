package de.a.newton; //Das eigene Package
import java.awt.Color; // Benötigt um Farben auszugeben
import java.awt.event.KeyEvent;

import de.otori.engine.FraktalProgram; // die Eltern-Klasse
import de.otori.engine.Point2F; // Format der Koordinate die übergeben wird

import de.otori.mandelbrot.Mandelbrot;
import de.otori.mandelbrot.ComplexNumber;
import de.otori.misc.ColorFun;
import de.otori.misc.FasterSinus;


public class Newton extends FraktalProgram{
	
	/**
	 * The Maximum number of iterations determines the Quality of the computation:
	 * A high number results in longer computation time, a low in less quality
	 */
	private int MAX_ITER = 42;

	public static final Newton GenerischeEinstellungen = new Newton();
	
	/**
	 * Konstruktor
	 */
	public Newton() {
		zoom = 1;
		center = new Point2F(0, 0);
	}

	
	/**
	 * 
	 * @param startValue
	 * @return anzahl der nötigen Itterationen 
	 */
	public int calcConvergence(ComplexNumber startValue) {
		
		int iteration = 1; // die Anzahl der Itterationen soll gezählt werden
		
		ComplexNumber aktuellerWert = new ComplexNumber(startValue); //mit dem startValue fängt die Folge an

		while(iteration < MAX_ITER)
		{
			
			//divident berechnen
		ComplexNumber divident = new ComplexNumber(aktuellerWert); 
		divident.pot(3); //z³
		divident.sub(1);//-1
		
		//divisor	
		ComplexNumber divisor = new ComplexNumber(aktuellerWert); 
		divisor.quad(); // z²
		divisor.mul(3.);//*3
		
		//Subtrahend
		ComplexNumber subtrahend = new ComplexNumber(divident);
		subtrahend.div(divisor); // quotient = dividend/ divisor
			
		//Differenz Berechnen
		aktuellerWert.sub(subtrahend);
			iteration++;
			if(aktuellerWert.absSqr() > 4.0){
				//nach http://de.wikipedia.org/wiki/Julia-Menge#Graphische_Darstellung_der_Julia-Mengen
				break;
		}
		}
		return iteration;
	
	}
		
	
	
	
	
	@Override
	public Color calcPixel(Point2F coordinate) {
	
		
	int	iter = calcConvergence( new ComplexNumber(coordinate.x,coordinate.y));//Die Nummer holen auf der die Farbe beruht	
		if(iter == MAX_ITER)
		{
			return Color.black;
		}
		else
		{	double near = Math.sqrt(iter) / Math.sqrt((double)MAX_ITER);
			return ColorFun.farbVerlauf(Color.getHSBColor((float) (((coordinate.x / 3) + 0.5f) % 1.f), 1.f, 1.f), Color.getHSBColor((float) (coordinate.x / 3), 1.f, 1.f), near);}
	}

}
