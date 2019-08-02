package com.example.learnworderapp;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter2 pagerAdapter = new SectionsPagerAdapter2(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

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

            DictionaryFragment frag = new DictionaryFragment();
            switch (position) {
                case 0:
                    return frag;
                case 1:
                    return frag;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.not_learned);
                case 1:
                    return getResources().getText(R.string.learned);
            }
            return null;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
       //overridePendingTransition(0,0);
    }
}
