package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Date;

public class WakeupObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean fadein; //fadein on or off
	Date wakeuptime; //when should alarm clock ring
	float fadeintime; //how long should fadein take
	boolean ownmusic; //should alarm tone be own music or default (false default, true own)
	boolean active; //shows if wakeup is active
	int id;
	
	public WakeupObject(int setid){
		fadein = false;
		wakeuptime = null;
		fadeintime = 15.0f;
		ownmusic = false;
		active = false;
		id = setid;
	}
}
