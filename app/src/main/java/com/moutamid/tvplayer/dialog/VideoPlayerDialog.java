package com.moutamid.tvplayer.dialog;

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
import java.nio.charset.StandardCharsets;

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
            } else {
                if (checkIsInstall(ids)) {
                    progressDialog.show();
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
            } else {
                if (checkIsInstall(ids)) {
                    progressDialog.show();
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
        } else {
            if (checkIsInstall(id)) {
                progressDialog.show();
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
                .setMessage(name + " is not installed. Click the button below to download it or selected any other player.")
                .setPositiveButton("Install", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
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
            Stash.put("packageName", "com.zgz.supervideo");
            return isPackageExisted("com.zgz.supervideo");
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
        new GetLink().execute("");
    }

    public boolean isPackageExisted(String targetPackage){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }


    public class GetLink extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String url = stream.getToken();
            try {
                Document doc = Jsoup.connect(url).get();
                Elements body = doc.getElementsByTag("body");
                final String[] token = {stream.getStream_link() + body.text()};
                Log.d("htmlTAG", "url  " + url);
                MetaRequest key = new MetaRequest(Request.Method.GET, url, null,
                        response -> {
                            //JSONObject headers = response.getJSONObject("headers");
                            String session = Stash.getString("SeassionHeader");
                            Log.d("htmlTAG", "Session : " + session);
// Sending side
                            byte[] send = session.getBytes(StandardCharsets.UTF_8);
                           // String base64 = Base64.encodeToString(send, Base64.DEFAULT);

// Receiving side
                            byte[] data = Base64.decode(send, Base64.DEFAULT);
                            String text = new String(data, StandardCharsets.UTF_8);
                            token[0] = token[0].replace("$", text);
                            Stash.put("videoURL", token[0]);
                            Log.d("htmlTAG", "Session Decode : " + token[0]);

                        }, error -> {
                    Log.d("htmlTAG", "error " + error.getMessage());
                });

                requestQueue.add(key);
                Log.d("htmlTAG", body.text().toString());
                Log.d("htmlTAG", token[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            String url = Stash.getString("videoURL");
            String packageName = Stash.getString("packageName");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setPackage(packageName);
            i.setDataAndType(Uri.parse(url),"video/*");
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(i);

        }
    }
}
