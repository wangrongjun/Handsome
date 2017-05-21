package com.wang.video_downloader;

import android.app.Application;
import android.content.Context;

import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloader;

/**
 * by 王荣俊 on 2016/9/26.
 */
public class VideoDownloaderApplication extends Application {

    private Context context;

    public VideoDownloaderApplication(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDownload();
        P.context = getApplicationContext();
    }

    private void initDownload() {
//        1、创建Builder
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(this);

//      2.配置Builder
//      配置下载文件保存的文件夹
        builder.configFileDownloadDir(C.downloadDir);
//      配置同时下载任务数量，如果不配置默认为2
        builder.configDownloadTaskSize(3);
//      配置失败时尝试重试的次数，如果不配置默认为0不尝试
        builder.configRetryDownloadTimes(5);
//      开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
        builder.configDebugMode(true);
//       配置连接网络超时时间，如果不配置默认为15秒
        builder.configConnectTimeout(25000);// 25秒

//      3、使用配置文件初始化FileDownloader
        FileDownloadConfiguration configuration = builder.build();
        FileDownloader.init(configuration);
    }
}
