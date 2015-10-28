package com.giffedup.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akshay on 10/27/15.
 */
public class StoryModel implements Parcelable{

    String id;
    String contentId;
    String title;
    ImageConfigurationModel downSized;
    ImageConfigurationModel downsizedStill;
    ImageConfigurationModel original;
    ImageConfigurationModel smallImage;

    //List<ParseObject> feeds;

    public StoryModel(String id, String contentId, String title, ImageConfigurationModel downSized, ImageConfigurationModel downsizedStill, ImageConfigurationModel original, ImageConfigurationModel smallImage) {
        this.id = id;
        this.contentId = contentId;
        this.title = title;
        this.downSized = downSized;
        this.downsizedStill = downsizedStill;
        this.original = original;
        this.smallImage = smallImage;
    }

    protected StoryModel(Parcel in) {
        id = in.readString();
        contentId = in.readString();
        title = in.readString();
        downSized = in.readParcelable(ImageConfigurationModel.class.getClassLoader());
        downsizedStill = in.readParcelable(ImageConfigurationModel.class.getClassLoader());
        original = in.readParcelable(ImageConfigurationModel.class.getClassLoader());
        smallImage = in.readParcelable(ImageConfigurationModel.class.getClassLoader());
    }

    public static final Creator<StoryModel> CREATOR = new Creator<StoryModel>() {
        @Override
        public StoryModel createFromParcel(Parcel in) {
            return new StoryModel(in);
        }

        @Override
        public StoryModel[] newArray(int size) {
            return new StoryModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(contentId);
        dest.writeString(title);
        dest.writeParcelable(downSized, flags);
        dest.writeParcelable(downsizedStill, flags);
        dest.writeParcelable(original, flags);
        dest.writeParcelable(smallImage, flags);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageConfigurationModel getDownSized() {
        return downSized;
    }

    public void setDownSized(ImageConfigurationModel downSized) {
        this.downSized = downSized;
    }

    public ImageConfigurationModel getDownsizedStill() {
        return downsizedStill;
    }

    public void setDownsizedStill(ImageConfigurationModel downsizedStill) {
        this.downsizedStill = downsizedStill;
    }

    public ImageConfigurationModel getOriginal() {
        return original;
    }

    public void setOriginal(ImageConfigurationModel original) {
        this.original = original;
    }

    public ImageConfigurationModel getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(ImageConfigurationModel smallImage) {
        this.smallImage = smallImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
