package com.wrjxjh.daqian.core.comment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wang.android_lib.util.DialogUtil;
import com.wang.android_lib.util.M;
import com.wang.android_lib.view.helper.RecyclerViewDivider;
import com.wang.java_util.GsonUtil;
import com.wrjxjh.daqian.BaseActivity;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.Sign;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.bean.result.CommentListResult;
import com.wrjxjh.daqian.bean.result.SignListResult;
import com.wrjxjh.daqian.core.sign.SignFragment;

import java.util.List;

/**
 * by wangrongjun on 2017/3/14.
 */
public class CommentActivity extends BaseActivity implements CommentContract.View {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvSign;
    private EditText etComment;

    private CommentAdapter adapter;
    private CommentContract.Presenter presenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_comment);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        rvSign = (RecyclerView) findViewById(R.id.rv_sign);
        etComment = (EditText) findViewById(R.id.et_comment);

        swipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED);
        swipeRefresh.measure(0, 0);
        rvSign.setLayoutManager(new LinearLayoutManager(this));
        rvSign.addItemDecoration(new RecyclerViewDivider(this));
    }

    @Override
    protected void initListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshList();
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new CommentPresenter(this);
    }

    public static void start(Context context, SignListResult result) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("SignListResult", GsonUtil.toJson(result));
        context.startActivity(intent);
    }

    public void onClickComment(View view) {
        String s = etComment.getText().toString().trim();
        if (s.length() > 0) {
            presenter.comment(s);
        } else {
            showToastMessage("请输入内容");
        }
    }

    @Override
    public void showList(SignListResult signListResult, List<CommentListResult> commentListResultList) {
        adapter = new CommentAdapter(this, this, signListResult, commentListResultList);
        rvSign.setAdapter(adapter);
    }

    @Override
    public SignListResult getSignListResult() {
        try {
            String json = getIntent().getStringExtra("SignListResult");
            return GsonUtil.fromJson(json, SignListResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new SignListResult(new Sign(), new User(), -1, -1);
        }
    }

    @Override
    public void hideInputMethodAndClearEditText() {
        etComment.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void addLatestItem(CommentListResult commentListResult) {
        if (adapter != null) {
            rvSign.scrollToPosition(0);
            adapter.addItemToTop(commentListResult);
        }
    }

    @Override
    public void showRefresher() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void dismissRefresher() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showInputMethod() {
        etComment.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(etComment, 0);
        }
    }

    @Override
    public void clickThumbUp() {
        presenter.thumbUp();
    }

    @Override
    public void showThumbUpCountAndNotifySignPage(int signId, int thumbUpCount) {
        adapter.setThumbUpCount(thumbUpCount);
        adapter.notifyDataSetChanged();

        Intent intent = new Intent(SignFragment.RECEIVER_UPDATE_THUMB_UP_COUNT);
        intent.putExtra("thumbUpCount", thumbUpCount);
        intent.putExtra("signId", signId);
        sendBroadcast(intent);
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
        M.t(this, message);
    }
}