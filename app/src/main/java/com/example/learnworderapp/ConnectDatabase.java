package com.example.learnworderapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ConnectDatabase {
    private Context context;
    private SQLiteDatabase dbWriable;
    private SQLiteDatabase dbReadable;

    public ConnectDatabase(Context context){
        this.context = context;
        setConnect();
    }

    private void setConnect(){
        SQLiteOpenHelper worderDatabaseHelper = new LearnWorderDatabaseHelper(context);
        try {
            dbWriable = worderDatabaseHelper.getWritableDatabase();
            dbReadable = worderDatabaseHelper.getReadableDatabase();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(context, R.string.database_unavailable, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public SQLiteDatabase getDbWritable() {
        return dbWriable;
    }

    public SQLiteDatabase getDbReadable() {
        return dbReadable;
    }

    public Cursor getCursorAll() {
        Cursor cursorAll;
        cursorAll = dbReadable.query("WORDS",
                new String[]{"_id", "WORD", "TRANSLATE"},
                null, null, null, null, null);
        return cursorAll;
    }

    public Cursor getCursorById(int id) {
        Cursor cursorById;
        cursorById = dbReadable.query("WORDS",
                new String[]{"_id", "WORD", "TRANSLATE"},
                "_id = ?", new String[] {Integer.toString(id)}, null, null, null);
        return cursorById;
    }
}
