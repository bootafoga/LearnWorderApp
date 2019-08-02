package com.example.learnworderapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ListDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = this.getArguments();
        final int position = bundle.getInt("position");

        builder.setTitle(R.string.pick_action)
                .setItems(R.array.actions_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        changeWordByUserChoice(position, which);
                    }
                });

        return builder.create();
    }

    private void changeWordByUserChoice(int position, int userChoise) {
        SQLiteDatabase db;
        Cursor cursor;

        ConnectDatabase connect = new ConnectDatabase(getLayoutInflater().getContext());
        db = connect.getDbWritable();
        cursor = connect.getCursorAll();

        int currNum = 0;
        cursor.moveToFirst();
        while (currNum != position){
            cursor.moveToNext();
            currNum++;
        }

        if (userChoise == 0){ //edit
            DialogFragment newFragment = new EditWordFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", cursor.getInt(0));
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "missiles");
        } else if (userChoise == 1) { // delete
            db.delete("WORDS", "_id = ?", new String[]{Integer.toString(cursor.getInt(0))});
            Intent intent = new Intent(getContext(), Dictionary.class);
            intent.putExtra("delete", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            getActivity().overridePendingTransition(0,0);
        } else { //replace to learned

        }

    }
}