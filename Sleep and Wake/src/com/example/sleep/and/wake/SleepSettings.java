package com.example.sleep.and.wake;

import java.io.Serializable;
import java.util.Date;

public class SleepSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	int id;
	boolean fadeout; //fadein on or off
	boolean displayfadeout;
	float fadeouttime; //how long should fadein take
	boolean ownmusic; //should alarm tone be own music or default (false default, true own)
	boolean active;
		
	public SleepSettings(){
		id = 1;		
		fadeout = false;
		displayfadeout = false;
		fadeouttime = 15.0f;
		ownmusic = false;
		active = false;
		
	}
	
	// Get Functions
		public int getID(){
			return this.id ;
		}
		
		// 
		public boolean getIsFadeOut(){
			return this.fadeout;
		}
		//
		public float getFadeOutTime(){
			
			return this.fadeouttime;
		}
		//
		public boolean getDisplayFadeOut(){
			
			return this.displayfadeout;
		}
		
		//
		public boolean getIsOwnMusic(){
			return this.ownmusic;
		}
		
		//
		public boolean getIsActive(){
			
			return this.active ;
		}
	
	//Set Functions
		public void setID(int idd){
			 this.id = idd ;
		}
		
		// 
		public void setIsFadeOut(boolean isfo){
			 this.fadeout = isfo;
		}
		//
		public void setFadeOutTime(float fot){
			
			this.fadeouttime= fot;
		}
		//
		public void setDisplayFadeOut(boolean displafo){
			
			this.displayfadeout= displafo;
		}
		
		//
		public void setIsOwnMusic(boolean ownmu){
			this.ownmusic= ownmu ;
		}
		
		//
		public void setIsActive(boolean at){
			
			this.active = at ;
		}
	
}
