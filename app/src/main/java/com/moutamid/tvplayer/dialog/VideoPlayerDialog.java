package com.moutamid.tvplayer.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fxn.stash.Stash;
import com.google.android.material.card.MaterialCardView;
import com.moutamid.tvplayer.MetaRequest;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.VolleySingleton;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class VideoPlayerDialog {

    Context context;
    StreamLinksModel stream;
    ProgressDialog progressDialog;

    private RequestQueue requestQueue;

    public VideoPlayerDialog(Context context, StreamLinksModel stream) {
        this.context = context;
        this.stream = stream;

        requestQueue = VolleySingleton.getmInstance(context).getRequestQueue();

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Creating Your Link");
        progressDialog.setMessage("Please Wait...");
    }

    public void showStream() {

        final Dialog videoPlayers = new Dialog(context);
        videoPlayers.requestWindowFeature(Window.FEATURE_NO_TITLE);
        videoPlayers.setContentView(R.layout.video_players);

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
            Stash.put("buttonID", R.id.mxPlayer);
            Stash.put("buttonTXT", "MX Player");

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
            Stash.put("buttonID", R.id.xyzPlayer);
            Stash.put("buttonTXT", "XYZ Player");

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
            Stash.put("buttonID", R.id.vlcPlayer);
            Stash.put("buttonTXT", "VLC Player");

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
            Stash.put("buttonID", R.id.localPlayer);
            Stash.put("buttonTXT", "LocalCast Player");

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
            Stash.put("buttonID", R.id.videoPlayer);
            Stash.put("buttonTXT", "Video Player");

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
            Stash.put("buttonID", R.id.wuffyPlayer);
            Stash.put("buttonTXT", "Wuffy Player");

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
            Stash.put("buttonID", R.id.androidPlayer);
            Stash.put("buttonTXT", "Android Player");

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
            Stash.put("buttonID", R.id.webPlayer);
            Stash.put("buttonTXT", "Web Video Cast Player");

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
            Stash.put("buttonID", R.id.bubblePlayer);
            Stash.put("buttonTXT", "BubbleUpnP Player");

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
            int ids = Stash.getInt("buttonID", R.id.alwaysAsk);
            if (ids == R.id.alwaysAsk) {
                Toast.makeText(context, "Please Select Any Player", Toast.LENGTH_SHORT).show();
            } else {
                if (checkIsInstall(ids)) {
                    progressDialog.show();
                    createLink();
                    videoPlayers.dismiss();
                } else {

                }
            }
        });

        always.setOnClickListener(v -> {
            int ids = Stash.getInt("buttonID", R.id.alwaysAsk);
            if (ids == R.id.alwaysAsk) {
                Toast.makeText(context, "Please Select Any Player", Toast.LENGTH_SHORT).show();
            } else {
                if (checkIsInstall(ids)) {
                    progressDialog.show();
                    Stash.put("buttonID", ids);
                    createLink();
                    videoPlayers.dismiss();
                } else {

                }
            }
        });


        if (id == R.id.alwaysAsk) {
            videoPlayers.show();
        } else {
            if (checkIsInstall(id)) {
                progressDialog.show();
                createLink();
            } else {

            }
        }
        videoPlayers.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        videoPlayers.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        videoPlayers.getWindow().setGravity(Gravity.CENTER);
    }

    private boolean checkIsInstall(int player) {
        if (player == R.id.mxPlayer) {

        }
        if (player == R.id.xyzPlayer) {

        }
        if (player == R.id.vlcPlayer) {

        }

        if (player == R.id.androidPlayer) {

        }
        if (player == R.id.videoPlayer) {

        }
        if (player == R.id.wuffyPlayer) {

        }

        if (player == R.id.webPlayer) {

        }
        if (player == R.id.bubblePlayer) {

        }
        if (player == R.id.localPlayer) {

        }
        return true;
    }

    private void createLink() {
        new GetLink().execute("");
    }

    public class GetLink extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String url = stream.getToken();
            try {
                Document doc = Jsoup.connect(url).get();
                Elements body = doc.getElementsByTag("body");
                String token = stream.getStream_link() + body.text();
                Log.d("htmlTAG", "url  " + url);
                MetaRequest key = new MetaRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                JSONObject headers = response.getJSONObject("headers");
                                String session = headers.getString("Session");
                                Log.d("htmlTAG", "Session : " + session);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("htmlTAG", "e " + e.getMessage());
                            }

                        }, error -> {
                    Log.d("htmlTAG", "error " + error.getMessage());
                });

                requestQueue.add(key);
                Log.d("htmlTAG", body.text().toString());
                Log.d("htmlTAG", token);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }
}
