package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.os.SystemClock;
import android.app.Activity;
import android.os.Bundle;

public class WakeupPanel extends ControlPanel {
	
	public MainActivity mainactivity;
	WakeupSettings settings;
	WakeupBroadcastReceiver wakeupreceiver;
	PendingIntent pendingintent;
	Intent wakeupintent;
	AlarmManager wakeupalarmmanager;
	int id;
	int requestCode;
		
	public WakeupPanel(Context context,MainActivity mymain,int myid) {
		super(context);
		settings = new WakeupSettings();
		wakeupreceiver = new WakeupBroadcastReceiver();
		mainactivity = mymain;
		pendingintent = null;
		wakeupintent = null;
		wakeupalarmmanager = null;
		id = myid;
		initializeButtons(context);
		setInactive(linear_layout);
	}
	
	public void initializeButtons(Context context){
		button_left_active = new Button(context);
		button_right_active = new Button(context);
		button_left_shade = new Button(context);
		button_right_shade = new Button(context);

						
		button_left_active.setBackgroundResource(R.drawable.buttontestwake120x120);
		button_right_active.setBackgroundResource(R.drawable.buttontestwake600x120);
		button_left_shade.setBackgroundResource(R.drawable.buttontestwakeshade120x120);
		button_right_shade.setBackgroundResource(R.drawable.buttontestwakeshade600x120);
				

//		button_left_active.setBackgroundResource(R.raw.buttontestwake120x120);
//		button_right_active.setBackgroundResource(R.raw.buttontestwake600x120);
//		button_left_shade.setBackgroundResource(R.raw.buttontestwakeshade120x120);
//		button_right_shade.setBackgroundResource(R.raw.buttontestwakeshade600x120);

		
		// Set the buttons size 80x80 pixels 
		// Maybe we can set the size of buttons corresponding to screen size of smart phone. etc.
		// Somehow is possible to read the actual screen size -> but i don't know it now .
		button_left_active.setLayoutParams(new LinearLayout.LayoutParams(80,80));
		button_right_active.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,80));
		button_left_shade.setLayoutParams(new LinearLayout.LayoutParams(80,80));
		button_right_shade.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,80));
		
		
		
		
		button_right_shade.setOnClickListener(new OnClickListener() {  
			public void onClick(View view) { 
				wakeup();
			}
		});
		
		button_right_active.setOnClickListener(new OnClickListener() {  
	   	    public void onClick(View view) { 
	   	    	wakeup();
	   	    }
		});
		
		button_left_active.setOnClickListener(new OnClickListener() {  
	   	    public void onClick(View view) { 
	   	    	if(settings.alarmstate == false){//if off start alarm, if on stop it
	   	    		startwakeup();
	   	    		settings.alarmstate = true;
	   	    	}
	   	    	else{
	   	    		settings.alarmstate = false;
	   	    		stopwakeup();	   	    		
	   	    	}
	   	    }
		});
		
				
	}
	
	public void wakeup(){
		mainactivity.wakeupSettings(this);
	}
	
	public void startwakeup(){
		mainactivity.startwakeup(this);
	}
	
	public void stopwakeup(){
		mainactivity.stopwakeup(this);
	}
	
	public void showSettings(){
		String tmpstring = "";
		if(settings.fadein)
			tmpstring += "Fadein: ON "+Float.toString(settings.fadeintime)+" Minutes";
		else
			tmpstring += "Fadein: OFF";
		button_right_active.setTextSize(10);
		button_right_active.setText(tmpstring);
	}
	
	public void setActive(){
		linear_layout.removeView(button_left_shade);
		linear_layout.removeView(button_right_shade);
		linear_layout.addView(button_left_active);
		linear_layout.addView(button_right_active);
		
	}
	
	public void setInactive(ViewGroup view){
		view.addView(button_left_shade);
		view.addView(button_right_shade);
	}
		
}