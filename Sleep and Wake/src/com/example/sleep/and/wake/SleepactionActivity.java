package com.example.sleep.and.wake;

import java.io.Serializable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class SleepactionActivity extends Activity {
	SleepSettings mywakeup;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.activity_sleepaction);
	        
	        Intent intent = getIntent();
	        mywakeup = (SleepSettings)intent.getSerializableExtra("MAIN_MESSAGE_WAKE");
	        
	}
	
	public void onPause(){
		super.onPause();
		//save where the track actually is and at which volume level
		//release the player
	}
	
	public void onResume(){
		super.onResume();
		//initialize the player and start at the saved point with saved volume
		
	}

}