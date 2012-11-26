package caskman.tinyturrets.screens;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import caskman.tinyturrets.model.BackgroundModel;
import caskman.tinyturrets.model.GameModel;

public class BackgroundScreen extends GameScreen {

	GameModel model;
	
	public BackgroundScreen(ScreenManager manager) {
		super(manager,true,null);
		model = new BackgroundModel(manager.getContext(),manager.getScreenDims());
	}
	
	@Override
	public void updateScreen() {
		model.update();
	}

	@Override
	public void drawScreen(Canvas canvas, float interpol) {
		model.draw(canvas, interpol);
	}

	@Override
	public void manageScreenInput(MotionEvent e) {
		model.manageInput(e);
	}
	

}
