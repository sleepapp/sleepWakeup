package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Date;

public class SleepSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	boolean fadeout; //fadein on or off
	float fadeouttime; //how long should fadein take
	boolean ownmusic; //should alarm tone be own music or default (false default, true own)
	boolean active;
		
	public SleepSettings(boolean fadeoutext,float fadeouttimeext,boolean ownmusicext,boolean activeext){
		fadeout = fadeoutext; 
		fadeouttime = fadeouttimeext; 
		ownmusic = ownmusicext; 
		active = activeext;
		
	}
}
