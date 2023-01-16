package com.moutamid.tvplayer.models;

import java.util.ArrayList;
import java.util.Map;

public class CountriesChannelModel {

    String name;
    ArrayList<ChannelsModel> channelsList;
    Map<String, ArrayList<ChannelsModel>> map;

    public CountriesChannelModel() {
    }

    public CountriesChannelModel(String name, ArrayList<ChannelsModel> channelsList) {
        this.name = name;
        this.channelsList = channelsList;
    }

    public CountriesChannelModel(String name, ArrayList<ChannelsModel> channelsList, Map<String, ArrayList<ChannelsModel>> map) {
        this.name = name;
        this.channelsList = channelsList;
        this.map = map;
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

    public ArrayList<ChannelsModel> getChannelsList() {
        return channelsList;
    }

    public void setChannelsList(ArrayList<ChannelsModel> channelsList) {
        this.channelsList = channelsList;
    }
}
