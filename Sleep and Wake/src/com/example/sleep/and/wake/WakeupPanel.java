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

public class WakeupPanel extends ControlPanel implements Serializable {
		
	private static final long serialVersionUID = 2L;

	public MainActivity mainactivity;
	boolean fadein; //fadein on or off
	Date wakeuptime; //when should alarm clock ring
	float fadeintime; //how long should fadein take
	boolean ownmusic; //should alarm tone be own music or default (false default, true own)
	boolean active; //shows if wakeup is active
	
	int id;
	int requestCode;
		
	public WakeupPanel(Context context,MainActivity mymain) {
		super(context);
		mainactivity = mymain;
		fadein = false;
		wakeuptime = null;
		fadeintime = 15.0f;
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
		
		button_left_active.setBackgroundResource(R.raw.buttontestwake120x120);
		button_right_active.setBackgroundResource(R.raw.buttontestwake600x120);
		button_left_shade.setBackgroundResource(R.raw.buttontestwakeshade120x120);
		button_right_shade.setBackgroundResource(R.raw.buttontestwakeshade600x120);
		
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
		
	}
	
	public void wakeup(){
		mainactivity.wakeupSettings(this);
	}
	
	public void showSettings(){
		if(fadein)
			button_right_active.setText("Fadein: ON "+Float.toString(fadeintime)+" Minutes");
		else
			button_right_active.setText("Fadein: OFF");
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