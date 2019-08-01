package com.example.learnworderapp;


import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class DictionaryFragment extends DialogFragment  {

    private SQLiteDatabase db;
    private Cursor cursor;

    ArrayList<String> wordsInDic = new ArrayList<>();
    ArrayList<String> transInDic = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView wordsRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_words, container, false);

        SQLiteOpenHelper worderDatabaseHelper = new LearnWorderDatabaseHelper(inflater.getContext());

        try {
            db = worderDatabaseHelper.getReadableDatabase(); // открытие базы на чтение
            cursor = db.query("WORDS",
                    new String[]{"_id", "WORD", "TRANSLATE"},
                    null, null, null, null, null);

            while (cursor.moveToNext()){
                if (cursor != null) {
                    wordsInDic.add(cursor.getString(1));
                    transInDic.add(cursor.getString(2));
                }
            }

            WordsAdapter adapter = new WordsAdapter(wordsInDic, transInDic);
            wordsRecycler.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            wordsRecycler.setLayoutManager(layoutManager);

            adapter.setListener(new WordsAdapter.Listener() {
                public void onClick(int position) {
                    changeWord(position);
                }
            });
            return wordsRecycler;
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(inflater.getContext(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

       public void changeWord(int position){
           DialogFragment newFragment = new ListDialogFragment();
           Bundle bundle = new Bundle();
           bundle.putInt("position", position);
           newFragment.setArguments(bundle);
           newFragment.show(getFragmentManager(), "missiles");
       }


    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

}
