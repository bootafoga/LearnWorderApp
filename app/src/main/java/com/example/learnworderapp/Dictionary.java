package com.example.learnworderapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Dictionary extends AppCompatActivity {

    private ConnectDatabase connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        connect = new ConnectDatabase(this);

        int num_page;
        if (getIntent().getExtras() == null ) num_page = 0;
        else num_page = getIntent().getExtras().getInt("num_page");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        SectionsPagerAdapter2 pagerAdapter = new SectionsPagerAdapter2(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(num_page);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(this, AddWord.class);
        startActivity(intent);
    }

    private class SectionsPagerAdapter2 extends FragmentPagerAdapter {
        public SectionsPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            DictionaryFragment frag = new DictionaryFragment();
            switch (position) {
                case 0:
                    bundle.putString("type", "not_learned");
                    frag.setArguments(bundle);
                    return frag;
                case 1:
                    frag.setArguments(bundle);
                    bundle.putString("type", "learned");
                    return frag;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.not_learned) + " (" + connect.getCountNotLearned() + ")";
                case 1:
                    return getResources().getText(R.string.learned) + " (" + connect.getCountLearned() + ")";
            }
            return null;
        }
    }
}
