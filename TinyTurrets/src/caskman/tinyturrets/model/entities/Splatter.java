package caskman.tinyturrets.model.entities;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import caskman.tinyturrets.model.GameContext;
import caskman.tinyturrets.model.GameModel;
import caskman.tinyturrets.model.Mob;
import caskman.tinyturrets.model.Vector;

public class Splatter extends Mob {
	
	static int SPLATTER_START_OPACITY = 0x3f;
	static int NUMBER_PARTICLES = 5;
	static float MAX_PARTICLE_VELOCITY = .5F;
	static float SPLATTER_BLOTCH_SIZE = 3.0F;
	static int MAX_DURATION = 50;
	static float PERCENT_VISIBLE = .85F;
	static float STROKE_WIDTH = 3.0F;
	
	static int VISIBLE_DURATION = (int) (MAX_DURATION*PERCENT_VISIBLE);
	static int ALPHA_DELTA = ((int)((SPLATTER_START_OPACITY/MAX_DURATION)/PERCENT_VISIBLE)) << 24;
	
	Paint paint;
	List<SplatterParticle> particles;
	int duration;

	public Splatter(GameModel model,Vector position,int color) {
		super(model);
		this.position = position;
		paint = new Paint();
		paint.setColor(SPLATTER_START_OPACITY << 24);
		paint.setStyle(Style.FILL);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeWidth(STROKE_WIDTH);
		duration = 0;
		particles = new ArrayList<SplatterParticle>(NUMBER_PARTICLES);
		float half = MAX_PARTICLE_VELOCITY/2;
		for (int i = 0; i < NUMBER_PARTICLES; i++) {
			float xVel = (model.getRandom().nextFloat()*MAX_PARTICLE_VELOCITY) - half;
			float yVel = (model.getRandom().nextFloat()*MAX_PARTICLE_VELOCITY) - half;
			SplatterParticle p = new SplatterParticle(model,this.position,new Vector(xVel,yVel));
			particles.add(p);
		}
	}

	@Override
	public void draw(Canvas canvas, float interpol, Vector offset) {
		for (Mob m : particles) {
			m.draw(canvas, interpol, offset);
		}
	}

	@Override
	public void update(GameContext g) {
		if (duration >= MAX_DURATION) {
			g.removals.add(this);
			return;
		}

		if (duration > VISIBLE_DURATION) {
			paint.setColor(paint.getColor() - ((int)ALPHA_DELTA));
		}
		
		for (SplatterParticle p : particles) {
			p.update(g);
		}
		duration++;
	}
	
	private class SplatterParticle extends Mob {
		
		Vector origin;
		
		public SplatterParticle(GameModel model,Vector position,Vector velocity) {
			super(model);
			this.position = position;
			origin = new Vector(position);
			this.velocity = velocity;
		}
		
		public void update(GameContext g) {
			position = Vector.add(position, velocity);
		}
		
		public void draw(Canvas canvas,float interpol,Vector offset) {
			canvas.drawLine(origin.x, origin.y, position.x, position.y, paint);
		}
	}
}
