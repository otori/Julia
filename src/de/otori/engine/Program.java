package de.otori.mandelbrot;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.otori.mandelbrot.ComplexNumber;
import de.otori.mandelbrot.Mandelbrot;




public class Program extends JPanel implements KeyListener, MouseListener, MouseMotionListener{

	
	
	private static final long serialVersionUID = 1L;
	private final BufferedImage mbImage;
	private  int width, height;
	//private final MBRenderThread[] renderer;
	private long tStart;
	private final Renderer renderer;
	
	private ComplexNumber startValue;
	private ComplexNumber pCenter;
	private double zoom;
	
	public Program(final int winWidth, final int winHeight, final int iThreads)
	{
		setFocusable(true);
		addKeyListener(this);		
		addMouseListener(this);	
		addMouseMotionListener(this);
		width = winWidth;
		height = winHeight;
		
		mbImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
				
		tStart = System.currentTimeMillis();
		
		renderer = new Renderer(mbImage, iThreads, new Dimension(160,160));
		
		state = ProgramState.IDLE;
		
		startValue = new ComplexNumber(0, 0);
		pCenter = new ComplexNumber(-0.5,0);
		zoom = 1;
		
	}

	
	@Override
	public void paint (Graphics g)
	{		
		long ltStart = System.currentTimeMillis(); 
				
		//renderImage();
		
		long deltaTime = System.currentTimeMillis() - tStart;
			
		updatePositions();
		
		renderer.renderImage(zoom, pCenter, startValue);
		g.drawImage(mbImage, 0, 0, null);
		
		long ltDur = System.currentTimeMillis() - ltStart;
		
		g.setColor(Color.white);		
				
		g.drawString(String.format("%.2f fp/s", 1000.0 / ltDur), 2, 12);
		
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

	
	private long lastClick = 0, lastClickRight = 0;
	final static long DOUBLECLICKMAXDIFF = 350;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (e.getButton())
		{
		case MouseEvent.BUTTON1:
			if(System.currentTimeMillis() - lastClick < DOUBLECLICKMAXDIFF)
			{			
				initZoom(e.getPoint().x, e.getPoint().y, true);
			}
			else
			{
				lastClick = System.currentTimeMillis();
			}
			break;
		case MouseEvent.BUTTON3: 
			if(System.currentTimeMillis() - lastClickRight < DOUBLECLICKMAXDIFF)
			{			
				initZoom(e.getPoint().x, e.getPoint().y, false);
			}
			else
			{
				lastClickRight = System.currentTimeMillis();
			}
			break;
		}		
	}


	private ComplexNumber centerPressed;
	private ComplexNumber centerStart;
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		centerPressed = Mandelbrot.cnFromPixelZoom(e.getPoint().x, e.getPoint().y, width, height, zoom, pCenter.getReal(), pCenter.getImag());
		centerStart = new ComplexNumber(pCenter);				
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub		
		centerPressed = null;	
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("keytyped");
		// TODO Auto-generated method stub
		
	}

		long startOfpressingKey = 0 ;
		int lastKey = 0;
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub		

		
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
			pCenter = new ComplexNumber(-.5, 0);	
		}		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(centerPressed != null && state == ProgramState.IDLE)
		{			
			ComplexNumber curPos = Mandelbrot.cnFromPixelZoom(e.getPoint().x, e.getPoint().y, width, height, zoom, pCenter.getReal(), pCenter.getImag());
			
			double deltaReal = (curPos.getReal() - centerPressed.getReal()) / 1.25;
			double deltaImag = (curPos.getImag() - centerPressed.getImag()) / 1.25;
			
			pCenter.setReal(centerStart.getReal() - deltaReal);
			pCenter.setImag(centerStart.getImag() - deltaImag);
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
