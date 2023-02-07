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
import com.moutamid.tvplayer.databinding.FragmentAllChannelsBinding;
import com.moutamid.tvplayer.models.TabLocal;
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

public class AllChannelsFragment extends Fragment {

    FragmentAllChannelsBinding binding;
    Context context;
    private ProgressDialog progressDialog;
    ArrayList<TabsModel> tabs;
    TabsModel tabsModel;
    ArrayList<TabsModel> list = new ArrayList<>();

    public AllChannelsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllChannelsBinding.inflate(inflater,container, false);
        View view = binding.getRoot();
        context = view.getContext();
        Constants.checkApp(requireActivity());

        tabs = new ArrayList<>();

        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        String dummy = "{\"status\":\"success\",\"data\":{\"pakistani movies\":[{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"china\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0},{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"usa\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0}],\"Entertainment\":[{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"pakistan\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0},{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"pakistan\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0}],\"Darama\":[{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"pakistan\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0}]}}";
        // String dummy = "{\"status\":\"success\",\"data\":{\"pakistani movies\":[{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"pakistan\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0},{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"india\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0}],\"Entertainment\":[{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"pakistan\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0},{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"china\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0}],\"Darama\":[{\"_id\":\"63c0201fa23a96dcd4d13e05\",\"name\":\"India vs Pakistan\",\"category\":\"pakistani movies\",\"image\":\"event-img-1673535519840.jpeg\",\"imageUrl\":\"http://95.217.210.178/image/event-img-1673535519840.jpeg\",\"hide\":false,\"country\":\"pakistan\",\"streamingLinks\":[{\"name\":\"Pakistani Movies\",\"token\":\"1\",\"url\":\"http://$/mooon/pakistaninewmovies/playlist.m3u8\",\"priority\":1,\"request_header\":\"Referer,http://google.com\",\"player_header\":\"User-Agent,vlcplayer\",\"_id\":\"63c0201fa23a96dcd4d13e06\"}],\"ID\":7,\"__v\":0}]}}";
        JSONObject data = (JSONObject) Stash.getObject(Constants.channelsData, JSONObject.class);

        /*JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(dummy);
            JSONObject data = jsonObject.getJSONObject("data");
            Stash.put(Constants.channelsData, data);
            ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity()
                    .getSupportFragmentManager());

            for (String s : iterate(data.keys())) {
                JSONArray channelsArray = data.getJSONArray(s);
                // Toast.makeText(context, channelsArray.toString(), Toast.LENGTH_SHORT).show();
                CommonFragment fragment = new CommonFragment(channelsArray.toString());
                adapter.addFrag(fragment, s);
                TabsModel model = new TabsModel(s, channelsArray.toString(), false);
                list.add(model);
            }
            Stash.put(Constants.channelsTab, list);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
*/
        /*progressDialog.show();
        getData();*/

//TODO:        if (data == null){
            progressDialog.show();
            getData();
//        } else {
//            progressDialog.show();
//            list = Stash.getArrayList(Constants.channelsTab, TabsModel.class);
//            getTabs();
//        }

        return view;
    }

    private void getTabs() {
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL(Constants.categories);
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
                        JSONObject json = new JSONObject(htmlData);
                        JSONArray jsonArray = json.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            for (int j=0; j< list.size(); j++){
                                if (list.get(j).getName().equals(obj.getString("name"))) {
                                    list.get(j).setId(obj.getInt("id"));
                                }
                            }
                            Stash.put(Constants.channelsTab, list);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Collections.sort(list, Comparator.comparing(TabsModel::getId));
                        }
                        Log.d("PositionTabs", list.toString());
                        //Collections.reverse(list);
                        setTabs();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }

    private void setTabs() {
        //ArrayList<TabsModel> chanel = Stash.getArrayList(Constants.channelsTab, TabsModel.class);
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity()
                .getSupportFragmentManager());
        //Toast.makeText(context, ""+chanel.size(), Toast.LENGTH_SHORT).show();
        ArrayList<String> t = Stash.getArrayList("hidden", String.class);
        for (TabsModel s : list) {
            //Toast.makeText(context, ""+s.getId()+"\n"+s.getName(), Toast.LENGTH_SHORT).show();
            if(t.isEmpty()){
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
        progressDialog.dismiss();
    }

    private void getData() {
        new Thread(() -> {
            URL google = null;
            try {
                google = new URL(Constants.channel);
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
//                        Toast.makeText(context, htmlData, Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject = new JSONObject(htmlData);
                        JSONObject data = jsonObject.getJSONObject("data");
                        Stash.put(Constants.channelsData, data);
                        /*ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity()
                                .getSupportFragmentManager());*/

                        for (String s : iterate(data.keys())) {
                            JSONArray channelsArray = data.getJSONArray(s);
                            /*Toast.makeText(context, channelsArray.toString(), Toast.LENGTH_SHORT).show();
                            CommonFragment fragment = new CommonFragment(channelsArray.toString());
                            adapter.addFrag(fragment, s);*/
                            TabsModel model = new TabsModel(s, channelsArray.toString(), false);
                            list.add(model);
                        }
                        Stash.put(Constants.channelsTab, list);
                        boolean ta = Stash.getBoolean(Constants.isAdjusted, false);
                        //Toast.makeText(context, "ta : " + ta, Toast.LENGTH_SHORT).show();
                        if (ta){
                            getLocalTabs();
                        } else {
                            getTabs();
                        }
                        /*binding.viewpager.setAdapter(adapter);
                        binding.tablayout.setupWithViewPager(binding.viewpager);*/
                        //progressDialog.dismiss();

                    } catch (JSONException error) {
                        Toast.makeText(requireActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    private void getLocalTabs() {
        ArrayList<TabLocal> tabLocals = Stash.getArrayList(Constants.localTab, TabLocal.class);
        //Toast.makeText(context, tabLocals.toString(), Toast.LENGTH_SHORT).show();
//        Log.d("PositionTabs", tabLocals.toString());
//        Log.d("PositionTabs", "List " + list.toString());
        for (int i = 0; i < tabLocals.size(); i++) {
            String name = tabLocals.get(i).getName();
            int id = tabLocals.get(i).getId();
            for (int j=0; j< list.size(); j++) {
                if (list.get(j).getName().equals(name)) {
                    list.get(j).setId(id);
                    //Log.d("PositionTabs", "loop Sort  \t " +list.get(j).getName() + "\t\t" + name);
                    //Toast.makeText(context, "list\n" + list.get(j).getName() + "\n" + list.get(j).getId(), Toast.LENGTH_SHORT).show();
                }
            }
            Stash.put(Constants.channelsTab, list);
        }
        //Log.d("PositionTabs", "List Sort  \t " + list.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(list, Comparator.comparing(TabsModel::getId));
        }
        //Log.d("PositionTabs", "List Sort A \t " + list.toString());
        //Collections.reverse(list);
        setTabs();
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