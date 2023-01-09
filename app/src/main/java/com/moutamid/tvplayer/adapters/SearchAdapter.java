package com.moutamid.tvplayer.adapters;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.tvplayer.models.ChannelsModel;

import java.util.ArrayList;

public class SearchAdapter {
    Context context;
    ArrayList<ChannelsModel> list;


    public class SearchVH extends RecyclerView.ViewHolder{

        public SearchVH(@NonNull View itemView) {
            super(itemView);
        }
    }


}
