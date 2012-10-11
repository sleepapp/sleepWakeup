package com.example.sleep.and.wake;

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
import android.widget.TextView;

public class MainActivity extends Activity {
	int requestCode;
	public int numberofwakeups = 0;
	public static WakeupObject wakeups[] = new WakeupObject[5];
			
	Button wake[] = new Button[5];
	Button waketext[] = new Button[5];
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        for(int i=0;i<5;i++)
        	wakeups[i] = new WakeupObject(i);
        
        setupButtons();
        
        waketext[0].setOnClickListener(new OnClickListener() {  
       	    public void onClick(View view) { 
            	wakeupSettings(view,0);
            }  
        });
        waketext[1].setOnClickListener(new OnClickListener() {  
       	    public void onClick(View view) { 
            	wakeupSettings(view,1);
            }  
        });
        waketext[2].setOnClickListener(new OnClickListener() {  
       	    public void onClick(View view) { 
            	wakeupSettings(view,2);
            }  
        });
        waketext[3].setOnClickListener(new OnClickListener() {  
       	    public void onClick(View view) { 
            	wakeupSettings(view,3);
            }  
        });
        waketext[4].setOnClickListener(new OnClickListener() {  
       	    public void onClick(View view) { 
            	wakeupSettings(view,4);
            }  
        });
    }
   
    void setupButtons(){
    	wake[0] = (Button) findViewById(R.id.button_wakeup0);
    	wake[1] = (Button) findViewById(R.id.button_wakeup1);
    	wake[2] = (Button) findViewById(R.id.button_wakeup2);
    	wake[3] = (Button) findViewById(R.id.button_wakeup3);
    	wake[4] = (Button) findViewById(R.id.button_wakeup4);
    	waketext[0] = (Button) findViewById(R.id.button_textwakeup0);
    	waketext[1] = (Button) findViewById(R.id.button_textwakeup1);
    	waketext[2] = (Button) findViewById(R.id.button_textwakeup2);
    	waketext[3] = (Button) findViewById(R.id.button_textwakeup3);
    	waketext[4] = (Button) findViewById(R.id.button_textwakeup4);
    	
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	WakeupObject tmpwakeup ;
    	WakeupObject nextwakeup;
        tmpwakeup= (WakeupObject)data.getExtras().getSerializable("SETTINGS_MESSAGE_WAKE");
        wakeups[tmpwakeup.id] = tmpwakeup;        
        if(tmpwakeup.active == true){
        	if(tmpwakeup.id<=5)
        		setwakeoutput(tmpwakeup.id);
        	if(tmpwakeup.id<4 ){
        		nextwakeup=wakeups[tmpwakeup.id+1];
        		if(nextwakeup.active == false)
        			setnextwakelayout(tmpwakeup.id+1);
        	}
        }else{
        	numberofwakeups--;
        }
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText(Integer.toString(numberofwakeups));
    }
    
    
    public void wakeupSettings(View view,int i){
    	Intent intent = new Intent(this, WakeupsettingsActivity.class);
    	if(i==0)
    		intent.putExtra("MAIN_MESSAGE_WAKE", wakeups[0]);
    	if(i==1)
    		intent.putExtra("MAIN_MESSAGE_WAKE", wakeups[1]);
    	if(i==2)
    		intent.putExtra("MAIN_MESSAGE_WAKE", wakeups[2]);
    	if(i==3)
    		intent.putExtra("MAIN_MESSAGE_WAKE", wakeups[3]);
    	if(i==4)
    		intent.putExtra("MAIN_MESSAGE_WAKE", wakeups[4]);
  		  		
  		numberofwakeups++;
  		startActivityForResult(intent,requestCode);
  	}  
    public void setnextwakelayout(int i){
    	if(i==1){
    		wake[i] = (Button) findViewById(R.id.button_wakeup1);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup1);
    	}
    	if(i==2){
    		wake[i] = (Button) findViewById(R.id.button_wakeup2);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup2);
    	}if(i==3){
    		wake[i] = (Button) findViewById(R.id.button_wakeup3);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup3);
    	}if(i==4){
    		wake[i] = (Button) findViewById(R.id.button_wakeup4);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup4);
    	}
    		wake[i].setVisibility(0);
    		waketext[i].setVisibility(0);
    	
    }
    public void setwakeoutput(int i){
    	String wakemsg = "";
    	if(i==0){
    		wake[i] = (Button) findViewById(R.id.button_wakeup0);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup0);
    	}
    	if(i==1){
    		wake[i] = (Button) findViewById(R.id.button_wakeup1);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup1);
    	}
    	if(i==2){
    		wake[i] = (Button) findViewById(R.id.button_wakeup2);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup2);
    	}
    	if(i==3){
    		wake[i] = (Button) findViewById(R.id.button_wakeup3);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup3);
    	}if(i==4){
    		wake[i] = (Button) findViewById(R.id.button_wakeup4);
    		waketext[i] = (Button) findViewById(R.id.button_textwakeup4);
    	}
		wake[i].setBackgroundColor(Color.argb(255, 255, 0, 0));
		waketext[i].setBackgroundColor(Color.argb(255, 30, 144, 255));
		if(wakeups[i].fadein){
			wakemsg += "FadeIn: ON";
			wakemsg += ", FadeIn Time:"+Float.toString(wakeups[i].fadeintime);
		}
		else
			wakemsg += "FadeIn: OFF";
		
		waketext[i].setText(wakemsg);
    }
      
}
