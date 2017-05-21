package com.wang.music_player.activity;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.wang.android_lib.util.DoubleClickToExit;
import com.wang.java_program.shopping_system.bean.User;
import com.wang.music_player.R;
import com.wang.music_player.fragment.CalculatorFragment;
import com.wang.music_player.fragment.MusicPlayerFragment;
import com.wang.music_player.fragment.MyFragmentPagerAdapter;
import com.wang.music_player.fragment.UserPageFragment;
import com.wang.music_player.service.MusicPlayerService;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayerActivity extends FragmentActivity {

    private ViewPager pager;
    private List<Fragment> fragList;
    private List<String> titleList;
    private MyFragmentPagerAdapter adapter;
    private Intent serviceIntent = null;
    private ServiceConnection connection;
    private MusicPlayerService mpService;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        pager = (ViewPager) findViewById(R.id.view_pager);
        titleList = new ArrayList<>();
        fragList = new ArrayList<>();
        titleList.add("音乐播放器");
        fragList.add(new MusicPlayerFragment());
        titleList.add("迷你计算器");
        fragList.add(new CalculatorFragment());
        titleList.add("用户界面");
        fragList.add(new UserPageFragment());
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                fragList, titleList);
        pager.setAdapter(adapter);

        user = (User) getIntent().getSerializableExtra("user");

    }

    public void stopMpService() {
        if (serviceIntent != null && connection != null) {
            unbindService(connection);
            mpService.onDestroy();
            stopService(serviceIntent);
        }
    }

    public void setServiceConnection(ServiceConnection connection) {
        this.connection = connection;
    }

    public void setServiceIntent(Intent serviceIntent) {
        this.serviceIntent = serviceIntent;
    }

    public void setMpService(MusicPlayerService mpService) {
        this.mpService = mpService;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return DoubleClickToExit.onKeyDown(keyCode, event, this) || super.onKeyDown(keyCode, event);
    }
}
