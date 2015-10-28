package com.giffedup.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.giffedup.R;
import com.giffedup.model.Content;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by vinayr on 10/28/15.
 */
public class FeedsListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mListView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (RecyclerView) findViewById(R.id.recyclerview);
        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setItemAnimator(new DefaultItemAnimator());
        setUpToolbar();
        fetchFeeds(getIntent().getStringExtra("content"));

    }

    private void fetchFeeds(String id) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
        query.include("feeds.content");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if( e == null) {
                    System.out.println(object.getString("title"));
                    Content content = new Content(object.getParseObject("content"));
                    System.out.println(object.getParseObject("content"));
                }else
                    e.printStackTrace();
            }
        });
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
