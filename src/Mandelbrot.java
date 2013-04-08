public class Mandelbrot {

	/**
	 * @param args
	 */
	
	public static final int MAX_ITER = 40;
	
	public static int isInMandel(final ComplexNumber cn)
	{
		int iteration = 1;
		
		ComplexNumber mandelFolge = new ComplexNumber(cn);
		
		while(iteration < MAX_ITER)
		{
			mandelFolge.quad();
			mandelFolge.add(cn);
			
			iteration++;
			
			if(Math.abs(mandelFolge.getReal()) > 2 || Math.abs(mandelFolge.getImag()) > 2)
				break;
		}
		
		return iteration;
	}
	
	public static ComplexNumber cnFromPixel(final int x, final int y, final int winWidth, final int winHeight)
	{
		return new ComplexNumber((x * 3.0 / (double)winWidth) - 2, (y * 2.0 / (double)winHeight) - 1);
	}
	
}
