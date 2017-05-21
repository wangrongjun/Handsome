package com.wang.wifi_manager.wifi;

import com.wang.wifi_manager.bean.WifiInfo;

import java.util.List;

/**
 * by wangrongjun on 2017/3/14.
 */
public class WifiContract {

    interface Presenter {
        void onCreate();
    }

    interface View {
        void showWifiInfoList(List<WifiInfo> wifiInfoList);

        void showMessage(String message);
    }

}
