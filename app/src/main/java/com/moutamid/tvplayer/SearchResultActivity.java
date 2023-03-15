package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.android.material.card.MaterialCardView;
import com.moutamid.tvplayer.adapters.StreamLinksAdapter;
import com.moutamid.tvplayer.databinding.ActivitySearchResultBinding;
import com.moutamid.tvplayer.dialog.LinkDialog;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchResultActivity extends AppCompatActivity {
    ActivitySearchResultBinding binding;
    ChannelsModel searchedChannel;
    boolean isfvrt = false;
    ArrayList<ChannelsModel> favrtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        favrtList = new ArrayList<>();

        favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);

        searchedChannel = (ChannelsModel) Stash.getObject("searchedModel", ChannelsModel.class);

        Glide.with(this).load(searchedChannel.getImageUrl()).into(binding.image);
        binding.name.setText(searchedChannel.getName());

        for (ChannelsModel channelsModel : favrtList){
            if (channelsModel.get_id().equals(searchedChannel.get_id())){
                binding.favrt.setImageResource(R.drawable.ic_favorite);
                isfvrt = true;
            }
        }

        binding.channel.setOnClickListener(v -> {
            if (searchedChannel.getStreamingLinks().size()>1){
                linkDialog();
            } else if (searchedChannel.getStreamingLinks().size() == 0) {
                Toast.makeText(SearchResultActivity.this, "No Streaming link found", Toast.LENGTH_SHORT).show();
            }  else {
                videoPlayerDialog();
            }
        });

        binding.favrt.setOnClickListener(v -> {
            favrtList.clear();
            favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
            if (!isfvrt) {
                favrtList.add(searchedChannel);
                Stash.put(Constants.favrtList, favrtList);
                isfvrt = true;
                binding.favrt.setImageResource(R.drawable.ic_favorite);
            } else {
                for (int i = 0; i < favrtList.size(); i++) {
                    if (favrtList.get(i).get_id().equals(searchedChannel.get_id())) {
                        favrtList.remove(i);
                        isfvrt = false;
                        binding.favrt.setImageResource(R.drawable.ic_favorite_border);
                    }
                }
                Stash.put(Constants.favrtList, favrtList);
            }
        });

        binding.channel.setOnLongClickListener(view -> {
            new AlertDialog.Builder(SearchResultActivity.this)
                    .setMessage("Do you want to favourite it?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        favrtList.clear();
                        favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
                        if (!isfvrt) {
                            favrtList.add(searchedChannel);
                            Stash.put(Constants.favrtList, favrtList);
                            isfvrt = true;
                        } else {
                            Toast.makeText(this, "Already Added", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
            return false;
        });

    }

    public void linkDialog(){
        LinkDialog ld = new LinkDialog(this, searchedChannel);
        ld.show();
    }

    private void videoPlayerDialog() {
        VideoPlayerDialog vd = new VideoPlayerDialog(SearchResultActivity.this, searchedChannel.getStreamingLinks().get(0), searchedChannel);
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