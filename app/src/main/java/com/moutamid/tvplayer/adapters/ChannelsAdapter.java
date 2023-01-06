package com.moutamid.tvplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.models.ChannelsModel;

import java.util.ArrayList;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChannelVH> {
    Context context;
    ArrayList<ChannelsModel> list;

    public ChannelsAdapter(Context context, ArrayList<ChannelsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChannelVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chanel_card, parent, false);
        return new ChannelVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelVH holder, int position) {
        ChannelsModel model = list.get(holder.getAdapterPosition());

        Glide.with(context).load(model.getImage()).into(holder.image);
        holder.name.setText(model.getName());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, model.getName(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChannelVH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public ChannelVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }
    }
}
