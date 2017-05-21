package com.wang.calculator.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wang.calculator.R;
import com.wang.calculator.listener.CalculatorListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorActivity extends Activity {

    private Intent intent;
    private SimpleAdapter adapter;
    private GridView gridView;
    private TextView showText;
    private Dialog dialog;
    private long exitTime = 0;
    private List<Map<String, Object>> dataList;
    String sign[] = {"(", ")", "Del", "CE", "1", "2", "3", "+", "4", "5", "6",
            "-", "7", "8", "9", "*", ".", "0", "=", "/"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_layout);

        intent = getIntent();

        gridView = (GridView) findViewById(R.id.gridView);
        showText = (TextView) findViewById(R.id.showText);

        dataList = new ArrayList<Map<String, Object>>();

        adapter = new SimpleAdapter(this, getData(),
                R.layout.calculator_button_layout, new String[]{"button"},
                new int[]{R.id.item});
        gridView.setAdapter(adapter);
        CalculatorListener listener = new CalculatorListener();
        listener.setAdapter(adapter);
        listener.setTextView(showText);
        listener.setSigns(sign);
        gridView.setOnItemClickListener(listener);
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < sign.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("button", sign[i]);
            dataList.add(map);
        }

        return dataList;
    }

}
