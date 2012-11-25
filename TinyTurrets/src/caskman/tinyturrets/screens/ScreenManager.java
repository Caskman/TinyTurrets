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
	
	private void initialize() {
		screens = new ArrayList<GameScreen>();
		screens.add(new BackgroundScreen());
		screens.add(new MainMenuScreen());
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
	
}
