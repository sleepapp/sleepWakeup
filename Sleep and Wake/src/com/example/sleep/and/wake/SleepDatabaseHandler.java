package com.example.sleep.and.wake;

import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class SleepDatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "sleepManager";
 
    // Contacts table name
    private static final String TABLE_SLEEP = "sleep";
 
    // Contacts Table Columns names

	
    private static final String KEY_ID = "id";
    private static final String IS_FADE_OUT = "isFadeOut";
    private static final String FADE_OUT_TIME = "fade_out_time";
    private static final String  IS_ACTIVE ="is_active";
    private static final String  IS_OWNMUSIC ="is_ownmusic";
    private static final String IS_DISPLAY_FADE_OUT = "isDisplayFadeOut";

    
    
    
    public SleepDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d("Databasehandler-----", "Call onCreate");
        String CREATE_WAKEUP_TABLE = "CREATE TABLE " + TABLE_SLEEP +
        		"(" + KEY_ID + " INTEGER PRIMARY KEY," 
        		    + IS_FADE_OUT + " BIT," 
        		    + FADE_OUT_TIME+ " FLOAT,"
        		    + IS_ACTIVE + " BIT," 
        		    + IS_OWNMUSIC + " BIT," 
        		    + IS_DISPLAY_FADE_OUT + " BIT" 
        		    + ")";
        db.execSQL(CREATE_WAKEUP_TABLE);
        
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEP);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new sleep setting
    void addSetting(SleepSettings sleepSetting) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, sleepSetting.getID()); // key id
        values.put(IS_FADE_OUT, boolToBit(sleepSetting.getIsFadeOut())); // fade in or not
        values.put(FADE_OUT_TIME, sleepSetting.getFadeOutTime()); // fade in time
        values.put(IS_ACTIVE, boolToBit(sleepSetting.getIsActive()));
        values.put(IS_OWNMUSIC, boolToBit(sleepSetting.getIsOwnMusic()));
        values.put(IS_DISPLAY_FADE_OUT, boolToBit(sleepSetting.getDisplayFadeOut()));
 
        
        // Inserting Row
        db.insert(TABLE_SLEEP, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting a single sleep setting
    SleepSettings getSetting(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_SLEEP, new String[] { KEY_ID,
                IS_FADE_OUT, FADE_OUT_TIME,
                IS_ACTIVE,IS_OWNMUSIC,IS_DISPLAY_FADE_OUT}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        // create object from SleepSettings class.
        SleepSettings sleepTemp = new SleepSettings();
        sleepTemp.setID(Integer.parseInt(cursor.getString(0))); // set ID
        sleepTemp.setIsFadeOut((bitToBool(Integer.parseInt(cursor.getString(1))))); // set IsFadeIn
        sleepTemp.setFadeOutTime(Float.parseFloat(cursor.getString(2))); // set fade in time
        sleepTemp.setIsActive(bitToBool(Integer.parseInt(cursor.getString(3)))); // set active
        sleepTemp.setIsOwnMusic(bitToBool(Integer.parseInt(cursor.getString(4)))); // set is ownmusic 
        sleepTemp.setDisplayFadeOut(bitToBool(Integer.parseInt(cursor.getString(5)))); // set is display fade out
        
        
        
        cursor.close();
        db.close();
        return sleepTemp;
    }
 
    // Getting All SleepSettings
    public List<SleepSettings> getAllSettings() {
        List<SleepSettings> sleepList = new ArrayList<SleepSettings>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SLEEP;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SleepSettings sleepTemp = new SleepSettings();
                sleepTemp.setID(Integer.parseInt(cursor.getString(0))); // set ID
                sleepTemp.setIsFadeOut((bitToBool(Integer.parseInt(cursor.getString(1))))); // set IsFadeIn
                sleepTemp.setFadeOutTime(Float.parseFloat(cursor.getString(2))); // set fade in time
                sleepTemp.setIsActive(bitToBool(Integer.parseInt(cursor.getString(3)))); // set active
                sleepTemp.setIsOwnMusic(bitToBool(Integer.parseInt(cursor.getString(4)))); // set is ownmusic 
                sleepTemp.setDisplayFadeOut(bitToBool(Integer.parseInt(cursor.getString(5)))); // set is display fade out
             
                
                // Adding contact to list
                sleepList.add(sleepTemp);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return sleepList;
    }
 
    // Updating sleepSetting
    public int updateSetting(SleepSettings sleepSetting) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, sleepSetting.getID()); // key id
        values.put(IS_FADE_OUT, boolToBit(sleepSetting.getIsFadeOut())); // fade in or not
        values.put(FADE_OUT_TIME, sleepSetting.getFadeOutTime()); // fade in time
        values.put(IS_ACTIVE, boolToBit(sleepSetting.getIsActive()));
        values.put(IS_OWNMUSIC, boolToBit(sleepSetting.getIsOwnMusic()));
        values.put(IS_DISPLAY_FADE_OUT, boolToBit(sleepSetting.getDisplayFadeOut()));
        
 
        // updating row
        return db.update(TABLE_SLEEP, values, KEY_ID + " = ?",
                new String[] { String.valueOf(sleepSetting.getID()) });
    }
 
    // Deleting single contact
    public void deleteSetting(SleepSettings sleep) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SLEEP, KEY_ID + " = ?",
                new String[] { String.valueOf(sleep.getID()) });
        db.close();
    }
 
    // Getting contacts Count
    public int getSettingsCount() {
    	
    	Log.d("Databasehandler", "Call getSettingsCount Debug0");
        String countQuery = "SELECT  * FROM " + TABLE_SLEEP;
        Log.d("Databasehandler", "Call getSettingsCount Debug1");
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("Databasehandler", "Call getSettingsCount Debug2");
        Cursor cursor = db.rawQuery(countQuery, null);
        Log.d("Databasehandler", "Call getSettingsCount Debug3");
        int numOfSettings = cursor.getCount();
        cursor.close();
        Log.d("Databasehandler", "Call getSettingsCount Debug4");
        db.close();
        // return count
        return  numOfSettings;
    }
    
    public int boolToBit(boolean boolValue){
    	int bit =0;
    	if(boolValue==true){
    		bit = 1;
    	}else{
    		bit =0;
    	}
    	return bit;
    }
    
    public boolean bitToBool(int bitValue){
    	boolean bit = false;
    	if(bitValue==1){
    		bit = true;
    	}else{
    		bit = false;
    	}
    	return bit;
    }
 
}