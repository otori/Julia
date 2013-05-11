package de.otori.mandelbrot.mandelbrotEvents;

import de.otori.engine.animation.AnimationEvent;

public class IterationChanged implements AnimationEvent {
	
	private int changeVal;	
	
	public IterationChanged(int changeVal)
	{
		this.changeVal = changeVal;
	}

	public int getChangeVal() {
		return changeVal;
	}

	public void setChangeVal(int changeVal) {
		this.changeVal = changeVal;
	}
	
}
