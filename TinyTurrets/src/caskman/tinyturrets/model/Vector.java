package caskman.blocks.model;

import android.graphics.PointF;
import android.util.FloatMath;

public class Vector {
	public float x;
	public float y;
	
	public Vector() {
		
	}
	
	public Vector(float x,float y) {
		this.x = x;
		this.y = y;
	}
	
	public static Vector subtract(Vector u,Vector v) {
		return new Vector(u.x-v.x,u.y-v.y);
	}
	
	public static Vector add(Vector u, Vector v) {
		return new Vector(u.x+v.x,u.y+v.y);
	}
	
	public static Vector scalar(float scalar,Vector v) {
		return new Vector(scalar*v.x,scalar*v.y);
	}
	
	public static float dot(Vector u, Vector v) {
		return u.x*v.x+u.y*v.y;
	}
	
	public static Vector normalize(Vector v) {
		float mag = Vector.mag(v);
		return new Vector(v.x/mag,v.y/mag);
	}
	
	public static Vector displacement(PointF u,PointF v) {
		return new Vector(v.x-u.x,v.y-u.y);
	}

	public static Vector displacement(Vector u,Vector v) {
		return new Vector(v.x-u.x,v.y-u.y);
	}

	public static float mag(Vector v) {
		return FloatMath.sqrt(v.x*v.x+v.y*v.y);
	}
}
