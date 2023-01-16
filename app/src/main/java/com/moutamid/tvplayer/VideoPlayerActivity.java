package com.moutamid.tvplayer;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.iplayer.base.AbstractMediaPlayer;
import com.android.iplayer.controller.VideoController;
import com.android.iplayer.interfaces.IVideoController;
import com.android.iplayer.listener.OnPlayerEventListener;
import com.android.iplayer.media.core.ExoPlayerFactory;
import com.android.iplayer.media.core.IjkPlayerFactory;
import com.android.iplayer.widget.WidgetFactory;
import com.android.iplayer.widget.controls.ControWindowView;
import com.android.iplayer.widget.controls.ControlCompletionView;
import com.android.iplayer.widget.controls.ControlFunctionBarView;
import com.android.iplayer.widget.controls.ControlGestureView;
import com.android.iplayer.widget.controls.ControlLoadingView;
import com.android.iplayer.widget.controls.ControlStatusView;
import com.android.iplayer.widget.controls.ControlToolBarView;
import com.moutamid.tvplayer.databinding.ActivityVideoPlayerBinding;

import java.util.logging.Logger;

public class VideoPlayerActivity extends AppCompatActivity {
    ActivityVideoPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String url = getIntent().getStringExtra("url");
        String name = getIntent().getStringExtra("name");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.videoPlayer.setTooltipText(name);
        }

        // binding.videoPlayer.getLayoutParams().height= getResources().getDisplayMetrics().widthPixels * 9 /16;

        binding.videoPlayer.setAutoChangeOrientation(true);

        /*int MEDIA_CORE=1;
        binding.videoPlayer.setOnPlayerActionListener(new OnPlayerEventListener() {

            @Override
            public AbstractMediaPlayer createMediaPlayer() {
                if(1==MEDIA_CORE){
                    return IjkPlayerFactory.create().createPlayer(VideoPlayerActivity.this);//IJK解码器，需引用库：implementation 'com.github.hty527.iPlayer:ijk:lastversion'
                }else if(2==MEDIA_CORE){
                    return ExoPlayerFactory.create().createPlayer(VideoPlayerActivity.this);//EXO解码器，需引用库：implementation 'com.github.hty527.iPlayer:exo:lastversion'
                }else{
                    return null;//返回null时,SDK内部会自动使用系统MediaPlayer解码器,自定义解码器请参考Demo中ExoMediaPlayer类或ijk中的IJkMediaPlayer类
                }
            }
        });*/

        VideoController mController = new VideoController(binding.videoPlayer.getContext());
        mController.showLocker(true);
        mController.setCanTouchInPortrait(true);
        mController.setCanTouchPosition(true);
        mController.setGestureEnabled(true);
        mController.setDoubleTapTogglePlayEnabled(true);
        binding.videoPlayer.setController(mController);

        ControlToolBarView toolBarView=new ControlToolBarView(this);
        toolBarView.setTarget(IVideoController.TARGET_CONTROL_TOOL);
        toolBarView.showBack(true);
        toolBarView.setTitle(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            toolBarView.setTooltipText(name);
        }
        toolBarView.showMenus(true,false,false);

        toolBarView.setOnToolBarActionListener(new ControlToolBarView.OnToolBarActionListener() {
            @Override
            public void onBack() {
                //Logger.d(TAG,"onBack");
                onBackPressed();
            }

            @Override
            public void onTv() {
                //Logger.d(TAG,"onTv");
                startActivity(new Intent("android.settings.CAST_SETTINGS"));
            }

            @Override
            public void onWindow() {
                //Logger.d(TAG,"onWindow");
                //startGoableWindow(null);
            }

            @Override
            public void onMenu() {
                //Logger.d(TAG,"onMenu");
                //showMenuDialog();
            }
        });
        ControlFunctionBarView functionBarView=new ControlFunctionBarView(this);//底部时间、seek、静音、全屏功能栏
        functionBarView.showSoundMute(true,false);//启用静音功能交互\默认不静音
        ControlGestureView gestureView=new ControlGestureView(this);//手势控制屏幕亮度、系统音量、快进、快退UI交互
        ControlCompletionView completionView=new ControlCompletionView(this);//播放完成、重试
        ControlStatusView statusView=new ControlStatusView(this);//移动网络播放提示、播放失败、试看完成
        ControlLoadingView loadingView=new ControlLoadingView(this);//加载中、开始播放
        ControWindowView windowView=new ControWindowView(this);//悬浮窗窗口播放器的窗口样式
        //将自定义交互组件添加到控制器
        mController.addControllerWidget(toolBarView,functionBarView,gestureView,completionView,statusView,loadingView,windowView);

        /*VideoController controller = new VideoController(binding.videoPlayer.getContext());
        binding.videoPlayer.setController(controller);
        WidgetFactory.bindDefaultControls(controller);
        controller.setTitle(name);*/
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