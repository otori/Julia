import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;


import javax.swing.JFrame;
import javax.swing.JPanel;

import de.misc.ColorFun;

public class Renderer extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage mbImage;
	
	
	public Renderer()
	{
		mbImage = new BufferedImage(900, 600, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	private void renderImage()
	{		
		final byte[] pixelArray = ((DataBufferByte)(mbImage.getRaster().getDataBuffer())).getData();
		
		int winWidth = mbImage.getWidth();
		int winHeight = mbImage.getHeight();				
		
		for(int x = 0; x < winWidth; x++)
		{
			for(int y = 0; y < winHeight; y++)
			{
				int iter = Mandelbrot.isInMandel(Mandelbrot.cnFromPixel(x, y, winWidth, winHeight));
								
				if(iter == Mandelbrot.MAX_ITER)
				{					
					pixelArray[y * winWidth * 3 + x * 3] = 0;
					pixelArray[y * winWidth * 3 + x * 3 + 1] = 0;
					pixelArray[y * winWidth * 3 + x * 3 + 2] = 0;									
				}
				else
				{					
					Color pxCol = null;
					
					//Choose either the upper color selection for a psychedelic color experience,
					//Or the lower one for a calm one
										
					double near = Math.sqrt(iter) / Math.sqrt((double)Mandelbrot.MAX_ITER);
					pxCol = ColorFun.farbVerlauf(Color.getHSBColor(((x / (float)winWidth) + 0.5f) % 1.f, 1.f, 1.f), Color.getHSBColor(x / (float)winWidth, 1.f, 1.f), near);					
					//int colVal = (int)((Math.sqrt(iter) / Math.sqrt((double)Mandelbrot.MAX_ITER)) * 255);
					//int colVal = (int)((Math.pow(iter, .28) / Math.pow((double)Mandelbrot.MAX_ITER, .28)) * 255);
					//pxCol = new Color(colVal, 255-colVal, 0);
										
															
					pixelArray[y * winWidth * 3 + x * 3] = (byte)pxCol.getBlue();
					pixelArray[y * winWidth * 3 + x * 3 + 1] = (byte)pxCol.getGreen();
					pixelArray[y * winWidth * 3 + x * 3 + 2] = (byte)pxCol.getRed();
					
				}				
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

		Renderer mbProgram = new Renderer();
		JFrame frame = new JFrame("Mandelbrot / Julia");		
        frame.add(mbProgram);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(900, 600);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
       
        Insets winInsets = frame.getInsets(); 
        frame.setSize(900 + winInsets.left + winInsets.right, 600 + winInsets.top + winInsets.bottom);

        System.out.println(winInsets);
        
		System.out.println("Fractal Time 1337");			
	}
}
