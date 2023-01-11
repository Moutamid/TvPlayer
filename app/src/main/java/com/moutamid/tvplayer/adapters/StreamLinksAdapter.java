package com.moutamid.tvplayer.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.android.material.card.MaterialCardView;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;

import java.util.ArrayList;

public class StreamLinksAdapter extends RecyclerView.Adapter<StreamLinksAdapter.LinkVH> {
    Context context;
    ArrayList<StreamLinksModel> list;
    Dialog dialog;

    public StreamLinksAdapter(Context context, ArrayList<StreamLinksModel> list, Dialog dialog) {
        this.context = context;
        this.list = list;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public LinkVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.links, parent, false);
        return new LinkVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkVH holder, int position) {
        StreamLinksModel model = list.get(holder.getAdapterPosition());
        holder.txt.setText(model.getName());

        holder.itemView.setOnClickListener(v -> {int idx = Stash.getInt("buttonIDX", 0);
            if (idx == 0) {
                videoPlayerDialog(list.get(holder.getAdapterPosition()));
            }
            dialog.dismiss();
        });
    }

    private void videoPlayerDialog(StreamLinksModel model) {
        VideoPlayerDialog vd = new VideoPlayerDialog(context, model);
        vd.showStream();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LinkVH extends RecyclerView.ViewHolder{
        Button txt;

        public LinkVH(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.linkText);
        }
    }

}
