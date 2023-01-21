package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fxn.stash.Stash;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.InterstitialPlacement;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.moutamid.tvplayer.databinding.ActivityMainBinding;
import com.moutamid.tvplayer.fragments.AllChannelsFragment;
import com.moutamid.tvplayer.fragments.EventsFragment;
import com.moutamid.tvplayer.fragments.FavouritesFragment;
import com.moutamid.tvplayer.fragments.LastPlayedFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        String s = Stash.getString("android_id", "");

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        IronSource.init(this, "18494ecc5", IronSource.AD_UNIT.INTERSTITIAL);

        IronSource.setMetaData("Vungle_coppa","true");

        IronSource.setMetaData("InMobi_AgeRestricted","true");

//        IronSource.Agent.setMetaData("UnityAds_coppa","true");
//
//        IronSource.Agent.setAdaptersDebug(true);

        IronSource.setAdaptersDebug(true);
        IronSource.setInterstitialListener(new InterstitialListener() {
            /**
             * Invoked when Interstitial Ad is ready to be shown after load function was called.
             */
            @Override
            public void onInterstitialAdReady() {
                Toast.makeText(MainActivity.this, "Ready", Toast.LENGTH_SHORT).show();
                IronSource.isInterstitialPlacementCapped("Home_Screen");
                IronSource.showInterstitial("Home_Screen");
            }
            /**
             * invoked when there is no Interstitial Ad available after calling load function.
             */
            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {
            }
            /**
             * Invoked when the Interstitial Ad Unit is opened
             */
            @Override
            public void onInterstitialAdOpened() {
            }
            /*
             * Invoked when the ad is closed and the user is about to return to the application.
             */
            @Override
            public void onInterstitialAdClosed() {
            }
            /**
             * Invoked when Interstitial ad failed to show.
             * @param error - An object which represents the reason of showInterstitial failure.
             */
            @Override
            public void onInterstitialAdShowFailed(IronSourceError error) {
            }
            /*
             * Invoked when the end user clicked on the interstitial ad, for supported networks only.
             */
            @Override
            public void onInterstitialAdClicked() {
            }
            /** Invoked right before the Interstitial screen is about to open.
             *  NOTE - This event is available only for some of the networks.
             *  You should NOT treat this event as an interstitial impression, but rather use InterstitialAdOpenedEvent
             */
            @Override
            public void onInterstitialAdShowSucceeded() {
            }
        });

        IronSource.loadInterstitial();

        IntegrationHelper.validateIntegration(this);

        if (s.isEmpty()){
            registerDevice();
        }

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.navView.getCheckedItem().setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        }

    }

    private void registerDevice() {
        try {
            JSONObject device_id = new JSONObject();
            String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_id.put("device_id", android_id);
            final String requestBody = device_id.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.post,
                    (Response.Listener<String>) response -> {
                Log.i("VOLLEY", response);
                Stash.put("android_id", android_id);
                // Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            }, (Response.ErrorListener) error -> Log.e("VOLLEY", error.toString())) {

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("device_id", android_id);
                    return params;
                }

            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            } else {
                Toast.makeText(this, "Password is Wrong", Toast.LENGTH_SHORT).show();
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
                /*binding.navView.setCheckedItem(R.id.nav_channels);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    binding.navView.setItemIconTintList(null);
                    binding.navView.getCheckedItem().setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                }*/
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllChannelsFragment()).commit();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.nav_favourites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouritesFragment()).commit();
                break;
            case R.id.nav_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
                break;
            case R.id.nav_last_played:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LastPlayedFragment()).commit();
                break;
            case R.id.nav_settings:
                boolean isLock = Stash.getBoolean("lockState", false);
                if (isLock){
                    Dialog();
                } else {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
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

    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }

}