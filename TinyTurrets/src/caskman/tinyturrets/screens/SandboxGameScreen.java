package caskman.tinyturrets.screens;

import android.graphics.Canvas;
import android.view.MotionEvent;
import caskman.tinyturrets.model.SandboxGameModel;

public class SandboxGameScreen extends GameScreen {

	SandboxGameModel model;
	
	public SandboxGameScreen(ScreenManager manager) {
		super(manager,true);
		model = new SandboxGameModel(manager.getContext(),manager.getScreenDims());
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
