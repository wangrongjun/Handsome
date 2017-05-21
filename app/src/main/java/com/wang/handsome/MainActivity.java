package com.wang.handsome;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wang.android_lib.view.helper.RecyclerViewDivider;
import com.wang.calculator.activity.CalculatorActivity;
import com.wang.contacts_backup.ContactsBackupActivity;
import com.wang.long_picture_browser.example.TouchActivity;
import com.wang.music_player.activity.MusicPlayerActivity;
import com.wang.picture_puzzle.select_picture.SelectPictureActivity;
import com.wang.video_downloader.DownloadListActivity;
import com.wang.wifi_manager.wifi.WifiActivity;
import com.wang.youdao_dictionary.YoudaoActivity;
import com.wrjxjh.daqian.extra.activity.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

//  C:\IDE\android-studio-project\Handsome\app\build\outputs\apk
public class MainActivity extends Activity {

    private List<AppBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rvApp = (RecyclerView) findViewById(R.id.rv_app);
        rvApp.setLayoutManager(new LinearLayoutManager(this));
        rvApp.addItemDecoration(new RecyclerViewDivider(this));
        rvApp.setAdapter(new AppRvAdapter(this, list));
    }

    private void initData() {
        list = new ArrayList<>();

        String cbn = getResources().getString(R.string.contacts_backup_name);
        String cbd = getResources().getString(R.string.contacts_backup_description);
        list.add(new AppBean(R.mipmap.ic_contacts_backup, cbn, cbd, ContactsBackupActivity.class));

        String lpbn = getResources().getString(R.string.long_picture_browser_name);
        String lpbd = getResources().getString(R.string.long_picture_browser_description);
        list.add(new AppBean(R.mipmap.ic_long_picture_browser, lpbn, lpbd, TouchActivity.class));

        String dn = getResources().getString(R.string.daqian_name);
        String dd = getResources().getString(R.string.daqian_description);
        list.add(new AppBean(R.mipmap.logo_daqian, dn, dd, WelcomeActivity.class));

        String ppn = getResources().getString(R.string.picture_puzzle_name);
        String ppd = getResources().getString(R.string.picture_puzzle_description);
        list.add(new AppBean(R.mipmap.logo_picture_puzzle, ppn, ppd, SelectPictureActivity.class));

        String mpn = getResources().getString(R.string.music_player_name);
        String mpd = getResources().getString(R.string.music_player_description);
        list.add(new AppBean(R.mipmap.logo_music_player, mpn, mpd, MusicPlayerActivity.class));

        String wmn = getResources().getString(R.string.wifi_manager_name);
        String wmd = getResources().getString(R.string.wifi_manager_description);
        list.add(new AppBean(R.mipmap.logo_wifi_manager, wmn, wmd, WifiActivity.class));

        String vdn = getResources().getString(R.string.video_downloader_name);
        String vdd = getResources().getString(R.string.video_downloader_description);
        list.add(new AppBean(R.mipmap.logo_video_downloader, vdn, vdd, DownloadListActivity.class));

        String cn = getResources().getString(R.string.calculator_name);
        String cd = getResources().getString(R.string.calculator_description);
        list.add(new AppBean(R.mipmap.logo_calculator, cn, cd, CalculatorActivity.class));

        String yn = getResources().getString(R.string.youdao_name);
        String yd = getResources().getString(R.string.youdao_description);
        list.add(new AppBean(R.mipmap.logo_youdao_dictionary, yn, yd, YoudaoActivity.class));

    }

}
