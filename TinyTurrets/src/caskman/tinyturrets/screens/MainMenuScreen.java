package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.Vector;

public class MainMenuScreen extends GameScreen {
	
	private List<MenuItem> menuItems;
	
	public MainMenuScreen(ScreenManager manager) {
		super(manager,false,new CircleTransition());
		initialize();
	}
	
	private void initialize() {
		menuItems = new ArrayList<MenuItem>();
		MenuItem m;
		
		m = new MenuItem();
		m.text = "Start";
		m.setTextSize(20.0F);
		m.isButton = true;
		m.dims = new Dimension(100,50);
		m.position = new Vector((manager.getScreenDims().width - m.dims.width)/2,2*(manager.getScreenDims().height - m.dims.height)/3);
		m.addMenuItemListener(new MenuItemListener() {
			public void itemActivated() {
				GameScreen[] screens = {new SandboxGameScreen(manager)};
				LoadingScreen.load(manager,screens,false);
			}
		});
		menuItems.add(m);
		
		m = new MenuItem();
		m.text = "Tiny Turrets";
		m.setTextColor(Color.GREEN);
		m.isButton = false;
		m.setTextSize(50.0F);
		Rect r = m.getTextBounds();
		m.dims = new Dimension(r.width(),r.height());
		m.position = new Vector((manager.getScreenDims().width - m.dims.width)/2,(manager.getScreenDims().height - m.dims.height)/4);
		menuItems.add(m);
		
		
	}
	
	
	@Override
	public void updateScreen() {
		
	}

	@Override
	public void drawScreen(Canvas canvas, float interpol) {
		for (MenuItem m : menuItems) {
			m.draw(canvas,interpol);
		}
	}

	@Override
	public void manageScreenInput(MotionEvent e) {
		for (MenuItem m : menuItems) {
			m.manageInput(e);
		}
	}

	
}
