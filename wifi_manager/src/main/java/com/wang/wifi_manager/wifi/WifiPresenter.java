package com.wang.wifi_manager.wifi;

import com.wang.math.sort.ISort;
import com.wang.math.sort.SortHelper;
import com.wang.wifi_manager.bean.WifiInfo;
import com.wang.wifi_manager.data.IWifiData;
import com.wang.wifi_manager.data.WifiDataImpl;

import java.util.List;

/**
 * by wangrongjun on 2017/2/28.
 */
public class WifiPresenter implements WifiContract.Presenter {

    private IWifiData data;
    private WifiContract.View wifiView;

    public WifiPresenter(WifiContract.View wifiView) {
        this.wifiView = wifiView;
        data = new WifiDataImpl();
    }

    @Override
    public void onCreate() {
        try {
            List<WifiInfo> wifiInfoList = data.getWifiInfoList();
            SortHelper.sortInsertion(wifiInfoList, new ISort<WifiInfo>() {
                @Override
                public int compare(WifiInfo entity1, WifiInfo entity2) {
                    return entity1.getPriority() < entity2.getPriority() ? 1 : 0;
                }
            });
            wifiView.showWifiInfoList(wifiInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            wifiView.showMessage(e.getMessage());
        }
    }

}