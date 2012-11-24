package caskman.blocks.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import caskman.blocks.model.entities.Blob;

public class CollisionManager {
	private static QuadTree qt;
	
	public static void drawTree(Canvas canvas) {
		qt.draw(canvas);
	}
	
	public static void resolveCollisions(List<Collidable> collidables,Dimension screenDims,Dimension largest) {
		initializeCollisionDetection(collidables,screenDims,largest);
		
		for (Collidable c1 : collidables) {
			List<Collidable> likelycollisions = qt.retrieve(new ArrayList<Collidable>(), c1);
			for (Collidable c2 : likelycollisions) {
				detectAndResolveCollision(c1,c2);
			}
		}
	}
	
	private static void initializeCollisionDetection(List<Collidable> collidables,Dimension screenDims,Dimension largest) {
		qt = new QuadTree(screenDims,largest);
		for (Collidable c : collidables) {
			c.setTempNextPosition(1.0F);
			qt.insert(c);
		}
	}
	
	private static void detectAndResolveCollision(Collidable c1,Collidable c2) {
		if (c1 == c2)
			return;
		float velMag1 = Vector.mag(c1.getVelocity());
		float velMag2 = Vector.mag(c2.getVelocity());
		
		float percent1 = (c1.getDims().width>>1)/velMag1;
		float percent2 = (c2.getDims().width>>1)/velMag2;
		
		if (percent1 > 1.0F || percent2 > 1.0F) {
			if (detectCollision(c1,c2)) {
				resolveCollision(c1,c2);
			}
		} else {
			float percent = 1.0F/((velMag1 < velMag2)?percent1:percent2);
			for (float cPercent = percent; cPercent < 1.0F; cPercent += percent) {
				c1.setTempNextPosition(cPercent);
				c2.setTempNextPosition(cPercent);
				if (detectCollision(c1,c2)) {
					resolveCollision(c1,c2);
					break;
				}
			}
			c1.setTempNextPosition(1.0F);
			c2.setTempNextPosition(1.0F);
		}
		
	}
	
	private static void resolveCollision(Collidable c1,Collidable c2) {
		Blob a = (Blob) c1;
		Blob b = (Blob) c2;
		
		Vector axisDisplacementA = Vector.displacement(b.getTempPosition(),a.getTempPosition());
		Vector axisDisplacementB = Vector.displacement(a.getTempPosition(),b.getTempPosition());
		axisDisplacementA = Vector.normalize(axisDisplacementA);
		axisDisplacementB = Vector.normalize(axisDisplacementB);// calculate unit displacement vectors
		
		float axisVelocityInitialMagA = Vector.dot(axisDisplacementA,a.velocity);// calculate velocities along collision axis
		float axisVelocityInitialMagB = Vector.dot(axisDisplacementB,b.velocity);
		
		Vector axisVelocityA = Vector.scalar(axisVelocityInitialMagA,axisDisplacementA);
		Vector axisVelocityB = Vector.scalar(axisVelocityInitialMagB,axisDisplacementB);
		
		a.velocity = Vector.subtract(a.velocity,axisVelocityA); // take away velocity components along axis of collision
		b.velocity = Vector.subtract(b.velocity,axisVelocityB);
		
		float axisVelocityFinalMagA = axisVelocityInitialMagB; // work out final velocity components along axis
		float axisVelocityFinalMagB = axisVelocityInitialMagA;
		
		axisDisplacementA = Vector.scalar(axisVelocityFinalMagA,axisDisplacementA);
		axisDisplacementB = Vector.scalar(axisVelocityFinalMagB,axisDisplacementB);
		
		a.velocity = Vector.add(a.velocity,axisDisplacementA);
		b.velocity = Vector.subtract(b.velocity,axisDisplacementB);
		
		// collision resolution finished, refresh positions
		a.setTempNextPosition(1.0F);
		b.setTempNextPosition(1.0F);
		
	}

	
	private static List<Collidable> detectCollisions(Collidable c) {
		List<Collidable> collisions = new ArrayList<Collidable>();
		List<Collidable> likelyCollisions = qt.retrieve(new ArrayList<Collidable>(), c);
		for (Collidable c2 : likelyCollisions) {
			if (detectCollision(c,c2))
				collisions.add(c2);
		}
		return collisions;
	}
	
	private static boolean detectCollision(Collidable a, Collidable b) {
		if (a == b)
			return false;
		
		
		Vector displacement = Vector.displacement(a.getTempPosition(),b.getTempPosition());
		float magnitude = Vector.mag(displacement);
		if (magnitude > a.getDims().width)
			return false;
		return true;
	}
	
	
}
