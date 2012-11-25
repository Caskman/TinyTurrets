package caskman.tinyturrets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import caskman.tinyturrets.model.Dimension;
import caskman.tinyturrets.screens.BackgroundScreen;
import caskman.tinyturrets.screens.MainMenuScreen;
import caskman.tinyturrets.screens.ScreenManager;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();

	private MainThread thread;
//	private List<Block> blocks;
	private float INTERPOL = 0.0F;
	private ScreenManager manager;

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);


		// create the game loop thread
		thread = new MainThread(this, getHolder());

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
		manager = new ScreenManager(context,new Dimension(display.getWidth(),display.getHeight()));
		manager.addScreen(new BackgroundScreen(manager));
		manager.addScreen(new MainMenuScreen(manager));
//		model = new GameModel(context,new Dimension(display.getWidth(),display.getHeight()));
		
//		layers = model.getLayerList();
//		blocks = model.getBlocks();
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		thread.setRunning(false);
		// this is a clean shutdown 
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		thread.setPause(false);
//		model.manageInput(event);
		manager.manageInput(event);
		return true;
	}

	public void updateModel() {
//		model.update();
		manager.update();
	}
	
	public void drawGame(Canvas canvas, float interpol) {
		INTERPOL = interpol;
		onDraw(canvas);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
//		model.draw(canvas,INTERPOL);
		manager.draw(canvas,INTERPOL);
	}

	public void setPause(boolean pause) {
		thread.setPause(pause);
	}
	
}