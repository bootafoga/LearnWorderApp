package com.example.learnworderapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class LearnWorderDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "learnworder"; // Имя базы данных
    private static final int DB_VERSION = 1; // Версия базы данных

    public void getVritableDatabase(){

    }
        LearnWorderDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE WORDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "WORD TEXT, "
                    + "TRANSLATE TEXT,"
                    + "FLAG INTEGER); "
            );


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }