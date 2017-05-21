package com.wrjxjh.daqian.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.wang.java_util.GsonUtil;
import com.wrjxjh.daqian.bean.User;

import java.util.Iterator;
import java.util.Map;

/**
 * by wangrongjun on 2017/3/4.
 */
public class LocalDataImpl implements ILocalData {

    public static Context context;
    private static final String prefName = "daqian";

    @Override
    public void setUser(User user) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        if (user == null) {
            pref.edit().putString("user", "").apply();
        } else {
            String json = GsonUtil.toJson(user);
            pref.edit().putString("user", json).apply();
        }
    }

    @Override
    public User getUser() {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String json = pref.getString("user", "");
        User user = null;
        try {
            user = GsonUtil.fromJson(json, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Override
    public void clearAll() {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        pref.edit().clear().apply();
    }

    @Override
    public boolean isFirst() {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", true);
        if (isFirst) {
            pref.edit().putBoolean("isFirst", false).apply();
        }
        return isFirst;
    }

    public String getAllString() {
        String result = "";
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        Map<String, ?> map = pref.getAll();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            result += "key : " + key;
            result += "value : " + value;
            result += "\n\n\n";
        }
        return result;
    }
}
