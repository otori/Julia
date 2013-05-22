package de.otori.mandelbrot.mandelbrotEvents;

import de.otori.engine.animation.AnimationEvent;
import de.otori.mandelbrot.Mandelbrot.ColorMode;

public class ColorModeChanged implements AnimationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5667698916402618116L;
	private ColorMode cMode;
	
	public ColorModeChanged(ColorMode cMode)
	{
		this.cMode = cMode;
	}

	public ColorMode getcMode() {
		return cMode;
	}

	public void setcMode(ColorMode cMode) {
		this.cMode = cMode;
	}	
	
}
