package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.Vector;

public class MenuItem {
	
	Vector position;
	Dimension dims;
	Paint borderPaint;
	public String text;
	private List<MenuItemListener> listeners;
	
	public MenuItem() {
		listeners = new ArrayList<MenuItemListener>();
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(Color.WHITE);
	}
	
	public void update() {
		
	}
	
	public void draw(Canvas canvas,float interpol) {
		canvas.drawRect(position.x, position.y, position.x+dims.width, position.y+dims.height, borderPaint);
		canvas.drawText(text, position.x, position.y, borderPaint);
	}
	
	public void manageInput(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
//			Vector point = new Vector(e.getX(),e.getY());
			if (e.getX() > position.x && e.getX() < (position.x+dims.width) && e.getY() > position.y && e.getY() < (position.y + dims.height)) {
				notifyListeners();
			}
		}
	}
	
	public void addMenuItemListener(MenuItemListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners() {
		for (MenuItemListener l : listeners) {
			l.itemActivated();
		}
	}
}
