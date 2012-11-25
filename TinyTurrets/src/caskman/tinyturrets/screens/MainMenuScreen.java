package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.Vector;

public class MainMenuScreen extends GameScreen {
	
	private List<MenuItem> menuItems;
	
	public MainMenuScreen(ScreenManager manager) {
		super(manager,ScreenState.PartiallyCovering);
		initialize();
	}
	
	private void initialize() {
		menuItems = new ArrayList<MenuItem>();
		MenuItem m;
		
		m = new MenuItem();
		m.text = "Start";
		m.position = new Vector(50,50);
		m.dims = new Dimension(100,20);
		m.addMenuItemListener(new MenuItemListener() {
			public void itemActivated() {
				GameScreen[] screens = {new TinyTurretsScreen(manager)};
				LoadingScreen.load(manager,screens,false);
			}
		});
		menuItems.add(m);
	}
	
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw(Canvas canvas, float interpol) {
		Vector zero = new Vector();
		for (MenuItem m : menuItems) {
			m.draw(canvas,interpol);
		}
	}

	@Override
	public void manageInput(MotionEvent e) {
		for (MenuItem m : menuItems) {
			m.manageInput(e);
		}
	}

}
