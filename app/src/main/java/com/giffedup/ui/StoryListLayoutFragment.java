package com.giffedup.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdErrorType;
import com.flurry.android.ads.FlurryAdNative;
import com.flurry.android.ads.FlurryAdNativeAsset;
import com.flurry.android.ads.FlurryAdNativeListener;
import com.giffedup.R;
import com.giffedup.adapters.StoryListAdapter;
import com.giffedup.model.FlurryDataModel;
import com.giffedup.model.ImageConfigurationModel;
import com.giffedup.model.StoryModel;
import com.giffedup.utils.ItemClickListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StoryListLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoryListLayoutFragment extends Fragment implements ItemClickListener, AdListener {

    private final String AD_SPACE_NAME = "Story screen native ad";

    private RelativeLayout mAdViewContainer;
    private RecyclerView recyclerView;
    private StoryListAdapter adapter;
    private List<StoryModel> storyList;

    private AdView adView;
    private FlurryAdNative mNativeAd;
    private FlurryDataModel mFlurryDataModel;

    private FlurryAdNativeListener mFlurryAdNativeListener = new FlurryAdNativeListener() {
        @Override
        public void onFetched(FlurryAdNative flurryAdNative) {
            FlurryAdNativeAsset headlineAsset = flurryAdNative.getAsset("headline");
            FlurryAdNativeAsset summaryAsset = flurryAdNative.getAsset("summary");
            FlurryAdNativeAsset sourceAsset = flurryAdNative.getAsset("source");
            FlurryAdNativeAsset sponsoredImageUrl = flurryAdNative.getAsset("secHqBrandingLogo");
            FlurryAdNativeAsset secHqImageAsset = flurryAdNative.getAsset("secHqImage");

            mFlurryDataModel = new FlurryDataModel.Builder()
                    .setmHeadlineText(headlineAsset)
                    .setmSecondaryAsset(secHqImageAsset)
                    .setmSummaryText(summaryAsset)
                    .setmSponsorName(sourceAsset)
                    .setmSponsoredImageUrl(sponsoredImageUrl)
                    .setNativeAd(flurryAdNative)
                    .build();

            if(adapter != null) {
                adapter.setmFlurryDataModel(mFlurryDataModel);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onShowFullscreen(FlurryAdNative flurryAdNative) {

        }

        @Override
        public void onCloseFullscreen(FlurryAdNative flurryAdNative) {

        }

        @Override
        public void onAppExit(FlurryAdNative flurryAdNative) {

        }

        @Override
        public void onClicked(FlurryAdNative flurryAdNative) {

        }

        @Override
        public void onImpressionLogged(FlurryAdNative flurryAdNative) {

        }

        @Override
        public void onExpanded(FlurryAdNative flurryAdNative) {

        }

        @Override
        public void onCollapsed(FlurryAdNative flurryAdNative) {

        }

        @Override
        public void onError(FlurryAdNative flurryAdNative, FlurryAdErrorType flurryAdErrorType, int i) {

        }
    };

    public static StoryListLayoutFragment newInstance() {
        StoryListLayoutFragment fragment = new StoryListLayoutFragment();

        return fragment;
    }

    public StoryListLayoutFragment() {
    }

    public void getStories() {
        if (storyList == null || storyList.isEmpty()) {
            storyList = new ArrayList<StoryModel>();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
            query.addDescendingOrder("createdAt");
            query.include("smallImage");
            query.include("downSized");
            query.include("downsizedStill");
            query.include("original");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> object, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < object.size(); i++) {
                            String title = object.get(i).getString("title");
                            String id = object.get(i).getObjectId();
                            String contentId = object.get(i).getString("contentId");
                            ImageConfigurationModel downSized = ImageConfigurationModel.getConfiguration(object.get(i).getParseObject("downSized"));
                            ImageConfigurationModel downsizedStill = ImageConfigurationModel.getConfiguration(object.get(i).getParseObject("downsizedStill"));
                            ImageConfigurationModel original = ImageConfigurationModel.getConfiguration(object.get(i).getParseObject("original"));
                            ImageConfigurationModel smallImage = ImageConfigurationModel.getConfiguration(object.get(i).getParseObject("smallImage"));
                            StoryModel sm = new StoryModel(id, contentId, title, downSized, downsizedStill, original, smallImage);
                            storyList.add(sm);
                        }
                        checkAndSetAdapters();

                    } else {
                        Log.e("stories", "Error: " + e.getMessage());
                    }
                }
            });
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_story_list_layout, container, false);
        mAdViewContainer = (RelativeLayout) layout.findViewById(R.id.adViewContainer);
        fbAdSetup();
        flurryAdSetup();


        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview_story_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    private void flurryAdSetup() {
        mNativeAd = new FlurryAdNative(getActivity(), AD_SPACE_NAME);
        mNativeAd.setListener(mFlurryAdNativeListener);
    }

    private void fbAdSetup() {
        adView = new AdView(getActivity(), "1015153831849777_1032392983459195", AdSize.BANNER_320_50);
        mAdViewContainer.addView(adView);
        //Test Mode for facebook
//        AdSettings.addTestDevice("babaed38fb5e3953285e6eff31a23308");
//        AdSettings.addTestDevice("403dccecad18f54448023f184ec25d3c");
        adView.loadAd();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(getActivity());
        mNativeAd.fetchAd();
    }

    @Override
    public void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(getActivity());
    }

    private void checkAndSetAdapters() {
        if (adapter == null && storyList != null) {
            adapter = new StoryListAdapter(storyList, mFlurryDataModel);
            adapter.setOnItemClicklistener(this);
            recyclerView.setAdapter(adapter);
        } else if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getStories();
        checkAndSetAdapters();
    }

    public void reload() {
        storyList = null;
        adapter = null;
        getStories();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(getActivity(), FeedsListActivity.class);
        intent.putExtra("content", storyList.get(position));
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        mNativeAd.destroy();
        super.onDestroy();
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        if (ad == adView) {
            Log.e("FAIL", adError.getErrorMessage());
        }
    }

    @Override
    public void onAdLoaded(Ad ad) {

    }

    @Override
    public void onAdClicked(Ad ad) {

    }

}
