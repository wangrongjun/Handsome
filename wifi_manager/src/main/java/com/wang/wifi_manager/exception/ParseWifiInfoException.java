package com.wang.wifi_manager.exception;

/**
 * by wangrongjun on 2017/2/28.
 */
public class ParseWifiInfoException extends Exception {

    public ParseWifiInfoException(String wifiInfoText) {
        super("包含wifi信息的文本解析失败，文本如下：\n" + wifiInfoText);
    }

}
