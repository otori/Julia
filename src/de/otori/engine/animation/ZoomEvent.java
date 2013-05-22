package de.otori.engine.animation;

import de.otori.engine.Point2F;

public class ZoomEvent implements AnimationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3411594764075395416L;
	private boolean zoomIn;
	private Point2F dest;
	
	public ZoomEvent(boolean zoomIn, Point2F dest)
	{
		this.zoomIn = zoomIn; 
		this.dest = dest;
	}

	public Point2F getDest() {
		return dest;
	}

	public void setDest(Point2F dest) {
		this.dest = dest;
	}

	public boolean isZoomIn() {
		return zoomIn;
	}

	public void setZoomIn(boolean zoomIn) {
		this.zoomIn = zoomIn;
	}
	
	
}
