package com.moutamid.tvplayer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Clicklistners;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.dialog.LinkDialog;
import com.moutamid.tvplayer.dialog.VideoPlayerDialog;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.CountriesChannelModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

public class CountriesWiseAdapter extends RecyclerView.Adapter<CountriesWiseAdapter.CountriesVH> {
    Context context;
    ArrayList<ChannelsModel> list1 = new ArrayList<>();
    Clicklistners clicklistners;
    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
   ArrayList<CountriesChannelModel> list;
    //ArrayList<Map<String, ArrayList<ChannelsModel>>> list;


    public CountriesWiseAdapter(Context context, ArrayList<CountriesChannelModel> list, Clicklistners clicklistners) {
        this.context = context;
        this.list = list;
        this.clicklistners = clicklistners;
    }

    /*  public CountriesWiseAdapter(Context context, ArrayList<Map<String, ArrayList<ChannelsModel>>> list, Clicklistners clicklistners) {
            this.context = context;
            this.list = list;
            this.clicklistners = clicklistners;
        }
    */
    @NonNull
    @Override
    public CountriesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries_wise, parent, false);
        return new CountriesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesVH holder, int position) {
        CountriesChannelModel model = list.get(position);
//        Log.d("tager", "country data: "+model);
        ArrayList<String> newList = Stash.getArrayList("newList", String.class);

        // String ss = newList.get(position);

        holder.name.setText(model.getName());

//        Toast.makeText(context, "Adapter Position : " + holder.getAdapterPosition() + "\n\nAdapter Name : " + model.getName() + "\n\n" + "Adapter STream : " + model.getChannelsList().get(0).getStreamingLinks().get(0).getStream_link() + "\n\n", Toast.LENGTH_SHORT).show();


        GridLayoutManager layoutManager = new GridLayoutManager(holder.recyclerView.getContext(),3);
        layoutManager.setInitialPrefetchItemCount(model.getChannelsList().size());
        holder.recyclerView.setLayoutManager(layoutManager);
        list1.clear();
        for (ChannelsModel s : model.getChannelsList()) {
            if (s.getCountry().equals(model.getName())){
                list1.add(s);
//                Log.d("tager", "items: "+s);
            }
        }

        ChannelsAdapter channelsAdapter = new ChannelsAdapter(context, list1, clicklistners);
        holder.recyclerView.setAdapter(channelsAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountriesVH extends RecyclerView.ViewHolder{
        TextView name;
        RecyclerView recyclerView;
        public CountriesVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            recyclerView = itemView.findViewById(R.id.recycler);
        }
    }

}
