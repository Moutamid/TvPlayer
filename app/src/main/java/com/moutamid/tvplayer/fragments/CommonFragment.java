package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import java.util.ArrayList;

public class CommonFragment extends Fragment {

    String title;
    Context context;
    ArrayList<ChannelsModel> channelsList;
    ArrayList<StreamLinksModel> streamLinks;
    ChannelsAdapter adapter;

    public static CommonFragment newInstance() {
        return new CommonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public CommonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_common, container, false);
    }
}