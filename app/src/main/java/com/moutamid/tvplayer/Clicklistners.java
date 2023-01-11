package com.moutamid.tvplayer;

import android.widget.ImageView;

import com.moutamid.tvplayer.models.ChannelsModel;

public interface Clicklistners {
    void click (ChannelsModel model);
    void favrt(ChannelsModel model, boolean isfvrt, ImageView favrt);
}

