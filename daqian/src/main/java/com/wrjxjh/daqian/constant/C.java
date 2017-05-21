package com.wrjxjh.daqian.constant;

public class C {
    public static final String PREFNAME = "DaQian";

    // path=sd卡路径/<dirName>/
    public static String path;
    public static String dirName = "/daqian/";
    public static final String txtFileName = "DaQianLog.txt";
    public static final String headFileName = "head.jpg";

    public static final String MY_PROGRESS_DIALOG_TITLE = "提示";
    public static final String MY_PROGRESS_DIALOG_HINT = "正在加载";

    public static final int USER_MAN = 1;
    public static final int USER_WOMAN = 0;

    public static final int STATE_OK = 6;
    public static final int STATE_NO_INTERNET = 3;
    public static final int STATE_OVER_INTERNET = -3;
    public static final int STATE_USER_EXISTED = -4;
    public static final int STATE_USER_OR_PASS_ERROR = -5;
    public static final int STATE_UNKNOW_ERROR = -2;

    public static final String HINT_NO_INTERNET = "无网络连接，请检查您的手机网络";
    public static final String HINT_OVER_INTERNET = "网络超时";
    public static final String HINT_USER_EXISTED = "用户已存在";
    public static final String HINT_USER_OR_PASS_ERROR = "用户名或密码出错";
    public static final String HINT_UNKNOW_ERROR = "未知错误";

    public static final int CONNECTION_TIMEOUT = 5 * 1000;
    public static final int READ_TIMEOUT = 5 * 1000;

    public static final int FLAG_LOGIN = 10;
    public static final int FLAG_GET_USER_INFO_JSON = 11;
    public static final int FLAG_QUERY_TODAY_SIGNS = 12;
    public static final int FLAG_GET_USER_SIGN_HISTORY = 13;
    public static final int FLAG_UPDATE_PASSWORD = 14;
    public static final int FLAG_UPDATE_NICKNAME = 15;
    public static final int FLAG_UPDATE_SEX = 16;
    public static final int FLAG_QUERY_COMMENTS = 18;
    public static final int FLAG_SIGN = 19;
    public static final int FLAG_COMMENT = 20;
    public static final int FLAG_THUMBS_UP = 21;
    public static final int FLAG_REGISTER = 22;

    public static final String SERVER_IP = "http://192.168.199.206:8080/";

    // 1.登录验证
    public static final String getCanLoginURL(String username, String password) {
        return SERVER_IP + "XiaoQian/login?username=" + username + "&"
                + "password=" + password;
    }

    // 2.获取用户的个人信息：
    public static final String getUserInfoJsonURL(String username) {
        return SERVER_IP + "XiaoQian/getUserInfo?username=" + username;
    }

    // 7.获取当天的签到信息：
    public static final String getTodaySignerURL() {
        return SERVER_IP + "XiaoQian/getTodaySignInContent";
    }

    // 6.获取用户的签到历史：
    public static final String getUserSignHistoryURL(String username) {
        return SERVER_IP + "XiaoQian/getHistorySignInContent?username="
                + username;
    }

    // 8.获取某条签到的评论内容：
    public static final String getCommentByIdURL(int signId) {
        return SERVER_IP + "XiaoQian/getComment?signInID=" + signId;
    }

    // 3.更新用户密码：
    public static final String getUpdatePasswordURL(String username,
                                                    String password) {
        return SERVER_IP + "XiaoQian/updatePsd?username=" + username + "&"
                + "password=" + password;
    }

    // 4.更新用户昵称：
    public static final String getUpdateNicknameURL(String username,
                                                    String nickname) {
        return SERVER_IP + "XiaoQian/updateNickName?username=" + username + "&"
                + "nickName=" + nickname;
    }

    // 5.更新用户性别：
    public static final String getUpdateSexURL(String username, int sex) {
        return SERVER_IP + "XiaoQian/updateGender?username=" + username + "&"
                + "gender=" + sex;
    }

    // 9.用户签到：
    public static final String getSignURL() {
        return SERVER_IP + "XiaoQian/userSignIn";
    }

    // 10.用户进行评论：
    public static final String getCommentURL() {
        return SERVER_IP + "XiaoQian/userComment";
    }

    // 11.用户点赞：
    public static final String getThumbsUpURL(int signInId) {
        return SERVER_IP + "XiaoQian/userSetLike?signInID=" + signInId;
    }

    // 12.用户注册:
    public static final String getRegisterURL(String username, String password) {
        return SERVER_IP + "XiaoQian/register?username=" + username
                + "&password=" + password;
    }

}
