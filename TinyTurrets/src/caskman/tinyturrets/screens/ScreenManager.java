package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;

public class ScreenManager {
	
	private Context context;
	private Dimension screenDims;
	private List<GameScreen> screens;
	
	public ScreenManager(Context context,Dimension screenDims) {
		this.context = context;
		this.screenDims = screenDims;
		
		initialize();
	}
	
	public Context getContext() {
		return context;
	}
	
	public Dimension getScreenDims() {
		return screenDims;
	}
	
	private void initialize() {
		screens = new ArrayList<GameScreen>();
	}
	
	public void update() {
		for (GameScreen s : screens) {
			s.update();
		}
	}
	
	public void draw(Canvas canvas, float interpol) {
		for (GameScreen s : screens) {
			if (s.state != ScreenState.Hidden)
				s.draw(canvas,interpol);
		}
	}
	
	public void manageInput(MotionEvent e) {
		screens.get(screens.size()-1).manageInput(e);
	}
	
	public void addScreen(GameScreen g) {
		screens.add(g);
	}
	
	public void removeScreen(GameScreen g) {
		screens.remove(g);
	}
	
	public void exitAllScreens() {
		for (int i = 0; i < screens.size(); i++) {
			screens.set(i, null);
		}
		screens = new ArrayList<GameScreen>();
	}
}
