package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;

public class ScreenManager {
	
	private Context context;
	private Dimension screenDims;
	private List<GameScreen> screens;
	private BlockingQueue<MotionEvent> inputQueue;
	private Bitmap lastDraw;
	
	public ScreenManager(Context context,Dimension screenDims) {
		this.context = context;
		this.screenDims = screenDims;
		inputQueue = new ArrayBlockingQueue<MotionEvent>(10);
		lastDraw = Bitmap.createBitmap(screenDims.width, screenDims.height, Config.ARGB_8888);
		screens = new ArrayList<GameScreen>();
	}
	
	public Context getContext() {
		return context;
	}
	
	public Dimension getScreenDims() {
		return screenDims;
	}
	
	public Bitmap getLastDraw() {
		return lastDraw;
	}
	
	public void update() {
//		GameScreen g = screens.get(screens.size()-1);
		List<GameScreen> screensToInput =  new ArrayList<GameScreen>();
		for (int i = screens.size() - 1; i >= 0; i--) {
			if (screens.get(i).state != ScreenState.HIDDEN) {
				screensToInput.add(screens.get(i));
			}
		}
		
		while (!inputQueue.isEmpty()) {
			MotionEvent e = inputQueue.poll();
			for (GameScreen s : screensToInput) {
				s.manageInput(e);
			}
//			g.manageInput(e);
		}
		
		GameScreen[] screensToUpdate = new GameScreen[screens.size()];
		screens.toArray(screensToUpdate);
		for (GameScreen s : screensToUpdate) {
			s.update();
		}
	}
	
	public void draw(Canvas canvas, float interpol) {
		lastDraw = Bitmap.createBitmap(screenDims.width, screenDims.height, Config.ARGB_8888);
		Canvas lastDrawCanvas = new Canvas(lastDraw);
		for (GameScreen s : screens) {
			if (s.state != ScreenState.HIDDEN)
				s.draw(lastDrawCanvas,interpol);
		}
		canvas.drawBitmap(lastDraw, 0F, 0F, null);
	}
	
	public void manageInput(MotionEvent e) {
		inputQueue.offer(e);
//		screens.get(screens.size()-1).manageInput(e);
	}
	
	public void addScreen(GameScreen g) {
		if (!screens.isEmpty()) {
			GameScreen s = screens.get(screens.size()-1);
			if (g.isFullscreen())
				s.state = ScreenState.HIDDEN;
			else 
				s.state = ScreenState.PARTIALLYCOVERED;
		}
		screens.add(g);
	}
	
	public void removeScreen(GameScreen g) {
		int index = screens.indexOf(g);
		screens.remove(index);
		if (!screens.isEmpty()) {
			if (index == screens.size())
				screens.get(screens.size()-1).state = ScreenState.VISIBLE;
		}
	}
	
	public void exitAllScreens() {
		GameScreen[] screensToExit = new GameScreen[screens.size()];
		screens.toArray(screensToExit);
		for (GameScreen g : screensToExit) {
			g.exitScreen();
		}
	}
	public int getNumScreens() {
		return screens.size();
	}
	
	
}
