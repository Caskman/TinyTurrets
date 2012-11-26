package caskman.tinyturrets.screens;

import android.graphics.Canvas;
import android.view.MotionEvent;
import caskman.tinyturrets.model.SandboxGameModel;

public class SandboxGameScreen extends GameScreen {

	SandboxGameModel model;
	
	public SandboxGameScreen(ScreenManager manager) {
		super(manager,true,null);
		model = new SandboxGameModel(manager.getContext(),manager.getScreenDims());
	}

	@Override
	public void updateScreen() {
		model.update();
	}

	@Override
	public void drawScreen(Canvas canvas, float interpol) {
		model.draw(canvas,interpol);
	}

	@Override
	public void manageScreenInput(MotionEvent e) {
		model.manageInput(e);
	}
	

}
