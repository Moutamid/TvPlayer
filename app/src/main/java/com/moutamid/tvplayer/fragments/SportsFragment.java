package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.VolleySingleton;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.databinding.FragmentSportsBinding;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class SportsFragment extends Fragment {
    FragmentSportsBinding binding;
    private RequestQueue requestQueue;
    Context context;
    ArrayList<ChannelsModel> channelsList;
    ArrayList<StreamLinksModel> streamLinks;
    ChannelsAdapter adapter;
    int MY_SOCKET_TIMEOUT_MS = 10000;

    public SportsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSportsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        channelsList = new ArrayList<>();
        streamLinks = new ArrayList<>();

        binding.recycler.setLayoutManager(new GridLayoutManager(context, 3));
        binding.recycler.setHasFixedSize(false);

        requestQueue = VolleySingleton.getmInstance(context).getRequestQueue();

        fetchData();

        return view;
    }

    private void fetchData() {
        String url = Constants.channel;

        /*try {
            URL url = new URL(Constants.channel);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Toast.makeText(context, urlConnection.getResponseMessage(), Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            Toast.makeText(context, "ee : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }*/


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject data = response.getJSONObject("data");

                        JSONArray sports = data.getJSONArray("sports");

                        for (int i =0; i<sports.length(); i++) {
                            Toast.makeText(context, "FOR", Toast.LENGTH_LONG).show();
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

                            for (int j =0; j < streamingLinks.length(); j++) {
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "eee : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    adapter = new ChannelsAdapter(context, channelsList);
                    binding.recycler.setAdapter(adapter);

                },
                error -> {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
    }
}