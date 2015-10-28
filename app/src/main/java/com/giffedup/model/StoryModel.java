package com.giffedup.model;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by akshay on 10/27/15.
 */
public class StoryModel {

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

}
