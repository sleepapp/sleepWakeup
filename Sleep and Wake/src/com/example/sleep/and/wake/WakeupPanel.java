package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
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
	int id;
	int requestCode;
		
	public WakeupPanel(Context context,MainActivity mymain,int myid) {
		super(context);
		settings = new WakeupSettings();
		wakeupreceiver = new WakeupBroadcastReceiver();
		mainactivity = mymain;
		id = myid;
		settings.id =myid;
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
		
		String tmpstring = "";
		
		tmpstring += "tab to add new alarm clock";
		
		button_right_shade.setTextSize(12);
		button_right_shade.setTextColor(Color.WHITE);
		button_right_shade.setText(tmpstring);
		
		
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
	   	    		settings.alarmstate = true;
	   	    		setAlarmButton();
	   	    		startwakeup();
	   	    	}
	   	    	else{
	   	    		settings.alarmstate = false;
	   	    		setAlarmButton();
	   	    		stopwakeup();	   	    		
	   	    	}
	   	    }
		});
		
				
	}
			
	public void setAlarmButton(){
		if(settings.alarmstate == true){//if off start alarm, if on stop it
	    		linear_layout.removeView(button_left_active);
	    		linear_layout.removeView(button_right_active);
	    		button_left_active.setBackgroundResource(R.drawable.buttontestwake120x120on);
	    		linear_layout.addView(button_left_active);
	    		linear_layout.addView(button_right_active);
	    	}
	    	else{
	    		linear_layout.removeView(button_left_active);
	    		linear_layout.removeView(button_right_active);
	    		button_left_active.setBackgroundResource(R.drawable.buttontestwake120x120);
	    		linear_layout.addView(button_left_active);
	    		linear_layout.addView(button_right_active);
	    	}
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
		String alarmtime = "";
		String fadein = "";
		String days = "";
		if(settings.minute<10)
			alarmtime += "<big> "+settings.hour + ":0" + settings.minute +"</big>   ";
		else
			alarmtime += "<big> "+settings.hour + ":" + settings.minute +"</big>   ";
				
		days += "<small>";
		if(settings.mon)
			days += "mo ";
		else
			days += "<font color='#FF0000'>"+"mo "+"</font>";
		if(settings.tue)
			days += "tu ";
		else
			days += "<font color='#FF0000'>"+"tu "+"</font>";
		if(settings.wed)
			days += "we ";
		else
			days += "<font color='#FF0000'>"+"we "+"</font>";
		if(settings.thu)
			days += "th ";
		else
			days += "<font color='#FF0000'>"+"th "+"</font>";
		if(settings.fri)
			days += "fr ";
		else
			days += "<font color='#FF0000'>"+"fr "+"</font>";
		if(settings.sat)
			days += "sa ";
		else
			days += "<font color='#FF0000'>"+"sa "+"</font>";
		if(settings.sun)
			days += "su ";
		else
			days += "<font color='#FF0000'>"+"su "+"</font>";
		
		days += "</small><br />";
		
		int tmpfadeintime;
		tmpfadeintime = (int) settings.fadeintime;
		
		fadein += "<small>";
		if(settings.fadein)
			fadein += "Fadein: "+Integer.toString(tmpfadeintime)+" Minutes";
		else
			fadein += "Fadein: OFF";
		fadein += "</small>";
		button_right_active.setTextSize(16);
		button_right_active.setTextColor(Color.WHITE);
		button_right_active.setText(Html.fromHtml(alarmtime+days+fadein));
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