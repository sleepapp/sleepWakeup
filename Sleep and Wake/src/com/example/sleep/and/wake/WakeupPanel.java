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
	AlarmManager wakeupalarmmanager[] = new AlarmManager[7];
	int id;
	int requestCode;
		
	public WakeupPanel(Context context,MainActivity mymain,int myid) {
		super(context);
		settings = new WakeupSettings();
		wakeupreceiver = new WakeupBroadcastReceiver();
		mainactivity = mymain;
		pendingintent = null;
		wakeupintent = null;
		for(int i=0;i<7;i++)
			wakeupalarmmanager[i] = null;
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
		tmpstring += "Waketime: " + settings.hour + "." + settings.minute +"\n";
		
		if(settings.fadein)
			tmpstring += "Fadein: "+Float.toString(settings.fadeintime)+" Minutes";
		else
			tmpstring += "Fadein: OFF";
		button_right_active.setTextSize(12);
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