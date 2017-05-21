package com.wrjxjh.daqian.extra.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wrjxjh.daqian.R;

public class AboutActivity extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initItem();
    }

    private void initItem() {
        ((TextView) findViewById(R.id.menu_back_tv)).setText("关于大签");
        findViewById(R.id.menu_btn_back).setOnClickListener(this);

        View btnIntroduce = findViewById(R.id.btn_about_introduce);
        ((TextView) btnIntroduce.findViewById(R.id.btn_tv_item))
                .setText("功能介绍");
        btnIntroduce.setOnClickListener(this);

        View btnQuestion = findViewById(R.id.btn_about_question);
        ((TextView) btnQuestion.findViewById(R.id.btn_tv_item))
                .setText("常见问题");
        btnQuestion.setOnClickListener(this);

        View btnHelpAndResponsible = findViewById(R.id.btn_about_help_and_responsible);
        ((TextView) btnHelpAndResponsible.findViewById(R.id.btn_tv_item))
                .setText("帮助与反馈");
        btnHelpAndResponsible.setOnClickListener(this);

        View btnUpdate = findViewById(R.id.btn_about_update);
        ((TextView) btnUpdate.findViewById(R.id.btn_tv_item)).setText("更新");
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_about_introduce) {
            Toast.makeText(this, "功能介绍", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_about_question) {
            Toast.makeText(this, "常见问题", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_about_help_and_responsible) {
            Toast.makeText(this, "帮助与反馈", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_about_update) {
            Toast.makeText(this, "更新", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.menu_btn_back) {
            finish();
        }
    }

}
