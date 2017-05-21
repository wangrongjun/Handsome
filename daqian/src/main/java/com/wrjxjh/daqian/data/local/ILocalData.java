package com.wrjxjh.daqian.data.local;

import com.wrjxjh.daqian.bean.User;

/**
 * by wangrongjun on 2017/3/5.
 * 本地数据，保存在本地的配置信息，登录信息等。
 */
public interface ILocalData {

    void setUser(User user);

    User getUser();

    void clearAll();

    /**
     * 是否第一次启动，用于判断是否显示导航页面。
     *
     * @return 第一次调用该方法返回true，第二次调用返回false。
     */
    boolean isFirst();

}
