package com.moutamid.tvplayer;

import android.app.Activity;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Constants {

    public static final String dummy = "https://raw.githubusercontent.com/Moutamid/CashAppReplica/master/dummy.txt";
    public static final String channel = "http://api.multistreamz.com/v1/channel/";
    public static final String event = "http://api.multistreamz.com/v1/event/";
    public static final String post = "http://api.multistreamz.com/v1/devices";
    public static final String eventsData = "eventsData";
    public static final String channelsData = "channelsData";
    public static final String channelsTab = "channelsTab";
    public static final String favrtList = "favrtList";
    public static final String localTab = "localTab";
    public static final String isAdjusted = "isAdjusted";
    public static final String eventsTab = "eventsTab";
    public static final String categories = "http://api.multistreamz.com/v1/channel/categories";
    public static final String countries = "http://api.multistreamz.com/v1/channel/countries";
    public static final String categoriesEvent = "http://api.multistreamz.com/v1/event/categories";
    public static final String countriesEvent = "http://api.multistreamz.com/v1/event/countries";
    public static final String token = "http://api.multistreamz.com/v1/token/";
    public static final String VIDEO_TITLE = "VIDEO_TITLE";
    public static void checkApp(Activity activity) {
        String appName = "tvapp";// ADD THIS NAME TO THE REPOSITORY IN BELOW LINK

        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("https://raw.githubusercontent.com/Moutamid/Moutamid/main/apps.txt");
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            try {
                JSONObject myAppObject = new JSONObject(htmlData).getJSONObject(appName);

                boolean value = myAppObject.getBoolean("value");
                String msg = myAppObject.getString("msg");

                if (value) {
                    activity.runOnUiThread(() -> {
                        new AlertDialog.Builder(activity)
                                .setMessage(msg)
                                .setCancelable(false)
                                .show();
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

}