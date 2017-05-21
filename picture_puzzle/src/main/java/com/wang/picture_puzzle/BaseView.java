package com.wang.picture_puzzle;

/**
 * by wangrongjun on 2017/3/8.
 */
public interface BaseView {
    void showProgressIndicator(String hint);

    void dismissProgressIndicator();

    void showToastMessage(String message);
}
