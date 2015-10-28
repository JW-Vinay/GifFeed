package com.giffedup.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.giffedup.R;
import com.giffedup.adapters.TabsPagerAdapter;
import com.giffedup.utils.Constants;

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
                startActivityForResult(intent, Constants.ADD_REQUEST);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_REQUEST && resultCode == Activity.RESULT_OK) {
            StoryListLayoutFragment fragment = (StoryListLayoutFragment)
                    getSupportFragmentManager().findFragmentByTag("123");
            if(fragment != null) {
                fragment.reload();
            }
        }
    }

}
