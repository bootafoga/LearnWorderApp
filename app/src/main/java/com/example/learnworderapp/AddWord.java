package com.example.learnworderapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
        new AddNewWord().execute();
    }

    private class AddNewWord extends AsyncTask<Void, Void, Boolean>{

        String usersWord;
        String usersTranslate;
        SQLiteDatabase db;

        protected void onPreExecute() {

            TextView word = (TextView)findViewById(R.id.enterWord);
            TextView translate = (TextView)findViewById(R.id.enterTranslate);
            usersWord = word.getText().toString();
            usersTranslate = translate.getText().toString();

            ConnectDatabase connect = new ConnectDatabase(AddWord.this);
            db = connect.getDbWritable();
        }

        protected Boolean doInBackground(Void ... voids) {

            if (!usersWord.trim().equals("") && !usersTranslate.trim().equals("")){
                ContentValues newWord = new ContentValues();
                newWord.put("WORD", usersWord);
                newWord.put("TRANSLATE", usersTranslate);
                newWord.put("FLAG", 0);
                db.insert("WORDS", null, newWord);
                return true;
            } else {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            //Код, выполняемый при завершении задачи
            Toast toast;
            if (success) toast = Toast.makeText(AddWord.this, R.string.done, Toast.LENGTH_SHORT);
            else toast = Toast.makeText(AddWord.this, R.string.incorrect_input, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
