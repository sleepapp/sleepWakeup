package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Date;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

public class SleepPanel extends ControlPanel implements Serializable {
		
	private static final long serialVersionUID = 2L;

	public MainActivity mainactivity;
	boolean fadeout; //fadein on or off
	float fadeouttime; //how long should fadein take
	boolean ownmusic; //should alarm tone be own music or default (false default, true own)
	boolean active; //shows if wakeup is active
	
	int id;
	int requestCode;
		
	public SleepPanel(Context context,MainActivity mymain) {
		super(context);
		mainactivity = mymain;
		fadeout = false;
		fadeouttime = 15.0f;
		ownmusic = false;
		active = false;
		
		initializeButtons(context);
		setInactive(this);
	}
	
	public void initializeButtons(Context context){
		button_left_active = new Button(context);
		button_right_active = new Button(context);
		button_left_shade = new Button(context);
		button_right_shade = new Button(context);
		
		button_left_active.setBackgroundResource(R.raw.buttontestsleep120x120);
		button_right_active.setBackgroundResource(R.raw.buttontestsleep600x120);
		button_left_shade.setBackgroundResource(R.raw.buttontestsleepshade120x120);
		button_right_shade.setBackgroundResource(R.raw.buttontestsleepshade600x120);
		
		button_right_shade.setOnClickListener(new OnClickListener() {  
			public void onClick(View view) { 
				sleep();
			}
		});
		
		button_right_active.setOnClickListener(new OnClickListener() {  
	   	    public void onClick(View view) { 
	   	    	sleep();
	   	    }
		});
		
	}
	
	public void sleep(){
		mainactivity.sleepSettings(this);
	}
	
	public void showSettings(){
		if(fadeout)
			button_right_active.setText("Fadeout: ON "+Float.toString(fadeouttime)+" Minutes");
		else
			button_right_active.setText("Fadeout: OFF");
	}
	
	public void setActive(){
		this.removeView(button_left_shade);
		this.removeView(button_right_shade);
		this.addView(button_left_active);
		this.addView(button_right_active);
		
	}
	
	public void setInactive(ViewGroup view){
		view.addView(button_left_shade);
		view.addView(button_right_shade);
	}
		
}