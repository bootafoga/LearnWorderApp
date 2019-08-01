package com.example.learnworderapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditWordFragment  extends DialogFragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    private String wordKey, translateKey;
    private View v;
    private EditText newWord, newTranslate;
    private LayoutInflater inflater;
    private int id;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");

        contactDatabase();

        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.edit_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(v);

        newWord = (EditText) v.findViewById(R.id.newUsersWord);
        newWord.setText(cursor.getString(1));
        newTranslate = (EditText) v.findViewById(R.id.newUsersTranslate);
        newTranslate.setText(cursor.getString(2));

        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    editButton();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    //The problem starts
                    //How do I get the reference to the EditText

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return builder.create();
    }

    private void contactDatabase() {
        SQLiteOpenHelper worderDatabaseHelper = new LearnWorderDatabaseHelper(getContext());
        try {
            db = worderDatabaseHelper.getReadableDatabase();
            cursor = db.query("WORDS",
                    new String[]{"_id", "WORD", "TRANSLATE"},
                    "_id = ?", new String[] {Integer.toString(id)}, null, null, null);
            cursor.moveToFirst();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getLayoutInflater().getContext(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void editButton() {

        String newUsersWord = newWord.getText().toString();
        String newUsersTranslate = newTranslate.getText().toString();

        if (!newUsersTranslate.equalsIgnoreCase("") && !newUsersWord.equalsIgnoreCase("")){
            ContentValues newVal = new ContentValues();
            newVal.put("WORD", newUsersWord);
            newVal.put("TRANSLATE", newUsersTranslate);
            db.update("WORDS", newVal, "_id = ?", new String[]{Integer.toString(id)});

            Intent intent = new Intent(getContext(), Dictionary.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {

        }
    }

}
