package com.wrjxjh.daqian.core.sign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wang.android_lib.util.DialogUtil;
import com.wang.android_lib.util.M;
import com.wang.android_lib.view.helper.RecyclerViewDivider;
import com.wang.java_util.TextUtil;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.result.SignListResult;

import java.util.List;

public class SignFragment extends Fragment implements SignContract.View {

    public static final String RECEIVER_UPDATE_THUMB_UP_COUNT =
            "com.wrjxjh.intent.RECEIVER_UPDATE_THUMB_UP_COUNT";

    private TextView tvState;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvTodaySign;
    private Button btnSign;

    private SignContract.Presenter presenter;
    private BroadcastReceiver receiver;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        initView(view);
        presenter = new SignPresenter(this);
        initBroadcastReceiver();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initBroadcastReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }

    private void initBroadcastReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 参数参考CommentActivity的showThumbUpCountAndNotifySignPage方法
                int thumbUpCount = intent.getIntExtra("thumbUpCount", -1);
                int signId = intent.getIntExtra("signId", -1);
                SignListAdapter adapter = (SignListAdapter) rvTodaySign.getAdapter();
                if (adapter != null) {
                    adapter.updateThumbUpCount(signId, thumbUpCount);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        IntentFilter filter = new IntentFilter(RECEIVER_UPDATE_THUMB_UP_COUNT);
        getActivity().registerReceiver(receiver, filter);
    }

    private void initView(View view) {
        tvState = (TextView) view.findViewById(R.id.tv_state);
        btnSign = (Button) view.findViewById(R.id.btn_sign);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        rvTodaySign = (RecyclerView) view.findViewById(R.id.rv_sign);

        tvState.setText("正在获取签到状态...");
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign();
            }
        });
        swipeRefresh.setColorSchemeColors(Color.BLUE);
        swipeRefresh.measure(0, 0);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshSignList();
            }
        });
        rvTodaySign.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTodaySign.addItemDecoration(new RecyclerViewDivider(getActivity()));
    }

    private void sign() {
        DialogUtil.showInputDialog(getActivity(), "签到", "请输入签到内容", "",
                new DialogUtil.OnInputFinishListener() {
                    @Override
                    public void onInputFinish(String text) {
                        if (!TextUtil.isEmpty(text)) {
                            presenter.sign(text);
                        } else {
                            M.t(getActivity(), "不能为空");
                        }
                    }
                });
    }

    @Override
    public void showRefresher() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefresher() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showProgressIndicator(String hint) {
        DialogUtil.showProgressDialog(getActivity(), hint);
    }

    @Override
    public void dismissProgressIndicator() {
        DialogUtil.cancelProgressDialog();
    }

    @Override
    public void showToastMessage(String message) {
        M.t(getActivity(), message);
    }

    @Override
    public void showSignList(List<SignListResult> signListResultList) {
        rvTodaySign.setAdapter(new SignListAdapter(getActivity(), signListResultList));
    }

    @Override
    public void showTodaySignState(boolean isTodaySign) {
        tvState.setText(isTodaySign ? "你今天已经签到" : "你今天还未签到");
        tvState.setTextColor(isTodaySign ? Color.BLACK : Color.RED);
        btnSign.setClickable(!isTodaySign);
        btnSign.setBackgroundResource(isTodaySign ? R.color.light_gray : R.drawable.btn_blue_selector);
    }

}
