package com.moutamid.tvplayer;

import com.moutamid.tvplayer.models.ChannelsModel;

public interface Clicklistners {
    void click (ChannelsModel model);
    void favrouite(ChannelsModel model, boolean isfvrt);
}

