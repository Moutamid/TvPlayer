package com.moutamid.tvplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moutamid.tvplayer.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.search.getEditText().requestFocus();

    }
}