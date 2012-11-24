package caskman.tinyturrets.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

/*
 * currently configured to work with squares, but not so much rectangles
 */

public class QuadTree {
	
	private List<Collidable> stuff;
	private List<QuadTree> nodes;
	private int level;
	private Rect bounds;
	private int MAX_LEVELS;
	private int MAX_OBJECTS = 10;
	private static Paint paint;
	
	public QuadTree(Dimension screenDims,Dimension largest) {
		int dimTarget = ((largest.width < largest.height)?largest.height:largest.width)<<3;
		int screenDim = (screenDims.height < screenDims.width)?screenDims.height:screenDims.width;
		int i;
		for (i = 0; screenDim > dimTarget; i++) {
			screenDim = screenDim >> 1;
		}
		MAX_LEVELS = i;
		initialize(0,new Rect(0,0,screenDims.width,screenDims.height));
	}
	
	private QuadTree(int level,Rect bounds,int maxLevels) {
		MAX_LEVELS = maxLevels;
		initialize(level,bounds);
	}
	
	private void initialize(int level,Rect bounds) {
		this.level = level;
		this.bounds = bounds;
		stuff = new ArrayList<Collidable>();
		nodes = new ArrayList<QuadTree>(4);
		for (int i = 0; i < 4; i++) {
			nodes.add(null);
		}
		if (paint == null) {
			paint = new Paint();
			paint.setColor(0xffffffff);
			paint.setStyle(Style.STROKE);
		}
	}
	
	public void draw(Canvas canvas) {
		canvas.drawRect(bounds, paint);
		if (nodes.get(0) != null) {
			for (int i = 0; i < 4; i++) {
				nodes.get(i).draw(canvas);
			}
		}
	}
	
	public void clear() {
		stuff.clear();
		for (int i = 0; i < 4; i++) {
			if (nodes.get(i) != null) {
				nodes.get(i).clear();
				nodes.set(i,null);
			}
		}
	}
	
	private void split() {
		int subWidth = bounds.width()>>1;
		int subHeight = bounds.height()>>1;
		int x = bounds.left;
		int y = bounds.top;
		nodes.set(0,new QuadTree(level + 1,new Rect(x+subHeight,y,x+(subHeight<<1),y + (subWidth)),MAX_LEVELS));
		nodes.set(1,new QuadTree(level + 1,new Rect(x,y,x+(subHeight),y + (subWidth)),MAX_LEVELS));
		nodes.set(2,new QuadTree(level + 1,new Rect(x,y+subWidth,x+(subHeight),y + (subWidth<<1)),MAX_LEVELS));
		nodes.set(3,new QuadTree(level + 1,new Rect(x+subHeight,y+subWidth,x+(subHeight<<1),y + (subWidth<<1)),MAX_LEVELS));
	}
	
	private int getIndex(Collidable c) {
		Rect r = c.getAABB();
		int index = -1;
		
		boolean topQuadrant = r.top < r.centerX() && r.top + r.height() < r.centerX();
		boolean bottomQuadrant = r.top > r.centerX();
		
		if (r.left < r.centerY() && r.left + r.width() < r.centerY()) {
			if (topQuadrant) {
				index = 1;
			} else if (bottomQuadrant) {
				index = 2;
			}
		} else if (r.left > r.centerY()) {
			if (topQuadrant) {
				index = 0;
			} else if (bottomQuadrant) {
				index = 3;
			}
		}
		return index;
	}
	
	public void insert(Collidable c) {
		Rect r = c.getAABB();
		if (nodes.get(0) != null) {
			int index = getIndex(c);
			if (index != -1) {
				nodes.get(index).insert(c);
				return;
			}
		}
		stuff.add(c);
		if (stuff.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if (nodes.get(0) == null) {
				split();
			}
			int i = 0;
			while (i < stuff.size()) {
				int index = getIndex(stuff.get(i));
				if (index != -1) {
					nodes.get(index).insert(stuff.remove(i));
				} else {
					i++;
				}
			}
		}
	}
	
	public List<Collidable> retrieve(List<Collidable> returnStuff,Collidable c) {
		int index = getIndex(c);
		if (index != -1 && nodes.get(0) != null) {
			nodes.get(index).retrieve(returnStuff, c);
		}
		returnStuff.addAll(stuff);
		return returnStuff;
	}
	
}