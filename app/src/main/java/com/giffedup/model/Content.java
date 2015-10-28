package com.giffedup.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by vinay-r on 8/10/15.
 */
public class Content implements Parcelable {


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

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

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

    public Images getmImages() {
        return mImages;
    }

    public ImageConfigurationModel getSmallImage() {
        return (null != getmImages()) ? getmImages().getSmall() : null;
    }

    public ImageConfigurationModel getDownsizedImage() {
        return (null != getmImages()) ? getmImages().getDownSized() : null;
    }

    public ImageConfigurationModel getDownsizedStillImage() {
        return (null != getmImages()) ? getmImages().getDownSizedStill() : null;
    }

    public ImageConfigurationModel getOriginalImage() {
        return (null != getmImages()) ? getmImages().getOriginal() : null;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }

    public String getContentType() {
        return mContentType;
    }

    public Content() {

    }

    public ParseObject createParseObject() {
        ParseObject parseObject = new ParseObject("Content");
        parseObject.put("id", mId);
        parseObject.put("type", mType);
        if(!TextUtils.isEmpty(mContentType))
            parseObject.put("contentType", mContentType);
//        parseObject.put("rating", mRating);
//        parseObject.put("caption", mCaption);
        parseObject.put("sourceUrl", mSourceUrl);
        parseObject.put("creationDateTime", mCreationDateTime);
        parseObject.put("trendingDateTime", mTrendingDateTime);
        parseObject.put("images", mImages.createParseObject());

        return parseObject;
    }

    public Content(ParseObject parseObject) {

        if(!parseObject.isDataAvailable()) {
            parseObject.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(final ParseObject object, ParseException e) {
                    setData(object);

                }
            });
        }else {
            setData(parseObject);
        }

    }

    public void setData(ParseObject object) {
        mId = object.getString("id");
        mType = object.getString("type");
        mContentType = object.getString("contentType");
        mRating = object.getString("rating");
        mCaption = object.getString("caption");
        mCreationDateTime = object.getString("creationDateTime");
        mTrendingDateTime = object.getString("trendingDateTime");
        mSourceUrl = object.getString("sourceUrl");
        mImages = new Images(object.getParseObject("images"));
    }

    public Content(Parcel in) {
        mId = in.readString();
        mType = in.readString();
        mCaption = in.readString();
        mRating = in.readString();
        mSourceUrl = in.readString();
        mCreationDateTime = in.readString();
        mTrendingDateTime = in.readString();
        mContentType = in.readString();
        mImages = in.readParcelable(Images.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mType);
        dest.writeString(mCaption);
        dest.writeString(mRating);
        dest.writeString(mSourceUrl);
        dest.writeString(mCreationDateTime);
        dest.writeString(mTrendingDateTime);
        dest.writeString(mContentType);
        dest.writeParcelable(mImages, flags);
    }

    private static class Images implements Parcelable {
        @SerializedName("downsized")
        private ImageConfigurationModel mDownSized;

        @SerializedName("downsized_still")
        private ImageConfigurationModel mDownSizedStill;

        @SerializedName("original")
        private ImageConfigurationModel mOriginal;

        @SerializedName("fixed_width_downsampled")
        private ImageConfigurationModel mSmall;

        public Images() {

        }

        public Images(ParseObject parseObject) {
            if(!parseObject.isDataAvailable()) {
                parseObject.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        mDownSized = new ImageConfigurationModel(object.getParseObject("downsized"));
                        mDownSizedStill = new ImageConfigurationModel(object.getParseObject("downsizedStill"));
                        mOriginal = new ImageConfigurationModel(object.getParseObject("original"));
                        mSmall = new ImageConfigurationModel(object.getParseObject("small"));
                    }
                });
            } else {
                mOriginal = new ImageConfigurationModel(parseObject.getParseObject("original"));
                mDownSized = new ImageConfigurationModel(parseObject.getParseObject("downsized"));
                mDownSizedStill = new ImageConfigurationModel(parseObject.getParseObject("downsizedStill"));
                mSmall = new ImageConfigurationModel(parseObject.getParseObject("small"));
            }


        }

        public ParseObject createParseObject() {
            ParseObject parseObject = new ParseObject("Images");
            parseObject.put("downsized",mDownSized.createParseObject());
            parseObject.put("downsizedStill",mDownSizedStill.createParseObject());
            parseObject.put("original",mOriginal.createParseObject());
            parseObject.put("small",mSmall.createParseObject());

            return parseObject;
        }

        protected Images(Parcel in) {
            mDownSized = in.readParcelable(ImageConfigurationModel.class.getClassLoader());
            mDownSizedStill = in.readParcelable(ImageConfigurationModel.class.getClassLoader());
            mOriginal = in.readParcelable(ImageConfigurationModel.class.getClassLoader());
            mSmall = in.readParcelable(ImageConfigurationModel.class.getClassLoader());
        }

        public static final Creator<Images> CREATOR = new Creator<Images>() {
            @Override
            public Images createFromParcel(Parcel in) {
                return new Images(in);
            }

            @Override
            public Images[] newArray(int size) {
                return new Images[size];
            }
        };

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

        protected void setSmall(ImageConfigurationModel small) {
            mSmall = small;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(mDownSized, flags);
            dest.writeParcelable(mDownSizedStill, flags);
            dest.writeParcelable(mOriginal, flags);
            dest.writeParcelable(mSmall, flags);
        }
    }

}
