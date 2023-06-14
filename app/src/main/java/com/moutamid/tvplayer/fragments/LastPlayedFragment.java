package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.databinding.FragmentLastPlayedBinding;
import com.moutamid.tvplayer.dialog.LinkDialog;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;


public class LastPlayedFragment extends Fragment {
    FragmentLastPlayedBinding binding;
    Context context;
    ArrayList<ChannelsModel> favrtList;
    ArrayList<ChannelsModel> channelsList;
    ChannelsAdapter adapter;

    public LastPlayedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLastPlayedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        channelsList = Stash.getArrayList("LastPlayed", ChannelsModel.class);

        Log.d("VideoURLPlayer", "Size FRA "+channelsList.size());

        if (channelsList==null || channelsList.isEmpty()){
            channelsList = new ArrayList<>();
            binding.recycler.setVisibility(View.GONE);
            binding.text.setVisibility(View.VISIBLE);
        } else {
            binding.recycler.setVisibility(View.VISIBLE);
            binding.text.setVisibility(View.GONE);
        }

        ArrayList<ChannelsModel> newList = new ArrayList<>(new LinkedHashSet<>(channelsList));

        Log.d("VideoURLPlayer", "Size newList "+newList.size());

        adapter = new ChannelsAdapter(context, channelsList, clicklistners);
        binding.recycler.setAdapter(adapter);

        binding.recycler.setLayoutManager(new GridLayoutManager(context, 3));
        binding.recycler.setHasFixedSize(false);


        return view;
    }

    Clicklistners clicklistners = new Clicklistners() {
        @Override
        public void click(ChannelsModel model) {
            Stash.put(Constants.VIDEO_TITLE, model.getName());
            if (model.getStreamingLinks().size()>1){
                linkDialog(model);
            } else if (model.getStreamingLinks().size() == 0) {
                Toast.makeText(context, "No Streaming link found", Toast.LENGTH_SHORT).show();
            } else {
                videoPlayerDialog(model);
            }
        }

        @Override
        public void favrouite(ChannelsModel model, boolean isfvrt) {
            favrtList.clear();
            favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
            if (!isfvrt) {
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                favrtList.add(model);
                Stash.put(Constants.favrtList, favrtList);
                adapter.notifyDataSetChanged();
            } else {
                for (int i = 0; i < favrtList.size(); i++) {
                    if (favrtList.get(i).get_id().equals(model.get_id())) {
                        Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
                        favrtList.remove(i);
                    }
                }
                Stash.put(Constants.favrtList, favrtList);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void linkDialog(ChannelsModel model) {
        LinkDialog ld = new LinkDialog(context, model);
        ld.show();
    }

    private void videoPlayerDialog(ChannelsModel model) {
        VideoPlayerDialog vd = new VideoPlayerDialog(context, model.getStreamingLinks().get(0), model);
        vd.showStream();
    }
}