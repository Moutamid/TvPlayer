package com.moutamid.tvplayer.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountriesChannelModel {

    int id;
    String name;
    List<ChannelsModel> channelsList;
//    ArrayList<ChannelsModel> channelsList;
    Map<String, ArrayList<ChannelsModel>> map;

    public CountriesChannelModel() {
    }

    public CountriesChannelModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

//    public CountriesChannelModel(String name, ArrayList<ChannelsModel> channelsList) {
    public CountriesChannelModel(String name, List<ChannelsModel> channelsList) {
        this.name = name;
        this.channelsList = channelsList;
    }

    public CountriesChannelModel(String name, ArrayList<ChannelsModel> channelsList, Map<String, ArrayList<ChannelsModel>> map) {
        this.name = name;
        this.channelsList = channelsList;
        this.map = map;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, ArrayList<ChannelsModel>> getMap() {
        return map;
    }

    public void setMap(Map<String, ArrayList<ChannelsModel>> map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ArrayList<ChannelsModel> getChannelsList() {
    public List<ChannelsModel> getChannelsList() {
        return channelsList;
    }

    public void setChannelsList(ArrayList<ChannelsModel> channelsList) {
        this.channelsList = channelsList;
    }

    @Override
    public String toString() {
        return "CountriesChannelModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", channelsList=" + channelsList +
                ", map=" + map +
                '}';
    }
}
