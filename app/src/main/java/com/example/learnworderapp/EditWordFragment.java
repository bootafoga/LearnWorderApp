package com.example.learnworderapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private EditText newWord, newTranslate;
    private int id;
    private int num_page;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v;
        LayoutInflater inflater;
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");

        ConnectDatabase connect = new ConnectDatabase(getContext());
        db = connect.getDbWritable();
        cursor = connect.getCursorById(id);
        cursor.moveToFirst();
        num_page = cursor.getInt(3);

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
            }
        });

        return builder.create();
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
            intent.putExtra("num_page", num_page);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            getActivity().overridePendingTransition(0,0);

        } else {
            Toast toast = Toast.makeText(getLayoutInflater().getContext(), R.string.incorrect_input, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }
}
