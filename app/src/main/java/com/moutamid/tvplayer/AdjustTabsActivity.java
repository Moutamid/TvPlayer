package com.moutamid.tvplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.adapters.TabsAdjustAdapter;
import com.moutamid.tvplayer.databinding.ActivityAdjustTabsBinding;
import com.moutamid.tvplayer.models.TabLocal;
import com.moutamid.tvplayer.models.TabsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdjustTabsActivity extends AppCompatActivity {
    ActivityAdjustTabsBinding binding;
    TabsAdjustAdapter adapter;
    ArrayList<TabsModel> list = new ArrayList<>();
    ArrayList<TabLocal> tabLocals;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdjustTabsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding.tabsRC.setLayoutManager(new LinearLayoutManager(this));
        binding.tabsRC.setHasFixedSize(true);
        binding.tabsRC.setNestedScrollingEnabled(false);

        tabLocals = new ArrayList<>();

        list = Stash.getArrayList(Constants.channelsTab, TabsModel.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(list, Comparator.comparing(TabsModel::getId));
        }

        for (int i=0; i<list.size(); i++){
            tabLocals.add(new TabLocal(list.get(i).getId(), list.get(i).getName()));
        }

        adapter = new TabsAdjustAdapter(this, list);
        binding.tabsRC.setAdapter(adapter);

        ItemTouchHelper.Callback ithCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                tabLocals.get(viewHolder.getAdapterPosition()).setId(target.getAdapterPosition());
                // and notify the adapter that its dataset has changed
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Log.d("PositionTabs", "holder : " + viewHolder.getAdapterPosition() + "   target : " + target.getAdapterPosition() );
                Stash.put(Constants.channelsTab, list);
                Stash.put(Constants.localTab, tabLocals);
                Stash.put(Constants.isAdjusted, true);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        ItemTouchHelper ith = new ItemTouchHelper(ithCallback);
        ith.attachToRecyclerView(binding.tabsRC);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}