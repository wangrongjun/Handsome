package com.wang.wifi_manager.data;

import com.wang.wifi_manager.bean.WifiInfo;

import java.util.List;

/**
 * by wangrongjun on 2017/2/28.
 */
public interface IWifiData {

    List<WifiInfo> getWifiInfoList() throws Exception;

}
