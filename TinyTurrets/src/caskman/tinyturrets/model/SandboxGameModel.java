package caskman.tinyturrets.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import caskman.tinyturrets.model.entities.Bullet;
import caskman.tinyturrets.model.entities.Explosion;
import caskman.tinyturrets.model.entities.IntroText;
import caskman.tinyturrets.model.entities.Splatter;
import caskman.tinyturrets.model.entities.Turret;

public class SandboxGameModel extends GameModel { 
	List<Mob> explosions;
	List<Mob> turrets;
	List<Mob> bullets;
	List<Mob> splatters;
	List<Mob> texts;
	BlockingQueue<InputAction> inputQueue;
	List<Layer> layers;
	Vector offset;
	static float offsetDecayFactor = .5F;
	static float SCREEN_SHAKE_FACTOR = 15.0F;
	
	
	public SandboxGameModel(Context context,Dimension dims) {
		super(context,dims);
		initializeGame();
	}
	
	private void initializeGame() {
		explosions = new ArrayList<Mob>();
		turrets = new ArrayList<Mob>();
		bullets = new ArrayList<Mob>();
		splatters = new ArrayList<Mob>();
		texts = new ArrayList<Mob>();
		texts.add(new IntroText(this));
		inputQueue = new ArrayBlockingQueue<InputAction>(10);
		layers = getLayerList();
		offset = new Vector();
//		backdropBitmap = Bitmap.createBitmap(screenDims.width, screenDims.height, Config.ARGB_8888);
//		Canvas c = new Canvas(backdropBitmap);
	}
	
	public List<Layer> getLayerList() {
		List<Layer> list = new ArrayList<Layer>();
		Layer l;
		
		l = new Layer(0);
		l.set(splatters);
		list.add(l);
		
		l = new Layer(1);
		l.set(turrets);
		list.add(l);
		
		l = new Layer(2);
		l.set(bullets);
		list.add(l);
		
		l = new Layer(3);
		l.set(explosions);
		list.add(l);
		
		l = new Layer(4);
		l.set(texts);
		list.add(l);

		return list;
	}
	
	@Override
	public void manageInput(MotionEvent e) {
		
		if (e.getAction() == MotionEvent.ACTION_DOWN)
			inputQueue.offer(new InputAction(new PointF(e.getX(),e.getY())));
		
	}
	
	@Override
	public void update() {
		// resolve input
		InputAction i;
		while (!inputQueue.isEmpty()) {
			i = inputQueue.poll();
			if (i == null)
				continue;
			turrets.add(new Turret(this,i.point.x,i.point.y,getRandNotDarkColor()));
		}
		
		// update all objects
		GameContext g = new GameContext();
		g.turrets = turrets;
		g.additions = new ArrayList<Mob>(10);
		g.removals = new ArrayList<Mob>(10);
		g.impulseStrength = 0;
//		g.backdrop = new Canvas(backdropBitmap);
		
		for (Mob m : explosions) {
			m.update(g);
		}
		for (Mob m : turrets) {
			m.update(g);
		}
		for (Mob m : bullets) {
			m.update(g);
		}
		for (Mob m : splatters) {
			m.update(g);
		}
		for (Mob m : texts) {
			m.update(g);
		}
		
		offset = updateOffset(offset,offsetDecayFactor,g.impulseStrength);
		
		// filter new objects and deleted objects
		
		for (Mob m : g.removals) {
			if (m instanceof Explosion) {
				explosions.remove(m);
			} else if (m instanceof Bullet) {
				bullets.remove(m);
			} else if (m instanceof Turret) {
				turrets.remove(m);
			} else if (m instanceof Splatter) {
				splatters.remove(m);
			} else if (m instanceof IntroText) {
				texts.remove(m);
			}
		}
		
		
		for (Mob m : g.additions) {
			if (m instanceof Explosion) {
				explosions.add(m);
			} else if (m instanceof Bullet) {
				bullets.add(m);
			} else if (m instanceof Turret) {
				turrets.add(m);
			} else if (m instanceof Splatter) {
				splatters.add(m);
			}
		}
		
		
	}
	
	@Override
	public void draw(Canvas canvas,float interpol) {
		canvas.drawColor(Color.BLACK);
//		canvas.drawBitmap(backdropBitmap, offset.x, offset.y, null);
		for (Layer l : layers) {
			l.draw(canvas, interpol,offset);
		}
		
//		Paint paint = new Paint();
//		paint.setColor(0xffffffff);
//		
//		canvas.drawText(screenDims.width+" "+screenDims.height, 50, 50, paint);
	}
	
	private Vector updateOffset(Vector offset,float offsetDecayFactor,int impulse) {
		Vector tempOffset = Vector.scalar(-1.0F*offsetDecayFactor, offset);
		
		if (impulse > 0) {
			float half = SCREEN_SHAKE_FACTOR/2;
			float xImpulse = impulse*r.nextFloat()*SCREEN_SHAKE_FACTOR - half;
			float yImpulse = impulse*r.nextFloat()*SCREEN_SHAKE_FACTOR - half;
			tempOffset = Vector.add(new Vector(xImpulse,yImpulse), tempOffset);
		}
		return tempOffset;
	}
}
