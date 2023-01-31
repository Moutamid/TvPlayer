package com.moutamid.tvplayer.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.adapters.StreamLinksAdapter;
import com.moutamid.tvplayer.databinding.FragmentLastPlayedBinding;
import com.moutamid.tvplayer.dialog.LinkDialog;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;


public class LastPlayedFragment extends Fragment {
    FragmentLastPlayedBinding binding;
    Context context;
    ArrayList<String> favrtList;

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

        favrtList = Stash.getArrayList(Constants.favrtList, String.class);
        if (favrtList == null){
            favrtList = new ArrayList<>();
        }

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
            if (model.getStreamingLinks().size()>1){
                linkDialog(model);
            } else {
                videoPlayerDialog(model);
            }
        }

        @Override
        public void favrt(ChannelsModel model, boolean isfvrt, ImageView favrt) {
            /*favrtList.clear();
            favrtList = Stash.getArrayList(Constants.favrtList, String.class);*/
            if (!isfvrt) {
                favrt.setImageResource(R.drawable.ic_favorite);
                isfvrt = true;
                favrtList.add(model.get_id());
                Stash.put(Constants.favrtList, favrtList);
                adapter.notifyDataSetChanged();
            } else {
                favrt.setImageResource(R.drawable.ic_favorite_border);
                isfvrt = false;
                favrtList.remove(favrtList.indexOf(model.get_id()));
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