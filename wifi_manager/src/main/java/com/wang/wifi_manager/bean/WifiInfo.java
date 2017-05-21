package com.wang.wifi_manager.bean;

/**
 * by wangrongjun on 2017/2/28.
 */
public class WifiInfo {

    private String ssid;//wifi名称
    private String psk;//wifi密码
    private int priority;//顺序
    private String keyMgmt;//加密方式，比如WPA-PSK

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getKeyMgmt() {
        return keyMgmt;
    }

    public void setKeyMgmt(String keyMgmt) {
        this.keyMgmt = keyMgmt;
    }
}
