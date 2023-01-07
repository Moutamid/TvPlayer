package com.moutamid.tvplayer.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.adapters.ChannelsAdapter;
import com.moutamid.tvplayer.databinding.FragmentAllChannelsBinding;
import com.moutamid.tvplayer.models.ChannelsModel;
import com.moutamid.tvplayer.models.StreamLinksModel;
import com.moutamid.tvplayer.models.TabsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

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

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), binding.tablayout.getTabCount());
        binding.viewpager.setAdapter(viewPagerAdapter);

        binding.tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.tablayout.setupWithViewPager(binding.viewpager);

        return view;
    }

    private void addTabs() {
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("http://95.217.210.178/api/v1/channel/");
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                Log.d("TAG", "compress: ERROR: " + e.toString());
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            Log.d("TAG", "compress: " + htmlData);

            requireActivity().runOnUiThread(() -> {
                try {
                    JSONObject jsonObject = new JSONObject(htmlData);
                    JSONObject data = jsonObject.getJSONObject("data");
                    for (String s : iterate(data.keys())) {
                        binding.tablayout.addTab(binding.tablayout.newTab().setText(s.toUpperCase(Locale.ROOT)));
                    }

                } catch (JSONException error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        Fragment fragment = null;

        public ViewPagerAdapter(@NonNull FragmentManager fm, int mNumOfTabs) {
            super(fm);
            this.mNumOfTabs = mNumOfTabs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            for (int i = 0; i < mNumOfTabs ; i++) {
                if (i == position) {
                    fragment = new CommonFragment(getPageTitle(i).toString().toUpperCase(Locale.ROOT));
                    break;
                }
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

    }

    private <T> Iterable<T> iterate(final Iterator<T> i) {
        return () -> i;
    }
}