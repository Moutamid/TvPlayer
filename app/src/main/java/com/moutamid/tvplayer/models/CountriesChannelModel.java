package com.moutamid.tvplayer.models;

import java.util.ArrayList;

public class CountriesChannelModel {

    String name;
    ArrayList<ChannelsModel> channelsList;

    public CountriesChannelModel() {
    }

    public CountriesChannelModel(String name, ArrayList<ChannelsModel> channelsList) {
        this.name = name;
        this.channelsList = channelsList;
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
