package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class WakeupSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	int id;
	boolean fadein; //fadein on or off
	float fadeintime; //how long should fadein take
	boolean ownmusic; //should alarm tone be own music or default (false default, true own)
	boolean active; //shows if wakeup is active
	boolean mon,tue,wed,thu,fri,sat,sun;
	boolean weeklyrepetition;
	boolean alarmstate; //used to check if the alarm is started or not
	int hour; //when should alarm clock ring
	int minute; //when should alarm clock ring
	
	
	public WakeupSettings(){
		id = 1;
		fadein = false;
		fadeintime = 0.30f;
		hour = 19;
		minute = 0;
		ownmusic = false;
		active = false;
		mon = false;
		tue = false;
		wed = false;
		thu = false;
		fri = false;
		sat = false;
		sun = false;
		weeklyrepetition = false;
		alarmstate = false;
		
	}
	
	public WakeupSettings(int idxt, boolean fadeinext,float fadeintimeext){
		this.id = idxt;
		this.fadein = fadeinext;
		this.fadeintime = fadeintimeext;
		this.ownmusic = false;
		this.active = false;
		

		
	}
	
	// Get Functions
	public int getID(){
		return this.id ;
	}
	
	// 
	public boolean getIsFadeIn(){
		return this.fadein;
	}
	//
	public float getFadeInTime(){
		
		return this.fadeintime;
	}
	
	//
	public int getHours(){
		return this.hour;
	}
	
	//
	public int getMinutes(){
		return this.minute ;
		
	}
	//
	public boolean getIsOwnMusic(){
		return this.ownmusic;
	}
	
	//
	public boolean getIsActive(){
		
		return this.active ;
	}
	
	//
	public boolean getIsWeekRep(){
		
		return this.weeklyrepetition ;
		
	}
	//
	public boolean getIsMonday(){
		
		return this.mon;
	}
	//
	public boolean getIsTuesday(){
		return this.tue;
	}
	
	//
	public boolean getIsWednesday(){
		return this.wed ;
	}
	//
	public boolean getIsThursday(){
		return this.thu;
	}
	
	//
	public boolean getIsFriday(){
		
		return this.fri; 
	}
	//
	public boolean getIsSaturday(){
		
		return this.sat;
	}
	// 
	public boolean getIsSunday(){
		return this.sun;
	}
	public boolean getIsAlarm(){
		return this.alarmstate ;
	}
	
	
	
	
	
	// Set Functions
	public void setID(int id){
		this.id =id;
	}
	//
	public void setIsFadeIn(boolean input){
		this.fadein = input;
	}
	
	public void setFadeInTime(float fadeInTime){
		this.fadeintime = fadeInTime;
	}
	public void setHours(int h){
		this.hour = h;
	}
	//
	public void setMinutes(int min){
		this.minute= min ;
		
	}
	//
	public void setIsOwnMusic(boolean mymu){
		this.ownmusic = mymu;
	}
	
	//
	public void setIsActive(boolean act){
		
		this.active = act ;
	}
	
	//
	public void setIsWeekRep(boolean wkr){
		
		this.weeklyrepetition = wkr ;
		
	}
	//
	public void setIsMonday(boolean monday){
		
		this.mon = monday;
	}
	//
	public void setIsTuesday(boolean tuesday){
		this.tue = tuesday;
	}
	
	//
	public void setIsWednesday(boolean wednesday){
		this.wed = wednesday ;
	}
	//
	public void setIsThursday(boolean thursday){
		this.thu = thursday;
	}
	
	//
	public void setIsFriday(boolean friday){
		
		this.fri = friday ; 
	}
	//
	public void setIsSaturday(boolean saturday){
		
		this.sat = saturday ;
	}
	// 
	public void setIsSunday(boolean sunday){
		this.sun = sunday;
	}
	
	public void setIsAlarm(boolean al){
		this.alarmstate = al ;
	}
	
	
}
