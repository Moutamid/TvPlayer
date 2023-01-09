package com.moutamid.tvplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.moutamid.tvplayer.adapters.SearchAdapter;
import com.moutamid.tvplayer.databinding.ActivitySearchBinding;
import com.moutamid.tvplayer.models.ChannelsModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    SearchAdapter adapter;
    ArrayList<ChannelsModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.search.getEditText().requestFocus();

        list = new ArrayList<>();

        binding.suggestions.setLayoutManager(new LinearLayoutManager(this));
        binding.suggestions.setHasFixedSize(false);

    }
}