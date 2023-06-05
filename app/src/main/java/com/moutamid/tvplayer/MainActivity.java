package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
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
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.InterstitialPlacement;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.LevelPlayBannerListener;
import com.ironsource.mediationsdk.sdk.LevelPlayRewardedVideoListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.moutamid.tvplayer.databinding.ActivityMainBinding;
import com.moutamid.tvplayer.dialog.PasswordDialog;
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
        IronSource.init(this, "18494ecc5", IronSource.AD_UNIT.BANNER);
        IronSource.init(this, "18494ecc5", IronSource.AD_UNIT.REWARDED_VIDEO);

        IronSource.setMetaData("Vungle_coppa", "true");

        IronSource.setMetaData("InMobi_AgeRestricted", "true");

        Log.d("IronSource", "Ads Initialized");

        IronSource.setMetaData("UnityAds_coppa","true");
        IronSource.shouldTrackNetworkState(this, true);

        boolean available = IronSource.isRewardedVideoAvailable();
      //  showRewardVideo();

        IronSource.setAdaptersDebug(true);
        showInterstial();
       // showBanner();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        // IntegrationHelper.validateIntegration(this);

        if (s.isEmpty()) {
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

    private void showBanner() {
        IronSourceBannerLayout banner = IronSource.createBanner(this, ISBannerSize.BANNER);
        banner.setLevelPlayBannerListener(new LevelPlayBannerListener() {
            // Invoked each time a banner was loaded. Either on refresh, or manual load.
            //  AdInfo parameter includes information about the loaded ad
            @Override
            public void onAdLoaded(AdInfo adInfo) {
                IronSource.loadBanner(banner);
            }
            // Invoked when the banner loading process has failed.
            //  This callback will be sent both for manual load and refreshed banner failures.
            @Override
            public void onAdLoadFailed(IronSourceError error) {}
            // Invoked when end user clicks on the banner ad
            @Override
            public void onAdClicked(AdInfo adInfo) {}
            // Notifies the presentation of a full screen content following user click
            @Override
            public void onAdScreenPresented(AdInfo adInfo) {}
            // Notifies the presented screen has been dismissed
            @Override
            public void onAdScreenDismissed(AdInfo adInfo) {}
            //Invoked when the user left the app
            @Override
            public void onAdLeftApplication(AdInfo adInfo) {}

        });


    }

    private void showInterstial() {
        IronSource.setInterstitialListener(new InterstitialListener() {
            /**
             * Invoked when Interstitial Ad is ready to be shown after load function was called.
             */
            @Override
            public void onInterstitialAdReady() {
                Log.d("IronSource", "Ads is Ready to show");
                IronSource.isInterstitialPlacementCapped("Home_Screen");
                IronSource.showInterstitial("Home_Screen");
            }

            /**
             * invoked when there is no Interstitial Ad available after calling load function.
             */
            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {
                Log.d("IronSource", "Error : " + error.getErrorMessage());
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
    }

    private void showRewardVideo() {
        IronSource.setRewardedVideoListener(new RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdOpened() {
                IronSource.isRewardedVideoPlacementCapped("Rewarded");
                IronSource.showRewardedVideo("Rewarded");
            }

            @Override
            public void onRewardedVideoAdClosed() {

            }

            @Override
            public void onRewardedVideoAvailabilityChanged(boolean b) {
                if (b) {
                    IronSource.isRewardedVideoPlacementCapped("Rewarded");
                    IronSource.showRewardedVideo("Rewarded");
                }
            }

            @Override
            public void onRewardedVideoAdStarted() {

            }

            @Override
            public void onRewardedVideoAdEnded() {

            }

            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {

            }

            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {

            }

            @Override
            public void onRewardedVideoAdClicked(Placement placement) {

            }
        });

        IronSource.loadRewardedVideo();
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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("device_id", android_id);
                    return params;
                }

            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

            if (isLock) {
                PasswordDialog passwordDialog = new PasswordDialog(MainActivity.this);
                passwordDialog.show();
            } else {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }

            return true;
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
                startActivity(new Intent(MainActivity.this, EventsActivity.class));
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
                break;
            case R.id.nav_last_played:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LastPlayedFragment()).commit();
                break;
            case R.id.nav_settings:
                boolean isLock = Stash.getBoolean("lockState", false);
                if (isLock) {
                    PasswordDialog passwordDialog = new PasswordDialog(MainActivity.this);
                    passwordDialog.show();
                } else {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
                break;
            case R.id.nav_privacy:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://multistreamz.com/privacy-policy-2")));
                break;
            case R.id.nav_website:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://multistreamz.com/")));
                break;
            case R.id.nav_telegram:
                if (isPackageExisted("org.telegram.messenger")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+4D6mmaFEiBUxZDE8"));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Install Telegram first", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.nav_feedback:
                Intent in = new Intent(Intent.ACTION_SENDTO);
                in.setData(Uri.parse("mailto:info@multistreamz.com"));
                in.putExtra(Intent.EXTRA_SUBJECT, "FEEDBACK");
                startActivity(Intent.createChooser(in, ""));
                break;
            case R.id.nav_share:
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareBody = "Hi, I am using this amazing Multi Streamz app. This app Stream all your favourite channels. To download this app click the below link \n" +
                        "https://multistreamz.com/wp-content/uploads/2023/03/MultiStreamz-release-1.apk";
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Multi Streamz");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share With"));
                break;

            default:
                break;
        }

        binding.drawLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isPackageExisted(String targetPackage) {
        PackageManager pm = getApplicationContext().getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int newOrientation = newConfig.orientation;

        if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            recreate();
        } else if (newOrientation == Configuration.ORIENTATION_PORTRAIT) {
            recreate();
        }
    }*/


}