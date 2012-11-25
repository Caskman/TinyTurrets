package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.Vector;

public class MenuItem {
	
	Vector position;
	Dimension dims;
	Paint borderPaint;
	Paint textPaint;
	String text;
	boolean isButton;
	private List<MenuItemListener> listeners;
	
	public MenuItem() {
		listeners = new ArrayList<MenuItemListener>();
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(Color.WHITE);
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
		isButton = false;
	}
	
	public void update() {
		
	}
	
	public void setTextColor(int color) {
		textPaint.setColor(color);
	}
	
	public Rect getTextBounds() {
		Rect r = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), r);
		return r;
	}
	
	public void setTextSize(float size) {
		textPaint.setTextSize(size);
	}
	
	public void draw(Canvas canvas,float interpol) {
		if (isButton) {
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			canvas.drawRect(position.x, position.y, position.x+dims.width, position.y+dims.height, paint);
			canvas.drawRect(position.x, position.y, position.x+dims.width, position.y+dims.height, borderPaint);
		}
		Rect rect = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), rect);
		float startOffsetX = (dims.width - rect.width()) / 2.0F;
		float startOffsetY = dims.height - ((dims.height - rect.height()) / 2.0F);
		canvas.drawText(text,position.x + startOffsetX,position.y + startOffsetY, textPaint);
	}
	
	public void manageInput(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
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
