package com.example.sleep.and.wake;


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


public class WakeupsettingsActivity extends Activity {
	WakeupObject mywakeup ;
	RadioButton rbfadeinon,rbfadeinoff;
	EditText editfadeintime;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.activity_wakeupsettings);
	        
	        Intent intent = getIntent();
	        mywakeup = (WakeupObject)intent.getExtras().getSerializable("MAIN_MESSAGE_WAKE");
	        
	        rbfadeinon = (RadioButton) findViewById(R.id.radioButton_fadeinon);
	        rbfadeinoff = (RadioButton) findViewById(R.id.radioButton_fadeinoff);
	        editfadeintime = (EditText) findViewById(R.id.edit_fadeintime);
	        
	        if(mywakeup.fadein == true)
	        	rbfadeinon.setChecked(true);
	        else
	        	rbfadeinoff.setChecked(true);
	        
	        editfadeintime.setText(Float.toString(mywakeup.fadeintime));
	                
	 }
	 
	 public void saveWakeupSettings(View view){
		 	
		 	mywakeup.fadeintime = Float.parseFloat(editfadeintime.getText().toString());
		 	
		 	
		 	if(rbfadeinon.isChecked())
		 		mywakeup.fadein = true;
		 	else
		 		mywakeup.fadein = false;
		 
	  		Intent intent = new Intent(this, MainActivity.class);
	  		mywakeup.active = true;
	  		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
	  		setResult(1,intent);
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