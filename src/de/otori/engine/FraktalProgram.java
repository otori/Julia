package de.otori.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public abstract class FraktalProgram implements Renderable, KeyListener, MouseListener, MouseMotionListener {

	/**
	 * Indicates zoom level
	 */
	protected double zoom;
	Point2F center;
		
	protected int winWidth;
	protected int winHeight;		
	
	private enum ProgramState {IDLE, ZOOMING, SHIFTING};		
	private ProgramState state; // For sample zooming implementation
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	
	@Override	
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	private long lastClick = 0, lastClickRight = 0;
	final static long DOUBLECLICKMAXDIFF = 350;
	
	private Point2F zCenterSrc, zCenterDest;	
	private double zoomStart;
	private double zoomDest;
	private long zoomTimeStart;
	final long ZOOM_DURATION = 400;
	final double ZOOM_FACTOR = 2.; // to da squareee !! :D
	/**
	 * Internal function used for zoom animation.
	 * @param x
	 * @param y
	 * @param zoomIn indicates, whether to zoom in or out.
	 */
	private void initZoom(int x, int y, boolean zoomIn)
	{
		if(state != ProgramState.IDLE)
			return;
		
		state = ProgramState.ZOOMING;
		
		zCenterSrc = new Point2F(center);		
		zCenterDest = Misc.calculatePixelRealCoordinates(x, y, winWidth, winHeight, zoom, center);
		zoomStart = zoom;
		if(zoomIn)
			zoomDest = zoom * ZOOM_FACTOR * ZOOM_FACTOR;
		else
			zoomDest = zoom / ZOOM_FACTOR / ZOOM_FACTOR;
		zoomTimeStart = System.currentTimeMillis();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
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
	
	@Override	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
}
