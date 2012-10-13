package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Date;

public class WakeupSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	boolean fadein; //fadein on or off
	Date wakeuptime; //when should alarm clock ring
	float fadeintime; //how long should fadein take
	boolean ownmusic; //should alarm tone be own music or default (false default, true own)
	boolean active; //shows if wakeup is active
		
	public WakeupSettings(boolean fadeinext,Date wakeuptimeext,float fadeintimeext,boolean ownmusicext,boolean activeext){
		fadein = fadeinext;
		wakeuptime = wakeuptimeext;
		fadeintime = fadeintimeext;
		ownmusic = ownmusicext;
		active = activeext;
		
	}
}
