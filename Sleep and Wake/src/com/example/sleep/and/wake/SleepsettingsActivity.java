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


public class SleepsettingsActivity extends Activity {
	
	SleepSettings mywakeup;
	RadioButton rbfadeouton,rbfadeoutoff,rbfadeoutdisplayon,rbfadeoutdisplayoff;
	EditText editfadeouttime;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.activity_sleepsettings);
	        
	        Intent intent = getIntent();
	        mywakeup = (SleepSettings)intent.getSerializableExtra("MAIN_MESSAGE_WAKE");
	        
	        rbfadeouton = (RadioButton) findViewById(R.id.radioButton_fadeouton);
	        rbfadeoutoff = (RadioButton) findViewById(R.id.radioButton_fadeoutoff);
	        rbfadeoutdisplayon = (RadioButton) findViewById(R.id.radioButton_fadeoutdisplayon);
	        rbfadeoutdisplayoff = (RadioButton) findViewById(R.id.radioButton_fadeoutdisplayoff);
	        
	        editfadeouttime = (EditText) findViewById(R.id.edit_fadeouttime);
	        
	        if(mywakeup.fadeout == true)
	        	rbfadeouton.setChecked(true);
	        else
	        	rbfadeoutoff.setChecked(true);
	        
	        if(mywakeup.displayfadeout == true)
	        	rbfadeoutdisplayon.setChecked(true);
	        else
	        	rbfadeoutdisplayoff.setChecked(true);
	        
	        editfadeouttime.setText(Float.toString(mywakeup.fadeouttime));
	                
	 }
	 
	 public void saveWakeupSettings(View view){
		 	
		 	mywakeup.fadeouttime = Float.parseFloat(editfadeouttime.getText().toString());
		 			 	
		 	if(rbfadeouton.isChecked())
		 		mywakeup.fadeout = true;
		 	else
		 		mywakeup.fadeout = false;
		 	
		 	if(rbfadeoutdisplayon.isChecked())
		 		mywakeup.displayfadeout = true;
		 	else
		 		mywakeup.displayfadeout = false;
		 
		 	if(mywakeup.active){
		 		Intent intent = new Intent(this, MainActivity.class);
		 		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
		 		setResult(5,intent);
		 		finish();
		 	}else{
		 		Intent intent = new Intent(this, MainActivity.class);
		 		mywakeup.active = true;
		 		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
		 		setResult(4,intent);
		 		finish();
		 	}
 	}  
	 
	 public void deleteWakeupSettings(View view){
		 	
		 		Intent intent = new Intent(this, MainActivity.class);
		 		mywakeup.active = true;
		 		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
		 		setResult(6,intent);
		 		finish();
		 	
	} 
	 
	 public void cancelWakeupSettings(View view){
	  		Intent intent = new Intent(this, MainActivity.class);
	  		mywakeup.active = false;
	  		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
	  		setResult(0,intent);
	  		finish();
	  	}  
}