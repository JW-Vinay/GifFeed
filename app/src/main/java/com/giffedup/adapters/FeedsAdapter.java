package com.giffedup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
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

    private List<NativeAd> mAd;
    private StoryModel mStoryModel;
    private ItemClickListener itemClickListener;

    private int TYPE_HEADER = 0;
    private int TYPE_AD = 1;
    private int TYPE_ITEM = 2;

    private NativeAd ad;
    private int mAdCount = 0;

    public FeedsAdapter(Context context, StoryModel model, List<FeedModel> feeds, NativeAd ad) {
        this.mContext = context;
        this.mFeeds = feeds;
        this.mStoryModel = model;
        this.ad = ad;
        mInflater = LayoutInflater.from(context);
        mAdCount = mFeeds.size() / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {

            View v = mInflater.inflate(R.layout.feeds_caption_view, parent, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(v);
            return headerViewHolder;

        } else if (viewType == TYPE_AD) {
            View v = mInflater.inflate(R.layout.ad_unit, parent, false);
            AdViewHolder adViewHolder = new AdViewHolder(v);
            return adViewHolder;
        } else {

            View view = mInflater.inflate(R.layout.feeds_item, parent, false);
            FeedsHolder holder = new FeedsHolder(view);
            return holder;
        }

    }

    private boolean isAdPosition(int position) {
        if(mAdCount == 0)
            return false;
        else if(mAdCount == 1 && position == mFeeds.size()/2)
            return  true;
        else if(mAdCount > 1 && position < mFeeds.size() && position % (mFeeds.size()/mAdCount) == 0)
            return true;
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        if (isAdPosition(position))
            return TYPE_AD;
        return TYPE_ITEM;
    }

    public void adNativeAd(NativeAd ad) {
        if (ad == null) {
            return;
        }
        if (this.ad != null) {
            // Clean up the old ad before inserting the new one
            this.ad.unregisterView();
            this.ad = null;
            this.notifyDataSetChanged();
        }
        this.ad = ad;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            viewHolder.mStoryTextview.setText(mStoryModel.getTitle());
        } else if (holder instanceof AdViewHolder) {
            AdViewHolder viewHolder = (AdViewHolder) holder;
            if (ad != null) {
                viewHolder.nativeAdSocialContext.setText(ad.getAdSocialContext());
                viewHolder.nativeAdCallToAction.setText(ad.getAdCallToAction());
                viewHolder.nativeAdCallToAction.setVisibility(View.VISIBLE);
                viewHolder.nativeAdTitle.setText(ad.getAdTitle());
                viewHolder.nativeAdBody.setText(ad.getAdBody());
                viewHolder.inflateAd();
            } else {
                //TODO: Show empty view
            }
        } else {
            FeedsHolder feedsHolder = (FeedsHolder) holder;
//            FeedModel model = mFeeds.get(position - 2);
            FeedModel model = getFeed(position);
            if (model.getmContent() != null) {
                Glide.with(mContext)
                        .load(model.getmContent().getOriginalImage().getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.gif_default)
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
        return mFeeds.size() + 1 + mAdCount;
    }

    public FeedModel getFeed(int position) {

        int temp = 0;
        if(mAdCount == 1) {
            if(position > mFeeds.size()/2)
                temp = 1;

        }else if(mAdCount > 1){
            int i = mFeeds.size()/mAdCount;
            while(i<=mFeeds.size()) {
                if(position < i)
                    break;
                temp++;
                i+= i;
            }
        }

        position = position - temp - 1;

        return mFeeds.get(position);
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

    class AdViewHolder extends RecyclerView.ViewHolder {
        ImageView nativeAdIcon;
        TextView nativeAdTitle, nativeAdBody, nativeAdSocialContext;
        MediaView nativeAdMedia;
        Button nativeAdCallToAction;
        View mParentView;

        public AdViewHolder(View itemView) {
            super(itemView);
            mParentView = itemView;
            nativeAdIcon = (ImageView) itemView.findViewById(R.id.nativeAdIcon);
            nativeAdTitle = (TextView) itemView.findViewById(R.id.nativeAdTitle);
            nativeAdBody = (TextView) itemView.findViewById(R.id.nativeAdBody);
            nativeAdMedia = (MediaView) itemView.findViewById(R.id.nativeAdMedia);
            nativeAdSocialContext = (TextView) itemView.findViewById(R.id.nativeAdSocialContext);
            nativeAdCallToAction = (Button) itemView.findViewById(R.id.nativeAdCallToAction);

        }

        public void inflateAd() {

            Context context = mParentView.getContext();
            // Downloading and setting the ad icon.
            NativeAd.Image adIcon = ad.getAdIcon();
            NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

            // Downloading and setting the cover image.
            NativeAd.Image adCoverImage = ad.getAdCoverImage();
            int bannerWidth = adCoverImage.getWidth();
            int bannerHeight = adCoverImage.getHeight();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;
            nativeAdMedia.setLayoutParams(new LinearLayout.LayoutParams(
                    screenWidth,
                    Math.min((int) (((double) screenWidth / (double) bannerWidth) * bannerHeight), screenHeight / 3)
            ));
            nativeAdMedia.setNativeAd(ad);

            // Wire up the View with the native ad, the whole nativeAdContainer will be clickable
            ad.registerViewForInteraction(mParentView);

            // Or you can replace the above call with the following function to specify the clickable areas.
            // nativeAd.registerViewForInteraction(adView,
            //     Arrays.asList(nativeAdCallToAction, nativeAdMedia));
        }
    }

    public void setOnItemClicklistener(ItemClickListener itemClicklistener) {
        this.itemClickListener = itemClicklistener;
    }


}
