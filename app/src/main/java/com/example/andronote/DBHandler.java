package com.example.andronote;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper{
    private static final String TABLE_NAME = "TITLENOTE";
    private static final String COL_TITLE = "TITLE";
    private static final String COL_NOTE = "NOTE";


    public DBHandler(Context context) {
        super(context,"dbName",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE " + TABLE_NAME + " (" + COL_TITLE + " TEXT PRIMARY KEY," + COL_NOTE + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Inserting an item
    public void addItem(String tit, String not)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE,tit);
        values.put(COL_NOTE, not);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    //Fetched data stored in Cursor variable
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data =  db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return data;
    }

    // deleting the data.
    public void deleteItem(String LItem) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "TITLE=?", new String[]{LItem});
        db.close();
    }
}
