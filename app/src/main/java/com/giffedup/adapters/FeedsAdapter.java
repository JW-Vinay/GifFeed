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
import com.giffedup.ui.FeedsListActivity;
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
    private int TYPE_AD = 1;
    private int TYPE_ITEM = 2;

    private static final int AD_INDEX = 0;
    private NativeAd ad;

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

        } else if(viewType == TYPE_AD){
            View v = mInflater.inflate(R.layout.ad_unit, parent, false);
            AdViewHolder adViewHolder = new AdViewHolder(v);
            return  adViewHolder;
        }
        else {

            View view = mInflater.inflate(R.layout.feeds_item, parent, false);
            FeedsHolder holder = new FeedsHolder(view);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        if (position == 1)
            return  TYPE_AD;
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

//    public void addNativeAd(NativeAd ad) {
//        if (ad == null) {
//            return;
//        }
//        if (this.ad != null) {
//            // Clean up the old ad before inserting the new one
//            this.ad.unregisterView();
//            this.mFeeds.remove(AD_INDEX);
//            this.ad = null;
//            this.notifyDataSetChanged();
//        }
//        this.ad = ad;
//        View adView = inflater.inflate(R.layout.ad_unit, null);
//        this.inflateAd(ad, adView, mContext);
//        mFeeds.add(AD_INDEX, adView);
//        this.notifyDataSetChanged();
//    }

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

    class AdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        //        SimpleDraweeView mImageView;
        ImageView mImageView;

        public AdViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            mTitle = (TextView) itemView.findViewById(R.id.captionTextView);
//            mImageView = (ImageView) itemView.findViewById(R.id.gifImageView);
            this.inflateAd();

        }

        public void inflateAd(NativeAd nativeAd, View adView, Context context) {
            // Create native UI using the ad metadata.

            ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.nativeAdIcon);
            TextView nativeAdTitle = (TextView) adView.findViewById(R.id.nativeAdTitle);
            TextView nativeAdBody = (TextView) adView.findViewById(R.id.nativeAdBody);
            MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.nativeAdMedia);
            TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.nativeAdSocialContext);
            Button nativeAdCallToAction = (Button) adView.findViewById(R.id.nativeAdCallToAction);

            // Setting the Text
            nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            nativeAdCallToAction.setVisibility(View.VISIBLE);
            nativeAdTitle.setText(nativeAd.getAdTitle());
            nativeAdBody.setText(nativeAd.getAdBody());

            // Downloading and setting the ad icon.
            NativeAd.Image adIcon = nativeAd.getAdIcon();
            NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

            // Downloading and setting the cover image.
            NativeAd.Image adCoverImage = nativeAd.getAdCoverImage();
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
            nativeAdMedia.setNativeAd(nativeAd);

            // Wire up the View with the native ad, the whole nativeAdContainer will be clickable
            nativeAd.registerViewForInteraction(adView);

            // Or you can replace the above call with the following function to specify the clickable areas.
            // nativeAd.registerViewForInteraction(adView,
            //     Arrays.asList(nativeAdCallToAction, nativeAdMedia));
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
