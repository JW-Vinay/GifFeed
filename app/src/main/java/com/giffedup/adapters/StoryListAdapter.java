package com.giffedup.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giffedup.R;
import com.giffedup.model.FlurryDataModel;
import com.giffedup.model.ImageConfigurationModel;
import com.giffedup.model.StoryModel;
import com.giffedup.utils.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by akshay on 10/27/15.
 */
public class StoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StoryModel> items;
    private ItemClickListener itemClickListener;
    private FlurryDataModel mFlurryDataModel;
    private int STORY_VIEW_TYPE = 0;
    private int AD_VIEW_TYPE = 1;

    public StoryListAdapter(List<StoryModel> items, FlurryDataModel flurryDataModel) {
        this.items = items;
        this.mFlurryDataModel = flurryDataModel;
    }

    public void setmFlurryDataModel(FlurryDataModel flurryDataModel) {
//        if(mFlurryDataModel != null)
//            mFlurryDataModel.getmNativeAd().removeTrackingView();
        this.mFlurryDataModel = flurryDataModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == STORY_VIEW_TYPE) {
            View view = inflater.inflate(R.layout.story_list_item, parent, false);
            StoryListViewHolder holder = new StoryListViewHolder(view);
            return holder;
        } else {
            View view = inflater.inflate(R.layout.flurry_adlayout, parent, false);
            FlurryAdViewHolder holder = new FlurryAdViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof StoryListViewHolder) {
            StoryListViewHolder viewHolder = (StoryListViewHolder) holder;

            StoryModel storymodel = items.get(getActualPosition(position));
            ImageConfigurationModel icm = storymodel.getDownsizedStill();
            //Uri uri;
            Uri uri = Uri.parse(icm.getUrl());
            viewHolder.title.setText(storymodel.getTitle());
            //holder.icon.setImageResource(uri);
            Picasso.with(viewHolder.itemView.getContext())
                    .load(uri)
                    .placeholder(R.drawable.gif_default)
                    .into(viewHolder.icon);
        } else {
            FlurryAdViewHolder viewHolder = (FlurryAdViewHolder) holder;
            if (mFlurryDataModel != null) {
                mFlurryDataModel.getmNativeAd().removeTrackingView();
                mFlurryDataModel.getmNativeAd().setTrackingView(viewHolder.itemView);
                viewHolder.mHeadlineTextView.setText(mFlurryDataModel.getmHeadlineText());
                viewHolder.mSummaryTextView.setText(mFlurryDataModel.getmSummaryText());
                viewHolder.mSponsorLabel.setText("By " + mFlurryDataModel.getmSponsorName());
                Picasso.with(viewHolder.mAdImageView.getContext())
                        .load(Uri.parse(mFlurryDataModel.getmMainImage()))
                        .placeholder(R.drawable.animate)
                        .into(viewHolder.mAdImageView);

                Picasso.with(viewHolder.mSponsorLogo.getContext())
                        .load(Uri.parse(mFlurryDataModel.getmSponsoredImageUrl()))
                        .into(viewHolder.mSponsorLogo);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == items.size() / 2)
            return AD_VIEW_TYPE;

        return STORY_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    public int getActualPosition(int position) {
        if (position > items.size() / 2)
            position = position - 1;
        return position;
    }

    class FlurryAdViewHolder extends RecyclerView.ViewHolder {
        TextView mHeadlineTextView, mSummaryTextView, mSponsorLabel;
        ImageView mAdImageView, mSponsorLogo;

        public FlurryAdViewHolder(View itemView) {
            super(itemView);
            mAdImageView = (ImageView) itemView.findViewById(R.id.mainImageView);
            mHeadlineTextView = (TextView) itemView.findViewById(R.id.headlineTextView);
            mSummaryTextView = (TextView) itemView.findViewById(R.id.summaryTextView);
            mSponsorLabel = (TextView) itemView.findViewById(R.id.sponsorTextView);
            mSponsorLogo = (ImageView) itemView.findViewById(R.id.sponsoredAdLogo);
        }
    }

    class StoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public StoryListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.storyListText);
            icon = (ImageView) itemView.findViewById(R.id.storyListIcon);

        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                int position = getAdapterPosition();
                if (position > items.size() / 2)
                    position = position - 1;
                itemClickListener.onClick(v, position);
            }

        }
    }

    public void setOnItemClicklistener(ItemClickListener itemClicklistener) {
        this.itemClickListener = itemClicklistener;
    }
}
