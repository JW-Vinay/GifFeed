package com.giffedup.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdBanner;
import com.flurry.android.ads.FlurryAdBannerListener;
import com.flurry.android.ads.FlurryAdErrorType;
import com.giffedup.ApplicationData;
import com.giffedup.R;
import com.giffedup.adapters.GridAdapter;
import com.giffedup.api.RestClient;
import com.giffedup.api.models.ApiResponse;
import com.giffedup.model.Content;
import com.giffedup.utils.ItemClickListener;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vinayr on 10/27/15.
 */
public class SearchActivity extends AppCompatActivity implements ItemClickListener {

    private final String AD_SPACE = "Banner bottom search screen";

    private Toolbar mToolbar;
    private RecyclerView mGridView;
    private RestClient mRestClient;
    private GridAdapter mGridAdapter;
    private TextView mEmptyTextView;
    private StaggeredGridLayoutManager mLayoutManager;
    private SearchView mSearchView;
    private List<Content> mList;
    private String mQuery = "";
    private int mGIFOffset = 0;
    private int mStickerOffset = 0;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mAdLayout = (RelativeLayout) findViewById(R.id.adFrame);
        mRestClient = ApplicationData.getInstance().getRestClient();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mGridView = (RecyclerView) findViewById(R.id.recyclerview);
        mEmptyTextView = (TextView) findViewById(R.id.emptyView);
        mGridView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mGridView.setLayoutManager(mLayoutManager);
        mGridView.setItemAnimator(new DefaultItemAnimator());
        mGridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int[] pastVisiblesItems = mLayoutManager.findFirstVisibleItemPositions(null);
//                    System.out.println("Pos: " + pastVisiblesItems[0]  + " " + pastVisiblesItems.length);
                    if (mGridAdapter != null && !mGridAdapter.isLoading()) {
                        if (pastVisiblesItems.length > 0 && (visibleItemCount + pastVisiblesItems[0]) >= totalItemCount) {
                            mGridAdapter.setIsLoading(true);
                            //TODO: Paginate.
                            fetchContentPaginate();
                        }
                    }
                }
            }
        });
        setUpToolbar();
        mFlurryAdBanner = new FlurryAdBanner(this, mAdLayout, AD_SPACE);
        mFlurryAdBanner.setListener(mAdListener);
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

    private void setUpToolbar() {

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gray));
        }

        mToolbar.setAlpha(0);
        mToolbar.animate().setDuration(1000).alpha(1);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.requestFocus();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction() == Intent.ACTION_SEARCH) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (!TextUtils.isEmpty(query)) {
                mQuery = query;
                clearData();
                fetchContent();
            } else {
                Toast.makeText(this, R.string.empty_query, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clearData() {
        if (mList != null) {
            mList.clear();
            if (mGridAdapter != null)
                mGridAdapter.notifyDataSetChanged();
        }

    }

    private Callback<ApiResponse> mCallback = new Callback<ApiResponse>() {
        @Override
        public void success(ApiResponse apiResponse, Response response) {
            if (mList == null)
                mList = (List<Content>) apiResponse.getContentList();
            else {
                mList.addAll((List<Content>) apiResponse.getContentList());
            }
            if(matchUrl(response.getUrl()))
                   mGIFOffset = apiResponse.getPages().getOffset() + apiResponse.getPages().getCount();
            else
                mStickerOffset = apiResponse.getPages().getOffset()+apiResponse.getPages().getCount();

            checkAndSetAdapters();
            if(mGridAdapter != null)
                mGridAdapter.setIsLoading(false);
        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
            System.out.println(error.getMessage());
        }
    };

    private boolean matchUrl(String url) {
        return url.contains("v1/gifs/");
    }
    private void checkAndSetAdapters() {

        if (mList == null || mList.isEmpty()) {
            mEmptyTextView.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
        } else {
            mEmptyTextView.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);

            if (mGridAdapter == null) {
                mGridAdapter = new GridAdapter(mList);
                mGridAdapter.setOnItemClicklistener(this);
                mGridView.setAdapter(mGridAdapter);
            } else if (mGridAdapter != null) {
                mGridAdapter.notifyDataSetChanged();
            }
        }

    }

    private void fetchContentPaginate() {
        if(mGIFOffset > 0)
            mRestClient.getGifService().searchGIFs(mQuery, mGIFOffset, mCallback);
        if(mStickerOffset > 0)
            mRestClient.getGifService().searchStickers(mQuery,mStickerOffset, mCallback);
    }

    private void fetchContent() {
        mRestClient.getGifService().searchGIFs(mQuery, mCallback);
        mRestClient.getGifService().searchStickers(mQuery, mCallback);
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
    public void onClick(View view, int position) {
//        Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("content", mList.get(position));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFlurryAdBanner.destroy();
    }
}
