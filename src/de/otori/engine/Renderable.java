package de.otori.engine;

import java.awt.Color;

import de.otori.engine.animation.AnimationEvent;

public interface Renderable {
	
	/**
	 * Implement a function to calculate the Color of Position (x,y)
	 * Please only implement Thread-safe functions!
	 * 
	 * @param coordinate Position (x,y)
	 * @return Color of the image point (x|y)
	 */
	public Color calcPixel(Point2F coordinate); // Make it Renderable 

	/**
	 * Function is called once, before frame is rendered.
	 */
	public void preRendering();
	
	/**
	 * Function is called once, after frame is rendered.
	 */
	public void postRendering();

	/**
	 * implement to handle animation events
	 * @param event specific event
	 */
	public void handleEvent(AnimationEvent event);
	
}
