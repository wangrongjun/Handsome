package com.wrjxjh.daqian.extra.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.data.local.LocalDataImpl;

/**
 * by wangrongjun on 2017/3/19.
 */
public class ShowPrefActivity extends Activity {

    private TextView tvPrefInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_show_pref);
        initView();
    }

    private void initView() {
        tvPrefInfo = (TextView) findViewById(R.id.tv_pref_info);
        tvPrefInfo.setText(new LocalDataImpl().getAllString());
    }
}
