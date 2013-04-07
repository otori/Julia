import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.misc.ColorFun;

public class Renderer extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Renderer()
	{
		
	}
	
	public void paint (Graphics g)
	{		
		Graphics2D g2d = (Graphics2D) g;
		
		long ltStart = System.nanoTime(); 
		
		Dimension size = this.getSize();
						
		for(int x = 0; x < size.width; x++)
		{
			for(int y = 0; y < size.height; y++)
			{
				int iter = Mandelbrot.isInMandel(Mandelbrot.cnFromPixel(x, y, size.width, size.height));
								
				if(iter == Mandelbrot.MAX_ITER)
				{										
					g2d.drawLine(x, y, x, y);
				}
				else
				{					
					double near = Math.sqrt(iter) / Math.sqrt((double)Mandelbrot.MAX_ITER);
					g2d.setColor(ColorFun.farbVerlauf(Color.getHSBColor(((x / (float)size.width) + 0.5f) % 1.f, 1.f, 1.f), Color.getHSBColor(x / (float)size.width, 1.f, 1.f), near));					
					//int colVal = (int)((Math.sqrt(iter) / Math.sqrt((double)Mandelbrot.MAX_ITER)) * 255);
					//int colVal = (int)((Math.pow(iter, .28) / Math.pow((double)Mandelbrot.MAX_ITER, .28)) * 255);
					//g2d.setColor(new Color(colVal, 255-colVal, 0));					
					g2d.drawLine(x, y, x, y);
					g2d.setColor(Color.black);
				}				
			}
		}
		
		long ltDur = System.nanoTime() - ltStart;
		
		System.out.println("Rendering took " + ltDur / 1000000 + "ms");
		System.out.println("Estimated FPS: " + 1000000000.0 / ltDur + "");
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Renderer mbProgram = new Renderer();
		JFrame frame = new JFrame("Mandelbrot / Julia");		
        frame.add(mbProgram);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
       
		System.out.println("Fractal Time 1337");			
	}
}
