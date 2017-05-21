package com.wrjxjh.daqian.data.remote;

import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.bean.result.CommentListResult;
import com.wrjxjh.daqian.bean.result.SignListResult;

import java.util.List;

/**
 * by wangrongjun on 2017/3/5.
 * 保持在远程的数据，通过接口回调来访问
 */
public interface IRemoteData {

    interface Callback<T> {
        void onSuccess(T entity);

        void onFail(int errorCode);
    }

    // 登录，返回用户信息
    void login(String username, String password, Callback<User> callback);

    // 注册
    void register(User user, Callback listener);

    // 修改密码
    void updatePassword(int userId, String oldPassword, String newPassword, Callback callback);

    // 修改昵称
    void updateNickname(int userId, String nickname, Callback callback);

    // 修改性别
    void updateGender(int userId, int sex, Callback callback);

    // 查询用户今天是否已签到，返回true或false
    void isTodaySigned(int userId, Callback<Boolean> callback);

    // 获取当天签到列表（按时间倒序排序）
    void getTodaySignedList(Callback<List<SignListResult>> callback);

    // 获取所有签到列表（按时间倒序排序），begin和length用于分页。
    // 设数据库有10条数据，若begin>=10，则返回空数组
    void getSignedList(int begin, int length, Callback<List<SignListResult>> callback);

    // 用户签到
    void sign(int userId, String signText, Callback callback);

    // 用户对某一条签到进行评论，返回评论数目
    void comment(int userId, int signId, String commentText, Callback<Integer> callback);

    //获取某条签到的所有评论
    void getCommentList(int signId, Callback<List<CommentListResult>> callback);

    // 某人对某条签到点赞，返回点赞次数
    void thumbUp(int userId, int signId, Callback<Integer> callback);
}
