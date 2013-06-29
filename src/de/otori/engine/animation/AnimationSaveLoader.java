package de.otori.engine.animation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public class AnimationSaveLoader {

	public static String scanDir(String dir, String filePrefix)
	{
		
		File folder = new File(dir);	
		if(!folder.isDirectory()) return "";
		String[] files = folder.list();
		
		String regexPattern = filePrefix + "[1-9][0-9]*\\.anim";
					
		LinkedList<Integer> foundFiles = new LinkedList<Integer>();
		
		for(String file : files)
		{
			if(file.matches(regexPattern))
			{
				foundFiles.add(Integer.parseInt(file.substring(filePrefix.length(), file.lastIndexOf('.'))));
			}
		}
	
		Iterator<Integer> iter = foundFiles.iterator();		
		int i = 1;
		boolean found = false;
		for(;;)
		{
			while(iter.hasNext())
			{
				if(iter.next().equals(new Integer(i)))
				{
					found = true;
					break;
				}			
			}
			if (found)
			{
				++i;
				iter = foundFiles.iterator();
				found = false;
				continue;
			}
			else
			{
				return filePrefix + i + ".anim";
			}
		}
		
	}
	
	public static void SaveAnimation(String dir, String filename, Animation anim)
	{
		if(filename.equals("")) return;
		
		String filenameFull = dir.endsWith("/") ? dir + filename : dir + "/" + filename;
		
		try {
			FileOutputStream fStream = new FileOutputStream(filenameFull);
			ObjectOutputStream objWriter = new ObjectOutputStream(fStream);
			
			objWriter.writeObject(anim);			
			
			objWriter.close();
			fStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Animation LoadAnimation(String dir, String filename)
	{
		if(filename.equals("")) return null;
		
		String filenameFull =  dir.endsWith("/") ? dir + filename : dir + "/" + filename;
		
		Animation ret = null;
		
		try {
			FileInputStream fStream = new FileInputStream(filenameFull);
			ObjectInputStream objWriter = new ObjectInputStream(fStream);
			
			Object readObj = objWriter.readObject();			
			if (readObj instanceof Animation) ret = (Animation) readObj;
			
			objWriter.close();
			fStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
}
