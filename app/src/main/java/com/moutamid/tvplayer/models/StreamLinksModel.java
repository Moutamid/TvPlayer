package com.moutamid.tvplayer.models;

public class StreamLinksModel {
    String name, token;
    int priority;
    String request_header, player_header, _id;

    public StreamLinksModel() {
    }

    public StreamLinksModel(String name, String token, int priority, String request_header, String player_header, String _id) {
        this.name = name;
        this.token = token;
        this.priority = priority;
        this.request_header = request_header;
        this.player_header = player_header;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRequest_header() {
        return request_header;
    }

    public void setRequest_header(String request_header) {
        this.request_header = request_header;
    }

    public String getPlayer_header() {
        return player_header;
    }

    public void setPlayer_header(String player_header) {
        this.player_header = player_header;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
