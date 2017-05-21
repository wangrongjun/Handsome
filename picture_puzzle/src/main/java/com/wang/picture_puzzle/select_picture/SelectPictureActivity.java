package com.wang.picture_puzzle.select_picture;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.wang.picture_puzzle.R;
import com.wang.picture_puzzle.bean.Picture;
import com.wang.pull_recycler_view.view.PullBaseView;
import com.wang.pull_recycler_view.view.PullRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * by wangrongjun on 2017/3/29.
 */
public class SelectPictureActivity extends Activity {

    private PullRecyclerView rvPicture;

    private PictureListAdapter adapter;
    private List<Picture> pictureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        initData();
        initView();
    }

    private void initData() {
        pictureList = new ArrayList<>();
        Picture.Type type = Picture.Type.RES_ID;
        pictureList.add(new Picture(type, R.mipmap.picture_1));
        pictureList.add(new Picture(type, R.mipmap.picture_2));
        pictureList.add(new Picture(type, R.mipmap.picture_3));
        pictureList.add(new Picture(type, R.mipmap.picture_4));
        pictureList.add(new Picture(type, R.mipmap.picture_5));
        pictureList.add(new Picture(type, R.mipmap.picture_6));
        pictureList.add(new Picture(type, R.mipmap.picture_7));
    }

    private void initView() {
        rvPicture = (PullRecyclerView) findViewById(R.id.rv_picture);
        rvPicture.setOnHeaderRefreshListener(new PullBaseView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullBaseView pullBaseView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvPicture.onHeaderRefreshComplete();
                        Toast.makeText(SelectPictureActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
        rvPicture.setOnFooterRefreshListener(new PullBaseView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullBaseView pullBaseView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvPicture.onFooterRefreshComplete();
                        Toast.makeText(SelectPictureActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
        rvPicture.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new PictureListAdapter(this, pictureList);
        rvPicture.setAdapter(adapter);
    }

}
