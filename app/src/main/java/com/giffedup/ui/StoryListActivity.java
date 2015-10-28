package com.giffedup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.giffedup.R;
import com.giffedup.adapters.TabsPagerAdapter;

public class StoryListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private TabsPagerAdapter mTabsPagerAdapter;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolbar();
        mPager = (ViewPager) findViewById(R.id.viewpager);
        //mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //setUpTabs();
//        final Button addButton = (Button) findViewById(R.id.fab);
//        addButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(StoryListActivity.this, GiffSelectorActivity.class);
//                startActivity(intent);
//            }
//        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your FAB click action here...
                //Toast.makeText(getBaseContext(), "FAB Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StoryListActivity.this, AddFeedActivity.class);
                startActivity(intent);
            }
        });
        addBaseFragment();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addBaseFragment() {
        StoryListLayoutFragment storyListFrag = StoryListLayoutFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, storyListFrag, "123"); //fix last arg later
        ft.commit();
    }
    /*private void setUpTabs() {
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
    }*/

}
