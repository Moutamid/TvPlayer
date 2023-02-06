package com.moutamid.tvplayer.models;

public class TabLocal {
    int id;
    String name;

    public TabLocal(int i, String name) {
        this.id = i;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TabLocal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
