package com.moutamid.tvplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.iplayer.controller.VideoController;
import com.android.iplayer.widget.WidgetFactory;
import com.moutamid.tvplayer.databinding.ActivityVideoPlayerBinding;

public class VideoPlayerActivity extends AppCompatActivity {
    ActivityVideoPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String url = getIntent().getStringExtra("url");
        String name = getIntent().getStringExtra("name");

        // mVideoPlayer.getLayoutParams().height= getResources().getDisplayMetrics().widthPixels * 9 /16;//固定播放器高度，或高度设置为:match_parent
        //使用SDK自带控制器+各UI交互组件
        VideoController controller = new VideoController(binding.videoPlayer.getContext());//创建一个默认控制器
        binding.videoPlayer.setController(controller);//将播放器绑定到控制器
        WidgetFactory.bindDefaultControls(controller);//一键使用默认UI交互组件绑定到控制器(需集成：implementation 'com.github.hty527.iPlayer:widget:lastversion')
        //设置视频标题(仅横屏状态可见)
        controller.setTitle(name);
        Log.d("VideoURLPlayer", ""+url);
        binding.videoPlayer.setDataSource(url);
        // binding.videoPlayer.setDataSource("https://upload.dongfeng-nissan.com.cn/nissan/video/202204/4cfde6f0-bf80-11ec-95c3-214c38efbbc8.mp4");
        binding.videoPlayer.prepareAsync();

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.videoPlayer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.videoPlayer.onPause();
    }

    @Override
    public void onBackPressed() {
        if(binding.videoPlayer.isBackPressed()){
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.videoPlayer.onDestroy();
    }

}