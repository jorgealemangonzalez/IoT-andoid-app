package com.jorge.arnau.iot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_COLUMN_ID = "id";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Init courses table
        db.execSQL(
                "create table courses" +
                        "(id integer primary key AUTOINCREMENT, RFID text, start_date datetime, end_date datetime)"
        );

    }

    public void resetDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS courses");
        this.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS courses");
        onCreate(db);
    }

    public void insertDevice(String RFID, LocalDateTime dateTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RFID", RFID);
        contentValues.put("start_date", dateTime.toString());
        db.insert("courses", null, contentValues);
    }

    public void updateDeviceEndTime(String RFID, LocalDateTime dateTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("end_date", dateTime.toString());
        db.update("courses", contentValues, "RFID = ? and end_date is null", new String[] { RFID } );

    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from courses", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Course course = new Course(
                    res.getString(res.getColumnIndex("id")),
                    res.getString(res.getColumnIndex("RFID")),
                    res.getString(res.getColumnIndex("start_date")),
                    res.getString(res.getColumnIndex("end_date")));
            array_list.add(course);
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Course> getEndedCourses() {
        ArrayList<Course> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from courses where end_date is not null", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Course course = new Course(
                    res.getString(res.getColumnIndex("id")),
                    res.getString(res.getColumnIndex("RFID")),
                    res.getString(res.getColumnIndex("start_date")),
                    res.getString(res.getColumnIndex("end_date")));
            array_list.add(course);
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Course> getNotEndedCourses() {
        ArrayList<Course> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from courses where end_date is null", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Course course = new Course(
                    res.getString(res.getColumnIndex("id")),
                    res.getString(res.getColumnIndex("RFID")),
                    res.getString(res.getColumnIndex("start_date")),
                    res.getString(res.getColumnIndex("end_date")));
            array_list.add(course);
            res.moveToNext();
        }
        return array_list;
    }
}
