package com.moutamid.tvplayer.models;

import androidx.annotation.NonNull;

public class TabsModel {
    int id;
    String name;
    String object;
    boolean isHidden;

    public TabsModel() {
    }

    public TabsModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TabsModel(String title, String object) {
        this.name = title;
        this.object = object;
    }

    public TabsModel(String title, String object, boolean isHidden) {
        this.name = title;
        this.object = object;
        this.isHidden = isHidden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @NonNull
    @Override
    public String toString() {
        return "TabsModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", object='" + object + '\'' +
                ", isHidden=" + isHidden +
                '}';
    }
}
