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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.google.android.material.card.MaterialCardView;
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.adapters.StreamLinksAdapter;
import com.moutamid.tvplayer.databinding.FragmentFavouritesBinding;
import com.moutamid.tvplayer.dialog.LinkDialog;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;
import com.moutamid.tvplayer.models.TabsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavouritesFragment extends Fragment {

    FragmentFavouritesBinding binding;
    Context context;
    ArrayList<ChannelsModel> channelsList;
    ArrayList<StreamLinksModel> streamLinks;
    ChannelsAdapter adapter;
    ArrayList<String> favrtList;
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

        favrtList = Stash.getArrayList("favrtList", String.class);
        tabs = Stash.getArrayList("tabs", TabsModel.class);


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

        for (TabsModel s : tabs) {
            try {
                JSONArray channelsArray = new JSONArray(s.getObject());
                for (int i = 0; i < channelsArray.length(); i++) {
                    JSONObject obj = channelsArray.getJSONObject(i);
                    ChannelsModel channelsModel = new ChannelsModel();
                    channelsModel.set_id(obj.getString("_id"));
                    channelsModel.setName(obj.getString("name"));
                    channelsModel.setCategory(obj.getString("category"));
                    channelsModel.setImage(obj.getString("image"));
                    channelsModel.setImageUrl(obj.getString("imageUrl"));
                    channelsModel.setHide(obj.getBoolean("hide"));
                    channelsModel.setCountry(obj.getString("country"));
                    channelsModel.setID(obj.getInt("ID"));
                    channelsModel.set__v(obj.getInt("__v"));

                    JSONArray streamingLinks = obj.getJSONArray("streamingLinks");

                    streamLinks.clear();
                    for (int j = 0; j < streamingLinks.length(); j++) {
                        JSONObject stream = streamingLinks.getJSONObject(j);
                        StreamLinksModel model1 = new StreamLinksModel();
                        model1.set_id(stream.getString("_id"));
                        model1.setName(stream.getString("name"));
                        model1.setToken(stream.getString("token"));
                        model1.setPriority(stream.getInt("priority"));
                        model1.setRequest_header(stream.getString("request_header"));
                        model1.setPlayer_header(stream.getString("player_header"));
                        streamLinks.add(model1);
                    }

                    channelsModel.setStreamingLinks(streamLinks);
                    for (String id : favrtList) {
                        if (channelsModel.get_id().equals(id)) {
                            channelsList.add(channelsModel);
                        }
                    }
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

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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
        public void favrt(ChannelsModel model, boolean isfvrt, ImageView favrt) {
            if (!isfvrt) {
                favrt.setImageResource(R.drawable.ic_favorite);
                isfvrt = true;
                favrtList.add(model.get_id());
                Stash.put("favrtList", favrtList);
                adapter.notifyDataSetChanged();
            } else {
                favrtList.remove(favrtList.indexOf(model.get_id()));
                Stash.put("favrtList", favrtList);
                favrt.setImageResource(R.drawable.ic_favorite_border);
                isfvrt = false;
                getActivity().getSupportFragmentManager().beginTransaction().remove(FavouritesFragment.this);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouritesFragment()).commit();
                // adapter.notifyDataSetChanged();
            }
        }
    };
}