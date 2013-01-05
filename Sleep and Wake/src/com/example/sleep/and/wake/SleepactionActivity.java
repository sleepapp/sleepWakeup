package com.example.sleep.and.wake;

import java.io.Serializable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;


public class SleepactionActivity extends Activity {
	
	SleepSettings mywakeup;
	boolean sound_fadeout;
	boolean display_fadeout;
	float playtime;
	float actual_fadeoutvolume = 1.0f;
	float actual_displaybrightness = 1.0f;
	MusicFadeOutTask taskmusic;
	public MediaPlayer player = null;
	WindowManager.LayoutParams displaylayout;
	private Handler handler;
	Activity mainactivity;
	LinearLayout linearlayout_sleepaction;
	boolean stop = false;

	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.d("SleepactionActivity", "Call onCreate");
	        //set screent to fullscreen
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_sleepaction);
	              
	        //get the intent with the settings
	        Intent intent = getIntent();
	        mywakeup = (SleepSettings)intent.getSerializableExtra("MAIN_MESSAGE_WAKE");
	        //set variables needed by the player-thread
	        sound_fadeout = mywakeup.fadeout;
	        display_fadeout = mywakeup.displayfadeout;
	        playtime = mywakeup.fadeouttime;
	        mainactivity = this;
	        linearlayout_sleepaction = (LinearLayout)findViewById(R.id.linearlayout_activity_sleepaction);
	        
	        //create mediaplayer and set loop on
	        if(sound_fadeout){
	        	taskmusic = new MusicFadeOutTask();
	        	taskmusic.execute();
	        }
	        if(display_fadeout){
	        	//get a WindowManager for the display fadeout function
	        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD, WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
	        	displaylayout = getWindow().getAttributes();
	        	displaylayout.screenBrightness = actual_displaybrightness;
 				getWindow().setAttributes(displaylayout);
 				 				
 				//get and start the handler which starts the display fadeout
	        	handler = new Handler();	
	        	loopdisplay(linearlayout_sleepaction);
	        	
	        }
	     		       
	}
	
	
public void onAttachedToWindow() {
    //make the activity show even the screen is locked.
    Window window = getWindow();
    
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            + WindowManager.LayoutParams.FLAG_FULLSCREEN);
    
}
	
	public void loopdisplay(final View view){
		Runnable runnable = new Runnable(){
			public void run(){
				float tmpdisplaystep = 0.0f;
				//calculate in how many steps the display shall fade out (calculated in relation to 100ms sleep time)
		    	tmpdisplaystep = (100.0f) / (playtime*60000.0f);
				
				for(int i=0;i<playtime*600;i++){
		    		try{
						Thread.sleep(100);
					} catch(InterruptedException e){
						e.printStackTrace();
					}
					if(actual_displaybrightness - tmpdisplaystep > 0.00f && stop==false){
						//reduce display brightness
						actual_displaybrightness = actual_displaybrightness - tmpdisplaystep;
						if(actual_displaybrightness < 0.00f)
							actual_displaybrightness = 0.00f;
						
					}
						
        			handler.post(new Runnable(){
        				public void run(){
        					displaylayout = mainactivity.getWindow().getAttributes();
        					displaylayout.screenBrightness = actual_displaybrightness;
        					mainactivity.getWindow().setAttributes(displaylayout);
        		    		
        				}
        			});
        		}
			}
		};
		new Thread(runnable).start();    	
	}
	
	 private class MusicFadeOutTask extends AsyncTask <Long,Void,Float> {
	        protected Float doInBackground(Long... time) {
	        	try {
	        		//create and start the player
	        		player = MediaPlayer.create(getApplicationContext(), R.raw.sample); 
		        	player.setLooping(true);
		        	player.setVolume(actual_fadeoutvolume, actual_fadeoutvolume);
	        		player.start();
	        		float tmpvolumestep = 0.0f;
	        		//calculate same steps as for the display fadeout
	        		tmpvolumestep = (100.0f) / (playtime*60000.0f);
	        			while(actual_fadeoutvolume >= 0.0f && !(taskmusic.isCancelled()) ){
	        				Thread.sleep(100);
	        				player.setVolume(actual_fadeoutvolume-tmpvolumestep, actual_fadeoutvolume-tmpvolumestep);
	        				actual_fadeoutvolume = actual_fadeoutvolume - tmpvolumestep;
	        			}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        		return (actual_fadeoutvolume);
	        }

	        protected void onPostExecute(Float volume) {
	        	if(actual_fadeoutvolume <= 0.0f){
	        		player.stop();
	        		player.release();
	        		player = null;
	        		actual_fadeoutvolume = 1.0f;
	        	}
	        	finish();
	        }
	    }
	
	public void onPause(){
		super.onPause();
		Log.d("SleepactionActivity", "Call onPause"); 
		//save where the track actually is and at which volume level
		//release the player
		stop = true;
		if(player != null){
			taskmusic.cancel(true);
			player.stop();
			player.release();
			player = null;	
		}
	}
	
	public void onResume(){
		super.onResume();
		Log.d("SleepactionActivity", "Call onResume");
		stop = false;
		actual_fadeoutvolume = 1.0f;
		actual_displaybrightness = 1.0f;	
	}
	
	public void onStop(){
		super.onStop();
		finish();
		Log.d("SleepactionActivity", "Call onStop");
	}

	 @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	    	// TODO Auto-generated method stub
	    	super.onConfigurationChanged(newConfig);
	    	//Log.i("-----MainActivity-----", "Call onConfigurationChanged");
	    }    

}