/*
package com.krintos.timetrackerai.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

*/
/**
 * Created by root on 7/3/18.
 *//*


public class SQLiteHandler extends SQLiteOpenHelper{
    public static final String DataBase_Name = "users.db";
    public static final String TABLE_USER = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_PHONE= "phone";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILEPHOTOLINK = "userphotolink";
    public Context context;
    public SQLiteHandler(Context context) {
        super(context, DataBase_Name, null, 1);
        this.context=context;


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COLLECTIONS = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PHONE + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_USERNAME + " TEXT"
                + KEY_TOKEN + " TEXT" + ");";
        db.execSQL(CREATE_COLLECTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USER);

        // Create tables again
        onCreate(db);
    }
    public boolean storeuser(String phone, String token, String TABLE_USER){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHONE,phone);
        values.put(KEY_TOKEN,token);
        long result = db.insert(TABLE_USER, null, values);
        db.close();
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

    }



    public Cursor getAllUserDatas(String tableUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+tableUser, null);
        return result;
    }
}
*/
