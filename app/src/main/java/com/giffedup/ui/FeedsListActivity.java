package com.giffedup.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;
import com.giffedup.R;
import com.giffedup.adapters.FeedsAdapter;
import com.giffedup.model.Content;
import com.giffedup.model.FeedModel;
import com.giffedup.model.StoryModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinayr on 10/28/15.
 */
public class FeedsListActivity extends AppCompatActivity implements NativeAdsManager.Listener, AdListener {

    private Toolbar mToolbar;
    private RecyclerView mListView;
    private LinearLayoutManager mLayoutManager;
    private List<FeedModel> mFeeds;
    private FeedsAdapter mAdapter;
    private StoryModel mStoryModel;
    private NativeAdsManager listNativeAdsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeds_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (RecyclerView) findViewById(R.id.recyclerview);
        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setItemAnimator(new DefaultItemAnimator());
        setUpToolbar();

        listNativeAdsManager = new NativeAdsManager(this, "1015153831849777_1032392983459195", 2);
        listNativeAdsManager.setListener(this);
        listNativeAdsManager.loadAds();

        if(getIntent().getExtras() != null) {
            mStoryModel = (StoryModel) getIntent().getExtras().getParcelable("content");
            fetchFeeds();
        }else {
            System.exit(1);
        }


    }

    private void fetchFeeds() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Feed");
        query.whereEqualTo("parentId", mStoryModel.getId());
        query.include("content.images.original");
        query.include("content.images.small");
        query.include("content.images.downsized");
        query.include("content.images.downsizedStill");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    mFeeds = new ArrayList<FeedModel>();
                    for (ParseObject obj : list) {
                        String title = obj.getString("title");
                        Content content = new Content(obj.getParseObject("content"));
                        FeedModel feedModel = new FeedModel(title, content);
                        mFeeds.add(feedModel);
                        checkAndSetAdapters();
                    }
                } else
                    e.printStackTrace();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkAndSetAdapters();
    }

    private void checkAndSetAdapters() {
        if(mAdapter == null && mFeeds != null) {
            mAdapter = new FeedsAdapter(this, mStoryModel, mFeeds);
//            mAdapter.setOnItemClicklistener(this);
            mListView.setAdapter(mAdapter);
        }
        else if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        Toast.makeText(this, "Ad failed to load: " +  adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdLoaded(Ad ad) {

    }

    @Override
    public void onAdClicked(Ad ad) {
        Toast.makeText(this, "Ad Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdsLoaded() {
        NativeAd ad = this.listNativeAdsManager.nextNativeAd();
        ad.setAdListener(this);
        mAdapter.addNativeAd(ad);
    }

    @Override
    public void onAdError(AdError adError) {
        Toast.makeText(this, "Native ads manager failed to load: " +  adError.getErrorMessage(),
                Toast.LENGTH_SHORT).show();
    }
}
