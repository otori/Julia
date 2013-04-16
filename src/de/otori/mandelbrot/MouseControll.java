package de.otori.mandelbrot;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseControll extends MouseAdapter {
	
	private Program prog;
	
	public MouseControll(Program p)
	{
		prog = p;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
	}
	
}
