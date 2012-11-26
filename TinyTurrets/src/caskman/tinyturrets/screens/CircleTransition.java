package caskman.tinyturrets.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Path.Direction;

public class CircleTransition extends Transition {
	
	Bitmap bitmap;
	float radiusInc;
	float radius;

	public CircleTransition() {
		super();
	}

	@Override
	public void start(Bitmap frozenScreen) {
		bitmap = frozenScreen;
		float maxRadius = (screen.manager.getScreenDims().width < screen.manager.getScreenDims().height)?screen.manager.getScreenDims().height:screen.manager.getScreenDims().width;
		radiusInc = maxRadius/25.0F;
		radius = maxRadius;
}

	@Override
	public void draw(Canvas canvas, float interpol) {
		if (radius < 0F)
			radius = 0F;
		float interpolRadius = radius - interpol*radiusInc;
		canvas.drawColor(Color.WHITE);
//		canvas.drawColor(0xff0d0053);
		Path p = new Path();
		p.addCircle(screen.manager.getScreenDims().width>>1, screen.manager.getScreenDims().height>>1, interpolRadius, Direction.CW);
		canvas.clipPath(p);
		canvas.drawBitmap(bitmap, 0F, 0F, null);
	}
	
	@Override
	protected void updateTransition() {
		radius = radius - radiusInc;
		
		if (radius <= 0F) {
			isDone = true;
		}
	}

}
