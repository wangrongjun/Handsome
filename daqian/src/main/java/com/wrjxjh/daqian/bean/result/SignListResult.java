package com.wrjxjh.daqian.bean.result;

import com.wrjxjh.daqian.bean.Sign;
import com.wrjxjh.daqian.bean.User;

/**
 * by wangrongjun on 2017/3/5.
 */
public class SignListResult {

    private Sign sign;
    private User signer;
    private int thumbUpCount;
    private int commentCount;

    public SignListResult() {
    }

    public SignListResult(Sign sign, User signer, int thumbUpCount, int commentCount) {
        this.sign = sign;
        this.signer = signer;
        this.thumbUpCount = thumbUpCount;
        this.commentCount = commentCount;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public User getSigner() {
        return signer;
    }

    public void setSigner(User signer) {
        this.signer = signer;
    }

    public int getThumbUpCount() {
        return thumbUpCount;
    }

    public void setThumbUpCount(int thumbUpCount) {
        this.thumbUpCount = thumbUpCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
