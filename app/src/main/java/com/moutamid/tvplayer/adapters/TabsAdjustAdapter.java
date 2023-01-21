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

import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.models.TabsModel;

import java.util.ArrayList;

public class TabsAdjustAdapter extends RecyclerView.Adapter<TabsAdjustAdapter.TabsVH> {

    Context context;
    ArrayList<TabsModel> tabslist;

    public TabsAdjustAdapter(Context context, ArrayList<TabsModel> tabslist) {
        this.context = context;
        this.tabslist = tabslist;
    }

    @NonNull
    @Override
    public TabsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drag_tabs_layout, parent, false);
        return new TabsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabsVH holder, int position) {

        TabsModel tabsModel = tabslist.get(holder.getAdapterPosition());

        holder.text.setText(tabsModel.getName());

        holder.drag.setOnClickListener(v -> {
            Toast.makeText(context, "Long press and then swipe up or down to change position", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return tabslist.size();
    }

    public class TabsVH extends RecyclerView.ViewHolder {
        TextView text;
        ImageView drag;
        public TabsVH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tabs);
            drag = itemView.findViewById(R.id.drag);
        }
    }

}
