package com.giffedup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.giffedup.R;
import com.giffedup.model.Content;
import com.giffedup.model.FeedModel;
import com.giffedup.utils.EdtTextWatcher;
import com.giffedup.utils.ItemClickListener;

import java.util.List;

/**
 * Created by vinayr on 10/18/15.
 */
public class FeedCreateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<FeedModel> mFeeds;
    private ItemClickListener mItemClickListener;
    private EdtTextWatcher mEdtTextWatcher;

    private int TYPE_HEADER = 0;
    private int TYPE_ITEM = 1;

    public FeedCreateAdapter(Context context, List<FeedModel> list) {
        mContext = context;
        mFeeds = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {

            View v = mLayoutInflater.inflate(R.layout.feed_title_view, parent, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(v);
            return headerViewHolder;

        } else {
            View v = mLayoutInflater.inflate(R.layout.fragment_add_feed, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            viewHolder.mStoryEdt.setText(mFeeds.get(position).getmTitle());
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (mFeeds.get(position).getmContent() != null) {
                String url = mFeeds.get(position).getmContent().getOriginalImage().getUrl();
                if (TextUtils.isEmpty(url)) {

                    viewHolder.mImageView.setVisibility(View.INVISIBLE);
                    viewHolder.mPlaceholderText.setVisibility(View.VISIBLE);
                    viewHolder.mEditTextView.setVisibility(View.GONE);
                } else {

                    viewHolder.mImageView.setVisibility(View.VISIBLE);
                    viewHolder.mPlaceholderText.setVisibility(View.GONE);
                    viewHolder.mEditTextView.setVisibility(View.VISIBLE);
                    Glide.with(mContext)
                            .load(mFeeds.get(position).getmContent().getOriginalImage().getUrl())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .placeholder(R.drawable.plc_image)
                            .into(viewHolder.mImageView);
                }

//                Uri uri = Uri.parse(mFeeds.get(position).getmContent().getOriginalImage().getUrl());
//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setUri(uri)
//                        .setAutoPlayAnimations(true)
//                        .build();
//                viewHolder.mImageView.setController(controller);
            } else {
                viewHolder.mImageView.setImageResource(R.drawable.def_bg);
                viewHolder.mImageView.setVisibility(View.INVISIBLE);
                viewHolder.mEditTextView.setVisibility(View.GONE);
                viewHolder.mPlaceholderText.setVisibility(View.VISIBLE);
            }

            if (mFeeds.get(position).getmTitle() != null)
                viewHolder.mTitleEdt.setText(mFeeds.get(position).getmTitle());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private EditText mStoryEdt;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mStoryEdt = (EditText) itemView.findViewById(R.id.storyTitleEdt);
            mStoryEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mFeeds.get(getAdapterPosition()).setmTitle(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private EditText mTitleEdt;
        private TextView mPlaceholderText, mEditTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mEditTextView = (TextView) itemView.findViewById(R.id.editTextView);
            mPlaceholderText = (TextView) itemView.findViewById(R.id.hintTextView);
            mImageView = (ImageView) itemView.findViewById(R.id.gifImageView);
            mTitleEdt = (EditText) itemView.findViewById(R.id.captionEditText);
            mImageView.setOnClickListener(this);
            mPlaceholderText.setOnClickListener(this);
            mTitleEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mFeeds.get(getAdapterPosition()).setmTitle(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClicklistener(ItemClickListener itemClicklistener) {
        this.mItemClickListener = itemClicklistener;
    }

    public void setmEdtTextWatcher(EdtTextWatcher edtTextWatcher) {
        this.mEdtTextWatcher = edtTextWatcher;
    }

    public void updateItem(Content content, int position) {
        mFeeds.get(position).setmContent(content);
        notifyItemChanged(position);
    }
}
