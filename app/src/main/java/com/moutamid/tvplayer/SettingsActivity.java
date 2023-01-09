package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.moutamid.tvplayer.databinding.ActivitySettingsBinding;
import com.moutamid.tvplayer.models.StreamLinksModel;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        boolean lc = Stash.getBoolean("lockState", false);

        if (lc){
            binding.lockCheck.setChecked(true);
        } else {
            binding.lockCheck.setChecked(false);
        }

        binding.lockSetting.setOnClickListener(v->{
            if (binding.lockCheck.isChecked()){
                binding.lockCheck.setChecked(false);
                Stash.put("lockState", false);
            } else {
                binding.lockCheck.setChecked(true);
                Stash.put("lockState", true);
            }
        });

        binding.lockCheck.setOnClickListener(v -> {
            if (binding.lockCheck.isChecked()) {
                Stash.put("lockState", true);
                String s = Stash.getString("password", "");
                if (s.isEmpty()){
                    Stash.put("password", "1234");
                }
            } else {
                Stash.put("lockState", false);
            }
        });

        binding.videoPlayer.setOnClickListener(v -> player());

        binding.password.setOnClickListener(v -> {
            Dialog();
        });
    }

    private void player() {
        final Dialog players = new Dialog(this);
        players.requestWindowFeature(Window.FEATURE_NO_TITLE);
        players.setContentView(R.layout.players);

        Button cancel = players.findViewById(R.id.cancel);

        cancel.setOnClickListener(v -> players.dismiss());

        players.show();
        players.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        players.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        players.getWindow().setGravity(Gravity.CENTER);
    }

    private void Dialog() {
        final Dialog password = new Dialog(this);
        password.requestWindowFeature(Window.FEATURE_NO_TITLE);
        password.setContentView(R.layout.password_layout);

        EditText pasw = password.findViewById(R.id.et_password);
        Button ok = password.findViewById(R.id.ok);
        Button cancel = password.findViewById(R.id.cancel);

        cancel.setOnClickListener(v -> password.dismiss());

        ok.setOnClickListener(v -> {
            if (pasw.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add Password", Toast.LENGTH_SHORT).show();
            } else {
                Stash.put("password", pasw.getText().toString());
                Toast.makeText(this, "Password Updated", Toast.LENGTH_SHORT).show();
                password.dismiss();
            }
        });

        password.show();
        password.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        password.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        password.getWindow().setGravity(Gravity.CENTER);
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