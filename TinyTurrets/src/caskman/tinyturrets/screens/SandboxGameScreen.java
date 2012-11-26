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
	protected void updateScreen() {
		model.update();
	}

	@Override
	protected void drawScreen(Canvas canvas, float interpol) {
		model.draw(canvas,interpol);
	}

	@Override
	protected void manageScreenInput(MotionEvent e) {
		model.manageInput(e);
	}
	

}
