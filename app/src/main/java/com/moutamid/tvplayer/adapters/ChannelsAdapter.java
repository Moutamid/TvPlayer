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
import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Clicklistners;
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
        favrtList = Stash.getArrayList("favrtList", String.class);
        if (favrtList == null){
            favrtList = new ArrayList<>();
        }
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

        Glide.with(context).load(model.getImageUrl()).into(holder.image);
        holder.name.setText(model.getName());

       // Toast.makeText(context, "Adapter Position : " + holder.getAdapterPosition() + "\n\nAdapter Name : " + model.getName() + "\n\n" + "Adapter STream : " + model.getStreamingLinks().get(0).getStream_link() + "\n\n", Toast.LENGTH_SHORT).show();

        for (String id : favrtList){
            if (model.get_id().equals(id)){
                holder.favrt.setImageResource(R.drawable.ic_favorite);
                holder.isfvrt = true;
            }
        }

        holder.favrt.setOnClickListener(v -> {
            /*if (!holder.isfvrt) {
                holder.favrt.setImageResource(R.drawable.ic_favorite);
                holder.isfvrt = true;
                favrtList.add(model.get_id());
                Stash.put("favrtList", favrtList);
                // adapter.notifyDataSetChanged();
            } else {
                favrtList.remove(favrtList.indexOf(model.get_id()));
                Stash.put("favrtList", favrtList);
                holder.favrt.setImageResource(R.drawable.ic_favorite_border);
                holder.isfvrt = false;
                // adapter.notifyDataSetChanged();
            }*/

            clicklistners.favrt(list.get(holder.getAdapterPosition()), holder.isfvrt, holder.favrt);
        });

        holder.itemView.setOnClickListener(v -> {
            clicklistners.click(list.get(holder.getAdapterPosition()));
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
