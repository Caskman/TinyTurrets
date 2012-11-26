package caskman.tinyturrets.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.view.MotionEvent;

public abstract class GameScreen {
	
	public ScreenState state;
	public ScreenManager manager;
	private boolean isFullscreen;
	private Transition transition;
	private boolean isTransitioning;
	
	public GameScreen(ScreenManager manager,boolean isFullscreen,Transition t) {
		this.manager = manager;
		this.state = ScreenState.VISIBLE;
		this.isFullscreen = isFullscreen;
		transition = t;
		if (t != null)
			transition.setGameScreen(this);
		isTransitioning = false;
	}
	
	public boolean isFullscreen() {
		return isFullscreen;
	}
	
	public void exitScreen() {
		if (transition != null) {
			Bitmap map = Bitmap.createBitmap(manager.getScreenDims().width, manager.getScreenDims().height, Config.ARGB_8888);
			Canvas canvas = new Canvas(map);
			drawScreen(canvas,0.0F);
			transition.start(map);
			isTransitioning = true;
		} else {
			manager.removeScreen(this);
		}
	}
	
	public void update() {
		if (isTransitioning) {
			if (transition.isDone()) {
				manager.removeScreen(this);
				return;
			}
			transition.updateTransition();
		} else {
			updateScreen();
		}
	}
	
	protected abstract void updateScreen();
	
	public void draw(Canvas canvas,float interpol) {
		if (isTransitioning) {
			transition.draw(canvas,interpol);
		} else {
			drawScreen(canvas,interpol);
		}
	}
	
	protected abstract void drawScreen(Canvas canvas,float interpol);
	
	public void manageInput(MotionEvent e) {
		if (!isTransitioning) {
			manageScreenInput(e);
		}
	}
	
	protected abstract void manageScreenInput(MotionEvent e);
	
}
