package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_setting, menu);

        MenuItem menuSetting = menu.findItem(R.id.nav_settings_top);

        menuSetting.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, "Yess", Toast.LENGTH_SHORT).show();
            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_channels:
                binding.navView.setCheckedItem(R.id.nav_channels);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllChannelsFragment()).commit();
                break;
            /*case R.id.nav_notification:
                binding.toolbar.setTitle("Notification!");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment()).commit();
                break;*/

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