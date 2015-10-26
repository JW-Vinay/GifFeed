package com.giffedup.model;

/**
 * Created by vinayr on 10/19/15.
 */
public class FeedModel {

    public enum contentType {
        STORY,
        FEED
    }

    private String mTitle;
    private Content mContent;
    private contentType mType;

    public FeedModel() {
        mTitle = "";
        mContent = null;
        mType = contentType.FEED;
    }

    public FeedModel(contentType type) {
        this.mContent = null;
        this.mTitle = "";
        this.mType = type;
    }

    public FeedModel(String title, Content content, contentType type) {
        this.mContent = content;
        this.mTitle = title;
        this.mType = type;
    }

    public FeedModel(String title, Content content) {
        this.mTitle = title;
        this.mContent = content;
        mType = contentType.FEED;
    }

    public contentType getmType() {
        return mType;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Content getmContent() {
        return mContent;
    }

    public void setmContent(Content mContent) {
        this.mContent = mContent;
    }
}
