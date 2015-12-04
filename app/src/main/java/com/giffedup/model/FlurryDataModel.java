package com.giffedup.model;

import com.flurry.android.ads.FlurryAdNative;
import com.flurry.android.ads.FlurryAdNativeAsset;

/**
 * Created by vinayr on 12/1/15.
 */
public class FlurryDataModel {

    private FlurryAdNativeAsset mSponsorName;
    private FlurryAdNativeAsset mSponsoredImageUrl;
    private FlurryAdNativeAsset mHeadlineText;
    private FlurryAdNativeAsset mSummaryText;
    private FlurryAdNativeAsset mMainImage;

    private FlurryAdNative mNativeAd;

    private FlurryDataModel() {
    }

    public FlurryAdNative getmNativeAd() {
        return this.mNativeAd;
    }

    public FlurryDataModel setNativeAd(FlurryAdNative flurryAdNative) {
        this.mNativeAd = flurryAdNative;
        return this;
    }

    public String getmSponsorName() {
        return mSponsorName.getValue();
    }

    public FlurryDataModel setmSponsorName(FlurryAdNativeAsset mSponsorName) {
        this.mSponsorName = mSponsorName;
        return this;
    }

    public String getmSponsoredImageUrl() {
        return mSponsoredImageUrl.getValue();
    }

    public FlurryDataModel setmSponsoredImageUrl(FlurryAdNativeAsset mSponsoredImageUrl) {
        this.mSponsoredImageUrl = mSponsoredImageUrl;
        return this;
    }

    public String getmHeadlineText() {
        return mHeadlineText.getValue();
    }

    public FlurryDataModel setmHeadlineText(FlurryAdNativeAsset mHeadlineText) {
        this.mHeadlineText = mHeadlineText;
        return this;
    }

    public String getmSummaryText() {
        return mSummaryText.getValue();
    }

    public FlurryDataModel setmSummaryText(FlurryAdNativeAsset mSummaryText) {
        this.mSummaryText = mSummaryText;
        return this;
    }

    public String getmMainImage() {
        return mMainImage.getValue();
    }

    public FlurryDataModel setmMainImage(FlurryAdNativeAsset mMainImage) {
        this.mMainImage = mMainImage;
        return this;
    }

    public static class Builder {

        private FlurryAdNativeAsset mSecondaryAsset, mSummaryText, mSponsoredImageUrl, mHeadlineText,mSponsorName;
        private FlurryAdNative mNativFlurryAdNative;

        public Builder setNativeAd(FlurryAdNative flurryAdNative) {
            this.mNativFlurryAdNative = flurryAdNative;
            return this;
        }

        public Builder setmSponsorName(FlurryAdNativeAsset mSponsorName) {
            this.mSponsorName = mSponsorName;
            return this;
        }

        public Builder setmSecondaryAsset(FlurryAdNativeAsset mSecondaryAsset) {
            this.mSecondaryAsset = mSecondaryAsset;
            return this;
        }

        public Builder setmSummaryText(FlurryAdNativeAsset mSummaryText) {
            this.mSummaryText = mSummaryText;
            return this;
        }

        public Builder setmSponsoredImageUrl(FlurryAdNativeAsset mSponsoredImageUrl) {
            this.mSponsoredImageUrl = mSponsoredImageUrl;
            return this;
        }

        public Builder setmHeadlineText(FlurryAdNativeAsset mHeadlineText) {
            this.mHeadlineText = mHeadlineText;
            return this;
        }

        public FlurryDataModel build() {
            return new FlurryDataModel()
                    .setmHeadlineText(mHeadlineText)
                    .setmSummaryText(mSummaryText)
                    .setmMainImage(mSecondaryAsset)
                    .setmSponsorName(mSponsorName)
                    .setNativeAd(mNativFlurryAdNative)
                    .setmSponsoredImageUrl(mSponsoredImageUrl);
        }
    }
}
