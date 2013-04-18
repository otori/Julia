package de.otori.mandelbrot;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.org.apache.xml.internal.resolver.readers.XCatalogReader;

import de.otori.mandelbrot.ComplexNumber;
import de.otori.mandelbrot.Mandelbrot;




public class Program extends JPanel implements KeyListener, MouseListener{

	private enum ProgramState {IDLE, ZOOMING};
	
	/**
	 * 
	 */
	
	private ProgramState state;
	
	private static final long serialVersionUID = 1L;
	//use Zoom (true) or don't use (false)
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

	private ComplexNumber centerSrc = null;
	private ComplexNumber centerDest = null;
	private double zoomStart;
	private double zoomDest;
	private long zoomTimeStart;
	final long ZOOM_DURATION = 400;
	final double ZOOM_FACTOR = 2.; // to da squareee !! :D
	
	private void initZoom(int x, int y)
	{
		if(state != ProgramState.IDLE)
			return;
		
		state = ProgramState.ZOOMING;
		
		centerSrc = new ComplexNumber(pCenter); 
		centerDest = Mandelbrot.cnFromPixelZoom(x, y, width, height, zoom, pCenter.getReal(), pCenter.getImag());
		zoomStart = zoom;
		zoomDest = zoom * ZOOM_FACTOR * ZOOM_FACTOR;
		zoomTimeStart = System.currentTimeMillis();
	}
	
	private void updatePositions()
	{
		switch (state) {
		case ZOOMING:
						
			long ticksNow = System.currentTimeMillis() - zoomTimeStart;
			if(ticksNow >= ZOOM_DURATION)
			{
				ticksNow = ZOOM_DURATION;
				state = ProgramState.IDLE;
				
				System.out.println("Zoom finished..");
			}
			
			double zProgres = ticksNow / (double)ZOOM_DURATION;						
			zoom = zoomStart + (zoomDest - zoomStart) * zProgres;			
			pCenter.setImag(centerSrc.getImag() + (centerDest.getImag() - centerSrc.getImag()) * zProgres );
			pCenter.setReal(centerSrc.getReal() + (centerDest.getReal() - centerSrc.getReal()) * zProgres );
			
			break;

		default:
			break;
		}
	}
	
	private long lastClick = 0;
	final static long DOUBLECLICKMAXDIFF = 350;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(System.currentTimeMillis() - lastClick < DOUBLECLICKMAXDIFF)
		{
			System.out.println("Zoom initiated...");
			initZoom(e.getPoint().x, e.getPoint().y);
		}
		else
		{
			lastClick = System.currentTimeMillis();
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub		
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			startValue.setReal(startValue.getReal() + 0.01);
			break;
		case KeyEvent.VK_DOWN:
			startValue.setReal(startValue.getReal() - 0.01);
			break;
		case KeyEvent.VK_RIGHT:
			startValue.setImag(startValue.getImag() + 0.01);
			break;
		case KeyEvent.VK_LEFT:
			startValue.setImag(startValue.getImag() - 0.01);
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

}
