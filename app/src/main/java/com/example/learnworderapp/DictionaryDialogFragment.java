package com.example.learnworderapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.LinkedList;


public class DictionaryDialogFragment extends DialogFragment  {

    private SQLiteDatabase db;
    private Cursor cursor;
    private LinkedList<String> wordsInDictionary = new LinkedList<>();
    private LinkedList<String> translatesInDictionary = new LinkedList<>();
    private String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            type = bundle.getString("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView wordsRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_words, container, false);

        ConnectDatabase connect = new ConnectDatabase(inflater.getContext());
        db = connect.getDbReadable();
        if (type.equalsIgnoreCase("not_learned")) cursor = connect.getCursorAllNotLearned();
        else cursor = connect.getCursorAllLearned();

        while (cursor.moveToNext()){
            if (cursor != null) {
                wordsInDictionary.add(cursor.getString(1));
                translatesInDictionary.add(cursor.getString(2));
            }
        }

        WordsAdapter adapter = new WordsAdapter(wordsInDictionary, translatesInDictionary);
        wordsRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        wordsRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new WordsAdapter.Listener() {
            public void onClick(int position) {
                changeWord(position);
            }
        });
        return wordsRecycler;
    }

    public void changeWord(int position){
        DialogFragment newFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("type", type);
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
