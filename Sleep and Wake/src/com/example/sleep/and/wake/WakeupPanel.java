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

public class WakeupPanel extends ControlPanel {
		
	private static final long serialVersionUID = 2L;

	public MainActivity mainactivity;
	WakeupSettings settings;
	
	int id;
	int requestCode;
		
	public WakeupPanel(Context context,MainActivity mymain) {
		super(context);
		settings = new WakeupSettings();
		mainactivity = mymain;
		
		initializeButtons(context);
		setInactive(linear_layout);
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
		if(settings.fadein)
			button_right_active.setText("Fadein: ON "+Float.toString(settings.fadeintime)+" Minutes");
		else
			button_right_active.setText("Fadein: OFF");
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