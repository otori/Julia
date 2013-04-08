import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import de.misc.ColorFun;


public class MBRenderThread extends Thread {

	private final BufferedImage biImage;
	private final int x, y, renderWidth, renderHeight;
	final byte[] pixelArray;
	final int winWidth, winHeight;
	//Be careful, to not write in the same Image Segments :D
	
	public MBRenderThread(BufferedImage biImage, int x, int y, int renderWidth, int renderHeight)
	{
		this.biImage = biImage;
		this.x = x;
		this.y = y;
		this.renderWidth = renderWidth;
		this.renderHeight = renderHeight;
		
		pixelArray = ((DataBufferByte)(biImage.getRaster().getDataBuffer())).getData();
		
		winWidth = biImage.getWidth();
		winHeight = biImage.getHeight();
	}
	
	@Override
	public void run()
	{
		renderImage();
	}
	
	private void renderImage()
	{		
		for(int iX = x; iX < x + renderWidth; iX++)
		{
			for(int iY = y; iY < y + renderHeight; iY++)
			{
				int iter = Mandelbrot.isInMandel(Mandelbrot.cnFromPixel(iX, iY, winWidth, winHeight));
								
				if(iter == Mandelbrot.MAX_ITER)
				{					
					pixelArray[iY * winWidth * 3 + iX * 3] = 0;
					pixelArray[iY * winWidth * 3 + iX * 3 + 1] = 0;
					pixelArray[iY * winWidth * 3 + iX * 3 + 2] = 0;									
				}
				else
				{					
					Color pxCol = null;
					
					//Choose either the upper color selection for a psychedelic color experience,
					//Or the lower one for a calm one
										
					double near = Math.sqrt(iter) / Math.sqrt((double)Mandelbrot.MAX_ITER);
					pxCol = ColorFun.farbVerlauf(Color.getHSBColor(((iX / (float)winWidth) + 0.5f) % 1.f, 1.f, 1.f), Color.getHSBColor(iX / (float)winWidth, 1.f, 1.f), near);					
					//int colVal = (int)((Math.sqrt(iter) / Math.sqrt((double)Mandelbrot.MAX_ITER)) * 255);
					//int colVal = (int)((Math.pow(iter, .28) / Math.pow((double)Mandelbrot.MAX_ITER, .28)) * 255);
					//pxCol = new Color(colVal, 255-colVal, 0);
					
					pixelArray[iY * winWidth * 3 + iX * 3] = (byte)pxCol.getBlue();
					pixelArray[iY * winWidth * 3 + iX * 3 + 1] = (byte)pxCol.getGreen();
					pixelArray[iY * winWidth * 3 + iX * 3 + 2] = (byte)pxCol.getRed();
					
				}				
			}
		}		
		
	}
	
}
