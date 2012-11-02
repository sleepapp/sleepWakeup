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
	DisplayFadeOutTask taskdisplay;
	public MediaPlayer player = null;
	WindowManager.LayoutParams displaylayout;
	private Handler handler;
	Activity mainactivity;
	LinearLayout linearlayout_sleepaction;
	boolean stop = false;

	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.activity_sleepaction);
	              
	        
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
	        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD, WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
	        	displaylayout = getWindow().getAttributes();
	        	displaylayout.screenBrightness = actual_displaybrightness;
 				getWindow().setAttributes(displaylayout);
 				
 				//taskdisplay = new DisplayFadeOutTask();
	        	//taskdisplay.execute();
	        	handler = new Handler();	
	        	loopdisplay(linearlayout_sleepaction);
	        	
	        }
	     		       
	}
	
	public void loopdisplay(final View view){
		Runnable runnable = new Runnable(){
			public void run(){
				float tmpdisplaystep = 0.0f;
		    	tmpdisplaystep = (100.0f) / (playtime*60000.0f);
				//while(actual_displaybrightness >= 0.0f && stop==true ){
				for(int i=0;i<playtime*600;i++){
		    		try{
						Thread.sleep(100);
					} catch(InterruptedException e){
						e.printStackTrace();
					}
					if(actual_displaybrightness - tmpdisplaystep >= 0.01f && stop==false){
						actual_displaybrightness = actual_displaybrightness - tmpdisplaystep;
					}else
						finish();
        			handler.post(new Runnable(){
        				public void run(){
        					//linearlayout_sleepaction.setBackgroundColor(getResources().getColor(R.color.green));
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
	        		player = MediaPlayer.create(getApplicationContext(), R.raw.sample); 
		        	player.setLooping(true);
		        	player.setVolume(actual_fadeoutvolume, actual_fadeoutvolume);
	        		player.start();
	        		float tmpvolumestep = 0.0f;;
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
	        }
	    }

	 private class DisplayFadeOutTask extends AsyncTask <Long,Void,Float> {
	        protected Float doInBackground(Long... time) {
	        	try {
	        		float tmpdisplaystep = 0.0f;
	        		tmpdisplaystep = (100.0f) / (playtime*60000.0f);
     				while(actual_displaybrightness >= 0.0f && !(taskdisplay.isCancelled()) ){
	        			Thread.sleep(100);
	        			actual_displaybrightness = actual_displaybrightness - tmpdisplaystep;
	        			displaylayout = mainactivity.getWindow().getAttributes();
    					displaylayout.screenBrightness = actual_displaybrightness;
    					mainactivity.getWindow().setAttributes(displaylayout);
	        			
	        		}
	        		
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        		return (actual_displaybrightness);
	        }

	        protected void onPostExecute(Float volume) {
//	        	if(actual_displaybrightness <= 0.0f){
//	        		actual_displaybrightness = -1.0f;	
//	        	}
	        }
	    }
	
	public void onPause(){
		super.onPause();
		//save where the track actually is and at which volume level
		//release the player
		stop = true;
		if(player != null){
			taskmusic.cancel(true);
			player.stop();
			player.release();
			player = null;	
		}
//		if(display_fadeout)
//			taskdisplay.cancel(true);
	}
	
	public void onResume(){
		super.onResume();
		stop = false;
		actual_fadeoutvolume = 1.0f;
		actual_displaybrightness = 1.0f;	
	}
	
	public void onStop(){
		super.onStop();
		
	}

	 @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	    	// TODO Auto-generated method stub
	    	super.onConfigurationChanged(newConfig);
	    	//Log.i("-----MainActivity-----", "Call onConfigurationChanged");
	    }    

}