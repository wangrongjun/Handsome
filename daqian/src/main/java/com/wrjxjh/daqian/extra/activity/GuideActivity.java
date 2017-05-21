package com.wrjxjh.daqian.extra.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.extra.adapter.GuideAdapter;
import com.wrjxjh.daqian.core.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements OnPageChangeListener {

    ViewPager guide;
    View view1, view2, view3;
    List<View> viewList;
    ImageView points[];
    int ids[] = {R.id.guide_point_1, R.id.guide_point_2, R.id.guide_point_3};

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
        initPoints();
        guide.setOnPageChangeListener(this);
    }

    private void initViews() {
        view1 = View.inflate(this, R.layout.guide_1, null);
        view2 = View.inflate(this, R.layout.guide_2, null);
        view3 = View.inflate(this, R.layout.guide_3, null);

        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        guide = (ViewPager) findViewById(R.id.guide);
        GuideAdapter adapter = new GuideAdapter(viewList);
        guide.setAdapter(adapter);
    }

    private void initPoints() {
        points = new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            points[i] = (ImageView) findViewById(ids[i]);
        }
    }

    public void doClick(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        for (int i = 0; i < viewList.size(); i++) {
            if (arg0 == i) {
                points[i].setImageResource(R.mipmap.guide_point_selected);
            } else {
                points[i].setImageResource(R.mipmap.guide_point);
            }
        }
    }
}
