package caskman.tinyturrets.screens;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class GameScreen {
	
	public ScreenState state;
	public ScreenManager manager;
	private boolean isFullscreen;
	
	public GameScreen(ScreenManager manager,boolean isFullscreen) {
		this.manager = manager;
		this.state = ScreenState.VISIBLE;
		this.isFullscreen = isFullscreen;
	}
	
	public boolean isFullscreen() {
		return isFullscreen;
	}
	
	public abstract void update();
	
	public abstract void draw(Canvas canvas,float interpol);
	
	public abstract void manageInput(MotionEvent e);
	
}
