package com.giffedup.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.giffedup.R;
import com.giffedup.adapters.FeedCreateAdapter;
import com.giffedup.model.Content;
import com.giffedup.model.FeedModel;
import com.giffedup.utils.Constants;
import com.giffedup.utils.DialogClickListener;
import com.giffedup.utils.DialogUtils;
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
public class FeedTemplateFragment extends Fragment implements DialogClickListener {

    private View mProgressView;
    private ImageView mDoneBtn;
    private ProgressBar mProgressBar;
    private RecyclerView mListView;
    private FeedCreateAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<FeedModel> mFeeds;
    private Button mAddBtn;
    private int mSelectedPosition = 0;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case -1: //TODO: Error
                    mProgressView.setVisibility(View.GONE);
                    mProgressBar.setProgress(0);
                    mDoneBtn.setSelected(false);
                    Snackbar.make(getView(), R.string.error_progress, Snackbar.LENGTH_SHORT)
                            .show();
                    break;
                case 1:
                    mProgressBar.setProgress(mProgressBar.getProgress() + 1);
                    break;
                case 2:
                    mProgressBar.setProgress(mProgressBar.getProgress() + 1);
                    mDoneBtn.setSelected(true);
                    break;
            }
        }
    };

    private FragmentCommunicationInterface mFragmentCommunicationInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mFragmentCommunicationInterface = (FragmentCommunicationInterface) activity;
        } catch (ClassCastException e) {
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

        mProgressView = (View) view.findViewById(R.id.progressLayout);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mDoneBtn = (ImageView) view.findViewById(R.id.doneImageView);
        mDoneBtn.setSelected(false);
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
                mListView.smoothScrollToPosition(mFeeds.size() - 1);
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
                try {
                    DialogUtils.showDialog(getActivity(), R.string.title_confirm_publish, R.string.publish_confirm_msg, R.string.btn_confirm, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateParseObjects() {
        if (mFeeds.get(0).isTitleEmpty() || mFeeds.get(1).isContentEmpty()) {
            try {
                mProgressView.setVisibility(View.GONE);
                DialogUtils.showErrorDialog(getActivity(), R.string.story_error_title, R.string.story_error_msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;

        }
        mProgressBar.setMax(mFeeds.size() + 1);
        final ParseObject storyObject = new ParseObject("Story");
        storyObject.put("title", mFeeds.get(0).getmTitle());
        storyObject.put("contentId", mFeeds.get(1).getmContent().getId());
        storyObject.put("smallImage", mFeeds.get(1).getmContent().getSmallImage().createParseObject());
        storyObject.put("downsizedStill", mFeeds.get(1).getmContent().getDownsizedStillImage().createParseObject());
        storyObject.put("downSized", mFeeds.get(1).getmContent().getDownsizedImage().createParseObject());
        storyObject.put("original", mFeeds.get(1).getmContent().getOriginalImage().createParseObject());


        storyObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e != null) {
                    e.printStackTrace();
                    mHandler.obtainMessage(-1).sendToTarget();
                } else {
                    mHandler.obtainMessage(1).sendToTarget();
                    List<ParseObject> parseObjectList = new ArrayList<ParseObject>();
                    int index = 1;
                    for (FeedModel model : mFeeds) {
                        if (model.isContentEmpty())
                            continue;
                        if (model.getmType() == FeedModel.contentType.FEED) {
                            ParseObject object = new ParseObject("Feed");
                            object.put("title", model.getmTitle());
                            object.put("content", model.getmContent().createParseObject());
                            object.put("parentId", storyObject.getObjectId());
                            object.put("order", index++);
                            parseObjectList.add(object);
                            mHandler.obtainMessage(1).sendToTarget();
                        }
                    }

                    ParseObject.saveAllInBackground(parseObjectList, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                                mHandler.obtainMessage(-1).sendToTarget();
                            } else {
                                mHandler.obtainMessage(2).sendToTarget();
                                Bundle bundle = new Bundle();
                                bundle.putInt("finish", Activity.RESULT_OK);
                                mFragmentCommunicationInterface.sendMessage(bundle);
                            }
                        }
                    });
                }

            }
        });
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

    @Override
    public void onPositiveBtnClick(DialogUtils.dialogTypes dialogType) {
        if(dialogType == DialogUtils.dialogTypes.DIALOG_CONFIRM) {
            mProgressView.setVisibility(View.VISIBLE);
            generateParseObjects();
        }
    }

    @Override
    public void onNegativeBtnClick(DialogUtils.dialogTypes dialogType) {

    }
}
