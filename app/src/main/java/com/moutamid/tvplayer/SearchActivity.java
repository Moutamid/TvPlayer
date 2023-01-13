package com.moutamid.tvplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.adapters.SearchAdapter;
import com.moutamid.tvplayer.databinding.ActivitySearchBinding;
import com.moutamid.tvplayer.fragments.CommonFragment;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;
import com.moutamid.tvplayer.models.TabsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    SearchAdapter adapter;
    ArrayList<ChannelsModel> list;
    ArrayList<StreamLinksModel> streamLinks;
    JSONObject data;

    ArrayList<TabsModel> tabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.search.getEditText().requestFocus();

        list = new ArrayList<>();
        streamLinks = new ArrayList<>();

        data = (JSONObject) Stash.getObject("data", JSONObject.class);
        tabs = Stash.getArrayList("tabs", TabsModel.class);
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

                    list.add(channelsModel);
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        binding.suggestions.setLayoutManager(new LinearLayoutManager(this));
        binding.suggestions.setHasFixedSize(false);

        adapter = new SearchAdapter(this, list);
        binding.suggestions.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.search.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private <T> Iterable<T> iterate(final Iterator<T> i) {
        return () -> i;
    }
}