package de.otori.engine;

public final class Misc {
	
	/**
	 * Calculates the pixels coordinates of a image with given zoom and center parameters 
	 * @param x Pixel X
	 * @param y Pixel Y
	 * @param winWidth Window Width
	 * @param winHeight Window Height
	 * @param zoom Zoom Level
	 * @param xCenter Centered Point Coordinate X
	 * @param yCenter Centered Point Coordinate Y
	 * @return real Image Coordinate of (x,y)
	 */
	public static Point2F calculatePixelRealCoordinates(final int x, final int y, final int winWidth, final int winHeight, final double zoom, final Point2F center)
	{		
		return new Point2F(center.x + (x / (double)winWidth - 0.5)*(3/zoom) , center.y + (y / (double)winHeight - 0.5)*(2/zoom));
	}
	
}
