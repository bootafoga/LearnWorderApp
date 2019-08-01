package com.example.learnworderapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle; import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v7.widget.ShareActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.design.widget.TabLayout;
import android.widget.Toast;


public class TranslateWords extends AppCompatActivity {

    private Cursor cursor;
    private SQLiteDatabase db;
    boolean empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_words);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        SQLiteOpenHelper worderDatabaseHelper = new LearnWorderDatabaseHelper(this);

        try {
            db = worderDatabaseHelper.getReadableDatabase(); // открытие базы на чтение
            cursor = db.query("WORDS",
                    new String[]{"_id", "WORD", "TRANSLATE"},
                    null, null, null, null, null);
            if (!cursor.moveToLast()){
            AllertMessage();
            empty = true;
            }

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void AllertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getLayoutInflater().getContext());
        builder.setTitle("Внимание!")
                .setMessage("Ваш словарь пуст, ничего выведено не будет. Вернитесь на главную страницу и добавьте слова")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            LearnWordsFragment frag = new LearnWordsFragment();

            switch (position) {
                case 0:
                    bundle.putString("type", "eng");
                    bundle.putBoolean("empty", empty);
                    frag.setArguments(bundle);
                    return frag;
                case 1:
                    bundle.putString("type", "rus");
                    bundle.putBoolean("empty", empty);
                    frag.setArguments(bundle);
                    return frag;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.eng_rus);
                case 1:
                    return getResources().getText(R.string.rus_eng);
            }
            return null;
        }
    }


}

