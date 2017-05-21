package com.wrjxjh.daqian;

/**
 * by wangrongjun on 2017/3/8.
 */
public interface BaseView<T> {
    void showProgressIndicator(String hint);

    void dismissProgressIndicator();

    void showToastMessage(String message);
}
