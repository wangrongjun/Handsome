package com.wrjxjh.daqian.data.remote;

import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.bean.result.CommentListResult;
import com.wrjxjh.daqian.bean.result.SignListResult;

import java.util.List;

/**
 * by wangrongjun on 2017/3/7.
 * 保持在远程的数据，底层的具体实现
 */
public interface IBaseData {

    Result<User> login(String username, String password);

    Result register(User user);

    Result<Boolean> isTodaySigned(int userId);

    Result<List<SignListResult>> getTodaySignedList();

    Result<List<SignListResult>> getSignedList(int begin, int length);

    Result sign(int userId, String signText);

    Result<Integer> comment(int userId, int signId, String commentText);

    Result<List<CommentListResult>> getCommentList(int signId);

    Result<Integer> thumbUp(int userId, int signId);

    Result updatePassword(int userId, String oldPassword, String newPassword);

    Result updateNickname(int userId, String nickname);

    Result updateGender(int userId, int gender);

    class Result<T> {
        public int stateCode;
        public T entity;

        public Result(int stateCode, T entity) {
            this.stateCode = stateCode;
            this.entity = entity;
        }

        public Result(int stateCode) {
            this.stateCode = stateCode;
        }
    }

}
