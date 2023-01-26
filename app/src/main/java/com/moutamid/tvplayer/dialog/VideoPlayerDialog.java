package com.moutamid.tvplayer.dialog;

import static com.android.volley.Request.Method.GET;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fxn.stash.Stash;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonParser;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.MetaRequest;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.VideoPlayerActivity;
import com.moutamid.tvplayer.VolleySingleton;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class VideoPlayerDialog {

    Context context;
    StreamLinksModel stream;
    ProgressDialog progressDialog;
    ArrayList<ChannelsModel> channelsModelArrayList;
    ChannelsModel channelsModel;

    private RequestQueue requestQueue;

    public VideoPlayerDialog(Context context, StreamLinksModel stream, ChannelsModel channelsModel) {
        this.context = context;
        this.stream = stream;
        this.channelsModel = channelsModel;

        requestQueue = VolleySingleton.getmInstance(context).getRequestQueue();

        channelsModelArrayList = new ArrayList<>();

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Creating Your Link");
        progressDialog.setMessage("Please Wait...");
    }

    public void showStream() {

        final Dialog videoPlayers = new Dialog(context);
        videoPlayers.requestWindowFeature(Window.FEATURE_NO_TITLE);
        videoPlayers.setContentView(R.layout.video_players);

        Stash.clear("buttonIDDD");

        int id = Stash.getInt("buttonID", R.id.alwaysAsk);
        String player = Stash.getString("buttonTXT", "Always Ask");

        Button justOnce = videoPlayers.findViewById(R.id.justOnce);
        Button always = videoPlayers.findViewById(R.id.always);

        MaterialCardView mxPlayer = videoPlayers.findViewById(R.id.mxPlayer);
        MaterialCardView xyzPlayer = videoPlayers.findViewById(R.id.xyzPlayer);
        MaterialCardView vlcPlayer = videoPlayers.findViewById(R.id.vlcPlayer);

        MaterialCardView localPlayer = videoPlayers.findViewById(R.id.localPlayer);
        MaterialCardView videoPlayer = videoPlayers.findViewById(R.id.videoPlayer);
        MaterialCardView wuffyPlayer = videoPlayers.findViewById(R.id.wuffyPlayer);

        MaterialCardView androidPlayer = videoPlayers.findViewById(R.id.androidPlayer);
        MaterialCardView webPlayer = videoPlayers.findViewById(R.id.webPlayer);
        MaterialCardView bubblePlayer = videoPlayers.findViewById(R.id.bubblePlayer);

        mxPlayer.setOnClickListener(v -> {
            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.mxPlayer);
            Stash.put("buttonTTT", "MX Player");

            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        xyzPlayer.setOnClickListener(v -> {
            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.xyzPlayer);
            Stash.put("buttonTTT", "XYZ Player");

            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        vlcPlayer.setOnClickListener(v -> {
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.vlcPlayer);
            Stash.put("buttonTTT", "VLC Player");

            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        localPlayer.setOnClickListener(v -> {
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.localPlayer);
            Stash.put("buttonTTT", "LocalCast Player");

            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        videoPlayer.setOnClickListener(v -> {
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.videoPlayer);
            Stash.put("buttonTTT", "Video Player");

            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        wuffyPlayer.setOnClickListener(v -> {
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.wuffyPlayer);
            Stash.put("buttonTTT", "Wuffy Player");

            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        androidPlayer.setOnClickListener(v -> {
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.androidPlayer);
            Stash.put("buttonTTT", "Android Player");

            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        webPlayer.setOnClickListener(v -> {
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.webPlayer);
            Stash.put("buttonTTT", "Web Video Cast Player");

            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        bubblePlayer.setOnClickListener(v -> {
            bubblePlayer.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            Stash.put("buttonIDDD", R.id.bubblePlayer);
            Stash.put("buttonTTT", "BubbleUpnP Player");

            mxPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            vlcPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            localPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            videoPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            wuffyPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            androidPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            webPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            xyzPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        });

        justOnce.setOnClickListener(v -> {
            int ids = Stash.getInt("buttonIDDD", R.id.alwaysAsk);
            if (ids == R.id.alwaysAsk) {
                Toast.makeText(context, "Please Select Any Player", Toast.LENGTH_SHORT).show();
            } else if (ids == R.id.androidPlayer) {
                if (checkIsInstall(ids)){
                    Stash.put("androidInternal", 1);
                    progressDialog.show();
                    createLink();
                    videoPlayers.dismiss();
                }
            } else {
                if (checkIsInstall(ids)) {
                    progressDialog.show();
                    Stash.put("androidInternal", 0);
                    createLink();
                    videoPlayers.dismiss();
                } else {
                    videoPlayers.dismiss();
                    showAlert();
                }
            }
        });

        always.setOnClickListener(v -> {
            int ids = Stash.getInt("buttonIDDD", R.id.alwaysAsk);
            String name = Stash.getString("buttonTTT", "Always Ask");
            if (ids == R.id.alwaysAsk) {
                Toast.makeText(context, "Please Select Any Player", Toast.LENGTH_SHORT).show();
            } else if (ids == R.id.androidPlayer) {
                if (checkIsInstall(ids)){
                    progressDialog.show();
                    Stash.put("androidInternal", 1);
                    Stash.put("buttonID", ids);
                    Stash.put("buttonTXT", name);
                    createLink();
                    videoPlayers.dismiss();
                }
            } else {
                if (checkIsInstall(ids)) {
                    progressDialog.show();
                    Stash.put("androidInternal", 0);
                    Stash.put("buttonID", ids);
                    Stash.put("buttonTXT", name);
                    createLink();
                    videoPlayers.dismiss();
                } else {
                    videoPlayers.dismiss();
                    showAlert();
                }
            }
        });


        if (id == R.id.alwaysAsk) {
            videoPlayers.show();
        } else if (id == R.id.androidPlayer) {
            if (checkIsInstall(id)){
                Stash.put("androidInternal", 1);
                progressDialog.show();
                createLink();
                videoPlayers.dismiss();
            }
        } else {
            if (checkIsInstall(id)) {
                progressDialog.show();
                Stash.put("androidInternal", 0);
                /*Stash.put("buttonID", id);
                Stash.put("buttonTXT", player);*/
                createLink();
                videoPlayers.dismiss();
            } else {
                videoPlayers.dismiss();
                showAlert();
            }
        }
        videoPlayers.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        videoPlayers.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        videoPlayers.getWindow().setGravity(Gravity.CENTER);
    }

    private void showAlert() {
        String name = Stash.getString("buttonTTT");
        new AlertDialog.Builder(context)
                .setTitle("Important")
                .setMessage(name + " is not installed. Click the button below to download it or select android player.")
                .setPositiveButton("Install", (dialog, which) -> {
                    final String appPackageName = Stash.getString("packageName"); // getPackageName() from Context or Activity object
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private boolean checkIsInstall(int player) {
        if (player == R.id.mxPlayer) {
            Stash.put("packageName", "com.mxtech.videoplayer.ad");
            return isPackageExisted("com.mxtech.videoplayer.ad");
        }
        if (player == R.id.xyzPlayer) {
            Stash.put("packageName", "com.raddixcore.xyzplayer");
            return isPackageExisted("com.raddixcore.xyzplayer");
        }
        if (player == R.id.vlcPlayer) {
            Stash.put("packageName", "org.videolan.vlc");
            return isPackageExisted("org.videolan.vlc");
        }

        if (player == R.id.androidPlayer) {
            Stash.put("packageName", "com.moutamid.android");
            return true;
        }
        if (player == R.id.videoPlayer) {
            Stash.put("packageName", "video.player.videoplayer");
            return isPackageExisted("video.player.videoplayer");
        }
        if (player == R.id.wuffyPlayer) {
            Stash.put("packageName", "co.wuffy.player");
            return isPackageExisted("co.wuffy.player");
        }

        if (player == R.id.webPlayer) {
            Stash.put("packageName", "com.instantbits.cast.webvideo");
            return isPackageExisted("com.instantbits.cast.webvideo");
        }
        if (player == R.id.bubblePlayer) {
            Stash.put("packageName", "com.bubblesoft.android.bubbleupnp");
            return isPackageExisted("com.bubblesoft.android.bubbleupnp");
        }
        if (player == R.id.localPlayer) {
            Stash.put("packageName", "de.stefanpledl.localcast");
            return isPackageExisted("de.stefanpledl.localcast");
        }
        return true;
    }

    private void createLink() {
        getToken();
    }

    private boolean isPackageExisted(String targetPackage){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    private void getToken() {
        Log.d("testing123", "okTesting");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(GET, Constants.token, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    Stash.put(obj.getString("id"), obj.getString("url"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            new GetLink().execute("");
        }, error -> {

        });

        requestQueue.add(jsonArrayRequest);

       // new GetLink().execute("");
    }


    private class GetLink extends AsyncTask<String, String, String> {
        String[] token = new String[5];
        @Override
        protected String doInBackground(String... strings) {

            String tokenNumber = stream.getToken();
            Log.d("testing123", tokenNumber);

            String url = Stash.getString(tokenNumber, "");
            Log.d("testing123", "url  " + url);

            try {
                Document doc = Jsoup.connect(url).get();
                Elements body = doc.getElementsByTag("body");
                token[0] = stream.getStream_link() + body.text();
                Log.d("testing123", "url  " + url);
                MetaRequest key = new MetaRequest(GET, url, null,
                        response -> {
                            //JSONObject headers = response.getJSONObject("headers");
                            String session = Stash.getString("SeassionHeader");
                            Log.d("testing123", "Session : " + session);
                            byte[] send = session.getBytes(StandardCharsets.UTF_8);
                            byte[] data = Base64.decode(send, Base64.DEFAULT);
                            String text = new String(data, StandardCharsets.UTF_8);
                            token[0] = token[0].replace("$", text);
                            Stash.put("videoURL", token[0]);
                            Log.d("testing123", "Session Decode : " + token[0]);

                        }, error -> {
                    Log.d("testing123", "error " + error.getMessage());
                });

                requestQueue.add(key);
                Log.d("testing123", body.text().toString());
                Log.d("testing123", token[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return token[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new Handler().postDelayed(() -> {
                progressDialog.dismiss();
                String url = Stash.getString("videoURL");
                //String url = s;
                String packageName = Stash.getString("packageName");
                int internal = Stash.getInt("androidInternal",  0);
                Log.d("testing123", "VideoURLPlayer  URL "+url);
                Log.d("testing123", "VideoURLPlayer token "+token[0]);
                Log.d("testing123", ""+internal);
                Log.d("testing123", ""+packageName);

                channelsModelArrayList = Stash.getArrayList("LastPlayed", ChannelsModel.class);
                if (channelsModel==null) { channelsModelArrayList = new ArrayList<>(); }
                channelsModelArrayList.add(channelsModel);
                Stash.put("LastPlayed", channelsModelArrayList);
                Log.d("testing123", "Size  "+channelsModelArrayList.size());

                if (internal == 1){
                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("name", channelsModel.getName());
                    intent.putExtra("url", url);
                    context.startActivity(intent);
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setPackage(packageName);
                    i.setDataAndType(Uri.parse(url), "video/");
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(i);
                }
            }, 2000);
        }
    }
}
