package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.databinding.FragmentAllChannelsBinding;
import com.moutamid.tvplayer.models.TabsModel;

import java.util.ArrayList;

public class AllChannelsFragment extends Fragment {

    FragmentAllChannelsBinding binding;
    Context context;
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<TabsModel> tabs;
    TabsModel tabsModel;

    public AllChannelsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllChannelsBinding.inflate(inflater,container, false);
        View view = binding.getRoot();
        context = view.getContext();

        tabs = new ArrayList<>();

        addTabs();

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        binding.viewpager.setAdapter(viewPagerAdapter);

        binding.tablayout.setupWithViewPager(binding.viewpager);

        return view;
    }

    private void addTabs() {
        tabsModel = new TabsModel("Sports", new SportsFragment());
        tabs.add(tabsModel);

        tabsModel = new TabsModel("News", new NewsFragment());
        tabs.add(tabsModel);

        tabsModel = new TabsModel("Events", new EventsFragment());
        tabs.add(tabsModel);

        tabsModel = new TabsModel("Favourites", new FavouritesFragment());
        tabs.add(tabsModel);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = tabs.get(position).getFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return tabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = tabs.get(position).getTitle();
            return title;
        }
    }
}