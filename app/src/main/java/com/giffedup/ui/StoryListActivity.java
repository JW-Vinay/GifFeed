package com.giffedup.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    private CoordinatorLayout mRootView;
    private Toolbar mToolbar;
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private TabsPagerAdapter mTabsPagerAdapter;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        mRootView = (CoordinatorLayout) findViewById(R.id.baseLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolbar();
        mPager = (ViewPager) findViewById(R.id.viewpager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoryListActivity.this, AddFeedActivity.class);
                startActivityForResult(intent, Constants.ADD_REQUEST);
            }
        });
        addBaseFragment();

//        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);
//
//        adView = new AdView(this, "1015153831849777_1032392983459195", AdSize.BANNER_320_50);
//        adViewContainer.addView(adView);
//        //AdSettings.addTestDevice("babaed38fb5e3953285e6eff31a23308");
//        AdSettings.addTestDevice("403dccecad18f54448023f184ec25d3c");
//        adView.loadAd();

    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private void addBaseFragment() {
        StoryListLayoutFragment storyListFrag = StoryListLayoutFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, storyListFrag, Constants.TAG_STORY_LIST_FRAGMENT);
        ft.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_REQUEST && resultCode == Activity.RESULT_OK) {
            StoryListLayoutFragment fragment = (StoryListLayoutFragment)
                    getSupportFragmentManager().findFragmentByTag(Constants.TAG_STORY_LIST_FRAGMENT);
            if (fragment != null) {
                fragment.reload();
            }
            Snackbar.make(mRootView, R.string.story_published_msg, Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
