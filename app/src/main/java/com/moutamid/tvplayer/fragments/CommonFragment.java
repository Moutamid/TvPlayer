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
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.adapters.CountriesWiseAdapter;
import com.moutamid.tvplayer.adapters.StreamLinksAdapter;
import  com.moutamid.tvplayer.databinding.FragmentCommonBinding;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.dialog.LinkDialog;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.CountriesChannelModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;

public class CommonFragment extends Fragment {

    String title;
    Context context;
    FragmentCommonBinding binding;
    ArrayList<ChannelsModel> channelsList;
    ArrayList<CountriesChannelModel> countriesChannel;
    ArrayList<StreamLinksModel> streamLinks;
    CountriesWiseAdapter adapter;
    ArrayList<String> favrtList;

    public CommonFragment() {
        // Required empty public constructor
    }

    public CommonFragment(String title) {
        this.title = title;
        //Toast.makeText(context, title.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        channelsList = new ArrayList<>();
        streamLinks = new ArrayList<>();
        countriesChannel = new ArrayList<>();
        binding.recycler.setHasFixedSize(false);

        favrtList = Stash.getArrayList("favrtList", String.class);
        if (favrtList == null){
            favrtList = new ArrayList<>();
        }

        try {
            JSONArray jsonArray = new JSONArray(title);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
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
                    model1.setStream_link(stream.getString("url"));
                    streamLinks.add(model1);
                }

                channelsModel.setStreamingLinks(streamLinks);

                channelsList.add(channelsModel);

                countriesChannel.add(new CountriesChannelModel(channelsModel.getCountry(), channelsList));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        /*if(channelsList.size() == 1){
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 1));
        } else if(channelsList.size() == 2){
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 3));
        }*/


        binding.recycler.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CountriesWiseAdapter(context, countriesChannel, clicklistners );
        binding.recycler.setAdapter(adapter);

        return view;
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
                /*int idx = Stash.getInt("buttonIDX", 0);
                if (idx == 0) {
                    videoPlayerDialog(model);
                }*/
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
                //adapter.notifyDataSetChanged();
            }
        }
    };
}