package com.wang.video_downloader;

import android.os.Environment;

import com.wang.java_util.FileUtil;

import java.io.File;

/**
 * by 王荣俊 on 2016/9/25.
 */
public class C {

    public static final String imoocUrl = "http://www.imooc.com/learn/";
    public static final String downloadDir = Environment.getExternalStorageDirectory() +
            File.separator + "VideoDownload" + File.separator;

    static {
        FileUtil.mkdirs(C.downloadDir);
    }
}
