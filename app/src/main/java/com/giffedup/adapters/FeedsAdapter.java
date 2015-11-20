package com.giffedup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.giffedup.R;
import com.giffedup.model.FeedModel;
import com.giffedup.model.StoryModel;
import com.giffedup.utils.ItemClickListener;

import java.util.List;

/**
 * Created by vinayr on 10/28/15.
 */
public class FeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<FeedModel> mFeeds;
    private StoryModel mStoryModel;
    private ItemClickListener itemClickListener;

    private int TYPE_HEADER = 0;
    private int TYPE_ITEM = 1;

    public FeedsAdapter(Context context, StoryModel model, List<FeedModel> feeds) {
        this.mContext = context;
        this.mFeeds = feeds;
        this.mStoryModel = model;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {

            View v = mInflater.inflate(R.layout.feeds_caption_view, parent, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(v);
            return headerViewHolder;

        } else {

            View view = mInflater.inflate(R.layout.feeds_item, parent, false);
            FeedsHolder holder = new FeedsHolder(view);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            viewHolder.mStoryTextview.setText(mStoryModel.getTitle());
        } else {
            FeedsHolder feedsHolder = (FeedsHolder) holder;
            FeedModel model = mFeeds.get(position - 1);
            if (model.getmContent() != null) {
                Glide.with(mContext)
                        .load(model.getmContent().getOriginalImage().getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.plc_image)
                        .into(feedsHolder.mImageView);
//                Uri uri = Uri.parse(model.getmContent().getOriginalImage().getUrl());
//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setUri(uri)
//                        .setAutoPlayAnimations(true)
//                        .build();
//                feedsHolder.mImageView.setController(controller);
            } else
                feedsHolder.mImageView.setImageResource(R.drawable.def_bg);

            if (model.getmTitle() != null)
                feedsHolder.mTitle.setText(model.getmTitle());
        }

    }

    @Override
    public int getItemCount() {
        return mFeeds.size() + 1;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mStoryTextview;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mStoryTextview = (TextView) itemView.findViewById(R.id.captionTextView);
        }
    }

    class FeedsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        //        SimpleDraweeView mImageView;
        ImageView mImageView;

        public FeedsHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.captionTextView);
            mImageView = (ImageView) itemView.findViewById(R.id.gifImageView);

        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null)
                itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClicklistener(ItemClickListener itemClicklistener) {
        this.itemClickListener = itemClicklistener;
    }
}
