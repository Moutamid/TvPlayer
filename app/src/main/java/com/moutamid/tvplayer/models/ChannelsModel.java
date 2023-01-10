package com.moutamid.tvplayer.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ChannelsModel implements Serializable {

    String _id, name, category, image, imageUrl;
    boolean hide;
    String country;
    ArrayList<StreamLinksModel> streamingLinks;
    int ID, __v;

    public ChannelsModel() {
    }

    public ChannelsModel(String _id, String name, String category, String image, String imageUrl, boolean hide, String country, ArrayList<StreamLinksModel> streamingLinks, int ID, int __v) {
        this._id = _id;
        this.name = name;
        this.category = category;
        this.image = image;
        this.imageUrl = imageUrl;
        this.hide = hide;
        this.country = country;
        this.streamingLinks = streamingLinks;
        this.ID = ID;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<StreamLinksModel> getStreamingLinks() {
        return streamingLinks;
    }

    public void setStreamingLinks(ArrayList<StreamLinksModel> streamingLinks) {
        this.streamingLinks = streamingLinks;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
