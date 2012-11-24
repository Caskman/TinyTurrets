package caskman.tinyturrets.model.entities;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.model.GameContext;
import caskman.tinyturrets.model.GameModel;
import caskman.tinyturrets.model.Mob;

public class Turret extends Mob {
 
	private final static int size = 20;
	private int firingPeriod = 25;
	private final static int MAX_DEATH_DURATION = 50;
	private final static float GLOW_FLASH_RATIO = 0.75F;
	private final static int FLASH_MODULUS = 2; // preferrably even
	
	private final static int GLOW_FLASH_THRESHOLD = (int) (MAX_DEATH_DURATION*GLOW_FLASH_RATIO);
	private  int R_GLOW_DELTA;
	private  int G_GLOW_DELTA;
	private  int B_GLOW_DELTA;
	private int hp;
	private int color;
	private int reloadPoints;
	private Paint paint;
	private boolean isDying;
	private int deathDuration;
	
	
	
	public Turret(GameModel model,float xPos, float yPos, int color) {
		super(model);
		
		position = new PointF(xPos,yPos);
		dims = new Dimension(size,size);
		this.color = color;
		paint = new Paint();
		paint.setColor(color);
		paint.setAntiAlias(true);
		hp = 5;
		reloadPoints = 0;
		isDying = false;
		deathDuration = 0;
		R_GLOW_DELTA = (0xff - (0xff&(color>>16)))/GLOW_FLASH_THRESHOLD;
		G_GLOW_DELTA = (0xff - (0xff&(color>>8)))/GLOW_FLASH_THRESHOLD;
		B_GLOW_DELTA = (0xff - (0xff&color))/GLOW_FLASH_THRESHOLD;
	}

	@Override
	public void draw(Canvas canvas, float interpol) {
		int radius = dims.height >> 1;
		canvas.drawCircle(position.x,position.y,radius, paint);
	}

	@Override
	public void update(GameContext g) {
		
		if (isDying) {
			death(g);
		} else {
			if (hp <= 0) {
				isDying = true;
				return;
			}
			
			reloadPoints += 1;
			
			if (reloadPoints >= firingPeriod) {
				if (g.turrets.size() < 2)
					return;
				
				reloadPoints = 0;
				Turret victim = chooseTarget(g.turrets);
				Bullet b = new Bullet(model,this,victim,paint.getColor());
				g.additions.add(b);
		}
		
		}
	}
	
	private void death(GameContext g) {
		if (deathDuration == MAX_DEATH_DURATION) {
			Explosion e = new Explosion(model,position.x,position.y);
			g.additions.add(e);
			g.removals.add(this);
		}
		
		
		if (deathDuration <= GLOW_FLASH_THRESHOLD) {
			int color = paint.getColor();
			int red = (color>>16)&0xff;
			red += R_GLOW_DELTA;
			int green = (color>>8)&0xff;
			green += G_GLOW_DELTA;
			int blue = color&0xff;
			blue += B_GLOW_DELTA;
			color = 0xff000000 + (red<<16) + (green<<8) + blue;
			paint.setColor(color);
		} else {
			if (deathDuration%FLASH_MODULUS < (FLASH_MODULUS>>1)) {
				paint.setColor(color);
			} else {
				paint.setColor(0xffffffff);
			}
		}
		
		
		
		deathDuration++;
	}
	
	public boolean isDead() {
		return isDying;
	}
	
	private Turret chooseTarget(List<Mob> list) {
		
		int limit = 5;
		List<Float> dists = new ArrayList<Float>(limit);
		List<Integer> indexes = new ArrayList<Integer>(limit);
		int k = 0;
		Turret t;
		for (int i = model.getRandom().nextInt(list.size()); k < limit && k < list.size(); i = (i+1)%list.size()) {
			t = (Turret) list.get(i);
			if (t != this && !t.isDead()) {
				dists.add(abs((float)list.get(i).getX())+abs((float)list.get(i).getY()));
				indexes.add(i);
			}
			k++;
		}
		
		int minIndex = minIndex(dists);
		return (Turret) list.get((minIndex == -1)?0:indexes.get(minIndex));
		
	}
	
	private int minIndex(List<Float> l) {
		float min = Float.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i) < min) {
				min = l.get(i);
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public void hit() {
		hp -= 1;
	}

}
