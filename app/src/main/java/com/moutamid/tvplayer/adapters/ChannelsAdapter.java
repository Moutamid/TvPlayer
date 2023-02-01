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
    ArrayList<ChannelsModel> favrtList;
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
        ChannelsModel model = list.get(holder.getAbsoluteAdapterPosition());
//        Log.d("tager", "ModelChannelLink: "+model.getStreamingLinks().get(0).getStream_link());

        Glide.with(context).load(model.getImageUrl()).into(holder.image);
        holder.name.setText(model.getName());

       // Toast.makeText(context, "Adapter Position : " + holder.getAdapterPosition() + "\n\nAdapter Name : " + model.getName() + "\n\n" + "Adapter STream : " + model.getStreamingLinks().get(0).getStream_link() + "\n\n", Toast.LENGTH_SHORT).show();

        favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
        if (favrtList == null){
            favrtList = new ArrayList<>();
        }

        for (ChannelsModel fvrtModel : favrtList){
            if (fvrtModel.get_id().equals(model.get_id())){
                holder.favrt.setImageResource(R.drawable.ic_favorite);
                holder.isfvrt = true;
            }
        }

        holder.favrt.setOnClickListener(v -> {
           // clicklistners.favrouite(model, holder.isfvrt);
            ArrayList<ChannelsModel> favrtList = Stash.getArrayList(Constants.favrtList, ChannelsModel.class);
            if (!holder.isfvrt) {
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                favrtList.add(model);
                Stash.put(Constants.favrtList, favrtList);
                holder.favrt.setImageResource(R.drawable.ic_favorite);
                holder.isfvrt = true;
                //notifyDataSetChanged();
            } else {
                for (int i = 0; i < favrtList.size(); i++) {
                    if (favrtList.get(i).get_id().equals(model.get_id())) {
                        Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
                        favrtList.remove(i);
                    }
                }
                holder.favrt.setImageResource(R.drawable.ic_favorite_border);
                holder.isfvrt = false;
                Stash.put(Constants.favrtList, favrtList);
                //notifyDataSetChanged();
            }
//            holder.isfvrt = clicklistners.favrt(holder.isfvrt, holder.favrt);
//            clicklistners.favrtModel(model, holder.isfvrt);
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
                String msg = holder.msg;
                new AlertDialog.Builder(context)
                        .setMessage(msg)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            if (!holder.isfvrt) {
                                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                                favrtList.add(model);
                                holder.favrt.setImageResource(R.drawable.ic_favorite);
                                holder.isfvrt = true;
                                holder.msg = "Do you want to remove it favourite?";
                                Stash.put(Constants.favrtList, favrtList);
                                //notifyDataSetChanged();
                            } else {
                                for (int i = 0; i < favrtList.size(); i++) {
                                    if (favrtList.get(i).get_id().equals(model.get_id())) {
                                        Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
                                        favrtList.remove(i);
                                    }
                                }
                                holder.favrt.setImageResource(R.drawable.ic_favorite_border);
                                holder.isfvrt = false;
                                holder.msg = "Do you want to favourite it?";
                                Stash.put(Constants.favrtList, favrtList);
                                //notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
                return true;
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
        boolean isfvrt;
        String msg;

        public ChannelVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            favrt = itemView.findViewById(R.id.favrt);
            name = itemView.findViewById(R.id.name);
            isfvrt = false;
            msg = "Do you want to favourite it?";
        }
    }
}
