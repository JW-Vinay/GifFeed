package com.giffedup.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.giffedup.R;
import com.giffedup.adapters.TabsPagerAdapter;

import java.util.Arrays;
import java.util.List;

public class StoryListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private TabsPagerAdapter mTabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolbar();
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        setUpTabs();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpTabs() {
        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.content_tabs));
        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), titles);
        mPager.setAdapter(mTabsPagerAdapter);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
