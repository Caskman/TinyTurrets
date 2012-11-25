package caskman.tinyturrets.screens;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class GameScreen {
	
	public ScreenState state;
	
	public abstract void update();
	
	public abstract void draw(Canvas canvas,float interpol);
	
	public abstract void manageInput(MotionEvent e);
	
}
