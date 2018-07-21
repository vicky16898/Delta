package com.example.vicky.imagehide.DatabasePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHolder extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "userInfo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String PERSON_TABLE_NAME = "user";
    public static final String PERSON_COLUMN_ID = "_id";
    public static final String PERSON_COLUMN_NAME = "name";
    public static final String PERSON_COLUMN_PASSWORD = "password";

    public static final String USER_TABLE_NAME = "userimages";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_NAME = "account";
    public static final String USER_COLUMN_TITLE = "title";
    public static final String USER_COLUMN_ENCRYPTED = "encrypted";
    public static final String USER_COLUMN_DATE = "date";





    public DatabaseHolder(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PERSON_TABLE_NAME + "(" +
                PERSON_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PERSON_COLUMN_NAME + " TEXT, " +
                PERSON_COLUMN_PASSWORD + " TEXT)"

        );

        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + "(" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                USER_COLUMN_NAME + " TEXT, " +
                USER_COLUMN_TITLE + " TEXT, "  + USER_COLUMN_ENCRYPTED + " TEXT, " + USER_COLUMN_DATE + " TEXT)"

        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUsers(String id , String user_name_db , String user_pass_db){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_ID , id);
        contentValues.put(PERSON_COLUMN_NAME , user_name_db);
        contentValues.put(PERSON_COLUMN_PASSWORD , user_pass_db);
        db.insert(PERSON_TABLE_NAME, null, contentValues);
        return true;



    }

    public boolean insertUserContent(String _id , String account_name , String title , String encrypted , String date ){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_ID , _id);
        contentValues.put(USER_COLUMN_NAME , account_name);
        contentValues.put(USER_COLUMN_TITLE , title);
        contentValues.put(USER_COLUMN_ENCRYPTED , encrypted);
        contentValues.put(USER_COLUMN_DATE , date);
        db.insert(USER_TABLE_NAME, null, contentValues);
        return true;



    }

    public Cursor getAll_users(){


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PERSON_TABLE_NAME, null );
        return res;
    }

    public Cursor getUser_data(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + USER_TABLE_NAME, null );
        return res;

    }

    public Integer deleteData(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE_NAME,
                USER_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Cursor getIndividual(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                USER_COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }

}
