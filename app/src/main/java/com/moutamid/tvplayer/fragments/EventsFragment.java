package com.moutamid.tvplayer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.tvplayer.Constants;
import com.moutamid.tvplayer.databinding.FragmentEventsBinding;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EventsFragment extends Fragment {
    FragmentEventsBinding binding;
    Context context;
    private ProgressDialog progressDialog;
    ArrayList<TabsModel> tabs;
    TabsModel tabsModel;
    ArrayList<TabsModel> list = new ArrayList<>();

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        tabs = new ArrayList<>();

        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        /*requireContext().setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        JSONObject data = (JSONObject) Stash.getObject(Constants.eventsData, JSONObject.class);

//        progressDialog.show();
//        getData();

        if (data == null) {
            progressDialog.show();
            getData();
        } else {
            list = Stash.getArrayList(Constants.eventsTab, TabsModel.class);
            getTabs();
        }

        return view;
    }

    private void getTabs() {
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL(Constants.tabs);
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

            Log.d("TAG", "data: " + htmlData);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    try {
                        JSONArray jsonArray = new JSONArray(htmlData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            for (int j=0; j< list.size(); j++){
                                if (list.get(j).getName().equals(obj.getString("name"))) {
                                    list.get(j).setId(obj.getInt("id"));
                                }
                            }
                            // Stash.put(Constants.channelsTab, list);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Collections.sort(list, Comparator.comparing(TabsModel::getId));
                        }
                        //Collections.reverse(list);
                        setTabs();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }).start();
    }

    private void setTabs() {
        EventsFragment.ViewPagerAdapter adapter = new EventsFragment.ViewPagerAdapter(requireActivity()
                .getSupportFragmentManager());
        ArrayList<String> t = Stash.getArrayList("hidden", String.class);
        for (TabsModel s : list) {
            //Toast.makeText(context, ""+s.getId(), Toast.LENGTH_SHORT).show();
            if(t.isEmpty() || t == null){
                CommonFragment fragment = new CommonFragment(s.getObject());
                adapter.addFrag(fragment, s.getName());
            } else {
                for (String ss : t) {
                    if (!ss.equals(s.getName())){
                        CommonFragment fragment = new CommonFragment(s.getObject());
                        adapter.addFrag(fragment, s.getName());
                    }
                }
            }
        }
        binding.viewpager.setAdapter(adapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }

    private void getData() {
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL(Constants.event);
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

            Log.d("TAG", "data: " + htmlData);


            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    try {
                        //Toast.makeText(context, "Html  " + htmlData, Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject = new JSONObject(htmlData);
                        JSONObject data = jsonObject.getJSONObject("data");
                        Stash.put(Constants.eventsData, data);
                        /*ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity()
                                .getSupportFragmentManager());*/

                        for (String s : iterate(data.keys())) {
                            JSONArray channelsArray = data.getJSONArray(s);
                            // Toast.makeText(context, "Data " + channelsArray.toString(), Toast.LENGTH_SHORT).show();
                            //CommonEventFragment fragment = new CommonEventFragment(channelsArray.toString());
                            //adapter.addFrag(fragment, s);
                            TabsModel model = new TabsModel(s, channelsArray.toString(), false);
                            list.add(model);
                        }
                        Stash.put(Constants.eventsTab, list);
                        getTabs();
                        /*binding.viewpager.setAdapter(adapter);
                        binding.tablayout.setupWithViewPager(binding.viewpager);*/
                        progressDialog.dismiss();

                    } catch (JSONException error) {
                        Toast.makeText(requireActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private <T> Iterable<T> iterate(final Iterator<T> i) {
        return () -> i;
    }

}