package com.example.sleep.and.wake;

import java.io.Serializable;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	int requestCodeWakeup = 0;
	int requestCodeSleep = 1;
	LinearLayout linearlayout_wake;
	LinearLayout linearlayout_sleep;
	LinearLayout linearlayout_main;
	WakeupPanel mywakeup;
	SleepPanel mysleep;
	boolean playerisready = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        /***get layout for wakeup panels and for sleep panels***/
        linearlayout_wake = (LinearLayout) findViewById(R.id.linearlayout_wakeup);
        linearlayout_sleep = (LinearLayout) findViewById(R.id.linearlayout_sleep);
        linearlayout_main = (LinearLayout) findViewById(R.id.linearlayout_main);
               
        createNewWakeupPanel();
        createNewSleepPanel();
        
    }
    
    public void onResume(){
    	super.onResume(); 
    	if(playerisready == false)
    		playerisready = true;
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    		WakeupSettings mywakeupsettings;
    		WakeupPanel mywake;
    		SleepSettings mysleepsettings;
    	if(resultCode == 1){//WakeupControlpanel was saved by user and is now active & its a new created panel
    		mywakeupsettings = (WakeupSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mywakeup.settings = mywakeupsettings;
    		mywakeup.showSettings();
    		mywakeup.setActive();
    		createNewWakeupPanel();
    	}
    	if(resultCode == 2){//panel was already there but has been changed
    		mywakeupsettings = (WakeupSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mywakeup.settings = mywakeupsettings;
    		mywakeup.showSettings();
    		setContentView(linearlayout_main);
    	}
    	if(resultCode == 3){
    		mywakeupsettings = (WakeupSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mywakeup.settings = mywakeupsettings;
    		if(mywakeup.settings.active){
    			mywakeup.remove(linearlayout_wake);
    			setContentView(linearlayout_main);
    		}
    	}
    	if(resultCode == 4){//SleepControlpanel was saved by user and is now active & its a new created panel
    		mysleepsettings = (SleepSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mysleep.settings = mysleepsettings;
    		mysleep.showSettings();
    		mysleep.setActive();
    		createNewSleepPanel();
    	}
    	if(resultCode == 5){//panel was already there but has been changed
    		mysleepsettings = (SleepSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mysleep.settings = mysleepsettings;
    		mysleep.showSettings();
    		setContentView(linearlayout_main);
    	}
    	if(resultCode == 6){
    		mysleepsettings = (SleepSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mysleep.settings = mysleepsettings;
    		if(mysleep.settings.active){
    			SleepPanel tmp;
    			tmp = mysleep;
    			tmp.remove(linearlayout_sleep);
    			setContentView(linearlayout_main);
    		}
    	}
    	
    }
        
    public void createNewWakeupPanel(){
    	/***initialize the first wakeup and sleep panel***/
        WakeupPanel initialwakeup = new WakeupPanel(this,this);
        initialwakeup.activate(linearlayout_wake);
        //ToDo: add a sleep panel
        
        setContentView(linearlayout_main);
    }
    
    public void createNewSleepPanel(){
    	/***initialize the first wakeup and sleep panel***/
        final SleepPanel initialsleep = new SleepPanel(this,this);
        initialsleep.activate(linearlayout_sleep);
        //ToDo: add a sleep panel
        
        setContentView(linearlayout_main);
    }
    
    public void wakeupSettings(WakeupPanel tmpp){
    	mywakeup = tmpp;
    	Intent intent = new Intent(this, WakeupsettingsActivity.class);
    	intent.putExtra("MAIN_MESSAGE_WAKE", tmpp.settings);
    	startActivityForResult(intent,requestCodeWakeup);
  	}  
    public void sleepSettings(SleepPanel tmpp){
    	mysleep = tmpp;
    	Intent intent = new Intent(this, SleepsettingsActivity.class);
    	intent.putExtra("MAIN_MESSAGE_WAKE", tmpp.settings);
    	startActivityForResult(intent,requestCodeSleep);
  	}
    
    public void startsleep(SleepPanel tmpp){
    	if(playerisready){
    		playerisready = false;
    		Intent intent = new Intent(this, SleepactionActivity.class);
        	intent.putExtra("MAIN_MESSAGE_WAKE", tmpp.settings);
        	startActivityForResult(intent,requestCodeSleep);
    	}
    }
      
}
