package com.moutamid.tvplayer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.StreamLinksAdapter;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LinkDialog {

    Context context;
    ChannelsModel model;

    public LinkDialog(Context context, ChannelsModel model) {
        this.context = context;
        this.model = model;
    }

    public void show(){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.stream_links);

        ArrayList<StreamLinksModel> list = new ArrayList<>();

        TextView title = dialog.findViewById(R.id.title);
        RecyclerView rc = dialog.findViewById(R.id.links);
        rc.setLayoutManager(new LinearLayoutManager(context));
        rc.setHasFixedSize(false);
        String s = "We have got multiple links for " + model.getName() + ". Please Select one";
        title.setText(s);

        for (StreamLinksModel streamLinksModel : model.getStreamingLinks()){
            list.add(streamLinksModel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(list, Comparator.comparing(StreamLinksModel::getPriority));
        }

        StreamLinksAdapter adapter = new StreamLinksAdapter(context, list, dialog, model);
        rc.setAdapter(adapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

}
