package de.otori.mandelbrot;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Program extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//use Zoom (true) or don't use (false)
	public static final boolean useZoom = false ;
	private final BufferedImage mbImage;
	private  int width, height;
	//private final MBRenderThread[] renderer;
	private long tStart;
	private final Renderer renderer;
		
	public Program(final int winWidth, final int winHeight, final int iThreads)
	{
		width = winWidth;
		height = winHeight;
		
		mbImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
				
		tStart = System.currentTimeMillis();
		
		renderer = new Renderer(mbImage, iThreads, new Dimension(160,160));
		
	}

	
	@Override
	public void paint (Graphics g)
	{		
		long ltStart = System.currentTimeMillis(); 
				
		//renderImage();
		
		long deltaTime = System.currentTimeMillis() - tStart;
		
		double zoom;
		if (useZoom) 
			zoom = 1 + (Math.sin( 0.3 * (deltaTime ) / 1000)/10)*2;
		else 
			zoom = 1;
		
		zoom *= zoom;
		
		ComplexNumber startValue = new ComplexNumber(0//((Math.cos((0.3*deltaTime)/2000)*(Math.sin(0.3 * (deltaTime ) / 1000))))
													, 0);//((Math.sin((0.5*deltaTime)/1000)*(Math.cos(0.4 * (deltaTime ) / 1000))))*1.5); 
		
		renderer.renderImage(zoom, startValue);
		g.drawImage(mbImage, 0, 0, null);
		
		long ltDur = System.currentTimeMillis() - ltStart;
		
		g.setColor(Color.white);		
				
		g.drawString(String.format("%.2f fp/s", 1000.0 / ltDur), 2, 12);
		
		System.out.println("Rendering took " + ltDur + "ms");
		System.out.println("Estimated FPS: " + 1000.0 / ltDur + "");
			
		repaint();		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int imWidth = 800, imHeight = 600;
		
		Program mbProgram = new Program(imWidth, imHeight, 8);
		JFrame frame = new JFrame("Mandelbrot / Julia");		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
}
