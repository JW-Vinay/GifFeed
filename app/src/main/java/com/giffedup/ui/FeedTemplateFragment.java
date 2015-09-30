package com.giffedup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.giffedup.R;

/**
 * Created by vinay-r on 8/12/15.
 */
public class FeedTemplateFragment extends Fragment {
    private ImageView mImageView;

    public static FeedTemplateFragment newInstance() {
        FeedTemplateFragment feedTemplateFragment = new FeedTemplateFragment();
        return feedTemplateFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_feed, container, false);
        mImageView = (ImageView) view.findViewById(R.id.gifImageView);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GiffSelectorActivity.class);
                startActivity(intent);
            }
        });
    }
}
