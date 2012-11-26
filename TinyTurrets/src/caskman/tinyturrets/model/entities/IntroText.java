package caskman.tinyturrets.model.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import caskman.tinyturrets.model.GameContext;
import caskman.tinyturrets.model.GameModel;
import caskman.tinyturrets.model.Mob;
import caskman.tinyturrets.model.Vector;

public class IntroText extends Mob {
	
	static int MAX_DURATION = 125;
	static float PERCENT_VISIBLE = .75F;
	
	static int VISIBLE_DURATION = (int) (MAX_DURATION*PERCENT_VISIBLE);
	static int ALPHA_DELTA = ((int)((0xff/MAX_DURATION)/PERCENT_VISIBLE)) << 24;

	int duration;
	String text;
	Paint paint;
	Rect bounds;

	public IntroText(GameModel model) {
		super(model);
		duration = 0;
		text = "Tap to place a turret";
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize(20.0F);
		bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
	}

	@Override
	public void draw(Canvas canvas, float interpol, Vector offset) {
		canvas.drawText(text,(model.getScreenDims().width - bounds.width())/2.0F,2*model.getScreenDims().height/3,paint);
	}

	@Override
	public void update(GameContext g) {
		if (duration >= MAX_DURATION) {
			g.removals.add(this);
			return;
		}
		
		if (duration >= VISIBLE_DURATION) {
			paint.setColor(paint.getColor() - ALPHA_DELTA);
		}
		
		duration++;
	}

}
