package de.otori.engine;

import java.io.Serializable;

public class Point2F implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 52958166169832913L;
	
	public double x;
	public double y;
	
	public Point2F(double x, double y)
	{
		this.x = x;
		this.y = y;					
	}
	
	public Point2F(Point2F p)
	{
		x = p.x;
		y = p.y;
	}
	
	public Point2F add(Point2F p)
	{
		return new Point2F(x + p.x, y + p.y);
	}
}
