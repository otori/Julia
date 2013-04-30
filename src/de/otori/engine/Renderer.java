package de.otori.engine;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class Renderer {

	// Thread Manager
	
	final int width, height; 
	final int iThreads;
	final MBRenderThread[] activeThreads;
	final BufferedImage biImage;		
	final Dimension threadRenderArea;
	
	final int xThreads, yThreads;

	private FraktalProgram fraktal;
	
	public Renderer(FraktalProgram fraktal, BufferedImage biImage, int iThreads, Dimension renderArea)
	{
		width = biImage.getWidth();
		height = biImage.getHeight();
		
		this.biImage = biImage;		
		
		threadRenderArea = renderArea;
		
		xThreads = (int)Math.ceil(width / (double)threadRenderArea.width);
		yThreads = (int)Math.ceil(height / (double)threadRenderArea.height);

		this.iThreads = Math.min(iThreads, xThreads * yThreads);
				
		activeThreads = new MBRenderThread[this.iThreads];
		
		this.fraktal = fraktal;
				
	}
		
	public void setFraktalProgram(FraktalProgram frak)
	{		
		fraktal = frak;
	}
	
	public void renderImage()
	{		
		fraktal.preRendering();
		
		int x, y;
		for(int i = 0; i < iThreads; i++)
		{			
			x = i % xThreads;
			y = i / xThreads;
			activeThreads[i] = new MBRenderThread(biImage, fraktal, x * threadRenderArea.width, y * threadRenderArea.height, threadRenderArea.width, threadRenderArea.height, fraktal.zoom, fraktal.center);
			activeThreads[i].start();
		}
		
		int thrCount = iThreads;
		int anzThreads = xThreads * yThreads;
		while(thrCount < anzThreads)
		{
			for(int i = 0; i < iThreads; i++)
			{
				if (activeThreads[i].isAlive())
				{					
					try {
						Thread.sleep(0, 100000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("Thread Exception :(");
						e.printStackTrace();
					}
				}
				else
				{
					x = thrCount % xThreads;
					y = thrCount / xThreads;
					activeThreads[i] = new MBRenderThread(biImage, fraktal, x * threadRenderArea.width, y * threadRenderArea.height, threadRenderArea.width, threadRenderArea.height, fraktal.zoom, fraktal.center);
					activeThreads[i].start();
					thrCount++;
					if(!(thrCount < anzThreads))
						break;
				}
			}
		}
		
		for(MBRenderThread thread : activeThreads)
		{			
			while(thread.isAlive())
			{
				try{
					Thread.sleep(0, 100000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("Thread Exception :(");
					e.printStackTrace();
				}
			}
		}	
		
		fraktal.postRendering();
	}
	
}
