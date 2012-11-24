package caskman.tinyturrets.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.GameContext;
import caskman.tinyturrets.model.GameModel;
import caskman.tinyturrets.model.Mob;
import caskman.tinyturrets.model.Vector;

public class Explosion extends Mob {
	  
	private final static int NUM_PARTICLES = 30;
	private final static int MAX_DURATION = 25;
	private final static float PERCENT_ALPHA = 0.55F; // percentage of time explosion is fully visible
	private final static int MAX_SPEED = 30;
	private final static float DECAY_FACTOR = 1.08F;
	
	private List<Particle> particles;
	private int duration;
	private final static int ALPHA_DELTA = ((int)((0xff/MAX_DURATION)/PERCENT_ALPHA)) << 24;
	private final static int VISIBLE_DURATION = (int) (MAX_DURATION*PERCENT_ALPHA);
	static int SPLATTER_SIZE = 5;
	static int SPLATTER_OPACITY = 0x3f000000;
	int parentColor;
	Paint parentPaint;
	

	public Explosion(GameModel model,float x, float y,int parentColor) {
		super(model);
		
		this.parentColor = parentColor;
		parentPaint = new Paint();
		parentPaint.setColor(((parentColor)&0xffffff)|(SPLATTER_OPACITY));
		position = new Vector(x,y);
		particles = new ArrayList<Particle>();
		Particle p;
		Random r = model.getRandom();
		Vector vel;
		
		for (int i = 0; i < NUM_PARTICLES; i++) {
			vel = new Vector(r.nextFloat()-0.5F,r.nextFloat()-0.5F);
			p = new Particle(model,position.x,position.y,r.nextFloat(),vel,model.getRandColor());
			particles.add(p);
		}
		
		duration = 0;
	}

	@Override
	public void draw(Canvas canvas, float interpol,Vector offset) {
		for (Particle p : particles) {
			p.draw(canvas,interpol,offset);
		}
	}

	@Override
	public void update(GameContext g) {
		duration++;
		if (duration >= MAX_DURATION) {
			g.removals.add(this);
			return;
		}
		
		for (Particle p : particles) {
			p.update(g);
		}
	}
	
	private class Particle extends Mob {
		
		private Paint paint;

		public Particle(GameModel model,float xPos,float yPos,float speed,Vector direction,int color) {
			super(model);
			
			paint = new Paint();
			paint.setColor(color);
			paint.setAntiAlias(true);
			position = new Vector(xPos,yPos);
			float actualSpeed = MAX_SPEED*speed;
			velocity = new Vector(direction.x*actualSpeed,direction.y*actualSpeed);
			dims = new Dimension(2,2);
		}

		@Override
		public void draw(Canvas canvas, float interpol,Vector offset) {
//			int radius = dims.width>>1; // divided by 2
//			canvas.drawCircle((position.x + velocity.x*interpol) - radius, (position.y + velocity.y * interpol) - radius, radius, paint);
			canvas.drawCircle((position.x + offset.x + velocity.x*interpol), (position.y + offset.y + velocity.y * interpol), dims.width>>1, paint);
		}

		@Override
		public void update(GameContext g) {
			position.x += velocity.x;
			position.y += velocity.y;
			velocity.x = velocity.x/(DECAY_FACTOR);
			velocity.y = velocity.y/(DECAY_FACTOR);
//			int half = SPLATTER_SIZE>>1;
//			g.backdrop.drawCircle(position.x+half, position.y+half, SPLATTER_SIZE, parentPaint);
			if (duration > VISIBLE_DURATION) 
				paint.setColor(paint.getColor()-((int)ALPHA_DELTA));
		}
		
	}
	

}
