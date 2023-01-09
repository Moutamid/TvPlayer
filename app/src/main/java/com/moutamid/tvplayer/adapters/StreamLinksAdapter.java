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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.tvplayer.R;
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

        holder.itemView.setOnClickListener(v -> {
            videoPlayerDialog(list.get(holder.getAdapterPosition()));
            dialog.dismiss();
        });
    }

    private void videoPlayerDialog(StreamLinksModel model) {
        final Dialog videoPlayers = new Dialog(context);
        videoPlayers.requestWindowFeature(Window.FEATURE_NO_TITLE);
        videoPlayers.setContentView(R.layout.video_players);



        videoPlayers.show();
        videoPlayers.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        videoPlayers.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        videoPlayers.getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LinkVH extends RecyclerView.ViewHolder{
        TextView txt;

        public LinkVH(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.linkText);
        }
    }

}
