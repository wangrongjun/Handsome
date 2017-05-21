package com.wang.handsome;

import android.app.Application;

import com.wang.video_downloader.VideoDownloaderApplication;
import com.wrjxjh.daqian.DaqianApplication;

/**
 * by wangrongjun on 2017/3/9.
 */
public class PublicApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new DaqianApplication(getApplicationContext()).onCreate();
        new VideoDownloaderApplication(getApplicationContext()).onCreate();
    }

}
