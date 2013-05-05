package de.otori.engine;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.management.MBeanFeatureInfo;

import com.martiansoftware.jsap.*;

import de.otori.engine.Point2F;
import de.otori.engine.Program;
import de.otori.engine.Renderer;
import de.otori.mandelbrot.Mandelbrot;

public class CommandLineProgram {
	
	public static void cmdMain(String args[])
	{
		JSAPResult config = parseArgs(args);
		
		String outFile = config.getString("OutName");
		
		int iThreads;
		try
		{
			iThreads = config.getInt("Threads");
		}
		catch (Exception e)
		{
			iThreads = (int)(Runtime.getRuntime().availableProcessors() * 1.5);
		}
		
		boolean console = config.getBoolean("Console");
		
		if(console)
		{		
			int x = config.getInt("ResolutionX");
			int y = config.getInt("ResolutionY");
			int iterations = config.getInt("Iterations");
			double centerX = config.getDouble("CenterX");
			double centerY = config.getDouble("CenterY");
			double zoom = config.getDouble("Zoom");
			
			runProgram(x, y, iThreads, iterations, new Point2F(centerX, centerY), zoom, outFile);
			
			System.exit(0);			
		}
		else
		{
			System.out.println("You gave some arguments to this Program.");
			System.out.println("If You like to run it on the console, please enable the [-c/--console] option.");
			System.out.println("[-h/--help] for usage.");
			
			Program.main(new String[0]);
		}
	}
	
	private static void runProgram(int x, int y, int iThreads, int iterations, Point2F center, double zoom, String outName)
	{
		Mandelbrot.MBFraktal.setIteration(iterations);
		
		BufferedImage biImage = new BufferedImage(x, y, BufferedImage.TYPE_3BYTE_BGR);
		Renderer renderer = new Renderer(Mandelbrot.MBFraktal, biImage, iThreads, new Dimension(160, 160));
		Mandelbrot.MBFraktal.setZoom(zoom);
		Mandelbrot.MBFraktal.setCenter(center);
		renderer.renderImage();
		File outFile = new File(outName); 
		try
		{
			ImageIO.write(biImage, "png", outFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Error saving Image: ");
			System.out.println(e.getMessage());
		}
	}
	
	private static JSAPResult parseArgs(String args[])
	{
		JSAP jsap = new JSAP();
	
		QualifiedSwitch swHelp = (QualifiedSwitch)
								new QualifiedSwitch("Help")
								.setShortFlag('h')
								.setLongFlag("help");
						
		QualifiedSwitch swConsole = (QualifiedSwitch)
								new QualifiedSwitch("Console")
								.setShortFlag('c')
								.setLongFlag("console")
								.setHelp("Enables Console Mode");

		
		FlaggedOption optResX = new FlaggedOption("ResolutionX")
								.setStringParser(JSAP.INTEGER_PARSER)
								.setShortFlag('x')
								.setLongFlag("resolution-x")
								.setDefault("800")
								.setUsageName("Image width")
								.setRequired(true);
	
		FlaggedOption optResY = new FlaggedOption("ResolutionY")
								.setStringParser(JSAP.INTEGER_PARSER)
								.setShortFlag('y')
								.setLongFlag("resolution-y")
								.setDefault("600")
								.setUsageName("Image height")
								.setRequired(true);
		
		FlaggedOption optCenterX = new FlaggedOption("CenterX")
								.setStringParser(JSAP.DOUBLE_PARSER)
								.setShortFlag(JSAP.NO_SHORTFLAG)
								.setLongFlag("center-x")
								.setDefault("-.5")
								.setUsageName("Real part of Complex Number to zoom.")
								.setRequired(true);

		FlaggedOption optCenterY = new FlaggedOption("CenterY")
								.setStringParser(JSAP.DOUBLE_PARSER)
								.setShortFlag(JSAP.NO_SHORTFLAG)
								.setLongFlag("center-y")
								.setDefault("0.0")
								.setUsageName("Imag part of Complex Number to zoom.")
								.setRequired(true);
		
		FlaggedOption optZoom = new FlaggedOption("Zoom")
								.setStringParser(JSAP.DOUBLE_PARSER)
								.setShortFlag('z')
								.setLongFlag("zoom")
								.setDefault("1")
								.setUsageName("factor to zoom.")
								.setRequired(true);
		
		FlaggedOption optThreads = new FlaggedOption("Threads")
								.setRequired(false)
								.setStringParser(JSAP.STRING_PARSER)
								.setShortFlag('t')
								.setLongFlag("threads")
								.setUsageName("Amount of threads used. [Default: Cores * 1.5]")
								.setRequired(false);
		
		FlaggedOption optIter = new FlaggedOption("Iterations")
								.setStringParser(JSAP.INTEGER_PARSER)
								.setShortFlag('i')
								.setLongFlag("iterations")
								.setDefault("200")
								.setUsageName("Iterations to check for mandelbrot")
								.setRequired(true);
		
		UnflaggedOption optOutName = new UnflaggedOption("OutName")
								.setRequired(true)
								.setStringParser(JSAP.STRING_PARSER)
								.setDefault("fraktal_out.png")
								.setUsageName("Output image file.");
		
		try
		{
			jsap.registerParameter(swHelp);
			jsap.registerParameter(swConsole);
			jsap.registerParameter(optResX);
			jsap.registerParameter(optResY);
			jsap.registerParameter(optThreads);
			jsap.registerParameter(optIter);
			jsap.registerParameter(optCenterX);
			jsap.registerParameter(optCenterY);
			jsap.registerParameter(optZoom);
			jsap.registerParameter(optOutName);
		}
		catch (JSAPException e)
		{
			e.printStackTrace();
			
			System.out.println("Internal Program Error: ");
			System.out.println(e.getMessage());			
		}
								
		JSAPResult config = jsap.parse(args);
		
		if(!config.success())
		{
            System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help.  This is called "beating the user with a clue stick."
            for (java.util.Iterator errs = config.getErrorMessageIterator();
                    errs.hasNext();) {
                System.err.println("Error: " + errs.next());
            }
            
            System.err.println();
            System.err.println("Usage: ");
                                
            System.err.println("                "
                                + jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.exit(1);
        }
		
		if(config.getBoolean("Help"))
		{
			System.out.println("Usage: ");
            
            System.out.println("                "
                                + jsap.getUsage());
            System.out.println();
            System.out.println(jsap.getHelp());
            System.exit(0);
		}
		
		return config;
	}
	
}
