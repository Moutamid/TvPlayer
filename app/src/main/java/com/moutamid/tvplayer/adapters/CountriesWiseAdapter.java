package com.moutamid.tvplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.models.CountriesChannelModel;

import java.util.ArrayList;

public class CountriesWiseAdapter extends RecyclerView.Adapter<CountriesWiseAdapter.CountriesVH> {
    Context context;
    ArrayList<CountriesChannelModel> list;

    public CountriesWiseAdapter(Context context, ArrayList<CountriesChannelModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CountriesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries_wise, parent, false);
        return new CountriesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountriesVH extends RecyclerView.ViewHolder{

        public CountriesVH(@NonNull View itemView) {
            super(itemView);
        }
    }

}
