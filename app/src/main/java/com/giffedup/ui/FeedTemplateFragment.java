package com.giffedup.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.giffedup.R;
import com.giffedup.adapters.FeedCreateAdapter;
import com.giffedup.model.Content;
import com.giffedup.model.FeedModel;
import com.giffedup.utils.Constants;
import com.giffedup.utils.EdtTextWatcher;
import com.giffedup.utils.FragmentCommunicationInterface;
import com.giffedup.utils.ItemClickListener;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay-r on 8/12/15.
 */
public class FeedTemplateFragment extends Fragment {

    private RecyclerView mListView;
    private FeedCreateAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<FeedModel> mFeeds;
    private Button mAddBtn;
    private int mSelectedPosition = 0;

    private Handler mHandler = new Handler();
    private FragmentCommunicationInterface mFragmentCommunicationInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mFragmentCommunicationInterface = (FragmentCommunicationInterface)activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Communication Interface");
        }



    }

    private EdtTextWatcher mEdtTextWatcher = new EdtTextWatcher() {
        @Override
        public void onTextChanged(View view, String text, final int position) {
            mSelectedPosition = position;
            mFeeds.get(position).setmTitle(text);
            mAdapter.notifyItemChanged(position);

        }
    };

    private ItemClickListener mItemClickListener = new ItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            mSelectedPosition = position;
            Intent intent = new Intent(getActivity(), GiffSelectorActivity.class);
            startActivityForResult(intent, Constants.GIF_REQUEST);
        }
    };

    public static FeedTemplateFragment newInstance() {
        FeedTemplateFragment feedTemplateFragment = new FeedTemplateFragment();
        return feedTemplateFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridlayout, container, false);
        mListView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mLinearLayoutManager.setSmoothScrollbarEnabled(true);
        mListView.setLayoutManager(mLinearLayoutManager);
        mListView.setItemAnimator(new DefaultItemAnimator());
        mListView.setHasFixedSize(true);
        mFeeds = new ArrayList<FeedModel>();
        mFeeds.add(new FeedModel(FeedModel.contentType.STORY));
        mFeeds.add(new FeedModel());
        mAddBtn = (Button) view.findViewById(R.id.addBtn);
        mAddBtn.setVisibility(View.VISIBLE);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save Current Feed
                mFeeds.add(new FeedModel());
                mAdapter.notifyDataSetChanged();
                mListView.smoothScrollToPosition(mFeeds.size()-1);
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new FeedCreateAdapter(getActivity(), mFeeds);
        mAdapter.setOnItemClicklistener(mItemClickListener);
        mAdapter.setmEdtTextWatcher(mEdtTextWatcher);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                generateParseObjects();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateParseObjects() {
        final ParseObject storyObject = new ParseObject("Story");
        storyObject.put("title", mFeeds.get(0).getmTitle());
        storyObject.put("contentId", mFeeds.get(1).getmContent().getId());
//        storyObject.put("contentType", mFeeds.get(1).getmContent().getContentType());
        storyObject.put("smallImage", mFeeds.get(1).getmContent().getSmallImage().createParseObject());
        storyObject.put("downsizedStill", mFeeds.get(1).getmContent().getDownsizedStillImage().createParseObject());
        storyObject.put("downSized", mFeeds.get(1).getmContent().getDownsizedImage().createParseObject());
        storyObject.put("original", mFeeds.get(1).getmContent().getOriginalImage().createParseObject());



        storyObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e != null)
                    e.printStackTrace();
                else {

                    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
                    for (FeedModel model : mFeeds) {
                        if (model.getmType() == FeedModel.contentType.FEED) {
                            ParseObject object = new ParseObject("Feed");
                            object.put("title", model.getmTitle());
                            object.put("content", model.getmContent().createParseObject());
                            object.put("parentId", storyObject.getObjectId());
//                object.put("contentId", model.getmContent().getId());
//                storyObject.put("contentType", model.getmContent().getContentType());
//                object.put("smallImage", model.getmContent().getSmallImage().createParseObject());
//                object.put("downsizedStill", model.getmContent().getDownsizedStillImage().createParseObject());
//                object.put("downSized", model.getmContent().getDownsizedImage().createParseObject());
//                object.put("original", model.getmContent().getOriginalImage().createParseObject());
                            parseObjectList.add(object);
                            ParseObject.saveAllInBackground(parseObjectList, new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e != null)
                                        e.printStackTrace();
                                    else
                                    {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("finish", Activity.RESULT_OK);
                                        mFragmentCommunicationInterface.sendMessage(bundle);
                                    }
                                }
                            });
                        }
                    }
                }

            }
        });

//        storyObject.put("feeds", parseObjectList);
//        storyObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e != null)
//                    e.printStackTrace();
//            }
//        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GIF_REQUEST && resultCode == Activity.RESULT_OK) {
            Content content = (Content) data.getExtras().getParcelable("content");
            mAdapter.updateItem(content, mSelectedPosition);
            mSelectedPosition = -1;
        }
    }
}
