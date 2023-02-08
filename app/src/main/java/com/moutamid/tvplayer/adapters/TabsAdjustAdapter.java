package com.moutamid.tvplayer.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.models.TabLocal;
import com.moutamid.tvplayer.models.TabsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

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

        TabsModel tabsModel = tabslist.get(holder.getAbsoluteAdapterPosition());

        holder.text.setText(tabsModel.getName().toUpperCase(Locale.ROOT));
        holder.count.setText(""+(holder.getAbsoluteAdapterPosition()+1));


        holder.itemView.setOnLongClickListener(view -> {
            ArrayList<TabLocal> tabLocals = new ArrayList<>();
            TabsModel model = tabslist.get(holder.getAbsoluteAdapterPosition());
            tabslist.remove(model);
            tabslist.add(0, model);
            notifyDataSetChanged();
            for (int i=0; i<tabslist.size(); i++){
                tabLocals.add(new TabLocal(i, tabslist.get(i).getName()));
                Stash.put(Constants.localTab, tabLocals);
            }
            Stash.put(Constants.channelsTab, tabslist);
            Stash.put(Constants.isAdjusted, true);

            Log.d("PositionTabs", tabslist.toString());
            Log.d("PositionTabs", tabLocals.toString());
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Long press the item to move to the top position", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return tabslist.size();
    }

    public class TabsVH extends RecyclerView.ViewHolder {
        TextView text;
        TextView count;
        ImageView drag;
        public TabsVH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tabs);
            count = itemView.findViewById(R.id.count);
            drag = itemView.findViewById(R.id.drag);
        }
    }

}
