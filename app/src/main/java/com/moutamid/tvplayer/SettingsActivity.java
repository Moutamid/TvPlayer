package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.moutamid.tvplayer.databinding.ActivityAdjustTabsBinding;
import com.moutamid.tvplayer.databinding.ActivitySettingsBinding;
import com.moutamid.tvplayer.models.StreamLinksModel;
import com.moutamid.tvplayer.models.TabsModel;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    ArrayList<TabsModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        boolean lc = Stash.getBoolean("lockState", false);
        String player = Stash.getString("buttonTXT", "Always Ask");

        binding.playername.setText(player);


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

        binding.hideCategory.setOnClickListener(v -> {
            hideCategory();
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

        binding.adjust.setOnClickListener(v -> {
            startActivity(new Intent(this, AdjustTabsActivity.class));
        });

        binding.videoPlayer.setOnClickListener(v -> player());

        binding.password.setOnClickListener(v -> {
            Dialog();
        });
    }

    private void hideCategory() {
        final Dialog hide = new Dialog(this);
        hide.requestWindowFeature(Window.FEATURE_NO_TITLE);
        hide.setContentView(R.layout.hide_category_layout);

        Button cancel = hide.findViewById(R.id.cancel);
        Button ok = hide.findViewById(R.id.ok);

        cancel.setOnClickListener(v -> {
            hide.dismiss();
        });

        ok.setOnClickListener(v -> {
            hide.dismiss();
        });

        list = Stash.getArrayList("tabs", TabsModel.class);

        LinearLayout linearLayout = hide.findViewById(R.id.layout);

        // Create Checkbox Dynamically
        for (TabsModel s : list) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText("\t\t" + s.getTitle().toUpperCase(Locale.ROOT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                checkBox.setButtonTintList(getResources().getColorStateList(R.color.orange));
            }
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                String msg = "You have " + (isChecked ? "checked " : "unchecked ") + s.getTitle() + "  " + buttonView.toString();
                Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
            });

            // Add Checkbox to LinearLayout
            if (linearLayout != null) {
                linearLayout.addView(checkBox);
            }
        }

        hide.show();
        hide.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hide.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        hide.getWindow().setGravity(Gravity.CENTER);

    }

    private void player() {
        final Dialog players = new Dialog(this);
        players.requestWindowFeature(Window.FEATURE_NO_TITLE);
        players.setContentView(R.layout.players);

        RadioGroup radioGroup = players.findViewById(R.id.radiogroup);

        int id = Stash.getInt("buttonID", R.id.alwaysAsk);

        radioGroup.check(id);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                Stash.put("buttonID", radioButtonID);
                View radioButton = radioGroup.findViewById(radioButtonID);
                int idx = radioGroup.indexOfChild(radioButton);
                MaterialRadioButton r = (MaterialRadioButton) radioGroup.getChildAt(idx);
                String selectedText = r.getText().toString();
                selectedText = selectedText.replace("\t\t", "");
                Stash.put("buttonIDX", idx);
                Stash.put("buttonIDDD", idx);
                Stash.put("buttonTXT", selectedText);
                binding.playername.setText(selectedText);
                players.dismiss();
            }
        });



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