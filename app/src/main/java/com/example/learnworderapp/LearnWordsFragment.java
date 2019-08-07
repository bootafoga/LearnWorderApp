package com.example.learnworderapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LearnWordsFragment extends Fragment implements View.OnClickListener {

    private String type;
    private int imageTrue, imageFalse, bound;
    private Cursor cursor;
    private TextView word, translate;
    private ImageView pic;
    private boolean empty;
    ConnectDatabase connect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            type = bundle.getString("type");
            empty = bundle.getBoolean("empty");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout;
        Button check, nextWord, showTranslate;

        layout = inflater.inflate(R.layout.fragment_learn_words, container, false);

        check = (Button)layout.findViewById(R.id.checkTranslate);
        check.setOnClickListener(this);

        nextWord = (Button)layout.findViewById(R.id.nextWord);
        nextWord.setOnClickListener(this);

        showTranslate = (Button)layout.findViewById(R.id.showTranslate);
        showTranslate.setOnClickListener(this);

        word = (TextView)layout.findViewById(R.id.word);
        translate = (TextView) layout.findViewById(R.id.translate);

        pic = layout.findViewById(R.id.imageView);
        imageTrue = R.drawable.buttontrue;
        imageFalse = R.drawable.buttonfalse;

        connect = new ConnectDatabase(inflater.getContext());
        cursor = connect.getCursorAllNotLearned();

        if (!empty) {
            bound = connect.getCountNotLearned();
            cursor.moveToFirst();
            onClickNextWord();
        }
        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkTranslate:
                onClickCheckTranslate();
                break;
            case R.id.showTranslate:
                onClickShowTranslate();
                break;
            case R.id.nextWord:
                onClickNextWord();
                break;
        }
    }

    private void onClickCheckTranslate(){
        if (empty) return;
        String userInput;
        userInput = translate.getText().toString();

        if (type.equals("rus")){
            if (userInput.equalsIgnoreCase(cursor.getString(1)))
                pic.setImageResource(imageTrue);
            else pic.setImageResource(imageFalse);
        } else {
            if (userInput.equalsIgnoreCase(cursor.getString(2)))
                pic.setImageResource(imageTrue);
            else pic.setImageResource(imageFalse);
        }
    }

    private void onClickShowTranslate() {
        if (empty) return;
        if (type.equals("rus"))
            translate.setText(cursor.getString(1));
        else
            translate.setText(cursor.getString(2));
    }

    private void onClickNextWord(){
        if (empty) return;
        cursor.moveToFirst();
        pic.setImageResource(0);
        translate.setText("");
        Random r = new Random();

        int position = r.nextInt(bound);
        int current = 0;

        while (current != position){
            cursor.moveToNext();
            current++;
        }

        if (type.equals("eng"))
            word.setText(cursor.getString(1));
        else
            word.setText(cursor.getString(2));
    }
}