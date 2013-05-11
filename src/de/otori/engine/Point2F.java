package de.otori.engine;

public class Point2F {
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
