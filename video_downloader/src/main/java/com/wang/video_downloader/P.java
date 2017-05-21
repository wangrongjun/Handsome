package com.wang.video_downloader;

import android.content.Context;

import com.wang.android_lib.util.PrefUtil;
import com.wang.java_program.video_download.bean.Course;

/**
 * by 王荣俊 on 2016/9/26.
 */
public class P extends PrefUtil {

    public static Context context;

    public static void setCourse(Course course) {
        setEntity(context, "videoDownloader", course);
    }

    public static Course getCourse() {
        return getEntity(context, "videoDownloader", Course.class);
    }
}
