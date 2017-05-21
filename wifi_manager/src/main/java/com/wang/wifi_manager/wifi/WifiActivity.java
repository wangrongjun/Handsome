package com.wang.wifi_manager.wifi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.wang.wifi_manager.R;

import java.util.List;

/**
 * by wangrongjun on 2017/2/28.
 */
public class WifiActivity extends Activity implements WifiContract.View {

    private WifiContract.Presenter presenter;
    private ListView lvWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        initView();

        presenter = new WifiPresenter(this);
        presenter.onCreate();
    }

    private void initView() {
        lvWifi = (ListView) findViewById(R.id.lv_wifi);
    }

    @Override
    public void showWifiInfoList(List<com.wang.wifi_manager.bean.WifiInfo> wifiInfoList) {
        WifiListAdapter adapter = new WifiListAdapter(this, wifiInfoList);
        lvWifi.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(WifiActivity.this, message, Toast.LENGTH_LONG).show();
    }

}
