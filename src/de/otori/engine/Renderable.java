package de.otori.engine;

import java.awt.Color;

public interface Renderable {
	
	/**
	 * Implement a function to calculate the Color of Position (x,y)
	 * Please only implement Thread-safe functions!
	 * 
	 * @param x x-Coordinate of image
	 * @param y y-Coordinate of image
	 * @return Color of the image point (x|y)
	 */
	public Color calcPixel(double x, double y, double cenX, double cenY, double zoom); // Make it Renderable 

	/**
	 * Function is called once, before frame is rendered.
	 */
	public void preRendering();
	
	/**
	 * Function is called once, after frame is rendered.
	 */
	public void postRendering();

	
}
