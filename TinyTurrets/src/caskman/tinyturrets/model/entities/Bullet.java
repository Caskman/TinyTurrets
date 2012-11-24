package caskman.tinyturrets.model.entities;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.FloatMath;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.GameContext;
import caskman.tinyturrets.model.GameModel;
import caskman.tinyturrets.model.Mob;

public class Bullet extends Mob {

	private Paint paint; 
	private final static float speed = 8.0F;
	private float xDistCovered;
	private float xDistTarget;
	private Turret target;
	
	
	public Bullet(GameModel model,Turret shooter,Turret vic,int color) {
		super(model);
		
		paint = new Paint();
		paint.setColor(color);
		dims = new Dimension(5,5);
		target = vic;
		
		PointF shooterP = new PointF(shooter.getX(),shooter.getY());
		PointF vicP = new PointF(vic.getX(),vic.getY());
		
		position = shooterP;
		
		PointF vector = new PointF(vicP.x - shooterP.x,vicP.y - shooterP.y);
//		float mag = FloatMath.sqrt(vector.x*vector.x + vector.y*vector.y);
//		vector = new PointF(vector.x/mag,vector.y/mag);
		Float angle = (float) Math.atan(vector.y/vector.x);
		angle = (float) ((vector.x < 0)?angle + Math.PI:angle);
		velocity = new PointF(FloatMath.cos(angle)*speed,FloatMath.sin(angle)*speed);
		
		xDistCovered = 0;
		xDistTarget = abs(vicP.x - shooterP.x);
	}

	@Override
	public void draw(Canvas canvas, float interpol) {
		int radius = dims.width>>1; // divided by 2
		float x = (position.x + velocity.x*interpol);
		float y = (position.y + velocity.y * interpol);
		canvas.drawCircle(x,y , radius, paint);
	}

	@Override
	public void update(GameContext g) {
		xDistCovered += abs(velocity.x);
		if (xDistCovered >= xDistTarget) {
			target.hit();
			g.removals.add(this);
			return;
		}
		
		position.x += velocity.x;
		position.y += velocity.y;
	}

}
