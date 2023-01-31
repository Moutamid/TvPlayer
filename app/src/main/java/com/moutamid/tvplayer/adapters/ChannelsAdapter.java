package com.moutamid.tvplayer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.models.ChannelsModel;

import java.util.ArrayList;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChannelVH> {
    Context context;
    ArrayList<ChannelsModel> list;
    ArrayList<String> favrtList;
    Clicklistners clicklistners;

    public ChannelsAdapter(Context context, ArrayList<ChannelsModel> list, Clicklistners clicklistners) {
        this.context = context;
        this.list = list;
        this.clicklistners = clicklistners;
//        Log.d("tager", "List Items: "+list.toString());
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
//        Log.d("tager", "ModelChannelLink: "+model.getStreamingLinks().get(0).getStream_link());

        Glide.with(context).load(model.getImageUrl()).into(holder.image);
        holder.name.setText(model.getName());

       // Toast.makeText(context, "Adapter Position : " + holder.getAdapterPosition() + "\n\nAdapter Name : " + model.getName() + "\n\n" + "Adapter STream : " + model.getStreamingLinks().get(0).getStream_link() + "\n\n", Toast.LENGTH_SHORT).show();

        favrtList = Stash.getArrayList(Constants.favrtList, String.class);
        if (favrtList == null){
            favrtList = new ArrayList<>();
        }

        for (String id : favrtList){
            if (model.get_id().equals(id)){
                holder.favrt.setImageResource(R.drawable.ic_favorite);
                holder.isfvrt = true;
            }
        }

        holder.favrt.setOnClickListener(v -> {
            clicklistners.favrt(model, holder.isfvrt, holder.favrt);
        });

        holder.itemView.setOnClickListener(v -> {
            clicklistners.click(model);
           /* Log.d("tager", "ChannelName: "+list.get(holder.getAdapterPosition()).getName());
            Log.d("tager", "ChannelLink: "+list.get(holder.getAdapterPosition()).getStreamingLinks().get(0).getStream_link());
*/
//            Log.d("tager", "ModelChannelName: "+model.getName());
//            Log.d("tager", "ModelChannelLink: "+model.getStreamingLinks().get(0).getStream_link());


        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Do you want to favourite it?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            clicklistners.favrt(model, holder.isfvrt, holder.favrt);
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChannelVH extends RecyclerView.ViewHolder {
        ImageView image, favrt;
        TextView name;
        boolean isfvrt = false;

        public ChannelVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            favrt = itemView.findViewById(R.id.favrt);
            name = itemView.findViewById(R.id.name);
        }
    }
}
