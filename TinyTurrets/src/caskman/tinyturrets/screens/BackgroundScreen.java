package caskman.tinyturrets.screens;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class BackgroundScreen extends GameScreen {

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Canvas canvas, float interpol) {
		canvas.drawColor(Color.BLACK);
	}

	@Override
	public void manageInput(MotionEvent e) {
		
	}

}