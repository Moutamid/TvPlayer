package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.adapters.CountriesWiseAdapter;
import  com.moutamid.tvplayer.databinding.FragmentCommonBinding;
import com.moutamid.tvplayer.dialog.LinkDialog;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.CountriesChannelModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CommonFragment extends Fragment {

    String title;
    Context context;
    FragmentCommonBinding binding;
    ArrayList<ChannelsModel> channelsList;
    //ArrayList<CountriesChannelModel> countriesChannel;
    ArrayList<CountriesChannelModel> countriesChannel;
//    ArrayList<StreamLinksModel> streamLinks;
    CountriesWiseAdapter adapter;
    ArrayList<ChannelsModel> favrtList;
    Map<String, ArrayList<ChannelsModel>> map = new HashMap<>();

    ArrayList<String> newList = new ArrayList();

    public CommonFragment() {
        // Required empty public constructor
    }

    public CommonFragment(String title) {
        this.title = title;
        //Toast.makeText(context, title.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        channelsList = new ArrayList<>();
//        streamLinks = new ArrayList<>();
        //countriesChannel = new ArrayList<CountriesChannelModel>();
        countriesChannel = new ArrayList<CountriesChannelModel>();
        binding.recycler.setHasFixedSize(false);

        favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
        if (favrtList == null){
            favrtList = new ArrayList<>();
        }

        try {
            JSONArray jsonArray = new JSONArray(title);
            Log.d("tager", "jsonArray title Size: "+ jsonArray.length());
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

                JSONArray streamingLinksArrays = obj.getJSONArray("streamingLinks");
                Log.d("tager", "jsonArray Links Size: "+streamingLinksArrays.length());
                ArrayList<StreamLinksModel> streamLinks = new ArrayList<>();
                if (streamingLinksArrays.length() > 0) {//&& streamingLinks!=null
                    for (int j = 0; j < streamingLinksArrays.length(); j++) {
                        JSONObject stream = streamingLinksArrays.getJSONObject(j);
                        StreamLinksModel model1 = new StreamLinksModel();
                        model1.set_id(stream.getString("_id"));
                        model1.setName(stream.getString("name"));
                        model1.setToken(stream.getString("token"));
                        model1.setPriority(stream.getInt("priority"));
                        model1.setRequest_header(stream.getString("request_header"));
                        model1.setPlayer_header(stream.getString("player_header"));
                        model1.setStream_link(stream.getString("url"));
//                        Log.d("tager", "array link: "+stream.getString("url"));
                        streamLinks.add(model1);
                        channelsModel.setStreamingLinks(streamLinks);
                    }
                }else {
                    Log.d("tager", "streaming links error");
                }

                channelsList.add(channelsModel);
//                Log.d("tager", "model after array: "+channelsModel);
                //Toast.makeText(context, channelsList.get(0).getStreamingLinks().toString(), Toast.LENGTH_SHORT).show();

                //map.put(channelsModel.getCountry(), channelsList);

                newList.add(channelsModel.getCountry());

                for (CountriesChannelModel model : countriesChannel){
                    String channels = "null";
                    for (ChannelsModel channelsModell: model.getChannelsList()) {
                        channels = channelsModell.getName()+"----"+channelsModell.getStreamingLinks();
                    }
                    Log.d("tager", i+" country before: "+model.getName()+channels);
                }

                countriesChannel.add(new CountriesChannelModel(channelsModel.getCountry(), channelsList));
                // countriesChannel.add(map);

                for (CountriesChannelModel model : countriesChannel){
                    String channels = "null";
                    for (ChannelsModel channelsModekl: model.getChannelsList()) {
                        channels = channelsModekl.getName()+"----"+channelsModekl.getStreamingLinks();
                    }
                    Log.d("tager", i+" country data after: "+model.getName()+channels+"\n\n");

                }
            }
            Stash.put("newList", newList);

//            Log.d("tager", "countries data: "+countriesChannel);
            //getSorting();
            binding.recycler.setLayoutManager(new LinearLayoutManager(context));
            adapter = new CountriesWiseAdapter(context, countriesChannel, clicklistners );
            binding.recycler.setAdapter(adapter);
        } catch (Exception e){
            Log.d("tager", "ERROR at 147 CommonFrag: "+e.getMessage());
            e.printStackTrace();
        }

        Log.d("tager", "onCreateView: Ended");
        return view;
    }

    private void getSorting() {
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL(Constants.countries);
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                Log.d("TAG", "compress: ERROR: " + e.toString());
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            Log.d("TAG", "data: " + htmlData);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    try {
                        JSONObject json = new JSONObject(htmlData);
                        JSONArray jsonArray = json.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            for (int j=0; j< countriesChannel.size(); j++) {
                                if (countriesChannel.get(j).getName().equals(obj.getString("name"))) {
                                    countriesChannel.get(j).setId(obj.getInt("id"));
                                }
                            }
                            // Stash.put(Constants.channelsTab, list);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Collections.sort(countriesChannel, Comparator.comparing(CountriesChannelModel::getId));
                        }
                        //Collections.reverse(list);
                        binding.recycler.setLayoutManager(new LinearLayoutManager(context));
                        adapter = new CountriesWiseAdapter(context, countriesChannel, clicklistners );
                        binding.recycler.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }

    Clicklistners clicklistners = new Clicklistners() {
        @Override
        public void click(ChannelsModel model) {
//            Toast.makeText(context, model.getName()+"\n\n"+model.getStreamingLinks().get(0).toString(), Toast.LENGTH_SHORT).show();
//            Log.d("tager", "PassedChannelName: "+model.getName());
//            Log.d("tager", "PassedChannelLink: "+model.getStreamingLinks().get(0).getStream_link());

            if (model.getStreamingLinks().size() > 1) {
                LinkDialog ld = new LinkDialog(context, model);
                ld.show();
            } else {
                VideoPlayerDialog vd = new VideoPlayerDialog(context, model.getStreamingLinks().get(0), model);
                vd.showStream();
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
                adapter = new CountriesWiseAdapter(context, countriesChannel, clicklistners );
                binding.recycler.setAdapter(adapter);
            } else {
                for (int i = 0; i < favrtList.size(); i++) {
                    if (favrtList.get(i).get_id().equals(model.get_id())) {
                        Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
                        favrtList.remove(i);
                    }
                }
                Stash.put(Constants.favrtList, favrtList);
                adapter.notifyDataSetChanged();
                adapter = new CountriesWiseAdapter(context, countriesChannel, clicklistners );
                binding.recycler.setAdapter(adapter);
            }
        }
    };
}