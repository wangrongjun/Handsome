package com.wang.music_player.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wang.music_player.R;
import com.wang.music_player.activity.MusicPlayerActivity;


//第3界面：用户界面
public class UserPageFragment extends Fragment implements OnClickListener {

    private View view;
    private TextView tvUserName;
    private Button btnExit, btnLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_page_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init();
    }

    private void init() {
        tvUserName = (TextView) view.findViewById(R.id.tv_show_user_name);
        tvUserName.setText("当前用户:wang");
        btnExit = (Button) view.findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(this);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_exit) {
            getActivity().finish();
            ((MusicPlayerActivity) getActivity()).stopMpService();
            System.exit(0);
        } else if (v.getId() == R.id.btn_logout) {
            ((MusicPlayerActivity) getActivity()).stopMpService();
            getActivity().finish();
        }
    }

}
