package com.giffedup.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.giffedup.R;
import com.giffedup.utils.Constants;

public class AddFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        addBaseFragment();
    }

    private void addBaseFragment() {
        FeedTemplateFragment feedTemplateFragment = FeedTemplateFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, feedTemplateFragment, Constants.TAG_FEED_TEMPLATE_FRAGMENT);
        ft.commit();
    }
}
