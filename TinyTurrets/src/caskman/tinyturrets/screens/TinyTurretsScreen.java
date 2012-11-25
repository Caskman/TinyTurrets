package caskman.tinyturrets.screens;

import android.graphics.Canvas;
import android.view.MotionEvent;
import caskman.tinyturrets.model.GameModel;

public class TinyTurretsScreen extends GameScreen {

	GameModel model;
	
	public TinyTurretsScreen(ScreenManager manager) {
		super(manager,ScreenState.Visible);
		model = new GameModel(manager.getContext(),manager.getScreenDims());
	}

	@Override
	public void update() {
		model.update();
	}

	@Override
	public void draw(Canvas canvas, float interpol) {
		model.draw(canvas,interpol);
	}

	@Override
	public void manageInput(MotionEvent e) {
		model.manageInput(e);
	}

}
