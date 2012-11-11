package com.example.sleep.and.wake;

import java.io.Serializable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
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
import android.widget.Toast;


public class WakeupactionActivity extends Activity {
	
	WakeupSettings mywakeup;
	boolean sound_fadein;
	float playtime;
	float actual_fadeinvolume = 0.1f;
	MusicFadeInTask taskfadeinmusic;
	MusicTask taskmusic;
	public MediaPlayer player = null;


	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.activity_wakeupaction);
	              
	        
	        Intent intent = getIntent();
	        mywakeup = (WakeupSettings)intent.getSerializableExtra("FROM_BROADCAST");
	        //set variables needed by the player-thread
	        sound_fadein = mywakeup.fadein;
	        playtime = mywakeup.fadeintime;
	   	        
	        	if(sound_fadein == true){
	        		//fadein = true -> fadetime etc. has to be calculated
	        		taskfadeinmusic = new MusicFadeInTask();
	        		taskfadeinmusic.execute();
	        	}else{
	        		//fadein = false -> start player with full volume
	        		taskmusic = new MusicTask();
	        		taskmusic.execute();
	        	}
	        
	     		       
	}
			
	public void stopalarm(View view){
		if(sound_fadein)
			taskfadeinmusic.cancel(true);
		else
			taskmusic.cancel(true);
		
		if(player != null){
			player.stop();
			player.release();
			player = null;
		}
		finish();
	}
	
	
	 private class MusicTask extends AsyncTask <Long,Void,Float> {
	        protected Float doInBackground(Long... time) {
	        	try {
	        		player = MediaPlayer.create(getApplicationContext(), R.raw.sample); 
		        	player.setLooping(true);
		        	actual_fadeinvolume = 1.0f;
		        	player.setVolume(actual_fadeinvolume, actual_fadeinvolume);
	        		player.start();
	        			while( !taskmusic.isCancelled() ){
	        				Thread.sleep(10);
	        			}
	        			
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        		return (actual_fadeinvolume);
	        }

	        protected void onPostExecute(Float volume) {
	        	if(player != null){
	        		player.stop();
	        		player.release();
	        		player = null;
	        	}
	        		        			        	
	        }
	    }
	
	 private class MusicFadeInTask extends AsyncTask <Long,Void,Float> {
	        protected Float doInBackground(Long... time) {
	        	try {
	        		player = MediaPlayer.create(getApplicationContext(), R.raw.sample); 
		        	player.setLooping(true);
		        	actual_fadeinvolume = 0.1f;
		        	player.setVolume(actual_fadeinvolume, actual_fadeinvolume);
	        		player.start();
	        		float tmpvolumestep = 0.0f;
	        		tmpvolumestep = (100.0f) / (playtime*60000.0f);
	        			while( !taskfadeinmusic.isCancelled() ){
	        					if(actual_fadeinvolume <= 1.0f){
	        						Thread.sleep(100);
	        						player.setVolume(actual_fadeinvolume+tmpvolumestep, actual_fadeinvolume+tmpvolumestep);
	        						actual_fadeinvolume = actual_fadeinvolume + tmpvolumestep;
	        					}
	        			}
	        			
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        		return (actual_fadeinvolume);
	        }

	        protected void onPostExecute(Float volume) {
	        	if(player != null){
	        		player.stop();
	        		player.release();
	        		player = null;
	        	}
	        		
	        }
	    }

	
	public void onPause(){
		super.onPause();
		//cancel the task and release the player
		if(sound_fadein)
			taskfadeinmusic.cancel(true);
		else
			taskmusic.cancel(true);
		
		if(player != null){
			player.stop();
			player.release();
			player = null;
		}
		finish();

	}
	
	public void onResume(){
		super.onResume();
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