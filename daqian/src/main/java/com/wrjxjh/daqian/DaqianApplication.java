package com.wrjxjh.daqian;

import android.app.Application;
import android.content.Context;

import com.wrjxjh.daqian.data.local.LocalDataImpl;
import com.wrjxjh.daqian.util.dbhelper.SqliteDbHelper;

/**
 * by wangrongjun on 2017/3/9.
 */
public class DaqianApplication extends Application {

    private Context context;

    public DaqianApplication(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocalDataImpl.context = getApplicationContext();
        SqliteDbHelper.context = getApplicationContext();
    }
}
