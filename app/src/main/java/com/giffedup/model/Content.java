package com.giffedup.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinay-r on 8/10/15.
 */
public class Content {

    @SerializedName("type")
    private String mType;

    @SerializedName("id")
    private String mId;

    @SerializedName("caption")
    private String mCaption;

    @SerializedName("rating")
    private String mRating;

    @SerializedName("source")
    private String mSourceUrl;

    @SerializedName("import_datetime")
    private String mCreationDateTime;

    @SerializedName("trending_datetime")
    private String mTrendingDateTime;

    @SerializedName("images")
    private Images mImages;

    private String mContentType;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getSourceUrl() {
        return mSourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        mSourceUrl = sourceUrl;
    }

    public String getCreationDateTime() {
        return mCreationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        mCreationDateTime = creationDateTime;
    }

    public String getTrendingDateTime() {
        return mTrendingDateTime;
    }

    public void setTrendingDateTime(String trendingDateTime) {
        mTrendingDateTime = trendingDateTime;
    }

    public void setImages(Images images) {
        mImages = images;
    }

    public ImageConfigurationModel getSmallImage() {
        return (null != mImages)? mImages.getSmall() : null;
    }

    public ImageConfigurationModel getDownsizedImage() {
        return (null != mImages)? mImages.getDownSized() : null;
    }

    public ImageConfigurationModel getDownsizedStillImage() {
        return (null != mImages)? mImages.getDownSizedStill() : null;
    }

    public ImageConfigurationModel getOriginalImage() {
        return (null != mImages)? mImages.getOriginal() : null;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }

    public String getContentType() {
     return mContentType;
    }

    private static class Images {
        @SerializedName("downsized")
        private ImageConfigurationModel mDownSized;

        @SerializedName("downsized_still")
        private ImageConfigurationModel mDownSizedStill;

        @SerializedName("original")
        private ImageConfigurationModel mOriginal;

        @SerializedName("fixed_width_downsampled")
        private ImageConfigurationModel mSmall;

        protected ImageConfigurationModel getDownSized() {
            return mDownSized;
        }

        protected ImageConfigurationModel getSmall() {
            return mSmall;
        }

        protected void setDownSized(ImageConfigurationModel downSized) {
            mDownSized = downSized;
        }

        protected ImageConfigurationModel getDownSizedStill() {
            return mDownSizedStill;
        }

        protected void setDownSizedStill(ImageConfigurationModel downSizedStill) {
            mDownSizedStill = downSizedStill;
        }

        protected ImageConfigurationModel getOriginal() {
            return mOriginal;
        }

        protected void setOriginal(ImageConfigurationModel original) {
            mOriginal = original;
        }

        protected void setSmall(ImageConfigurationModel small) { mSmall = small;}
    }

}
