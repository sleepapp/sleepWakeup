package com.example.sleep.and.wake;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ControlPanel{

	Button button_left_active;
	Button button_right_active;
	Button button_left_shade;
	Button button_right_shade;
	LinearLayout linear_layout;
	
	public ControlPanel(Context context) {
		linear_layout = new LinearLayout(context);
		linear_layout.setOrientation(0);
		linear_layout.setGravity(17);
	}
	
	//activate in this case means, make it visual -> not to mix up with the "active" variable
	public void activate(ViewGroup view){
		view.removeView(linear_layout);
		view.addView(linear_layout);
		
	}
	
	//remove panel from current view
	public void remove(ViewGroup view){
		view.removeView(linear_layout);
		
	}
	
}