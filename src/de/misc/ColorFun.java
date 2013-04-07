package de.misc;

import java.awt.Color;

public class ColorFun {

	/**
	 * @param args
	 */

	public static Color farbVerlauf(Color cStart, Color cEnd, int iterMax, int iterCur)
	{
		double dR = ((cEnd.getRed() - cStart.getRed()) / (double)iterMax);
		double dG = ((cEnd.getGreen() - cStart.getGreen()) / (double)iterMax);
		double dB = ((cEnd.getBlue() - cStart.getBlue()) / (double)iterMax);
		
		return new Color((int)(cStart.getRed() + dR * iterCur), (int)(cStart.getGreen() + dG * iterCur), (int)(cStart.getBlue() + dB * iterCur));
	}

	public static Color farbVerlaufHSV(Color cStart, Color cEnd, int iterMax, int iterCur)
	{
		float[] hsbStart = new float[3];
		float[] hsbEnd = new float[3];
		
		Color.RGBtoHSB(cStart.getRed(), cStart.getGreen(), cStart.getBlue(), hsbStart);
		Color.RGBtoHSB(cEnd.getRed(), cEnd.getGreen(), cEnd.getBlue(), hsbEnd);
				
		double dH = ((hsbEnd[0] - hsbStart[0]) / (double)iterMax);
		double dS = ((hsbEnd[1] - hsbStart[1]) / (double)iterMax);
		double dB = ((hsbEnd[2] - hsbStart[2]) / (double)iterMax);
		
		return Color.getHSBColor((float)(hsbStart[0] + dH * iterCur), (float)(hsbStart[1] + dS * iterCur), (float)(hsbStart[2] + dB * iterCur));		
	}
	
	public static Color farbVerlauf(Color cStart, Color cEnd, double percentage)
	{
		return farbVerlauf(cStart, cEnd, 100, (int)(percentage * 100));		
	}
	
}
