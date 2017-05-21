package com.wang.wifi_manager.exception;

/**
 * by wangrongjun on 2017/2/28.
 */
public class NotRootException extends Exception {

    public NotRootException() {
        super("手机未获取root权限，无法操作");
    }

}
