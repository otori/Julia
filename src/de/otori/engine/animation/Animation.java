package de.otori.engine.animation;

import java.io.Serializable;
import java.util.LinkedList;

public class Animation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6899569101564482711L;
	private LinkedList<AnimationEvent> events;
	
	public Animation()
	{
		events = new LinkedList<AnimationEvent>();
	}
	
	public void addEvent(AnimationEvent event)
	{
		events.add(event);
	}
	
}
