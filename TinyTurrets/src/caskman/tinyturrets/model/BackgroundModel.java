package caskman.tinyturrets.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import caskman.tinyturrets.model.entities.Explosion;

public class BackgroundModel extends GameModel {
	
	float explosionProbability = .1F;
	
	List<Mob> explosions;
	List<Layer> layers;
	Queue<MotionEvent> inputList;
	
	public BackgroundModel(Context context,Dimension dims) {
		super(context,dims);
		
		explosions = new ArrayList<Mob>();
		layers = getLayerList();
		inputList = new LinkedList<MotionEvent>();
	}
	

	@Override
	public void draw(Canvas canvas, float interpol) {
		canvas.drawColor(Color.BLACK);
		Vector empty = new Vector();
		for (Layer l : layers) {
			l.draw(canvas, interpol, empty);
		}
	}

	@Override
	public void update() {
		if (r.nextFloat() < explosionProbability) {
			Explosion e = new Explosion(this,screenDims.width*r.nextFloat(),screenDims.height*r.nextFloat());
			explosions.add(e);
		}
		
		while (!inputList.isEmpty()) {
			MotionEvent e = inputList.poll();
			Explosion p = new Explosion(this,e.getX(),e.getY());
			explosions.add(p);
		}
		
		
		GameContext g = new GameContext();
		g.removals = new ArrayList<Mob>();
		
		for (Mob m : explosions) {
			m.update(g);
		}
		
		for (Mob m : g.removals) {
			explosions.remove(m);
		}
	}

	@Override
	public void manageInput(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN)
			inputList.add(e);
	}

	public List<Layer> getLayerList() {
		List<Layer> list = new ArrayList<Layer>();
		Layer l;
		
		l = new Layer(0);
		l.set(explosions);
		list.add(l);
		
		return list;
	}

}
