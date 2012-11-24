package caskman.tinyturrets.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

public abstract class Mob {
	
	protected Vector position; 
	protected Bitmap bitmap;
	protected Dimension dims;
	protected Vector velocity;
	protected GameModel model;
	
	public Mob(GameModel model) {
		this.model = model;
		position = null;
		bitmap = null;
		dims = null;
		velocity = null;
	}
	
	protected Context getContext() {
		return model.getContext();
	}
	
	protected Dimension getScreenDims() {
		return model.getScreenDims();
	}
	
	public int getWidth() {
		return dims.width;
	}
	
	public int getHeight() {
		return dims.height;
	}
	
	public int getX() {
		return (int)position.x;
	}
	
	public int getY() {
		return (int)position.y;
	}
	
	public float getXVel() {
		return velocity.x;
	}
	
	public float getYVel() {
		return velocity.y;
	}
	
	protected float abs(float f) {
		return (f < 0)?-1*f:f;
	}
	
	public abstract void draw(Canvas canvas, float interpol,Vector offset);
	
	public abstract void update(GameContext gContext);
}
