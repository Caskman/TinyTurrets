package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.Vector;

public class MenuItem {
	
	Vector position;
	Dimension dims;
	public String text;
	private List<MenuItemListener> listeners;
	
	public MenuItem() {
		listeners = new ArrayList<MenuItemListener>();
	}
	
	public void update() {
		
	}
	
	public void draw(Canvas canvas,float interpol) {
		// TODO
	}
	
	public void manageInput(MotionEvent e) {
		// TODO
	}
	
	public void addMenuItemListener(MenuItemListener listener) {
		listeners.add(listener);
	}
	
	
}
