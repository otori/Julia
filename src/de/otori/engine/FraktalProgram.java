package de.otori.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.otori.engine.animation.Animation;
import de.otori.engine.animation.AnimationEvent;
import de.otori.engine.animation.AnimationSaveLoader;
import de.otori.engine.animation.ShiftEvent;
import de.otori.engine.animation.ZoomEvent;


public abstract class FraktalProgram implements Renderable, KeyListener, MouseListener, MouseMotionListener {

	/**
	 * Indicates zoom level
	 */
	protected double zoom;
	protected Point2F center;
		
	protected int winWidth;
	protected int winHeight;		
	
	private enum ProgramState {IDLE, ZOOMING, SHIFTING, NOTIDLE}; // NOTIDLE is for subclasses to tell that they are doing something		
	private ProgramState state = ProgramState.IDLE; // For sample zooming implementation
	
	protected Animation currentAnimation;
	private boolean eventRecording = false; 
	
	public void setWindowSize(int width, int height)
	{
		winWidth = width;
		winHeight = height;
	}	
	
	@Override
	public void preRendering() {
		updatePositions();
		//System.out.println(String.format("Debug: Center: (%f,%f) zoom: %f", center.x, center.y, zoom));
	}
	
	@Override
	public void postRendering() {
		//Nothing yet...
		//Feel free to overide HEHE
	}
	
	@Override
	public void handleEvent(AnimationEvent event)
	{
		if (eventRecording)
			currentAnimation.addEvent(event);
		if(event instanceof ZoomEvent)
		{
			initZoom((ZoomEvent) event);
		}
		if(event instanceof ShiftEvent)
		{
			initShift((ShiftEvent) event);
		}
	}
	
	public void setZoom(double z)
	{
		zoom = z;
	}
	
	public void setCenter(Point2F p)
	{
		center = new Point2F(p);
	}
	
	private long lastClick = 0, lastClickRight = 0;
	final static long DOUBLECLICKMAXDIFF = 350;
	
	private Point2F zCenterSrc, zCenterDest;	
	private double zoomStart;
	private double zoomDest;
	private long zoomTimeStart;
	private final long ZOOM_DURATION = 400;
	private final double ZOOM_FACTOR = 2.; // to da squareee !! :D
	/**
	 * Internal function used for zoom animation.	 
	 * @param zEvent Zoom Event
	 */
	private void initZoom( ZoomEvent zEvent) 
	{
		if(state != ProgramState.IDLE)
			return;
		
		state = ProgramState.ZOOMING;
		
		zCenterSrc = new Point2F(center);		
		zCenterDest = new Point2F(zEvent.getDest()); 		
		zoomStart = zoom;
		if(zEvent.isZoomIn())
			zoomDest = zoom * ZOOM_FACTOR * ZOOM_FACTOR;
		else
			zoomDest = zoom / ZOOM_FACTOR / ZOOM_FACTOR;
		zoomTimeStart = System.currentTimeMillis();
	}
		
	private Point2F sCenterSrc, sCenterDest;
	private long shiftTimeStart;
	private final long SHIFT_DURATION = 400;	
	/**
	 * Internal function used for shift animation.	 
	 * @param sEvent Shift Event
	 */
	private void initShift( ShiftEvent sEvent)
	{
		if(state != ProgramState.IDLE)
			return;
		
		state = ProgramState.SHIFTING;
		
		sCenterSrc = new Point2F(center);
		sCenterDest = center.add(sEvent.getShiftVector());
		
		shiftTimeStart = System.currentTimeMillis();
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
			}
			
			double zProgres = ticksNow / (double)ZOOM_DURATION;						
			zoom = zoomStart + (zoomDest - zoomStart) * zProgres;			
			center.y = zCenterSrc.y + (zCenterDest.y - zCenterSrc.y) * zProgres;
			center.x = zCenterSrc.x + (zCenterDest.x - zCenterSrc.x) * zProgres;
			
			break;

		case SHIFTING:
			
			ticksNow = System.currentTimeMillis() - shiftTimeStart;
			if(ticksNow >= SHIFT_DURATION)
			{
				ticksNow = SHIFT_DURATION;
				state = ProgramState.IDLE;				
			}
			
			double sProgres = ticksNow / (double)SHIFT_DURATION;						
			center.y = sCenterSrc.y + (sCenterDest.y - sCenterSrc.y) * sProgres;
			center.x = sCenterSrc.x + (sCenterDest.x - sCenterSrc.x) * sProgres;
			
			break;
		default:
			break;
		}
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
				Point2F dest = Misc.calculatePixelRealCoordinates(e.getX(), e.getY(), winWidth, winHeight, zoom, center);
				ZoomEvent zEvent = new ZoomEvent(true, dest);
				initZoom(zEvent);				
			}
			else
			{
				lastClick = System.currentTimeMillis();
			}
			break;
		case MouseEvent.BUTTON3: 
			if(System.currentTimeMillis() - lastClickRight < DOUBLECLICKMAXDIFF)
			{			
				Point2F dest = Misc.calculatePixelRealCoordinates(e.getX(), e.getY(), winWidth, winHeight, zoom, center);
				ZoomEvent zEvent = new ZoomEvent(false, dest);
				initZoom(zEvent);				
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
	
	private Point2F centerPressed = null, centerStart = null;
	private boolean centerMoved = false; 
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		centerPressed = Misc.calculatePixelRealCoordinates(e.getPoint().x, e.getPoint().y, winWidth, winHeight, zoom, center);
		centerStart = new Point2F(center);
	}
	
	@Override	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(centerPressed != null && centerMoved == true)
		{
			ShiftEvent shift = new ShiftEvent(new Point2F(center.x - centerStart.x, center.y - centerStart.y));
			//currentAnimation.addEvent(shift);
		}
		centerPressed = null;
		centerMoved = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(centerPressed != null && state == ProgramState.IDLE)
		{		
			centerMoved = true;			
			Point2F curPos = Misc.calculatePixelRealCoordinates(e.getPoint().x, e.getPoint().y, winWidth, winHeight, zoom, center);
			
			double deltaReal = (curPos.x - centerPressed.x) / 1.25;
			double deltaImag = (curPos.y - centerPressed.y) / 1.25;
			
			center.x = centerStart.x - deltaReal;
			center.y = centerStart.y - deltaImag;
		}
	}
	
	@Override	
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_R:			
			startRecording();
			break;
		case KeyEvent.VK_E:
			stopRecording();
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public boolean isEventRecording() {
		return eventRecording;
	}	
	
	private void startRecording()
	{
		System.out.println("Recording started...");
		
		currentAnimation = new Animation();
		eventRecording = true;
	}
	
	private void stopRecording()
	{
		if(!eventRecording)
			return;
		
		eventRecording = false;
		String filename = AnimationSaveLoader.scanDir("animations", "fraktal");
		AnimationSaveLoader.SaveAnimation("animations", filename, currentAnimation);

		System.out.println(String.format("Animation saved (%s) started...", filename));
	}
}
