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

    private Toolbar mToolbar;
    private RecyclerView mGridView;
    private RestClient mRestClient;
    private GridAdapter mGridAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private SearchView mSearchView;
    private List<Content> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mRestClient = ApplicationData.getInstance().getRestClient();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mGridView = (RecyclerView) findViewById(R.id.recyclerview);
        mGridView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mGridView.setLayoutManager(mLayoutManager);
        mGridView.setItemAnimator(new DefaultItemAnimator());
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        checkAndSetAdapters();
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
                fetchContent(query);
            }
        }
    }

    private Callback<ApiResponse> mCallback = new Callback<ApiResponse>() {
        @Override
        public void success(ApiResponse apiResponse, Response response) {
            if (mList == null || mList.isEmpty())
                mList = (List<Content>) apiResponse.getContentList();
            else {
                mList.addAll((List<Content>) apiResponse.getContentList());
            }
            checkAndSetAdapters();
        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
            System.out.println(error.getMessage());
        }
    };

    private void checkAndSetAdapters() {
        if (mGridAdapter == null && mList != null) {
            mGridAdapter = new GridAdapter(mList);
            mGridAdapter.setOnItemClicklistener(this);
            mGridView.setAdapter(mGridAdapter);
        } else if (mGridAdapter != null) {
            mGridAdapter.notifyDataSetChanged();
        }
    }

    private void fetchContent(String query) {
        mRestClient.getGifService().searchGIFs(query, mCallback);
        mRestClient.getGifService().searchStickers(query, mCallback);
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
}
