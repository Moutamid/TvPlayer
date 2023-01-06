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

public class AllChannelsFragment extends Fragment {

    FragmentAllChannelsBinding binding;
    Context context;
    ViewPagerAdapter viewPagerAdapter;

    public AllChannelsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllChannelsBinding.inflate(inflater,container, false);
        View view = binding.getRoot();
        context = view.getContext();

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        binding.viewpager.setAdapter(viewPagerAdapter);

        binding.tablayout.setupWithViewPager(binding.viewpager);

        return view;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0)
                fragment = new SportsFragment();
            else if (position == 1)
                fragment = new NewsFragment();
            if (position == 2)
                fragment = new EventsFragment();
            else if (position == 3)
                fragment = new FavouritesFragment();

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0)
                title = "Sports";
            else if (position == 1)
                title = "News";
            if (position == 2)
                title = "Events";
            else if (position == 3)
                title = "Favourites";
            return title;
        }
    }
}