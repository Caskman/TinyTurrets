package caskman.tinyturrets.model;

import java.util.LinkedList;
import java.util.List;


import android.graphics.Canvas;

public class Layer implements Comparable<Layer> {
	List<Mob> list;
	private int depth; 
	
	public Layer(int depth) {
		list = new LinkedList<Mob>();
		this.depth = depth;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void add(Mob o) {
		list.add(o);
	}
	
	public void add(List<Mob> l) {
		for (int i = 0; i < l.size(); i++) {
			list.add(l.get(i));
		}
	}
	
	public void draw(Canvas canvas,float interpol) {
		for (Mob o : list) {
			o.draw(canvas,interpol);
		}
	}
	
	public void set(List<Mob> l) {
		list = l;
	}


	public int compareTo(Layer another) {
		if (depth < another.depth)
			return -1;
		else if (depth > another.depth)
			return 1;
		else return 0;
	}
	
}
