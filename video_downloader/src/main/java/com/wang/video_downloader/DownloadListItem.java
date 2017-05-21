package com.wang.video_downloader;

/**
 * by 王荣俊 on 2016/9/26.
 */
public class DownloadListItem {

    public static final int STATE_DOWNLOADING = 1;
    public static final int STATE_WAITING = 2;
    public static final int STATE_PAUSE = 3;
    public static final int STATE_FINISH = 4;
    public static final int STATE_FAILED = 5;
    public static final int STATE_READING = 6;//刚打开下载列表界面时的读取状态

    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_TXT = 1;
    public static final int TYPE_MP4 = 2;
    public static final int TYPE_APK = 3;
    public static final int TYPE_ZIP = 4;

    private String fileName;
    private String url;//不显示在界面，但任务的操作（开始，暂停，删除）需要用到
    private int type;
    private int currentSize;//单位KB
    private int totalSize;//单位KB
    private int remainTime;//单位秒
    private int speed;//单位KB/s
    private int state;

    /**
     * @param fileNameSuffix 文件名后缀
     */
    public static int toType(String fileNameSuffix) {
        switch (fileNameSuffix) {
            case "txt":
                return TYPE_TXT;
            case "mp4":
                return TYPE_MP4;
            case "apk":
                return TYPE_APK;
            case "zip":
                return TYPE_ZIP;
            default:
                return TYPE_UNKNOWN;
        }
    }

    public DownloadListItem(String fileName, String url, int type, int currentSize, int totalSize, int remainTime, int speed, int state) {
        this.fileName = fileName;
        this.url = url;
        this.type = type;
        this.currentSize = currentSize;
        this.totalSize = totalSize;
        this.remainTime = remainTime;
        this.speed = speed;
        this.state = state;
    }

    public DownloadListItem(String fileName, String url, int type, int state) {
        this.fileName = fileName;
        this.url = url;
        this.type = type;
        this.state = state;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }
}
