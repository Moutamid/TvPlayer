package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.databinding.FragmentFavouritesBinding;
import com.moutamid.tvplayer.models.ChannelsModel;
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
import java.util.Stack;

public class FavouritesFragment extends Fragment {

    FragmentFavouritesBinding binding;
    Context context;
    ArrayList<ChannelsModel> channelsList;
    ArrayList<StreamLinksModel> streamLinks;
    ChannelsAdapter adapter;
    ArrayList<String> favrtList;

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
        if (favrtList == null){
            favrtList = new ArrayList<>();
        }
        if (!favrtList.isEmpty()){
            binding.recycler.setVisibility(View.VISIBLE);
            binding.text.setVisibility(View.GONE);
/*            fetchChannels();
            fetchEvents();*/
        } else {
            binding.recycler.setVisibility(View.GONE);
            binding.text.setVisibility(View.VISIBLE);
        }

        return view;
    }

/*    private void fetchEvents() {
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("http://95.217.210.178/api/v1/event/");
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

            Log.d("TAG", "compress: " + htmlData);

            requireActivity().runOnUiThread(() -> {
                try {
                    JSONObject jsonObject = new JSONObject(htmlData);
                    JSONObject data = jsonObject.getJSONObject("data");

                    JSONArray sports = data.getJSONArray("sports");
                    JSONArray news = data.getJSONArray("news");

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
                        for (String id : favrtList) {
                            if (channelsModel.get_id().equals(id)) {
                                channelsList.add(channelsModel);
                            }
                        }

                    }

                    for (int i = 0; i < news.length(); i++) {
                        JSONObject obj = news.getJSONObject(i);
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
                        for (String id : favrtList) {
                            if (channelsModel.get_id().equals(id)) {
                                channelsList.add(channelsModel);
                            }
                        }

                    }

                    if(channelsList.size() == 1){
                        binding.recycler.setLayoutManager(new GridLayoutManager(context, 1));
                    } else if(channelsList.size() == 2){
                        binding.recycler.setLayoutManager(new GridLayoutManager(context, 2));
                    } else {
                        binding.recycler.setLayoutManager(new GridLayoutManager(context, 3));
                    }

                    adapter = new ChannelsAdapter(context, channelsList);
                    binding.recycler.setAdapter(adapter);

                } catch (JSONException error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }

    private void fetchChannels() {
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("http://95.217.210.178/api/v1/channel/");
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

            Log.d("TAG", "compress: " + htmlData);

            requireActivity().runOnUiThread(() -> {
                try {
                    JSONObject jsonObject = new JSONObject(htmlData);
                    JSONObject data = jsonObject.getJSONObject("data");

                    JSONArray sports = data.getJSONArray("sports");
                    JSONArray news = data.getJSONArray("news");

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
                        for (String id : favrtList) {
                            if (channelsModel.get_id().equals(id)) {
                                channelsList.add(channelsModel);
                            }
                        }

                    }

                    for (int i = 0; i < news.length(); i++) {
                        JSONObject obj = news.getJSONObject(i);
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
                        for (String id : favrtList) {
                            if (channelsModel.get_id().equals(id)) {
                                channelsList.add(channelsModel);
                            }
                        }

                    }

                    if(channelsList.size() == 1){
                        binding.recycler.setLayoutManager(new GridLayoutManager(context, 1));
                    } else if(channelsList.size() == 2){
                        binding.recycler.setLayoutManager(new GridLayoutManager(context, 2));
                    } else {
                        binding.recycler.setLayoutManager(new GridLayoutManager(context, 3));
                    }

                    adapter = new ChannelsAdapter(context, channelsList);
                    binding.recycler.setAdapter(adapter);

                } catch (JSONException error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }*/
}