package com.example.sleep.and.wake;

import android.content.res.Configuration;
import java.io.Serializable;
import java.util.Calendar;

import com.example.sleep.and.wake.R.color;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;


public class WakeupsettingsActivity extends Activity {
	
	WakeupSettings mywakeup;
	RadioButton rbfadeinon,rbfadeinoff;
	EditText editfadeintime;
	Button button_mon,button_tue,button_wed,button_thu,button_fri,button_sat,button_sun;
	CheckBox checkbox_weeklyrepetition;
	TimePicker timepicker_wakeuptime;
	Boolean nodaypicked = true;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
//	        requestWindowFeature(Window.FEATURE_NO_TITLE);
//	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.activity_wakeupsettings);
	        
	        Intent intent = getIntent();
	        mywakeup = (WakeupSettings)intent.getSerializableExtra("MAIN_MESSAGE_WAKE");
	        
	        button_mon = (Button) findViewById(R.id.button_monday);
	        button_tue = (Button) findViewById(R.id.button_tuesday);
	        button_wed = (Button) findViewById(R.id.button_wednesday);
	        button_thu = (Button) findViewById(R.id.button_thursday);
	        button_fri = (Button) findViewById(R.id.button_friday);
	        button_sat = (Button) findViewById(R.id.button_saturday);
	        button_sun = (Button) findViewById(R.id.button_sunday);
	        checkbox_weeklyrepetition = (CheckBox) findViewById(R.id.checkBox_weeklyrepetition);
	        timepicker_wakeuptime = (TimePicker) findViewById(R.id.timePicker_wakeup);
	        rbfadeinon = (RadioButton) findViewById(R.id.radioButton_fadeinon);
	        rbfadeinoff = (RadioButton) findViewById(R.id.radioButton_fadeinoff);
	        editfadeintime = (EditText) findViewById(R.id.edit_fadeintime);
	        timepicker_wakeuptime.setIs24HourView(true);
	        
	        initialize_components();
	
	        button_mon.setOnClickListener(new OnClickListener() {  
	        	public void onClick(View v) { 
	         	   if(mywakeup.mon == true){
	         		   mywakeup.mon = false;
	         		   setinactive(button_mon);
	         	   }else{
	         		  mywakeup.mon = true;
	         		  setactive(button_mon);
	         	   }               
	            }  
	        });
	        
	        button_tue.setOnClickListener(new OnClickListener() {  
	        	public void onClick(View v) { 
	         	   if(mywakeup.tue == true){
	         		   mywakeup.tue = false;
	         		   setinactive(button_tue);
	         	   }else{
	         		  mywakeup.tue = true;
	         		  setactive(button_tue);
	         	   }               
	            }  
	        });
	        
	        button_wed.setOnClickListener(new OnClickListener() {  
	        	public void onClick(View v) { 
	         	   if(mywakeup.wed == true){
	         		   mywakeup.wed = false;
	         		   setinactive(button_wed);
	         	   }else{
	         		  mywakeup.wed = true;
	         		  setactive(button_wed);
	         	   }               
	            }  
	        });
	        
	        button_thu.setOnClickListener(new OnClickListener() {  
	        	public void onClick(View v) { 
	         	   if(mywakeup.thu == true){
	         		   mywakeup.thu = false;
	         		   setinactive(button_thu);
	         	   }else{
	         		  mywakeup.thu = true;
	         		  setactive(button_thu);
	         	   }               
	            }  
	        });
	        
	        button_fri.setOnClickListener(new OnClickListener() {  
	        	public void onClick(View v) { 
	         	   if(mywakeup.fri == true){
	         		   mywakeup.fri = false;
	         		   setinactive(button_fri);
	         	   }else{
	         		  mywakeup.fri = true;
	         		  setactive(button_fri);
	         	   }               
	            }  
	        });
	        
	        button_sat.setOnClickListener(new OnClickListener() {  
	        	public void onClick(View v) { 
	         	   if(mywakeup.sat == true){
	         		   mywakeup.sat = false;
	         		   setinactive(button_sat);
	         	   }else{
	         		  mywakeup.sat = true;
	         		  setactive(button_sat);
	         	   }               
	            }  
	        });
	        
	        button_sun.setOnClickListener(new OnClickListener() {  
	        	public void onClick(View v) { 
	         	   if(mywakeup.sun == true){
	         		   mywakeup.sun = false;
	         		   setinactive(button_sun);
	         	   }else{
	         		  mywakeup.sun = true;
	         		  setactive(button_sun);
	         	   }               
	            }  
	        });
	        
	 }
	      
	void initialize_components(){
		if(mywakeup.fadein == true)
        	rbfadeinon.setChecked(true);
        else
        	rbfadeinoff.setChecked(true);
        
		timepicker_wakeuptime.setCurrentHour(mywakeup.hour);
		timepicker_wakeuptime.setCurrentMinute(mywakeup.minute);
        editfadeintime.setText(Float.toString(mywakeup.fadeintime));
        
        if(mywakeup.mon == true)
        	setactive(button_mon);
        else
        	setinactive(button_mon);
        
        if(mywakeup.tue == true)
        	setactive(button_tue);
        else
        	setinactive(button_tue);
        
        if(mywakeup.wed == true)
        	setactive(button_wed);
        else
        	setinactive(button_wed);
        
        if(mywakeup.thu == true)
        	setactive(button_thu);
        else
        	setinactive(button_thu);
        
        if(mywakeup.fri == true)
        	setactive(button_fri);
        else
        	setinactive(button_fri);
        
        if(mywakeup.sat == true)
        	setactive(button_sat);
        else
        	setinactive(button_sat);
        
        if(mywakeup.sun == true)
        	setactive(button_sun);
        else
        	setinactive(button_sun);
        
        if(mywakeup.weeklyrepetition == true)
        	checkbox_weeklyrepetition.setChecked(true);
        else
        	checkbox_weeklyrepetition.setChecked(false);
	}
	
	void setactive(Button mybutton){
		mybutton.setBackgroundColor(Color.GREEN);
	}
	
	void setinactive(Button mybutton){
		mybutton.setBackgroundColor(Color.GRAY);
	}
	
	public void saveWakeupSettings(View view){
		 	
		 	mywakeup.fadeintime = Float.parseFloat(editfadeintime.getText().toString());
					 	
		 	mywakeup.hour = timepicker_wakeuptime.getCurrentHour();
		 	mywakeup.minute = timepicker_wakeuptime.getCurrentMinute();
		 	
		 	if(checkbox_weeklyrepetition.isChecked())
		 		mywakeup.weeklyrepetition = true;
		 	else
		 		mywakeup.weeklyrepetition = false;
		 	
		 	if(rbfadeinon.isChecked())
		 		mywakeup.fadein = true;
		 	else if(rbfadeinoff.isChecked())
		 		mywakeup.fadein = false;
		 	
		 	
		 	//if the user didnt pick any day, the actual day is taken
		 	if(mywakeup.sun || mywakeup.mon || mywakeup.tue || mywakeup.wed || mywakeup.thu || mywakeup.fri || mywakeup.sat)
		 		nodaypicked = false;
		 	
		 	if(nodaypicked = true){
		 		Calendar calNow = Calendar.getInstance();
		 		if(calNow.get(Calendar.DAY_OF_WEEK) == 1)
		 			mywakeup.sun = true;
		 		if(calNow.get(Calendar.DAY_OF_WEEK) == 2)
		 			mywakeup.mon = true;
		 		if(calNow.get(Calendar.DAY_OF_WEEK) == 3)
		 			mywakeup.tue = true;
		 		if(calNow.get(Calendar.DAY_OF_WEEK) == 4)
		 			mywakeup.wed = true;
		 		if(calNow.get(Calendar.DAY_OF_WEEK) == 5)
		 			mywakeup.thu = true;
		 		if(calNow.get(Calendar.DAY_OF_WEEK) == 6)
		 			mywakeup.fri = true;
		 		if(calNow.get(Calendar.DAY_OF_WEEK) == 7)
		 			mywakeup.sat = true;
		 	}
		 	
		 	if(mywakeup.active){
		 		Intent intent = new Intent(this, MainActivity.class);
		 		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
		 		setResult(2,intent);
		 		finish();
		 	}else{
		 		Intent intent = new Intent(this, MainActivity.class);
		 		mywakeup.active = true;
		 		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
		 		setResult(1,intent);
		 		finish();
		 	}
 	}  
	 
	 public void deleteWakeupSettings(View view){
		 // Before delete ,check if it's now active or not.
		   if(mywakeup.active == true){
		 		Intent intent = new Intent(this, MainActivity.class);
		 		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
		 		setResult(3,intent);
		   }
		 		finish();
		 	
	} 
	 
	 public void cancelWakeupSettings(View view){

	  		Intent intent = new Intent(this, MainActivity.class);
	  		intent.putExtra("SETTINGS_MESSAGE_WAKE", mywakeup);
	  		setResult(0,intent);
	  		finish();
	  	}
	 

	 
	 
 	@Override
    public void onConfigurationChanged(Configuration newConfig) {
    	// TODO Auto-generated method stub
    	super.onConfigurationChanged(newConfig);
    	//Log.i("-----MainActivity-----", "Call onConfigurationChanged");
    }
  
 	
}
