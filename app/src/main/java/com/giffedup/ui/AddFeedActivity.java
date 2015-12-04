package com.giffedup.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.giffedup.R;
import com.giffedup.utils.Constants;
import com.giffedup.utils.DialogClickListener;
import com.giffedup.utils.DialogUtils;
import com.giffedup.utils.FragmentCommunicationInterface;

public class AddFeedActivity extends AppCompatActivity implements FragmentCommunicationInterface, DialogClickListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolbar();
        addBaseFragment();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addBaseFragment() {
        FeedTemplateFragment feedTemplateFragment = FeedTemplateFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, feedTemplateFragment, Constants.TAG_FEED_TEMPLATE_FRAGMENT);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                showDiscardDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDiscardDialog() {
        try {
            DialogUtils.showDialog(this, R.string.title_confirm_discard, R.string.discard_confirm, R.string.btn_discard, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        showDiscardDialog();
    }

    @Override
    public void sendMessage(Bundle bundle) {

        if (bundle != null) {
            int message = bundle.getInt("finish", Activity.RESULT_CANCELED);
            setResult(message);
            finish();
        }
    }

    @Override
    public void onPositiveBtnClick() {
        finish();
        setResult(RESULT_CANCELED);
    }

    @Override
    public void onNegativeBtnClick() {

    }


}
