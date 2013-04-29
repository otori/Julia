package de.otori.engine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class MBRenderThread extends Thread {
		
	private final int x, y, renderWidth, renderHeight;
	final byte[] pixelArray;
	final int winWidth, winHeight;
	double zoom;
	double centerX, centerY;	
	FraktalProgram fraktal;	
	
	//Be careful, to not write in the same Image Segments :D
	
	public MBRenderThread(BufferedImage biImage, FraktalProgram fraktal, int x, int y, int renderWidth, int renderHeight, double dZoom, double xCenter, double yCenter)
	{
		this.fraktal = fraktal;
		
		this.x = x;
		this.y = y;
		this.renderWidth = renderWidth;
		this.renderHeight = renderHeight;
		
		pixelArray = ((DataBufferByte)(biImage.getRaster().getDataBuffer())).getData();
		
		winWidth = biImage.getWidth();
		winHeight = biImage.getHeight();
		zoom = dZoom;
		centerX = xCenter;
		centerY = yCenter;					
		
	}
	
	@Override
	public void run()
	{
		renderImage();
	}
	
	private void renderImage()
	{		
		int xMax = Math.min(x + renderWidth, winWidth);
		int yMax = Math.min(y + renderHeight, winHeight);
		
		Color col;
		
		for(int iX = x; iX < xMax; iX++)
		{
			for(int iY = y; iY < yMax; iY++)
			{				
				col = fraktal.calcPixel(iX, iY, centerX, centerY, zoom);
				
				pixelArray[iY * winWidth * 3 + iX * 3] = (byte)col.getBlue();
				pixelArray[iY * winWidth * 3 + iX * 3 + 1] = (byte)col.getGreen();
				pixelArray[iY * winWidth * 3 + iX * 3 + 2] = (byte)col.getRed();							
			}
		}		
		
	}
	
}
