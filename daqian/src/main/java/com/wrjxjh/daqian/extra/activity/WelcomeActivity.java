package com.wrjxjh.daqian.extra.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wang.java_util.TextUtil;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.core.login.LoginActivity;
import com.wrjxjh.daqian.data.local.LocalDataImpl;
import com.wrjxjh.daqian.data.remote.BaseDataImpl;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {

    private static final int DELAY_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity();
//                addTestSign();
            }
        }, DELAY_TIME);
    }

    private void addTestSign() {
        for (int i = 0; i < 40; i++) {
            new BaseDataImpl().sign(i % 2 + 1, "signText" + i);
        }
    }

    private void startActivity() {
        LocalDataImpl localData = new LocalDataImpl();
        if (localData.isFirst()) {
            startActivity(new Intent(this, GuideActivity.class));
        } else {
            User user = localData.getUser();
            if (user != null && !TextUtil.isEmpty(user.getUsername())) {
                startActivity(new Intent(this, DaqianMainActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
        finish();
    }

}
