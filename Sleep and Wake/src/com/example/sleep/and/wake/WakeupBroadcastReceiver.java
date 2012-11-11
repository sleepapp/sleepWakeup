package com.example.sleep.and.wake;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.Serializable;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;


public class WakeupBroadcastReceiver extends BroadcastReceiver {
 
 final public static String WAKEUP = "wakeup";
 
// @Override
 public void onReceive(Context context, Intent intent) {
   PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         //Acquire the lock
         wl.acquire();
         	//get the intent with the settings and start the wakeup activity
           	WakeupSettings mywakeup;
        	mywakeup = (WakeupSettings)intent.getSerializableExtra(WAKEUP);
        	Intent myintent = new Intent(context, WakeupactionActivity.class);
        	myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //this flag is needed to start a task out of an onReceive function
        	myintent.putExtra("FROM_BROADCAST", mywakeup);
        	context.startActivity(myintent);
                	 
         //Release the lock
         wl.release();
 }
 

 //sets the alarm if repetition is on
 public void SetAlarm(Context context,WakeupPanel mywakeup)
    {
	 
	 Calendar calNow = Calendar.getInstance();
	 Calendar calSet = (Calendar) calNow.clone();
	 		 	
	 	if(mywakeup.settings.sun)
	 		setAlarmRepeat(context,mywakeup,calNow,calSet,1);
	 	if(mywakeup.settings.mon)
	 		setAlarmRepeat(context,mywakeup,calNow,calSet,2);
	 	if(mywakeup.settings.tue)
	 		setAlarmRepeat(context,mywakeup,calNow,calSet,3);
	 	if(mywakeup.settings.wed)
	 		setAlarmRepeat(context,mywakeup,calNow,calSet,4);
	 	if(mywakeup.settings.thu)
	 		setAlarmRepeat(context,mywakeup,calNow,calSet,5);
	 	if(mywakeup.settings.fri)
	 		setAlarmRepeat(context,mywakeup,calNow,calSet,6);
	 	if(mywakeup.settings.sat)
	 		setAlarmRepeat(context,mywakeup,calNow,calSet,7);
    }
//sets the alarm if repetition is off, but still more days can be selected
 public void setOnetimeTimer(Context context, WakeupPanel mywakeup)
 	{
 	
	 	Calendar calNow = Calendar.getInstance();
	 	Calendar calSet = (Calendar) calNow.clone();
	 		 	
	 	if(mywakeup.settings.sun)
	 		setAlarmOnetime(context,mywakeup,calNow,calSet,1);
	 	if(mywakeup.settings.mon)
	 		setAlarmOnetime(context,mywakeup,calNow,calSet,2);
	 	if(mywakeup.settings.tue)
	 		setAlarmOnetime(context,mywakeup,calNow,calSet,3);
	 	if(mywakeup.settings.wed)
	 		setAlarmOnetime(context,mywakeup,calNow,calSet,4);
	 	if(mywakeup.settings.thu)
	 		setAlarmOnetime(context,mywakeup,calNow,calSet,5);
	 	if(mywakeup.settings.fri)
	 		setAlarmOnetime(context,mywakeup,calNow,calSet,6);
	 	if(mywakeup.settings.sat)
	 		setAlarmOnetime(context,mywakeup,calNow,calSet,7);
	 	
 	}
 
    public void CancelAlarm(Context context,WakeupPanel mywakeup)
    {
    	
        for(int i=0;i<7;i++){
        	if(mywakeup.wakeupalarmmanager[i] != null){
        		mywakeup.wakeupalarmmanager[i].cancel(mywakeup.pendingintent);
                mywakeup.pendingintent.cancel();
        		mywakeup.wakeupalarmmanager[i] = null;
        	}
        }
        //cancel the alarmmanager AND!!! the pendingintent (else the pendingintent will be stored and new configurations would not be taken into account)
        mywakeup.wakeupintent = null;
        mywakeup.pendingintent = null;
                
    }
 
  
    
    public void setAlarmRepeat(Context context,WakeupPanel mywakeup,Calendar calNow,Calendar calSet,int day){
    	long timediff = 0;
    	calSet.set(Calendar.DAY_OF_WEEK, day);//Sunday=1;Monday=2;...;Saturday=7
 		calSet.set(Calendar.HOUR_OF_DAY, mywakeup.settings.hour);
 		calSet.set(Calendar.MINUTE, mywakeup.settings.minute);
 		calSet.set(Calendar.SECOND, 0);
 		calSet.set(Calendar.MILLISECOND, 0);
 		
 		if(calSet.compareTo(calNow) > 0){
        //Today Set time not yet passed
 			timediff = calSet.getTimeInMillis() - calNow.getTimeInMillis();
 			Toast.makeText(context, String.valueOf(timediff)+" first if" , Toast.LENGTH_LONG).show();
 		}else{
        //Today Set time passed, count to next week
 			calSet.add(Calendar.DATE, 7);//here 7 has to added because its then the next week
 			timediff = calSet.getTimeInMillis() - calNow.getTimeInMillis();
 			Toast.makeText(context, String.valueOf(timediff)+" else" , Toast.LENGTH_LONG).show();
 		}
 		
 		//each panel has its own intent, pending intent and alarm manager
 		mywakeup.wakeupalarmmanager[day-1] = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
 		mywakeup.wakeupintent = new Intent(context, WakeupBroadcastReceiver.class);
 		mywakeup.wakeupintent.putExtra(WAKEUP, mywakeup.settings);
 		mywakeup.pendingintent = PendingIntent.getBroadcast(context, mywakeup.id, mywakeup.wakeupintent, 0);
     		
 		mywakeup.wakeupalarmmanager[day-1].setRepeating(AlarmManager.RTC_WAKEUP, calNow.getTimeInMillis()+timediff, 604800000 , mywakeup.pendingintent);
    }
    
    public void setAlarmOnetime(Context context,WakeupPanel mywakeup,Calendar calNow,Calendar calSet,int day){
    	long timediff = 0;
    	calSet.set(Calendar.DAY_OF_WEEK, day);//Sunday=1;Monday=2;...;Saturday=7
 		calSet.set(Calendar.HOUR_OF_DAY, mywakeup.settings.hour);
 		calSet.set(Calendar.MINUTE, mywakeup.settings.minute);
 		calSet.set(Calendar.SECOND, 0);
 		calSet.set(Calendar.MILLISECOND, 0);
 	
 		if(calSet.compareTo(calNow) > 0){
        //Today Set time not yet passed
 			timediff = calSet.getTimeInMillis() - calNow.getTimeInMillis();
 			Toast.makeText(context, String.valueOf(timediff)+" first if" , Toast.LENGTH_LONG).show();
 		}else{
        //Today Set time passed, count to next week
 			calSet.add(Calendar.DATE, 7);//here 7 has to added because its then the next week
 			timediff = calSet.getTimeInMillis() - calNow.getTimeInMillis();
 			Toast.makeText(context, String.valueOf(timediff)+" else" , Toast.LENGTH_LONG).show();
 		}
     		
 		mywakeup.wakeupalarmmanager[day-1] = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
 		mywakeup.wakeupintent = new Intent(context, WakeupBroadcastReceiver.class);
 		mywakeup.wakeupintent.putExtra(WAKEUP, mywakeup.settings);
 		mywakeup.pendingintent = PendingIntent.getBroadcast(context, mywakeup.id, mywakeup.wakeupintent, 0);
    
 		mywakeup.wakeupalarmmanager[day-1].set(AlarmManager.RTC_WAKEUP, calNow.getTimeInMillis()+timediff, mywakeup.pendingintent);
    }
    
}