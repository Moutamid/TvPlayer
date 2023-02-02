package com.moutamid.tvplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        builder = new AlertDialog.Builder(this);

        builder.setMessage("Please make sure you have internet connection and try again");
        builder.setTitle("Disconnected");
        builder.setCancelable(false);

        builder.setNegativeButton("Close", (dialog, which) -> {
            finish();
            dialog.cancel();
        });

        new Handler().postDelayed(() -> {
            if (!Utils.isNetworkConnected(SplashScreenActivity.this)){
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);

    }
}