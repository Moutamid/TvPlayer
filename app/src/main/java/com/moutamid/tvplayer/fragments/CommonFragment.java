package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.tvplayer.Clicklistners;
import  com.moutamid.tvplayer.databinding.FragmentCommonBinding;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommonFragment extends Fragment {

    String title;
    Context context;
    FragmentCommonBinding binding;
    ArrayList<ChannelsModel> channelsList;
    ArrayList<StreamLinksModel> streamLinks;
    ChannelsAdapter adapter;

    public CommonFragment() {
        // Required empty public constructor
    }

    public CommonFragment(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        channelsList = new ArrayList<>();
        streamLinks = new ArrayList<>();
        binding.recycler.setHasFixedSize(false);

        try {
            JSONArray sports = new JSONArray(title);

            for (int i = 0; i < sports.length(); i++) {
                JSONObject obj = sports.getJSONObject(i);
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

                channelsList.add(channelsModel);

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if(channelsList.size() == 1){
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 1));
        } else if(channelsList.size() == 2){
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            binding.recycler.setLayoutManager(new GridLayoutManager(context, 3));
        }

        adapter = new ChannelsAdapter(context, channelsList,clicklistners );
        binding.recycler.setAdapter(adapter);

        return view;
    }

    Clicklistners clicklistners = new Clicklistners() {
        @Override
        public void click(ChannelsModel model) {

        }
    };
}