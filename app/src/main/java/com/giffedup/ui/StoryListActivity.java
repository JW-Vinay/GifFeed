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
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.giffedup.R;
import com.giffedup.adapters.TabsPagerAdapter;
import com.giffedup.utils.Constants;

import com.facebook.ads.*; //fb native ads

public class StoryListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private TabsPagerAdapter mTabsPagerAdapter;
    FloatingActionButton fab;

    //private NativeAd nativeAd; //fb native ads
    //private AdView adView;

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

//    @Override
//    protected void onDestroy() {
//        adView.destroy();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onError(Ad ad, AdError adError) {
//        if (ad == adView) {
//            Log.d("FAIL", adError.getErrorMessage());
//        }
//    }
//
//    @Override
//    public void onAdLoaded(Ad ad) {
//
//    }
//
//    @Override
//    public void onAdClicked(Ad ad) {
//
//    }

//    private void showNativeAd(){ //fb native add function
//        nativeAd = new NativeAd(this, "1015153831849777_1032392983459195");
//        nativeAd.setAdListener(new AdListener() {
//
//            @Override
//            public void onError(Ad ad, AdError error) {
//
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//        });
//
//        nativeAd.loadAd();
//    }

}
