package com.wrjxjh.daqian.extra.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.core.edit_user.UserInfoActivity;
import com.wrjxjh.daqian.core.sign_history.SignHistoryActivity;
import com.wrjxjh.daqian.extra.activity.SettingsActivity;
import com.wrjxjh.daqian.extra.activity.ShowDatabaseActivity;
import com.wrjxjh.daqian.extra.activity.ShowPrefActivity;

public class UserFragment extends Fragment implements OnClickListener {

    TextView tvUserName;
    LinearLayout btnUserInfo, btnSign, btnSetting;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {

        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
//        tvUserName.setText(Pref.getUsername());
        btnUserInfo = (LinearLayout) view.findViewById(R.id.btn_user_userinfo);
        btnUserInfo.setOnClickListener(this);
        btnSign = (LinearLayout) view.findViewById(R.id.btn_user_sign);
        btnSign.setOnClickListener(this);
        btnSetting = (LinearLayout) view.findViewById(R.id.btn_user_setting);
        btnSetting.setOnClickListener(this);

        LinearLayout btnRemoteData = (LinearLayout) view.findViewById(R.id.btn_remote_data);
        btnRemoteData.setOnClickListener(this);
        LinearLayout btnLocalData = (LinearLayout) view.findViewById(R.id.btn_local_data);
        btnLocalData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_user_userinfo) {
            startActivity(new Intent(getActivity(), UserInfoActivity.class));

        } else if (v.getId() == R.id.btn_user_sign) {
            startActivity(new Intent(getActivity(), SignHistoryActivity.class));

        } else if (v.getId() == R.id.btn_user_setting) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));

        } else if (v.getId() == R.id.btn_remote_data) {
            startActivity(new Intent(getActivity(), ShowDatabaseActivity.class));

        } else if (v.getId() == R.id.btn_local_data) {
            startActivity(new Intent(getActivity(), ShowPrefActivity.class));
        }

    }

}
