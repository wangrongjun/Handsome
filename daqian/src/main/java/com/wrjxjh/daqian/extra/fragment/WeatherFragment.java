package com.wrjxjh.daqian.extra.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.util.BaiduWeather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherFragment extends Fragment implements OnClickListener,
        OnItemSelectedListener, OnTouchListener {

    private BaiduWeather baiduWeather;
    private String city;
    private int dayNum;
    private int tipNum;

    // private View btnRefresh;
    // private ScrollPullDownRefresher refresher;
    private ImageView ivTodayImg;
    private TextView tvTodayTemperature;
    private TextView tvTodayWeather;
    private TextView tvDateAndTime;
    private Spinner spinnerWeatherCity;
    private ScrollView scrollView;
    private SwipeRefreshLayout refreshLayout;

    private int[] itemWeatherId = {R.id.weather_item_weather_day1,
            R.id.weather_item_weather_day2, R.id.weather_item_weather_day3,
            R.id.weather_item_weather_day4};
    private int[] itemTipId = {R.id.weather_item_tip_sport,
            R.id.weather_item_tip_clothes, R.id.weather_item_tip_travel,
            R.id.weather_item_tip_car, R.id.weather_item_tip_cold,
            R.id.weather_item_tip_ziwaixian};

    // 天气
    private View[] itemWeather;
    // 今天，明天，后天，和大后天
    private TextView[] tvDays;
    // 今天，明天，后天，和大后天对应的图片
    private ImageView[] ivWeatherImg;
    // 今天，明天，后天，和大后天对应的文字说明，如:多云
    private TextView[] tvWeather;
    // 今天，明天，后天，和大后天对应的温度
    private TextView[] tvTemperature;
    private String[] daysName = {"今天", "明天", "后天", "大后天"};

    // 指数
    private View[] itemTip;
    // 指数对应的图标，如运动指数对应一张象征运动的篮球图标
    private ImageView[] ivTipImg;
    // 指数标题，如：运动指数，穿衣指数
    private TextView[] tvTipTipt;
    // 适宜情况，如：适宜，不适宜
    private TextView[] tvTipZs;
    // 建议，如：较适宜洗车
    private TextView[] tvTipDes;

    private boolean isQuerying = false;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container,
                false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        dayNum = itemWeatherId.length;
        tipNum = itemTipId.length;

        initRefreshView(view);
        initSpinnerView(view);
        initTodayView(view);
        initWeatherView(view);
        initTipView(view);

    }

    private void initRefreshView(View view) {
        refreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.scroll_weather);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                refresh();

            }
        });

        // scrollView = (ScrollView) view.findViewById(R.id.scroll_weather);
        // scrollView.setOnTouchListener(this);
        // refresher = new ScrollPullDownRefresher(view, scrollView);
        // refresher.setOnRefreshStartListener(this);
        // head = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
        // R.layout.head_me, null, true);
    }

    private void initTodayView(View view) {

        ivTodayImg = (ImageView) view
                .findViewById(R.id.iv_weather_today_weather);
        ivTodayImg.setImageResource(R.mipmap.ic_weather_no);

        tvTodayTemperature = (TextView) view
                .findViewById(R.id.tv_weather_today_temperature);
        tvTodayTemperature.setText("");

        tvTodayWeather = (TextView) view
                .findViewById(R.id.tv_weather_today_weather);
        tvTodayWeather.setText("");

        tvDateAndTime = (TextView) view
                .findViewById(R.id.tv_weather_today_date_and_time);

        refreshDateAndTime();

    }

    private void initWeatherView(View view) {

        itemWeather = new View[dayNum];

        tvDays = new TextView[dayNum];
        ivWeatherImg = new ImageView[dayNum];
        tvWeather = new TextView[dayNum];
        tvTemperature = new TextView[dayNum];

        for (int i = 0; i < dayNum; i++) {

            itemWeather[i] = view.findViewById(itemWeatherId[i]);

            tvDays[i] = ((TextView) itemWeather[i]
                    .findViewById(R.id.tv_weather_item_weather_day));
            tvDays[i].setText(daysName[i]);

            ivWeatherImg[i] = ((ImageView) itemWeather[i]
                    .findViewById(R.id.iv_weather_item_weather));
            ivWeatherImg[i].setImageResource(R.mipmap.ic_weather_no);

            tvWeather[i] = (TextView) itemWeather[i]
                    .findViewById(R.id.tv_weather_item_weather_weather);
            tvWeather[i].setText("");

            tvTemperature[i] = (TextView) itemWeather[i]
                    .findViewById(R.id.tv_weather_item_weather_temperature);
            tvTemperature[i].setText("");

        }

    }

    private void initTipView(View view) {

        itemTip = new View[tipNum];

        ivTipImg = new ImageView[tipNum];
        tvTipTipt = new TextView[tipNum];
        tvTipZs = new TextView[tipNum];
        tvTipDes = new TextView[tipNum];

        for (int i = 0; i < tipNum; i++) {

            itemTip[i] = view.findViewById(itemTipId[i]);
            // itemTip[i].setVisibility(View.GONE);

            ivTipImg[i] = (ImageView) itemTip[i]
                    .findViewById(R.id.iv_weather_item_tip_img);
            tvTipTipt[i] = (TextView) itemTip[i]
                    .findViewById(R.id.tv_weather_item_tip_tipt);
            tvTipZs[i] = (TextView) itemTip[i]
                    .findViewById(R.id.tv_weather_item_tip_zs);
            tvTipDes[i] = (TextView) itemTip[i]
                    .findViewById(R.id.tv_weather_item_tip_des);

        }
    }

    private void initSpinnerView(View view) {

        spinnerWeatherCity = (Spinner) view
                .findViewById(R.id.spinner_menu_weather_city);

        List<String> data_list = new ArrayList<String>();
        data_list.add("广州");
        data_list.add("上海");
        data_list.add("香港");
        data_list.add("深圳");
        data_list.add("天津");
        data_list.add("厦门");
        data_list.add("海口");

        // android.R.layout.simple_spinner_item
        // android.R.layout.simple_spinner_dropdown_item
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(
                getContext(), R.layout.spinner_item, data_list);
        arr_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeatherCity.setAdapter(arr_adapter);
        spinnerWeatherCity.setOnItemSelectedListener(this);

    }

    @SuppressLint("SimpleDateFormat")
    private void refreshDateAndTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        String s = formatter.format(new Date(System.currentTimeMillis()));
        tvDateAndTime.setText(s);

    }

    private void refresh() {

        if (isQuerying) {
            return;
        } else {
            isQuerying = true;
        }

        AsyncTask<String, Void, Void> readWeatherDataTask = new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {
                baiduWeather = new BaiduWeather();
                baiduWeather.queryWeather(params[0]);

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // refresher.onRefreshFinish(!baiduWeather.isEmpty());//
                // true表示加载成功
                isQuerying = false;

                if (baiduWeather.isEmpty() || baiduWeather.errorCode != 0) {
                    Toast.makeText(getActivity(), "天气获取失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                refreshTodayView();
                refreshWeatherView();
                refreshTipView();

                refreshLayout.setRefreshing(false);
            }

        };

        readWeatherDataTask.execute(city);
    }

    @Override
    public void onClick(View v) {
        refresh();
    }

    protected void refreshTodayView() {

        setWeatherImg(ivTodayImg, baiduWeather.weather[0]);
        tvTodayWeather.setText(baiduWeather.weather[0]);
        tvTodayTemperature.setText(baiduWeather.temperature[0]);
        refreshDateAndTime();

    }

    protected void refreshWeatherView() {

        for (int i = 0; i < dayNum; i++) {
            setWeatherImg(ivWeatherImg[i], baiduWeather.weather[i]);
            tvWeather[i].setText(baiduWeather.weather[i]);
            tvTemperature[i].setText(baiduWeather.temperature[i]);
        }

    }

    protected void refreshTipView() {

        for (int i = 0; i < tipNum; i++) {
            itemTip[i].setVisibility(View.VISIBLE);

            setTipImg(ivTipImg[i], baiduWeather.tipTipt[i]);
            tvTipTipt[i].setText(baiduWeather.tipTipt[i]);
            tvTipZs[i].setText(baiduWeather.tipZs[i]);
            tvTipDes[i].setText(baiduWeather.tipDes[i]);
        }

    }

    // 通过天气字符串加载相应的图片
    private void setWeatherImg(ImageView iv, String weather) {

        int id;

        if (weather.contains("晴")) {
            id = R.mipmap.ic_weather_sunny;

        } else if (weather.contains("多云")) {
            id = R.mipmap.ic_weather_cloudy;

        } else if (weather.contains("阴")) {
            id = R.mipmap.ic_weather_yintian;

        } else if (weather.contains("雨")) {
            id = R.mipmap.ic_weather_rain;

        } else if (weather.contains("雪")) {
            id = R.mipmap.ic_weather_snow;

        } else if (weather.contains("雾")) {
            id = R.mipmap.ic_weather_fog;

        } else if (weather.contains("沙") || weather.contains("尘")
                || weather.contains("霾")) {
            id = R.mipmap.ic_weather_dust;

        } else {
            id = R.mipmap.ic_weather_no;

        }

        iv.setImageResource(id);

    }

    // 通过指数标题字符串加载相应的图片
    private void setTipImg(ImageView iv, String tipt) {

        int id;

        if (tipt.equals("穿衣指数")) {
            id = R.mipmap.ic_weather_tip_clothes;
        } else if (tipt.equals("洗车指数")) {
            id = R.mipmap.ic_weather_tip_car;

        } else if (tipt.equals("旅游指数")) {
            id = R.mipmap.ic_weather_tip_travel;

        } else if (tipt.equals("感冒指数")) {
            id = R.mipmap.ic_weather_tip_cold;

        } else if (tipt.equals("运动指数")) {
            id = R.mipmap.ic_weather_tip_sport;

        } else if (tipt.equals("紫外线强度指数")) {
            id = R.mipmap.ic_weather_tip_ziwaixian;

        } else {
            id = R.mipmap.ic_weather_tip_error;

        }

        iv.setImageResource(id);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Spinner spinner = (Spinner) parent;
        city = spinner.getItemAtPosition(position) + "";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // 必须写 v.performClick();解决与单击事件的冲突
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // refresher.onTouch(v, event);
        return v.performClick();

    }

}
