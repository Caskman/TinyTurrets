package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;

public class ScreenManager {
	
	private Context context;
	private Dimension screenDims;
	private List<GameScreen> screens;
	private BlockingQueue<MotionEvent> inputQueue;
	
	public ScreenManager(Context context,Dimension screenDims) {
		this.context = context;
		this.screenDims = screenDims;
		inputQueue = new ArrayBlockingQueue<MotionEvent>(10);
		
		screens = new ArrayList<GameScreen>();
	}
	
	public Context getContext() {
		return context;
	}
	
	public Dimension getScreenDims() {
		return screenDims;
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
		
		for (GameScreen s : screens) {
			s.update();
		}
	}
	
	public void draw(Canvas canvas, float interpol) {
		for (GameScreen s : screens) {
			if (s.state != ScreenState.HIDDEN)
				s.draw(canvas,interpol);
		}
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
		screens.remove(g);
		if (!screens.isEmpty())
			screens.get(screens.size()-1).state = ScreenState.VISIBLE;
	}
	
	public void exitAllScreens() {
		for (int i = 0; i < screens.size(); i++) {
			screens.set(i, null);
		}
		screens = new ArrayList<GameScreen>();
	}
}
