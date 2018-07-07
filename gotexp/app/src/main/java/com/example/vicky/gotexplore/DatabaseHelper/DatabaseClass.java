package com.example.vicky.gotexplore.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseClass extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "got.dp";
    public static final String TABLE_NAME = "got_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "male";
    public static final String COL_4 = "spouse";
    public static final String COL_5 = "culture";
    public static final String COL_6 = "house";
    public static final String COL_7 = "dob";
    public static final String COL_8 = "dod";
    public static final String COL_9 = "imglink";

    public DatabaseClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,male TEXT, spouse TEXT,culture TEXT,house TEXT,dob TEXT,dod TEXT ,imglink TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String id ,String Name , String Gender , String Spouse , String Culture , String House , String DOB , String DOD , String Img){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1 , id);
        contentValues.put(COL_2 , Name);
        contentValues.put(COL_3 , Gender);
        contentValues.put(COL_4 , Spouse);
        contentValues.put(COL_5 , Culture);
        contentValues.put(COL_6 , House);
        contentValues.put(COL_7 , DOB);
        contentValues.put(COL_8 , DOD);
        contentValues.put(COL_9 , Img);


        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;


    }

    public Cursor getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COL_1 + "=?", new String[] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
