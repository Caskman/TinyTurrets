package caskman.tinyturrets;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;
	String TAG = "MainThread";
	private int FPS = 25;
	private int TICKS_PER_FRAME = 1000 / FPS;
	private int MAX_FRAMESKIP = 5;
	private boolean isPaused;
 
	public MainThread(MainGamePanel gamepanel, SurfaceHolder surfaceholder) {
		this.setName("Game Thread");
		this.gamePanel = gamepanel;
		this.surfaceHolder = surfaceholder;
		isPaused = false;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void run() {
		long ticks;
		long nextFrameTicks = System.currentTimeMillis();
		int framesSkipped;
		float interpol;
		
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
				
			
			
			canvas = null;
			// try locking the canvas for exclusive pixel editing on the surface
			
			framesSkipped = 0;
			while ((System.currentTimeMillis() > nextFrameTicks) && (framesSkipped < MAX_FRAMESKIP)) {
				if (!isPaused) 
					gamePanel.updateModel();
				
				nextFrameTicks += TICKS_PER_FRAME;
				
				framesSkipped++;
			}
			
			if (!isPaused) {
				long systemMil = System.currentTimeMillis();
				long num = systemMil + TICKS_PER_FRAME - nextFrameTicks;
				interpol= ((float)(num))/((float)(TICKS_PER_FRAME));
	//			interpol = ((float)(System.currentTimeMillis() + TICKS_PER_FRAME - nextFrameTicks))/((float)(TICKS_PER_FRAME));
				
				try {
					canvas = this.surfaceHolder.lockCanvas();
					synchronized (surfaceHolder) {
						// draws the canvas on the panel
						
						this.gamePanel.drawGame(canvas,interpol);
					}
				} finally {
					// in case of an exception the surface is not left in
					// an inconsistent state
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				} // end finally
			}
		}
		
		int i = 1;
	}
	
	public void setPause(boolean pause) {
		isPaused = pause;
	}
}
