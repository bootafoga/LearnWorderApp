package com.example.learnworderapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class LearnWorderDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "learnworder"; // Имя базы данных
    private static final int DB_VERSION = 1; // Версия базы данных

        LearnWorderDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE WORDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "WORD TEXT, "
                    + "TRANSLATE TEXT); "
            );
            /*ContentValues someWords = new ContentValues();
            someWords.put("WORD", "Cat1");
            someWords.put("TRANSLATE", "Кошка1");

            ContentValues someWords2 = new ContentValues();
            someWords2.put("WORD", "Cat2");
            someWords2.put("TRANSLATE", "Кошка2");

            ContentValues someWords3 = new ContentValues();
            someWords3.put("WORD", "Cat3");
            someWords3.put("TRANSLATE", "Кошка3");

            db.insert("WORDS", null, someWords);
            db.insert("WORDS", null, someWords2);
            db.insert("WORDS", null, someWords3);*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }