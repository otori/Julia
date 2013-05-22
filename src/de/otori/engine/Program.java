package de.otori.engine;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.a.fakefraktal.AnotherFakeFraktal;
import de.a.fakefraktal.FakeFraktal;
import de.a.ljapunow.Ljapunow;
import de.otori.mandelbrot.Mandelbrot;

public class Program extends JPanel{

	
	
	private static final long serialVersionUID = 1L;
	private final BufferedImage mbImage;
	private int width, height;
	private final Renderer renderer;	

	FraktalProgram currentFraktal;
	
	public Program(final int winWidth, final int winHeight, final int iThreads)
	{
		setFocusable(true);

		width = winWidth;
		height = winHeight;
		
		mbImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
	//	currentFraktal = Mandelbrot.MBFraktal;
	//	currentFraktal = Ljapunow.LjFraktal;
		currentFraktal = FakeFraktal.FakeFraktal;
		currentFraktal.setWindowSize(width, height);
		renderer = new Renderer(currentFraktal, mbImage, iThreads, new Dimension(160,160));
		
		addKeyListener(currentFraktal);		
		addMouseListener((FraktalProgram)currentFraktal);	
		addMouseMotionListener(currentFraktal);
	}
	
	@Override
	public void paint (Graphics g)
	{		
		long ltStart = System.nanoTime(); 
				
		//renderImage();
				
		renderer.renderImage();
				
		g.drawImage(mbImage, 0, 0, null);
		
		long ltDur = System.nanoTime() - ltStart;
		
		g.setColor(Color.white);		
				
		g.drawString(String.format("%.2f fp/s", 1000000000.0 / ltDur), 2, 12);
		
		//System.out.println("Rendering took " + ltDur + "ms");
		//System.out.println("Estimated FPS: " + 1000.0 / ltDur + "");
			
		repaint();		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length > 0)
			CommandLineProgram.cmdMain(args);		//Instead of using a lame GUI :D
		else
		{		
			int imWidth = 800, imHeight = 600;
			
			Program mbProgram = new Program(imWidth, imHeight, 8);
			final JFrame frame = new JFrame("Mandelbrot / Julia");		
		    frame.add(mbProgram);
		           
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setResizable(false);
		    frame.setSize(imWidth, imHeight);
		   
		    
		    frame.setLocationRelativeTo(null);
		    frame.setVisible(true);
		           
		    
		   
		    Insets winInsets = frame.getInsets(); 
		    frame.setSize(imWidth + winInsets.left + winInsets.right, imHeight + winInsets.top + winInsets.bottom);
		    
			System.out.println("Fractal Time 1337");
		}
	}

}
