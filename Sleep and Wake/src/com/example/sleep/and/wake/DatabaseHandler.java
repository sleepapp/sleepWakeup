package com.example.sleep.and.wake;

import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
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
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d("Databasehandler-----", "Call onCreate");
        String CREATE_WAKEUP_TABLE = "CREATE TABLE " + TABLE_WAKEUP +
        		"(" + KEY_ID + " INTEGER PRIMARY KEY," 
        		    + IS_FADE_IN + " BOOLEAN," 
        		    + FADE_IN_TIME+ " FLOAT"
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
        values.put(IS_FADE_IN, wakeUpSetting.getIsFadeIn()); // fade in or not
        values.put(FADE_IN_TIME, wakeUpSetting.getFadeInTime()); // fade in time
 
        // Inserting Row
        db.insert(TABLE_WAKEUP, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single panel
    WakeupSettings getSetting(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_WAKEUP, new String[] { KEY_ID,
                IS_FADE_IN, FADE_IN_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        WakeupSettings wake = new WakeupSettings(Integer.parseInt(cursor.getString(0)),Boolean.parseBoolean(cursor.getString(1)), Float.parseFloat(cursor.getString(2)));
        // return contact
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
                wake.setID(Integer.parseInt(cursor.getString(0)));
                wake.setIsFadeIn(Boolean.parseBoolean(cursor.getString(1)));
                wake.setFadeInTime(Float.parseFloat(cursor.getString(2)));
                // Adding contact to list
                wakeList.add(wake);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return wakeList;
    }
 
    // Updating single contact
    public int updateWakeUpSetting(WakeupSettings wake) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(IS_FADE_IN, wake.getIsFadeIn());
        values.put(FADE_IN_TIME, wake.getFadeInTime());
 
        // updating row
        return db.update(TABLE_WAKEUP, values, KEY_ID + " = ?",
                new String[] { String.valueOf(wake.getID()) });
    }
 
    // Deleting single contact
    public void deleteWakeUpSetting(WakeupSettings wake) {
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
 
}