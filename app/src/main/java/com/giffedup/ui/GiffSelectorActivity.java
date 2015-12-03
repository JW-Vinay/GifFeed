package com.giffedup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdBanner;
import com.flurry.android.ads.FlurryAdBannerListener;
import com.flurry.android.ads.FlurryAdErrorType;
import com.giffedup.R;
import com.giffedup.adapters.TabsPagerAdapter;
import com.giffedup.utils.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vinay-r on 8/7/15.
 */
public class GiffSelectorActivity extends AppCompatActivity {

    private final String AD_SPACE = "banner bottom gif screen";

    private Toolbar mToolbar;
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private TabsPagerAdapter mTabsPagerAdapter;
    private RelativeLayout mAdLayout;

    private FlurryAdBanner mFlurryAdBanner;

    private FlurryAdBannerListener mAdListener = new FlurryAdBannerListener() {
        @Override
        public void onFetched(FlurryAdBanner flurryAdBanner) {
            mFlurryAdBanner.displayAd();
        }

        @Override
        public void onRendered(FlurryAdBanner flurryAdBanner) {

        }

        @Override
        public void onShowFullscreen(FlurryAdBanner flurryAdBanner) {

        }

        @Override
        public void onCloseFullscreen(FlurryAdBanner flurryAdBanner) {

        }

        @Override
        public void onAppExit(FlurryAdBanner flurryAdBanner) {

        }

        @Override
        public void onClicked(FlurryAdBanner flurryAdBanner) {

        }

        @Override
        public void onVideoCompleted(FlurryAdBanner flurryAdBanner) {

        }

        @Override
        public void onError(FlurryAdBanner flurryAdBanner, FlurryAdErrorType flurryAdErrorType, int i) {
            mFlurryAdBanner.destroy();
            mFlurryAdBanner.fetchAd();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_selector_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolbar();
        mAdLayout = (RelativeLayout) findViewById(R.id.adFrame);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mFlurryAdBanner = new FlurryAdBanner(this, mAdLayout, AD_SPACE);
        mFlurryAdBanner.setListener(mAdListener);
        setUpTabs();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this);
        mFlurryAdBanner.fetchAd();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gif_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                setResult(RESULT_CANCELED);
                break;
            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivityForResult(intent, Constants.SEARCH_REQUEST);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.SEARCH_REQUEST && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFlurryAdBanner.destroy();
    }
}
