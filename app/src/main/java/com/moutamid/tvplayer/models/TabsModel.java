package com.moutamid.tvplayer.models;

import androidx.fragment.app.Fragment;

public class TabsModel {
    String title;
    String object;
    boolean isHidden;

    public TabsModel() {
    }

    public TabsModel(String title, String object) {
        this.title = title;
        this.object = object;
    }

    public TabsModel(String title, String object, boolean isHidden) {
        this.title = title;
        this.object = object;
        this.isHidden = isHidden;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public TabsModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
