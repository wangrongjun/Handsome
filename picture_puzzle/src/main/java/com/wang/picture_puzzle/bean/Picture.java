package com.wang.picture_puzzle.bean;

import java.io.Serializable;

/**
 * by wangrongjun on 2017/3/30.
 */
public class Picture implements Serializable {

    public enum Type {
        RES_ID,
        URL,
        FILE_PATH,
//        BITMAP
    }

    private Type type;
    private Object value;

    public Picture(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
