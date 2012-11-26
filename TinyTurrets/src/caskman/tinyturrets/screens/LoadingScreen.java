package caskman.tinyturrets.screens;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class LoadingScreen extends GameScreen {

	GameScreen[] screens;
	boolean isSlowLoad;
	ScreenManager manager;
	
	public static void load(ScreenManager manager,GameScreen[] screensToLoad,boolean isSlowLoad) {
		manager.exitAllScreens();
		
		LoadingScreen loading = new LoadingScreen(manager,isSlowLoad,screensToLoad);
		manager.addScreen(loading);
	}
	
	private LoadingScreen(ScreenManager manager,boolean isSlowLoad,GameScreen[] screensToLoad) {
		super(manager,isSlowLoad,null);
		screens = screensToLoad;
		this.isSlowLoad = isSlowLoad;
		this.manager = manager;
	}

	@Override
	protected void updateScreen() {
		if (manager.getNumScreens() == 1) {
			manager.removeScreen(this);
			for (GameScreen g : screens) {
				manager.addScreen(g);
			}
		}
	}

	@Override
	protected void drawScreen(Canvas canvas, float interpol) {
		if (isSlowLoad) {
			//TODO
		} else {
//			canvas.drawColor(Color.BLACK);
		}
	}

	@Override
	protected void manageScreenInput(MotionEvent e) {

	}

}
