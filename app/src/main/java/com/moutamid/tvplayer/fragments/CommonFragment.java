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
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.adapters.StreamLinksAdapter;
import  com.moutamid.tvplayer.databinding.FragmentCommonBinding;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CommonFragment extends Fragment {

    String title;
    Context context;
    FragmentCommonBinding binding;
    ArrayList<ChannelsModel> channelsList;
    ArrayList<StreamLinksModel> streamLinks;
    ChannelsAdapter adapter;
    ArrayList<String> favrtList;

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

    public void linkDialog(ChannelsModel model){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.stream_links);

        ArrayList<StreamLinksModel> list = new ArrayList<>();

        TextView title = dialog.findViewById(R.id.title);
        RecyclerView rc = dialog.findViewById(R.id.links);
        rc.setLayoutManager(new LinearLayoutManager(context));
        rc.setHasFixedSize(false);
        String s = "We have got multiple links for " + model.getName() + ". Please Select one";
        title.setText(s);

        for (StreamLinksModel streamLinksModel : model.getStreamingLinks()){
            list.add(streamLinksModel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(list, Comparator.comparing(StreamLinksModel::getPriority));
        }

        StreamLinksAdapter adapter = new StreamLinksAdapter(context, list, dialog);
        rc.setAdapter(adapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);

    }

    private void videoPlayerDialog(ChannelsModel model) {
        VideoPlayerDialog vd = new VideoPlayerDialog(context, model.getStreamingLinks().get(0));
        vd.showStream();
    }

    Clicklistners clicklistners = new Clicklistners() {
        @Override
        public void click(ChannelsModel model) {
            if (model.getStreamingLinks().size()>1){
                linkDialog(model);
            } else {
                int idx = Stash.getInt("buttonIDX", 0);
                if (idx == 0) {
                    videoPlayerDialog(model);
                }
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
                adapter.notifyDataSetChanged();
            }
        }
    };
}