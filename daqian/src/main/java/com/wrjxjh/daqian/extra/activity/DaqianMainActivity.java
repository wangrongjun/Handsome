package com.wrjxjh.daqian.extra.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.wang.android_lib.util.DoubleClickToExit;
import com.wrjxjh.daqian.BaseActivity;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.core.sign.SignFragment;
import com.wrjxjh.daqian.extra.adapter.MainActivityAdapter;
import com.wrjxjh.daqian.extra.fragment.UserFragment;
import com.wrjxjh.daqian.extra.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class DaqianMainActivity extends BaseActivity implements OnClickListener,
        OnPageChangeListener {

    private ViewPager viewPager;
    private LinearLayout[] tabList;

    private int tabId[] = {R.id.tab_prime_page, R.id.tab_weather,
            R.id.tab_user};
//    private int tabImgId[] = {R.id.tab_img_prime_page, R.id.tab_img_weather,
//            R.id.tab_img_user};

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main_daiqian);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SignFragment());
        fragmentList.add(new WeatherFragment());
        fragmentList.add(new UserFragment());

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MainActivityAdapter(getSupportFragmentManager(), fragmentList));

        tabList = new LinearLayout[tabId.length];
//        ImageButton[] tabImgList = new ImageButton[tabId.length];
        for (int i = 0; i < tabId.length; i++) {
//            tabImgList[i] = (ImageButton) findViewById(tabImgId[i]);
            tabList[i] = (LinearLayout) findViewById(tabId[i]);
            tabList[i].setOnClickListener(this);
        }
    }

    @Override
    protected void initListener() {
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tab_prime_page) {
            viewPager.setCurrentItem(0);
            resetTabPic();
            tabList[0].setAlpha(1f);

        } else if (v.getId() == R.id.tab_weather) {
            viewPager.setCurrentItem(1);
            resetTabPic();
            tabList[1].setAlpha(1f);

        } else if (v.getId() == R.id.tab_user) {
            viewPager.setCurrentItem(2);
            resetTabPic();
            tabList[2].setAlpha(1f);

        }
    }

    private void resetTabPic() {
        for (LinearLayout aTab : tabList) {
            aTab.setAlpha(0.25f);
        }
    }

    @Override
    public void onPageSelected(int page) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int position, float percentage, int arg2) {
        if (percentage > 0.001) {
            tabList[position].setAlpha(1 - percentage * 3 / 4);
            tabList[position + 1].setAlpha((float) (0.25 + percentage * 3 / 4));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return DoubleClickToExit.onKeyDown(keyCode, event, this) || super.onKeyDown(keyCode, event);
    }

}
