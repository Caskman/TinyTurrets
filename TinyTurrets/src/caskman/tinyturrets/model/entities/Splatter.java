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
	
	static int SPLATTER_START_OPACITY = 0xff;
	static int NUMBER_PARTICLES = 5;
	static float MAX_PARTICLE_VELOCITY = 30.0F;
//	static float SPLATTER_BLOTCH_SIZE = 3.0F;
	static int MAX_DURATION = 50;
	static float PERCENT_VISIBLE = .55F;
	static float STROKE_WIDTH = 5.0F;
	static int BITMAP_SQ_DIM = 50;
	
	static int VISIBLE_DURATION = (int) (MAX_DURATION*PERCENT_VISIBLE);
	static int ALPHA_DELTA = ((int)((SPLATTER_START_OPACITY/MAX_DURATION)/PERCENT_VISIBLE)) << 24;
	
	Paint paint;
	Bitmap bitmap;
//	List<SplatterParticle> particles;
	int duration;

	public Splatter(GameModel model,Vector position,int color) {
		super(model);
		this.position = position;
		paint = new Paint();
		paint.setColor((color&0xffffff)|(SPLATTER_START_OPACITY << 24));
		paint.setStyle(Style.FILL);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeWidth(STROKE_WIDTH);
		duration = 0;
//		particles = new ArrayList<SplatterParticle>(NUMBER_PARTICLES);
		Bitmap map = Bitmap.createBitmap(BITMAP_SQ_DIM, BITMAP_SQ_DIM, Config.ARGB_8888);
		Canvas canvas = new Canvas(map);
		float centerX = BITMAP_SQ_DIM>>1;
		float centerY = BITMAP_SQ_DIM>>1;
		
		float half = MAX_PARTICLE_VELOCITY/2;
		for (int i = 0; i < NUMBER_PARTICLES; i++) {
			float xEnd = ((model.getRandom().nextFloat()*MAX_PARTICLE_VELOCITY) - half) + centerX;
			float yEnd = ((model.getRandom().nextFloat()*MAX_PARTICLE_VELOCITY) - half) + centerY;
			canvas.drawLine(centerX, centerY, xEnd, yEnd, paint);
//			SplatterParticle p = new SplatterParticle(model,this.position,new Vector(xVel,yVel));
//			particles.add(p);
		}
	}

	@Override
	public void draw(Canvas canvas, float interpol, Vector offset) {
		float half = BITMAP_SQ_DIM>>1;
		canvas.drawBitmap(bitmap,position.x+half+offset.x,position.y+half+offset.y,paint);
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
		
//		for (SplatterParticle p : particles) {
//			p.update(g);
//		}
		duration++;
	}
	
	private class SplatterParticle extends Mob {
		
		Vector origin;
		boolean isSplattered;
		
		public SplatterParticle(GameModel model,Vector position,Vector velocity) {
			super(model);
			this.position = position;
			origin = new Vector(position);
			this.velocity = velocity;
			isSplattered = false;
		}
		
		public void update(GameContext g) {
			if (!isSplattered) {
				position = Vector.add(position, velocity);
				isSplattered = true;
			}
		}
		
		public void draw(Canvas canvas,float interpol,Vector offset) {
			canvas.drawLine(origin.x + offset.x, origin.y + offset.y, position.x + offset.x, position.y + offset.y, paint);
		}
	}
}
