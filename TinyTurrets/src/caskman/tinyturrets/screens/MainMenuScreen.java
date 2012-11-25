package caskman.tinyturrets.screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.Vector;

public class MainMenuScreen extends GameScreen {
	
	private List<MenuItem> menuItems;
	ScreenManager manager;
	
	public MainMenuScreen(ScreenManager manager) {
		this.manager = manager;
		initialize();
	}
	
	private void initialize() {
		menuItems = new ArrayList<MenuItem>();
		MenuItem m;
		
		m = new MenuItem();
		m.text = "Start";
		m.position = new Vector(50,50);
		m.dims = new Dimension(100,20);
		m.addActionListener(new MenuItemListener() {
			public void itemActivated() {
				
			}
		});
		menuItems.add(m);
	}
	
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw(Canvas canvas, float interpol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void manageInput(MotionEvent e) {
		// TODO Auto-generated method stub

	}

}
