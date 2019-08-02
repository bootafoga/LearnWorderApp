package com.example.learnworderapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddWord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickAddWord(View view) {

        TextView word = (TextView)findViewById(R.id.enterWord);
        TextView translate = (TextView)findViewById(R.id.enterTranslate);
        String usersWord = word.getText().toString();
        String usersTranslate = translate.getText().toString();

        SQLiteDatabase db;
        ConnectDatabase connect = new ConnectDatabase(this);
        db = connect.getDbWritable();

        if (!usersWord.equals("") && !usersTranslate.equals("")){
            ContentValues newWord = new ContentValues();
            newWord.put("WORD", usersWord);
            newWord.put("TRANSLATE", usersTranslate);
            db.insert("WORDS", null, newWord);

            Toast toast = Toast.makeText(this, R.string.done, Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, Dictionary.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, R.string.incorrect_input, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
