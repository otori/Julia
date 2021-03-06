package de.otori.engine.animation;

import de.otori.engine.Point2F;

public class ShiftEvent implements AnimationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2561702320065665334L;
	private Point2F shiftVector;
	
	public ShiftEvent (Point2F shiftVector)
	{
		this.shiftVector = shiftVector;
	}

	public Point2F getShiftVector() {
		return shiftVector;
	}

	public void setShiftVector(Point2F shiftVector) {
		this.shiftVector = shiftVector;
	}
		
}
