package com.wrjxjh.daqian.core.sign_history;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.wang.android_lib.util.DialogUtil;
import com.wang.pull_recycler_view.view.PullBaseView;
import com.wang.pull_recycler_view.view.PullRecyclerView;
import com.wrjxjh.daqian.BaseActivity;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.result.SignListResult;

import java.util.List;

/**
 * by wangrongjun on 2017/3/5.
 */
public class SignHistoryActivity extends BaseActivity implements SignHistoryContract.View, PullBaseView.OnFooterRefreshListener {

    private PullRecyclerView rvSign;
    private SwipeRefreshLayout swipeRefresh;

    private SignHistoryListAdapter adapter;
    private SignHistoryContract.Presenter presenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sign_history);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(Color.BLUE);
        swipeRefresh.measure(0, 0);

        rvSign = (PullRecyclerView) findViewById(R.id.rv_sign);
//        rvSign.setOnHeaderRefreshListener(this);//设置下拉监听
        rvSign.setOnFooterRefreshListener(this);//设置上拉监听
        rvSign.setCanScrollAtRereshing(false);//设置正在刷新时是否可以滑动，默认不可滑动
        rvSign.setCanPullDown(false);//设置是否可下拉
        rvSign.setCanPullUp(true);//设置是否可上拉
        rvSign.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new SignHistoryPresenter(this);
    }

    @Override
    public void showRefresh() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void dismissRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void dismissFooterRefresh() {
        rvSign.onFooterRefreshComplete();
    }

    @Override
    public void showSignList(List<SignListResult> signListResultList) {
        adapter = new SignHistoryListAdapter(this, signListResultList);
        rvSign.setAdapter(adapter);
    }

    @Override
    public void addSignListAndShow(List<SignListResult> signListResultList) {
        if (adapter == null) {
            adapter = new SignHistoryListAdapter(this, signListResultList);
            rvSign.setAdapter(adapter);
        } else {
            adapter.addData(signListResultList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgressIndicator(String hint) {
        DialogUtil.showProgressDialog(this, hint);
    }

    @Override
    public void dismissProgressIndicator() {
        DialogUtil.cancelProgressDialog();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(SignHistoryActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFooterRefresh(PullBaseView pullBaseView) {
        presenter.loadMore();
    }
}
