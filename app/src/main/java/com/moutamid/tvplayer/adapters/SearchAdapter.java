package com.moutamid.tvplayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.SearchResultActivity;
import com.moutamid.tvplayer.models.ChannelsModel;

import java.util.ArrayList;
import java.util.Collection;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchVH> implements Filterable {
    Context context;
    ArrayList<ChannelsModel> list;
    ArrayList<ChannelsModel> listAll;

    public SearchAdapter(Context context, ArrayList<ChannelsModel> list) {
        this.context = context;
        this.list = list;
        this.listAll = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.links, parent, false);
        return new SearchVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVH holder, int position) {
        ChannelsModel model = list.get(holder.getAdapterPosition());
        holder.textView.setText(model.getName());

        holder.itemView.setOnClickListener(v -> {
            Stash.put("searchedModel", list.get(holder.getAdapterPosition()));
            context.startActivity(new Intent(context, SearchResultActivity.class));
        });

    }

    @Override
    public int getItemCount() {
        if (list.size() > 15){
            return 15;
        }
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ChannelsModel> filterList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filterList.addAll(listAll);
            } else {
                for (ChannelsModel listModel : listAll){
                    if (listModel.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filterList.add(listModel);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        //run on Ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends ChannelsModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class SearchVH extends RecyclerView.ViewHolder{
        Button textView;
        public SearchVH(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.linkText);
        }
    }


}
