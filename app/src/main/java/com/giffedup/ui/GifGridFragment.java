package com.giffedup.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.giffedup.ApplicationData;
import com.giffedup.R;
import com.giffedup.adapters.GridAdapter;
import com.giffedup.api.RestClient;
import com.giffedup.api.models.ApiResponse;
import com.giffedup.model.Content;
import com.giffedup.model.GIF;
import com.giffedup.utils.ItemClickListener;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GifGridFragment extends Fragment implements ItemClickListener {

    private RecyclerView mGridView;
    private RestClient mRestClient;
    private GridAdapter mGridAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private List<? extends Content> mGifList;

    public GifGridFragment() {
        mRestClient = ApplicationData.getInstance().getRestClient();
    }

    public static GifGridFragment newInstance() {
        GifGridFragment fragment = new GifGridFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridlayout, container, false);
        mGridView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mGridView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mGridView.setLayoutManager(mLayoutManager);
        mGridView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(null == mGifList || mGifList.isEmpty()) {
            fetchGifs();
        }
    }

    private void fetchGifs() {
        mRestClient.getGifService().getTrendingGIFs(new Callback<ApiResponse>() {

            @Override
            public void success(ApiResponse apiResponse, Response response) {
                mGifList = (List<GIF>) apiResponse.getContentList();
                   checkAndSetAdapters();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAndSetAdapters();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ((item.getItemId())) {
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkAndSetAdapters() {
        if(mGridAdapter == null && mGifList != null) {
            mGridAdapter = new GridAdapter(getActivity(), mGifList);
            mGridAdapter.setOnItemClicklistener(this);
            mGridView.setAdapter(mGridAdapter);
        }
        else if(mGridAdapter != null) {
            mGridAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view, int position) {
//        Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("content", mGifList.get(position));
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
