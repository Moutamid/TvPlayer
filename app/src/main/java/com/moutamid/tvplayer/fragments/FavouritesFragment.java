package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.databinding.FragmentFavouritesBinding;
import com.moutamid.tvplayer.dialog.LinkDialog;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;
import com.moutamid.tvplayer.models.TabsModel;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {

    FragmentFavouritesBinding binding;
    Context context;
    ArrayList<ChannelsModel> channelsList;
    ArrayList<StreamLinksModel> streamLinks;
    ChannelsAdapter adapter;
    ArrayList<ChannelsModel> favrtList;
    ArrayList<TabsModel> tabs = new ArrayList<>();

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        channelsList = new ArrayList<>();
        streamLinks = new ArrayList<>();
        binding.recycler.setHasFixedSize(false);

        favrtList = new ArrayList<>();

        favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
        tabs = Stash.getArrayList(Constants.channelsTab, TabsModel.class);

        if (favrtList == null){
            favrtList = new ArrayList<>();
        }
        if (!favrtList.isEmpty()){
            binding.recycler.setVisibility(View.VISIBLE);
            binding.text.setVisibility(View.GONE);
            fetchChannels();
            // fetchEvents();
        } else {
            binding.recycler.setVisibility(View.GONE);
            binding.text.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void fetchChannels() {
        favrtList.clear();
        favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
        for (ChannelsModel model : favrtList){
            channelsList.add(model);
        }

        if (channelsList.size() == 1) {
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 1));
        } else if (channelsList.size() == 2) {
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 3));
        }

        adapter = new ChannelsAdapter(context, channelsList, clicklistners);
        binding.recycler.setAdapter(adapter);
    }

    public void linkDialog(ChannelsModel model){
        LinkDialog ld = new LinkDialog(context, model);
        ld.show();
    }

    private void videoPlayerDialog(ChannelsModel model) {
        VideoPlayerDialog vd = new VideoPlayerDialog(context, model.getStreamingLinks().get(0), model);
        vd.showStream();
    }

    Clicklistners clicklistners = new Clicklistners() {
        @Override
        public void click(ChannelsModel model) {
            if (model.getStreamingLinks().size()>1){
                linkDialog(model);
            } else {
                videoPlayerDialog(model);
            }
        }

        @Override
        public void favrouite(ChannelsModel model, boolean isfvrt) {
            favrtList.clear();
            favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
            if (!isfvrt) {
                favrtList.add(model);
                //adapter.notifyDataSetChanged();
            } else {
                for (int i = 0; i < favrtList.size(); i++) {
                    if (favrtList.get(i).get_id().equals(model.get_id())) {
                        favrtList.remove(i);
                    }
                }
                //adapter.notifyDataSetChanged();
            }
            Stash.put(Constants.favrtList, favrtList);
            fetchChannels();
        }
    };
}