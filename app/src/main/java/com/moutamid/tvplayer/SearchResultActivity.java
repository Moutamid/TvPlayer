package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.android.material.card.MaterialCardView;
import com.moutamid.tvplayer.adapters.StreamLinksAdapter;
import com.moutamid.tvplayer.databinding.ActivitySearchResultBinding;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchResultActivity extends AppCompatActivity {
    ActivitySearchResultBinding binding;
    ChannelsModel searchedChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        searchedChannel = (ChannelsModel) Stash.getObject("searchedModel", ChannelsModel.class);

        Glide.with(this).load(searchedChannel.getImageUrl()).into(binding.image);
        binding.name.setText(searchedChannel.getName());

        binding.channel.setOnClickListener(v -> {
            if (searchedChannel.getStreamingLinks().size()>1){
                linkDialog();
            } else {
                videoPlayerDialog();
            }
        });

    }

    public void linkDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.stream_links);

        ArrayList<StreamLinksModel> list = new ArrayList<>();

        TextView title = dialog.findViewById(R.id.title);
        RecyclerView rc = dialog.findViewById(R.id.links);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setHasFixedSize(false);
        String s = "We have got multiple links for " + searchedChannel.getName() + ". Please Select one";
        title.setText(s);

        for (StreamLinksModel streamLinksModel : searchedChannel.getStreamingLinks()){
            list.add(streamLinksModel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(list, Comparator.comparing(StreamLinksModel::getPriority));
        }

        StreamLinksAdapter adapter = new StreamLinksAdapter(this, list, dialog);
        rc.setAdapter(adapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);

    }

    private void videoPlayerDialog() {
        VideoPlayerDialog vd = new VideoPlayerDialog(SearchResultActivity.this, searchedChannel.getStreamingLinks().get(0));
        vd.showStream();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}