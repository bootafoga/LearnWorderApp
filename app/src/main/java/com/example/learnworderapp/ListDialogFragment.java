package com.example.learnworderapp;


import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class ListDialogFragment extends DialogFragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = this.getArguments();
        final int position = bundle.getInt("position");

        builder.setTitle(R.string.pick_action)
                .setItems(R.array.actions_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        changeWordByUserChoise(position, which);
                    }
                });

        return builder.create();
    }

    private void changeWordByUserChoise(int position, int userChoise) {
        SQLiteOpenHelper worderDatabaseHelper = new LearnWorderDatabaseHelper(getLayoutInflater().getContext());
        int currNum = 0;
        try {
            db = worderDatabaseHelper.getReadableDatabase(); // открытие базы на чтение
            cursor = db.query("WORDS",
                    new String[]{"_id", "WORD", "TRANSLATE"},
                    null, null, null, null, null);

            cursor.moveToFirst();
            while (currNum != position){
            cursor.moveToNext();
            currNum++;
            }

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getLayoutInflater().getContext(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (userChoise == 0){ //edit
            DialogFragment newFragment = new EditWordFragment();
            Bundle bundle = new Bundle();
            bundle.putString("word_key", cursor.getString(1));
            bundle.putString("translate_key", cursor.getString(2));
            bundle.putInt("id", cursor.getInt(0));
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "missiles");

            /*DialogFragment changeFragment = new EditWordFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            changeFragment.setArguments(bundle);
            changeFragment.show(getFragmentManager(), "missiles");*/
        } else {


        }

    }
}