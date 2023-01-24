package com.moutamid.tvplayer.models;

import java.util.ArrayList;

public class FavrtModel{
    String id, category;

    public FavrtModel(String id, String category) {
        this.id = id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "FavrtModel{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
