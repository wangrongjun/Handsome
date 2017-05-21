package com.wang.handsome;

import android.app.Activity;

/**
 * by wangrongjun on 2017/3/2.
 */
public class AppBean {

    private Integer appIconId;
    private String appName;
    private String appDescription;
    private Class<? extends Activity> startActivityClass;

    public AppBean(Integer appIconId, String appName, String appDescription,
                   Class<? extends Activity> startActivityClass) {
        this.appIconId = appIconId;
        this.appName = appName;
        this.appDescription = appDescription;
        this.startActivityClass = startActivityClass;
    }

    public Integer getAppIconId() {
        return appIconId;
    }

    public void setAppIconId(Integer appIconId) {
        this.appIconId = appIconId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public Class<? extends Activity> getStartActivityClass() {
        return startActivityClass;
    }

    public void setStartActivityClass(Class<Activity> startActivityClass) {
        this.startActivityClass = startActivityClass;
    }
}
