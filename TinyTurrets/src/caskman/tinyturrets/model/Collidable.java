package caskman.blocks.model;

import android.graphics.Rect;


public interface Collidable {
//	public void resolve(Collidable a);
	
	public void setTempNextPosition(float percent);
	
	public Vector getTempPosition();
	
	public Rect getAABB();
	
	public Vector getVelocity();
	
	public Dimension getDims();
}
