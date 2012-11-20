package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.SystemClock;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainActivity extends Activity {
	int requestCodeWakeup = 0;
	int requestCodeSleep = 1;
	LinearLayout linearlayout_wake;
	LinearLayout linearlayout_sleep;
	LinearLayout linearlayout_main;
	WakeupPanel mywakeup;
	SleepPanel mysleep;
	boolean playerisready = true;
	boolean firsttime=true;
	int globalidcounter = 0;
	List<WakeupSettings> wakeUpList;
	List<SleepSettings> sleepList;
	
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
               
        
        /****Get the global ID******/
        
        globalidcounter = LoadPreferences("GlobalID");
        Log.d("MainActivity GlobalID", Integer.toString(globalidcounter));
        
       
        /************** Wakeup Panels**************/
        
        // Create Database table for Wakeupsettings Objects
        
        WakeUpDatabaseHandler wakeDB = new WakeUpDatabaseHandler(this);
               
        WakeupSettings wakeTemp = null;
        
        
        // get number of wakeup settings
        
        int numOfWakeUpSettings = 0;
        
        numOfWakeUpSettings= wakeDB.getSettingsCount();
        
        Log.d("MainActivity NumOfWakeUps", Integer.toString(numOfWakeUpSettings));
        
        if(numOfWakeUpSettings >0){
        	// get all settings
        	wakeUpList=  wakeDB.getAllSettings();
        
      
        	for(int i=0;i<numOfWakeUpSettings;i++){
        		wakeTemp = wakeUpList.get(i);
        		// create wakeup panels with wakeupsettings objects
    			createNewWakeupPanel(wakeTemp);
        	}
        }
        
        //create to default(empty) panels
        createNewWakeupPanel();
        
        /**************** Sleep Panels******************/

        // create Database table for Sleepsettings objects 
        
        SleepDatabaseHandler sleepDB = new SleepDatabaseHandler(this);
      
       
        // get number of sleep settings
        int numOfSleepSettings = 0;
       
        numOfSleepSettings = sleepDB.getSettingsCount();
        
        SleepSettings sleepTemp = null;
        Log.d("MainActivity NumOfSleeps", Integer.toString(numOfSleepSettings));
        
        if(numOfSleepSettings >0){
        	// get all settings
        	sleepList=  sleepDB.getAllSettings();
        
      
        	for(int j=0;j<numOfSleepSettings;j++){
        		sleepTemp = sleepList.get(j);
        		// create wakeup panels with wakeupsettings objects
    			createNewSleepPanel(sleepTemp);
        	}
        }
        createNewSleepPanel();
        
        
        
        
        
        
    }
    
    public void onResume(){
    	super.onResume(); 
    	if(playerisready == false)
    		playerisready = true;
    	
    }

     @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	// TODO Auto-generated method stub
    	super.onConfigurationChanged(newConfig);
    	//Log.i("-----MainActivity-----", "Call onConfigurationChanged");
    }    


 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    		WakeupSettings mywakeupsettings;
    		WakeupPanel mywake;
    		SleepSettings mysleepsettings;
    		WakeUpDatabaseHandler wakeDB = new WakeUpDatabaseHandler(this);
    		SleepDatabaseHandler sleepDB = new SleepDatabaseHandler(this);
    	if(resultCode == 1){//WakeupControlpanel was saved by user and is now active & its a new created panel
    		mywakeupsettings = (WakeupSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		
    		// add wakeupsettting object to database 
    		wakeDB.addSetting(mywakeupsettings);
    		
    		mywakeup.settings = mywakeupsettings;
    		mywakeup.showSettings();
    		mywakeup.setActive();
    		createNewWakeupPanel();
    	}
    	if(resultCode == 2){//panel was already there but has been changed
    		mywakeupsettings = (WakeupSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mywakeup.settings = mywakeupsettings;
    		mywakeup.showSettings();
    		// update database table
    		wakeDB.updateSetting(mywakeupsettings);
    		setContentView(linearlayout_main);
    	}
    	if(resultCode == 3){
    		mywakeupsettings = (WakeupSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mywakeup.settings = mywakeupsettings;

    		if(mywakeup.settings.active){
        		// remove this object from database
    			stopwakeup(mywakeup);
        		wakeDB.deleteSetting(mywakeupsettings);
    			mywakeup.remove(linearlayout_wake);
    			setContentView(linearlayout_main);
    		}
    	}
    	if(resultCode == 4){//SleepControlpanel was saved by user and is now active & its a new created panel
    		mysleepsettings = (SleepSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mysleep.settings = mysleepsettings;
    		// add sleep setting to database
    		sleepDB.addSetting(mysleepsettings);
    		mysleep.showSettings();
    		mysleep.setActive();
    		createNewSleepPanel();
    	}
    	if(resultCode == 5){//panel was already there but has been changed
    		mysleepsettings = (SleepSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mysleep.settings = mysleepsettings;
    		// update database table
    		sleepDB.updateSetting(mysleepsettings);
    		mysleep.showSettings();
    		setContentView(linearlayout_main);
    	}
    	if(resultCode == 6){
    		mysleepsettings = (SleepSettings)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
    		mysleep.settings = mysleepsettings;
    		if(mysleep.settings.active){
    			// remove from database
    			sleepDB.deleteSetting(mysleepsettings);
    			SleepPanel tmp;
    			tmp = mysleep;
    			tmp.remove(linearlayout_sleep);
    			setContentView(linearlayout_main);
    		}
    	}
    	
    }
        
    public void createNewWakeupPanel(){
    	/***initialize the first wakeup and sleep panel***/
        WakeupPanel initialwakeup = new WakeupPanel(this,this,globalidcounter);
        globalidcounter++;
        initialwakeup.activate(linearlayout_wake);
        Context context = this.getApplicationContext();
        initialwakeup.wakeupintent = new Intent(context, WakeupBroadcastReceiver.class);
        //ToDo: add a sleep panel
        // save global ID
        
        SavePreferences("GlobalID", globalidcounter);
        
        setContentView(linearlayout_main);
    }
    
    // Create new wakeup panel with wakeupsettings object.
    public void createNewWakeupPanel(WakeupSettings wake){
    	/***initialize the first wakeup and sleep panel***/
        WakeupPanel initialwakeup = new WakeupPanel(this,this,wake.getID());
        initialwakeup.settings = wake;
        //globalidcounter++; // we don't need to update the global id (it was read from database)
        initialwakeup.activate(linearlayout_wake);
        Context context = this.getApplicationContext();
        initialwakeup.wakeupintent = new Intent(context, WakeupBroadcastReceiver.class);
        //ToDo: add a sleep panel
        
        setContentView(linearlayout_main);
        initialwakeup.showSettings();
        initialwakeup.setActive();
    }
    
    
    public void createNewSleepPanel(){
    	/***initialize the first wakeup and sleep panel***/
        final SleepPanel initialsleep = new SleepPanel(this,this,globalidcounter);
        globalidcounter++;
        initialsleep.activate(linearlayout_sleep);
        //ToDo: add a sleep panel
        // save global ID
        
        SavePreferences("GlobalID", globalidcounter);
        setContentView(linearlayout_main);
    }
    // Create new Sleep panel with sleepsetting
    public void createNewSleepPanel(SleepSettings sleep){
    	/***initialize the first wakeup and sleep panel***/
        final SleepPanel initialsleep = new SleepPanel(this,this,sleep.getID());
        initialsleep.settings = sleep;
        //globalidcounter++;
        initialsleep.activate(linearlayout_sleep);
        //ToDo: add a sleep panel
        
        setContentView(linearlayout_main);
        initialsleep.showSettings();
        initialsleep.setActive();
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
      
	 public void startwakeup(WakeupPanel tmpp){
		 mywakeup = tmpp;
		 Context context = this.getApplicationContext();
	     if(tmpp.wakeupreceiver != null){
	    	 if(tmpp.settings.weeklyrepetition == true)
	    		 tmpp.wakeupreceiver.SetAlarm(context,tmpp);
	    	 else
	    		 tmpp.wakeupreceiver.setOnetimeTimer(context,tmpp);
	     }else{
	      Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
	     }
	    	   	
	 }
	    
	 public void stopwakeup(WakeupPanel tmpp){
		 mywakeup = tmpp;
		 Context context = this.getApplicationContext();
	     if(tmpp.wakeupreceiver != null){
	    	 tmpp.wakeupreceiver.CancelAlarm(context,tmpp);
	     }else{
	      Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
	     }
	 }
	 
	 
	 
	private void SavePreferences(String key, int value){
		    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		    SharedPreferences.Editor editor = sharedPreferences.edit();
		    editor.putInt(key, value);    
		    editor.commit();
		   }
		  
	private int LoadPreferences(String key){
		 
		    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

		    int globalID  = sharedPreferences.getInt(key, 0);
		  
		    return globalID;
}
    
    
}
