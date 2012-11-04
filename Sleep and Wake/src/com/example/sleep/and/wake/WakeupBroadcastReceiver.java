package com.example.sleep.and.wake;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;
 
public class WakeupBroadcastReceiver extends BroadcastReceiver {
 
 final public static String ONE_TIME = "onetime";
 
// @Override
 public void onReceive(Context context, Intent intent) {
   PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         //Acquire the lock
         wl.acquire();
 
         //You can do the processing here.
         Bundle extras = intent.getExtras();
         StringBuilder msgStr = new StringBuilder();
          
         if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
          //Make sure this intent has been sent by the one-time timer button.
          msgStr.append("One time Timer : ");
         }
         Format formatter = new SimpleDateFormat("hh:mm:ss a");
         msgStr.append(formatter.format(new Date()));
         
//         Intent myintent = new Intent(context, WakeupactionActivity.class);
//         myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//         intent.putExtra("FROM_BROADCAST", 1);
//         context.startActivity(myintent);
         
         Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
          
         //Release the lock
         wl.release();
 }
 
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
 
    public void CancelAlarm(Context context,WakeupPanel mywakeup)
    {
    	mywakeup.wakeupintent = new Intent(context, WakeupBroadcastReceiver.class);
        mywakeup.pendingintent = PendingIntent.getBroadcast(context, mywakeup.id, mywakeup.wakeupintent, 0);
        for(int i=0;i<7;i++){
        	if(mywakeup.wakeupalarmmanager[i] != null){
        		mywakeup.wakeupalarmmanager[i] = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                mywakeup.wakeupalarmmanager[i].cancel(mywakeup.pendingintent);
        		mywakeup.wakeupalarmmanager[i] = null;
        	}
        }
                
    }
 
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
        //Today Set time passed, count to tomorrow
 			calSet.add(Calendar.DATE, 7);//here 7 has to added because its then the next week
 			timediff = calSet.getTimeInMillis() - calNow.getTimeInMillis();
 			Toast.makeText(context, String.valueOf(timediff)+" else" , Toast.LENGTH_LONG).show();
 		}
    
 		mywakeup.wakeupalarmmanager[day-1] = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
 		//mywakeup.wakeupintent = new Intent(context, WakeupBroadcastReceiver.class);
 		mywakeup.wakeupintent.putExtra(ONE_TIME, Boolean.TRUE);
 		mywakeup.pendingintent = PendingIntent.getBroadcast(context, mywakeup.id, mywakeup.wakeupintent, 0);
     		
 		mywakeup.wakeupalarmmanager[day-1].setRepeating(AlarmManager.RTC_WAKEUP, /*System.currentTimeMillis()*/calNow.getTimeInMillis()+timediff, 604800000 , mywakeup.pendingintent);
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
        //Today Set time passed, count to tomorrow
 			calSet.add(Calendar.DATE, 7);//here 7 has to added because its then the next week
 			timediff = calSet.getTimeInMillis() - calNow.getTimeInMillis();
 			Toast.makeText(context, String.valueOf(timediff)+" else" , Toast.LENGTH_LONG).show();
 		}
    
 		mywakeup.wakeupalarmmanager[day-1] = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
 		//mywakeup.wakeupintent = new Intent(context, WakeupBroadcastReceiver.class);
 		mywakeup.wakeupintent.putExtra(ONE_TIME, Boolean.TRUE);
 		mywakeup.pendingintent = PendingIntent.getBroadcast(context, mywakeup.id, mywakeup.wakeupintent, 0);
    
 		mywakeup.wakeupalarmmanager[day-1].set(AlarmManager.RTC_WAKEUP, /*System.currentTimeMillis()*/calNow.getTimeInMillis()+timediff, mywakeup.pendingintent);
    }
    
}