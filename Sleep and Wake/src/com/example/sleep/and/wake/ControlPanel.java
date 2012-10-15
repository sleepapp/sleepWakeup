package com.example.sleep.and.wake;

import java.io.Serializable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ControlPanel{
		
	private static final long serialVersionUID = 1L;
	Button button_left_active;
	Button button_right_active;
	Button button_left_shade;
	Button button_right_shade;
	LinearLayout linear_layout;
	
	public ControlPanel(Context context) {
		linear_layout = new LinearLayout(context);
		linear_layout.setOrientation(0);
	}
	
	public void activate(ViewGroup view){
		view.removeView(linear_layout);
		view.addView(linear_layout);
		//evtl. hier irgendwie setContentView() aufrufen ?!?
	}
	
	public void remove(ViewGroup view){
		view.removeView(linear_layout);
		//ControlPanel muss hier gelöscht werden, so dass keine Reste übrig bleiben evtl.
	}
	
}