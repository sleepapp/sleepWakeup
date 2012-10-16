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

public class SleepPanel extends ControlPanel {
		
	private static final long serialVersionUID = 2L;

	public MainActivity mainactivity;
	SleepSettings settings;
	
	int id;
	int requestCode;

		
	public SleepPanel(Context context,MainActivity mymain) {
		super(context);
		mainactivity = mymain;
		settings = new SleepSettings();		
		initializeButtons(context);
		setInactive(linear_layout);
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
		
		// Set the buttons size 80x80 pixels 
		// Maybe we can set the size of buttons corresponding to different screen sizes. etc.
		// Somehow it's possible to read the actual screen size -> but i don't know it now .:D
		button_left_active.setLayoutParams(new LinearLayout.LayoutParams(80,80));
		button_right_active.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,80));
		button_left_shade.setLayoutParams(new LinearLayout.LayoutParams(80,80));
		button_right_shade.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,80));
		
		
		//In the case of an inactive button
		button_right_shade.setOnClickListener(new OnClickListener() {  
			public void onClick(View view) { 
				sleep();
			}
		});
		
		//In the case of an active button
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
		if(settings.fadeout)
			button_right_active.setText("Fadeout: ON "+Float.toString(settings.fadeouttime)+" Minutes");
		else
			button_right_active.setText("Fadeout: OFF");
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