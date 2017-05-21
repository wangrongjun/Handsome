package com.wrjxjh.daqian.constant;

/**
 * by wangrongjun on 2017/3/5.
 */
public class SC {

    //成功
    public static final int OK = 0;

    //以下是程序运行错误状态

    //网络连接失败
    public static final int ERROR_INTERNET = -1;
    //APP异常
    public static final int ERROR_APP_UNKNOWN = -2;
    //服务器异常
    public static final int ERROR_SERVER_UNKNOWN = -3;
    //用户id不存在
    public static final int ERROR_USER_ID_NOT_EXISTS = 2;
    //签到id不存在
    public static final int ERROR_SIGN_ID_NOT_EXISTS = 3;
    //评论id不存在
    public static final int ERROR_COMMENT_ID_NOT_EXISTS = 4;

    //以下是业务逻辑错误状态

    //注册错误
    public static final int ERROR_REGISTER_USER_EXISTS = 1;

    //登录错误
    public static final int ERROR_LOGIN_USERNAME_NOT_EXISTS = 11;
    public static final int ERROR_LOGIN_PASSWORD = 12;

    //旧密码输入错误，无权更改密码
    public static final int ERROR_UPDATE_USER_PASSWORD_PERMISSION = 13;

    //今天已经签到
    public static final int ERROR_ALREADY_SIGNED = 14;

    /**
     * 这个方法最好只在程序异常时（把当前业务逻辑的错误码判断完都不符合）才调用，
     * 如果是业务逻辑的错误，最好把提示信息直接写到相应的Presenter或View中，而不是这里。
     */
    public static String getErrorMessage(int stateCode) {
        switch (stateCode) {
            case ERROR_INTERNET:
                return "网路连接失败";
            case ERROR_APP_UNKNOWN:
                return "程序出错";
            case ERROR_SERVER_UNKNOWN:
                return "服务器异常";
            case ERROR_USER_ID_NOT_EXISTS:
                return "程序异常，用户id不存在";
            case ERROR_SIGN_ID_NOT_EXISTS:
                return "程序异常，签到id不存在";
            case ERROR_COMMENT_ID_NOT_EXISTS:
                return "程序异常，评论id不存在";
            case ERROR_REGISTER_USER_EXISTS:
                return "注册失败，用户名已存在";
            case ERROR_UPDATE_USER_PASSWORD_PERMISSION:
                return "原密码输入错误，修改失败";
            default:
                return "未知的状态码：" + stateCode;
        }
    }

}
