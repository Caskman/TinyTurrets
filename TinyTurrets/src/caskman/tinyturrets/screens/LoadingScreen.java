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
		super(manager,true);
		screens = screensToLoad;
		this.isSlowLoad = isSlowLoad;
		this.manager = manager;
	}

	@Override
	public void update() {
		if (isSlowLoad) {
			//TODO
		} else {
			manager.removeScreen(this);
			for (GameScreen g : screens) {
				manager.addScreen(g);
			}
		}
	}

	@Override
	public void draw(Canvas canvas, float interpol) {
		if (isSlowLoad) {
			//TODO
		} else {
			canvas.drawColor(Color.BLACK);
		}
	}

	@Override
	public void manageInput(MotionEvent e) {

	}

}
