package com.example.sleep.and.wake;

import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class WakeUpDatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "wakeUpManager";
 
    // Contacts table name
    private static final String TABLE_WAKEUP = "wakeup";
 
    // Contacts Table Columns names

	
    private static final String KEY_ID = "id";
    private static final String IS_FADE_IN = "isFadeIn";
    private static final String FADE_IN_TIME = "fade_in_time";
    private static final String  HOURS= "in_hours";
    private static final String  MINUTES = "in_minutes";
    private static final String  IS_ACTIVE ="is_active";
    private static final String  IS_OWNMUSIC ="is_ownmusic";		
   	private static final String  IS_ALARM ="is_alarm";	
    private static final String  IS_WKREP = "is_wkrep";
    private static final String  IS_MONDAY = "is_monday";
    private static final String  IS_TUESDAY = "is_tuesday";
    private static final String  IS_WEDNESDAY = "is_wednesday";
    private static final String  IS_THURSDAY = "is_thursday";
    private static final String  IS_FRIDAY = "is_friday";
    private static final String  IS_SATURDAY = "is_saturday";
    private static final String  IS_SUNDAY = "is_sunday";
    
    
    
    public WakeUpDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d("Databasehandler-----", "Call onCreate");
        String CREATE_WAKEUP_TABLE = "CREATE TABLE " + TABLE_WAKEUP +
        		"(" + KEY_ID + " INTEGER PRIMARY KEY," 
        		    + IS_FADE_IN + " BIT," 
        		    + FADE_IN_TIME+ " FLOAT,"
        		    + HOURS+ " INTEGER,"
        		    + MINUTES+ " INTEGER,"
        		    + IS_ACTIVE + " BIT," 
        		    + IS_OWNMUSIC + " BIT," 
        		    + IS_ALARM + " BIT," 
        		    + IS_WKREP + " BIT," 
        		    + IS_MONDAY + " BIT," 
        		    + IS_TUESDAY + " BIT," 
        		    + IS_WEDNESDAY + " BIT," 
        		    + IS_THURSDAY + " BIT," 
        		    + IS_FRIDAY + " BIT," 
        		    + IS_SATURDAY + " BIT," 
        		    + IS_SUNDAY + " BIT" 
        		    + ")";
        db.execSQL(CREATE_WAKEUP_TABLE);
        
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAKEUP);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new wake up setting
    void addSetting(WakeupSettings wakeUpSetting) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, wakeUpSetting.getID()); // key id
        values.put(IS_FADE_IN, boolToBit(wakeUpSetting.getIsFadeIn())); // fade in or not
        values.put(FADE_IN_TIME, wakeUpSetting.getFadeInTime()); // fade in time
        values.put(HOURS, wakeUpSetting.getHours()); // hours
        values.put(MINUTES, wakeUpSetting.getMinutes());
        values.put(IS_ACTIVE, boolToBit(wakeUpSetting.getIsActive()));
        values.put(IS_OWNMUSIC, boolToBit(wakeUpSetting.getIsOwnMusic()));
        values.put(IS_ALARM, boolToBit(wakeUpSetting.getIsAlarm()));
        values.put(IS_WKREP, boolToBit(wakeUpSetting.getIsWeekRep()));
        values.put(IS_MONDAY, boolToBit(wakeUpSetting.getIsMonday()));
        values.put(IS_TUESDAY, boolToBit(wakeUpSetting.getIsTuesday()));
        values.put(IS_WEDNESDAY, boolToBit(wakeUpSetting.getIsWednesday()));
        values.put(IS_THURSDAY, boolToBit(wakeUpSetting.getIsThursday()));
        values.put(IS_FRIDAY, boolToBit(wakeUpSetting.getIsFriday()));
        values.put(IS_SATURDAY, boolToBit(wakeUpSetting.getIsSaturday()));
        values.put(IS_SUNDAY, boolToBit(wakeUpSetting.getIsSunday()));
        
        // Inserting Row
        db.insert(TABLE_WAKEUP, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single panel
    WakeupSettings getSetting(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_WAKEUP, new String[] { KEY_ID,
                IS_FADE_IN, FADE_IN_TIME, HOURS,
                MINUTES,IS_ACTIVE,IS_OWNMUSIC,IS_ALARM,IS_WKREP,
                IS_MONDAY,IS_TUESDAY,IS_WEDNESDAY,IS_THURSDAY,
                IS_FRIDAY,IS_SATURDAY,IS_SUNDAY}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        // create object from wakeupsetting class.
        WakeupSettings wake = new WakeupSettings();
        wake.setID(Integer.parseInt(cursor.getString(0))); // set ID
        wake.setIsFadeIn((bitToBool(Integer.parseInt(cursor.getString(1))))); // set IsFadeIn
        wake.setFadeInTime(Float.parseFloat(cursor.getString(2))); // set fade in time
        wake.setHours(Integer.parseInt(cursor.getString(3))); // set hours
        wake.setMinutes(Integer.parseInt(cursor.getString(4))); // set minutes
        wake.setIsActive(bitToBool(Integer.parseInt(cursor.getString(5)))); // set active
        wake.setIsOwnMusic(bitToBool(Integer.parseInt(cursor.getString(6)))); // set is ownmusic 
        wake.setIsAlarm(bitToBool(Integer.parseInt(cursor.getString(7)))); // set is alarm
        wake.setIsWeekRep(bitToBool(Integer.parseInt(cursor.getString(8)))); // set is weekly repetition
        wake.setIsMonday(bitToBool(Integer.parseInt(cursor.getString(9)))); // set is monday
        wake.setIsTuesday(bitToBool(Integer.parseInt(cursor.getString(10)))); // set is monday
        wake.setIsWednesday(bitToBool(Integer.parseInt(cursor.getString(11)))); // set is monday
        wake.setIsThursday(bitToBool(Integer.parseInt(cursor.getString(12)))); // set is monday
        wake.setIsFriday(bitToBool(Integer.parseInt(cursor.getString(13)))); // set is monday
        wake.setIsSaturday(bitToBool(Integer.parseInt(cursor.getString(14)))); // set is monday
        wake.setIsSunday(bitToBool(Integer.parseInt(cursor.getString(15)))); // set is monday
        
        
        
        cursor.close();
        db.close();
        return wake;
    }
 
    // Getting All Contacts
    public List<WakeupSettings> getAllSettings() {
        List<WakeupSettings> wakeList = new ArrayList<WakeupSettings>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WAKEUP;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WakeupSettings wake = new WakeupSettings();
                wake.setID(Integer.parseInt(cursor.getString(0))); // set ID
                wake.setIsFadeIn((bitToBool(Integer.parseInt(cursor.getString(1))))); // set IsFadeIn
                wake.setFadeInTime(Float.parseFloat(cursor.getString(2))); // set fade in time
                wake.setHours(Integer.parseInt(cursor.getString(3))); // set hours
                wake.setMinutes(Integer.parseInt(cursor.getString(4))); // set minutes
                wake.setIsActive(bitToBool(Integer.parseInt(cursor.getString(5)))); // set active
                wake.setIsOwnMusic(bitToBool(Integer.parseInt(cursor.getString(6)))); // set is ownmusic 
                wake.setIsAlarm(bitToBool(Integer.parseInt(cursor.getString(7)))); // set is alarm
                wake.setIsWeekRep(bitToBool(Integer.parseInt(cursor.getString(8)))); // set is weekly repetition
                wake.setIsMonday(bitToBool(Integer.parseInt(cursor.getString(9)))); // set is monday
                wake.setIsTuesday(bitToBool(Integer.parseInt(cursor.getString(10)))); // set is monday
                wake.setIsWednesday(bitToBool(Integer.parseInt(cursor.getString(11)))); // set is monday
                wake.setIsThursday(bitToBool(Integer.parseInt(cursor.getString(12)))); // set is monday
                wake.setIsFriday(bitToBool(Integer.parseInt(cursor.getString(13)))); // set is monday
                wake.setIsSaturday(bitToBool(Integer.parseInt(cursor.getString(14)))); // set is monday
                wake.setIsSunday(bitToBool(Integer.parseInt(cursor.getString(15)))); // set is monday
                
                // Adding contact to list
                wakeList.add(wake);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return wakeList;
    }
 
    // Updating wakeupSetting
    public int updateSetting(WakeupSettings wakeUpSetting) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, wakeUpSetting.getID()); // key id
        values.put(IS_FADE_IN, boolToBit(wakeUpSetting.getIsFadeIn())); // fade in or not
        values.put(FADE_IN_TIME, wakeUpSetting.getFadeInTime()); // fade in time
        values.put(HOURS, wakeUpSetting.getHours()); // hours
        values.put(MINUTES, wakeUpSetting.getMinutes());
        values.put(IS_ACTIVE, boolToBit(wakeUpSetting.getIsActive()));
        values.put(IS_OWNMUSIC, boolToBit(wakeUpSetting.getIsOwnMusic()));
        values.put(IS_ALARM, boolToBit(wakeUpSetting.getIsAlarm()));
        values.put(IS_WKREP, boolToBit(wakeUpSetting.getIsWeekRep()));
        values.put(IS_MONDAY, boolToBit(wakeUpSetting.getIsMonday()));
        values.put(IS_TUESDAY, boolToBit(wakeUpSetting.getIsTuesday()));
        values.put(IS_WEDNESDAY, boolToBit(wakeUpSetting.getIsWednesday()));
        values.put(IS_THURSDAY, boolToBit(wakeUpSetting.getIsThursday()));
        values.put(IS_FRIDAY, boolToBit(wakeUpSetting.getIsFriday()));
        values.put(IS_SATURDAY, boolToBit(wakeUpSetting.getIsSaturday()));
        values.put(IS_SUNDAY, boolToBit(wakeUpSetting.getIsSunday()));
        
 
        // updating row
        return db.update(TABLE_WAKEUP, values, KEY_ID + " = ?",
                new String[] { String.valueOf(wakeUpSetting.getID()) });
    }
 
    // Deleting single contact
    public void deleteSetting(WakeupSettings wake) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WAKEUP, KEY_ID + " = ?",
                new String[] { String.valueOf(wake.getID()) });
        db.close();
    }
 
    // Getting contacts Count
    public int getSettingsCount() {
    	
    	Log.d("Databasehandler", "Call getSettingsCount Debug0");
        String countQuery = "SELECT  * FROM " + TABLE_WAKEUP;
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