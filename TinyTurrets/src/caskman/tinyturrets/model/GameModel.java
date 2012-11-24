package caskman.tinyturrets.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import caskman.tinyturrets.model.entities.Bullet;
import caskman.tinyturrets.model.entities.Explosion;
import caskman.tinyturrets.model.entities.Turret;

public class GameModel { 
	private Context context;
	private Dimension screenDims;
	private Random r;
	List<Mob> explosions;
	List<Mob> turrets;
	List<Mob> bullets;
	BlockingQueue<InputAction> inputQueue;
	List<Layer> layers;
	Vector offset;
	float offsetDecayFactor = .66F;
	
	
	public GameModel(Context context,Dimension dims) {
		this.context = context;
		screenDims = dims;
		initializeGame();
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
	
	public int getRandColor() {
		int red = r.nextInt(255);
		int green = r.nextInt(255);
		int blue = r.nextInt(255);
		int color = (0xff000000)+(red << 16) + (green << 8) + blue;
		return color;
	}
	
	private void initializeGame() {
		r = new Random();
		explosions = new ArrayList<Mob>();
		turrets = new ArrayList<Mob>();
		bullets = new ArrayList<Mob>();
		inputQueue = new ArrayBlockingQueue<InputAction>(10);
		layers = getLayerList();
		offset = new Vector();
	}
	
	public List<Layer> getLayerList() {
		List<Layer> list = new ArrayList<Layer>();
		Layer l;
		
		l = new Layer(0);
		l.set(turrets);
		list.add(l);
		
		l = new Layer(1);
		l.set(bullets);
		list.add(l);
		
		l = new Layer(2);
		l.set(explosions);
		list.add(l);

		return list;
	}
	
	public void manageInput(MotionEvent e) {
		
		if (e.getAction() == MotionEvent.ACTION_DOWN)
			inputQueue.offer(new InputAction(new PointF(e.getX(),e.getY())));
		
	}
	
	public void update() {
		// resolve input
		InputAction i;
		while (!inputQueue.isEmpty()) {
			i = inputQueue.poll();
			if (i == null)
				continue;
			turrets.add(new Turret(this,i.point.x,i.point.y,getRandColor()));
		}
		
		// update all objects
		GameContext g = new GameContext();
		g.turrets = turrets;
		g.additions = new ArrayList<Mob>(10);
		g.removals = new ArrayList<Mob>(10);
		g.impulseStrength = 0;
		
		for (Mob m : explosions) {
			m.update(g);
		}
		for (Mob m : turrets) {
			m.update(g);
		}
		for (Mob m : bullets) {
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
			}
		}
		
		
		for (Mob m : g.additions) {
			if (m instanceof Explosion) {
				explosions.add(m);
			} else if (m instanceof Bullet) {
				bullets.add(m);
			} else if (m instanceof Turret) {
				turrets.add(m);
			}
		}
		
		
	}
	
	public void draw(Canvas canvas,float interpol) {
		canvas.drawColor(Color.BLACK);
		for (Layer l : layers) {
			l.draw(canvas, interpol,offset);
		}
	}
	
	private Vector updateOffset(Vector offset,float offsetDecayFactor,int impulse) {
		Vector tempOffset = Vector.scalar(offsetDecayFactor, offset);
		
		if (impulse > 0) {
			float xImpulse = impulse*r.nextFloat();
			float yImpulse = impulse*r.nextFloat();
			tempOffset = Vector.add(new Vector(xImpulse,yImpulse), tempOffset);
		}
		return tempOffset;
	}
}
