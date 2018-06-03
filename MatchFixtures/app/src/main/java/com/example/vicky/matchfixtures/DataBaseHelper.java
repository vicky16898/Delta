package com.example.vicky.matchfixtures;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "match.dp";
    public static final String TABLE_NAME = "match_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "team1";
    public static final String COL_3 = "team2";
    public static final String COL_4 = "date";
    public static final String COL_5 = "time";
    public static final String COL_6 = "venue";
    public static final String COL_7 = "image1";
    public static final String COL_8 = "image2";




    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,team1 TEXT,team2 TEXT, date TEXT,time TEXT,venue TEXT,image1 BLOB,image2 BLOB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean InsertData(String item0 ,String item1,String item2,String item3,String item4,String item5 ,byte[] bmarray1,byte[] bmarray2){

         SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,item0);
        contentValues.put(COL_2 , item1);
        contentValues.put(COL_3 , item2);
        contentValues.put(COL_4 , item3);
        contentValues.put(COL_5 , item4);
        contentValues.put(COL_6 , item5);
        contentValues.put(COL_7 , bmarray1);
        contentValues.put(COL_8 , bmarray2);


        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;


    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }


}

