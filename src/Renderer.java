import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import de.misc.ColorFun;

public class Renderer extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BufferedImage mbImage;
	private final int width, height, iThreads;
	private final MBRenderThread[] renderer;
	
	public Renderer(final int winWidth, final int winHeight, final int iThreads)
	{
		width = winWidth;
		height = winHeight;
		this.iThreads = iThreads;
		mbImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		renderer = new MBRenderThread[iThreads];
				
		Timer t = new Timer(100, this);
		t.start();
	}
	
	private void initThreads()
	{
		int renderHeight = height / iThreads;
		for(int i = 0; i < iThreads; i++)
		{
			renderer[i] = new MBRenderThread(mbImage, 0, i * renderHeight, width, renderHeight);
			System.out.println("" + 0 + "," + i * renderHeight + "," + width + "," + renderHeight);			
		}
	}
	
	private void renderImage()
	{	
		initThreads();
		
		for(int i = 0; i < iThreads; i++)
		{
			renderer[i].start();			
		}
		for(int i = 0; i < iThreads; i++)
		{
			try {
				renderer[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Thread Exception: " + e.getMessage());
			}			
		}
	}
	
	public void paint (Graphics g)
	{		
		long ltStart = System.currentTimeMillis(); 
				
		renderImage();
		g.drawImage(mbImage, 0, 0, null);
		
		long ltDur = System.currentTimeMillis() - ltStart;
		
		System.out.println("Rendering took " + ltDur + "ms");
		System.out.println("Estimated FPS: " + 1000.0 / ltDur + "");
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int imWidth = 1200, imHeight = 800;
		
		Renderer mbProgram = new Renderer(imWidth, imHeight, 4);
		JFrame frame = new JFrame("Mandelbrot / Julia");		
        frame.add(mbProgram);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(imWidth, imHeight);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
       
        Insets winInsets = frame.getInsets(); 
        frame.setSize(imWidth + winInsets.left + winInsets.right, imHeight + winInsets.top + winInsets.bottom);

        System.out.println(winInsets);
        
		System.out.println("Fractal Time 1337");			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		paint(getGraphics());
	}
}
