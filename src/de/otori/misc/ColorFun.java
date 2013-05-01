package de.otori.misc;

import java.awt.Color;

public class ColorFun {

	/**
	 * @param args
	 */

	public static Color farbVerlauf(final Color cStart, final Color cEnd,
			final int iterMax, final int iterCur) {
		final double dR = ((cEnd.getRed() - cStart.getRed()) / (double) iterMax);
		final double dG = ((cEnd.getGreen() - cStart.getGreen()) / (double) iterMax);
		final double dB = ((cEnd.getBlue() - cStart.getBlue()) / (double) iterMax);

		return new Color((int) (cStart.getRed() + dR * iterCur),
				(int) (cStart.getGreen() + dG * iterCur),
				(int) (cStart.getBlue() + dB * iterCur));
	}

	public static Color farbVerlaufHSV(final Color cStart, final Color cEnd,
			final int iterMax, final int iterCur) {
		final float[] hsbStart = new float[3];
		final float[] hsbEnd = new float[3];

		Color.RGBtoHSB(cStart.getRed(), cStart.getGreen(), cStart.getBlue(),
				hsbStart);
		Color.RGBtoHSB(cEnd.getRed(), cEnd.getGreen(), cEnd.getBlue(), hsbEnd);

		final double dH = ((hsbEnd[0] - hsbStart[0]) / (double) iterMax);
		final double dS = ((hsbEnd[1] - hsbStart[1]) / (double) iterMax);
		final double dB = ((hsbEnd[2] - hsbStart[2]) / (double) iterMax);

		return Color.getHSBColor((float) (hsbStart[0] + dH * iterCur),
				(float) (hsbStart[1] + dS * iterCur), (float) (hsbStart[2] + dB
						* iterCur));
	}

	public static Color farbVerlauf(final Color cStart, final Color cEnd,
			final double percentage) {
		return farbVerlauf(cStart, cEnd, 100, (int) (percentage * 100));
	}

}
