package caskman.tinyturrets;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private final static String TAG = MainActivity.class.getSimpleName();
	private MainGamePanel panel;
	
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(panel = new MainGamePanel(this));
        Log.d(TAG, "View added");
    }

    public void onStop() {
    	super.onStop();
    	finish();
    }
    
    public void onPause() {
    	super.onPause();
    	finish();
    }
}
