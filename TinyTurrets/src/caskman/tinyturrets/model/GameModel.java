package caskman.tinyturrets.model;

import java.util.List;
import java.util.Random;

import caskman.tinyturrets.Global;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class GameModel {

	protected Context context;

	public abstract void draw(Canvas canvas, float interpol);

	public abstract void update();

	public abstract void manageInput(MotionEvent e);

//	public abstract List<Layer> getLayerList();

	protected Dimension screenDims;
	protected Random r;

	public GameModel(Context context,Dimension dims) {
		this.context = context;
		screenDims = dims;
		r = new Random();
	}

	public Context getContext() {
		return context;
	}

	public Dimension getScreenDims() {
		return screenDims;
	}

	public Random getRandom() {
		return r;
	}

	public int getRandNotDarkColor() {
		return Global.getRandNotDarkColor();
//		int red = r.nextInt(255);
//		int green = r.nextInt(255);
//		int blue = r.nextInt(255);
//		int color = (0xff000000)+(red << 16) + (green << 8) + blue;
//		return color;
	}

}