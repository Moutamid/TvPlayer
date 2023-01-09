package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.moutamid.tvplayer.databinding.ActivityMainBinding;
import com.moutamid.tvplayer.fragments.AllChannelsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                binding.drawLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.navView.setNavigationItemSelectedListener(this);
        binding.drawLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setItemIconTintList(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllChannelsFragment()).commit();
        binding.navView.setCheckedItem(R.id.nav_channels);

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
            String s = Stash.getString("password", "");
            if (pasw.getText().toString().equals(s)){
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                password.dismiss();
            }
        });

        password.show();
        password.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        password.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        password.getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_setting, menu);

        MenuItem menuSetting = menu.findItem(R.id.nav_settings_top);
        MenuItem menuSearch = menu.findItem(R.id.action_search);

        menuSearch.setOnMenuItemClickListener(item -> {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            return true;
        });

        menuSetting.setOnMenuItemClickListener(item -> {
            boolean isLock = Stash.getBoolean("lockState", false);

            if (isLock){
                Dialog();
            } else {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }

            return  true;
        });



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_channels:
                // binding.navView.setCheckedItem(R.id.nav_channels);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllChannelsFragment()).commit();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.nav_privacy:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")));
                break;

            default:
                break;
        }

        binding.drawLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}