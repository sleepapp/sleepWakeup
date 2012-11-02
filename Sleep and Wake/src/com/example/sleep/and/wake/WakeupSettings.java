package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class WakeupSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	boolean fadein; //fadein on or off
	Calendar wakeuptime; //when should alarm clock ring
	float fadeintime; //how long should fadein take
	boolean ownmusic; //should alarm tone be own music or default (false default, true own)
	boolean active; //shows if wakeup is active
	boolean mon,tue,wed,thu,fri,sat,sun;
	boolean weeklyrepetition;
	boolean alarmstate; //used to check if the alarm is started or not
		
	@SuppressWarnings("deprecation")
	public WakeupSettings(){
		wakeuptime = null;
		fadein = false;
		fadeintime = 15.0f;
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
}
