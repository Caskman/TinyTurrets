package caskman.tinyturrets.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Transition {
	
	protected GameScreen screen;
	protected int duration;
	protected boolean isDone;
	
	public Transition() {
		duration = 0;
		isDone = false;
	}
	
	public void setGameScreen(GameScreen g) {
		screen = g;
	}
	
	public abstract void start(Bitmap frozenScreen);
	
	public abstract void draw(Canvas canvas,float interpol);
	
	public void update() {
		duration++;
		updateTransition();
	}
	
	protected abstract void updateTransition();
	
	public boolean isDone() {
		return isDone;
	}
	
}
