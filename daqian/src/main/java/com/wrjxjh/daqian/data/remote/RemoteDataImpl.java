package com.wrjxjh.daqian.data.remote;

import android.os.AsyncTask;

import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.bean.result.CommentListResult;
import com.wrjxjh.daqian.bean.result.SignListResult;
import com.wrjxjh.daqian.constant.SC;

import java.util.List;

/**
 * by wangrongjun on 2017/3/5.
 */
public class RemoteDataImpl implements IRemoteData {

    private IBaseData baseData = new BaseDataImpl();

    @Override
    public void login(final String username, final String password, final Callback<User> callback) {
        new AsyncTask<Void, Void, IBaseData.Result<User>>() {
            @Override
            protected IBaseData.Result<User> doInBackground(Void... params) {
                sleep();
                return baseData.login(username, password);
            }

            @Override
            protected void onPostExecute(IBaseData.Result<User> result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(result.entity);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void register(final User user, final Callback listener) {
        new AsyncTask<Void, Void, IBaseData.Result>() {
            @Override
            protected IBaseData.Result doInBackground(Void... params) {
                sleep();
                return baseData.register(user);
            }

            @Override
            protected void onPostExecute(IBaseData.Result result) {
                if (result.stateCode == SC.OK) {
                    listener.onSuccess(null);
                } else {
                    listener.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void updatePassword(final int userId, final String oldPassword, final String newPassword,
                               final Callback callback) {
        new AsyncTask<Void, Void, IBaseData.Result>() {
            @Override
            protected IBaseData.Result doInBackground(Void... params) {
                sleep();
                return baseData.updatePassword(userId, oldPassword, newPassword);
            }

            @Override
            protected void onPostExecute(IBaseData.Result result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(null);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void updateNickname(final int userId, final String nickname, final Callback callback) {
        new AsyncTask<Void, Void, IBaseData.Result>() {
            @Override
            protected IBaseData.Result doInBackground(Void... params) {
                sleep();
                return baseData.updateNickname(userId, nickname);
            }

            @Override
            protected void onPostExecute(IBaseData.Result result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(null);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void updateGender(final int userId, final int gender, final Callback callback) {
        new AsyncTask<Void, Void, IBaseData.Result>() {
            @Override
            protected IBaseData.Result doInBackground(Void... params) {
                sleep();
                return baseData.updateGender(userId, gender);
            }

            @Override
            protected void onPostExecute(IBaseData.Result result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(null);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void isTodaySigned(final int userId, final Callback<Boolean> callback) {
        new AsyncTask<Void, Void, IBaseData.Result<Boolean>>() {
            @Override
            protected IBaseData.Result<Boolean> doInBackground(Void... params) {
                sleep();
                return baseData.isTodaySigned(userId);
            }

            @Override
            protected void onPostExecute(IBaseData.Result<Boolean> result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(result.entity);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void getTodaySignedList(final Callback<List<SignListResult>> callback) {
        new AsyncTask<Void, Void, IBaseData.Result<List<SignListResult>>>() {
            @Override
            protected IBaseData.Result<List<SignListResult>> doInBackground(Void... params) {
                sleep();
                return baseData.getTodaySignedList();
            }

            @Override
            protected void onPostExecute(IBaseData.Result<List<SignListResult>> result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(result.entity);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void getSignedList(final int begin, final int length,
                              final Callback<List<SignListResult>> callback) {
        new AsyncTask<Void, Void, IBaseData.Result<List<SignListResult>>>() {
            @Override
            protected IBaseData.Result<List<SignListResult>> doInBackground(Void... params) {
                sleep();
                return baseData.getSignedList(begin, length);
            }

            @Override
            protected void onPostExecute(IBaseData.Result<List<SignListResult>> result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(result.entity);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void sign(final int userId, final String signText, final Callback callback) {
        new AsyncTask<Void, Void, IBaseData.Result>() {
            @Override
            protected IBaseData.Result doInBackground(Void... params) {
                sleep();
                return baseData.sign(userId, signText);
            }

            @Override
            protected void onPostExecute(IBaseData.Result result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(null);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void comment(final int userId, final int signId, final String commentText,
                        final Callback<Integer> callback) {
        new AsyncTask<Void, Void, IBaseData.Result<Integer>>() {
            @Override
            protected IBaseData.Result<Integer> doInBackground(Void... params) {
                sleep();
                return baseData.comment(userId, signId, commentText);
            }

            @Override
            protected void onPostExecute(IBaseData.Result<Integer> result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(null);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void getCommentList(final int signId, final Callback<List<CommentListResult>> callback) {
        new AsyncTask<Void, Void, IBaseData.Result<List<CommentListResult>>>() {
            @Override
            protected IBaseData.Result<List<CommentListResult>> doInBackground(Void... params) {
                sleep();
                return baseData.getCommentList(signId);
            }

            @Override
            protected void onPostExecute(IBaseData.Result<List<CommentListResult>> result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(result.entity);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    @Override
    public void thumbUp(final int userId, final int signId, final Callback<Integer> callback) {
        new AsyncTask<Void, Void, IBaseData.Result<Integer>>() {
            @Override
            protected IBaseData.Result<Integer> doInBackground(Void... params) {
                sleep();
                return baseData.thumbUp(userId, signId);
            }

            @Override
            protected void onPostExecute(IBaseData.Result<Integer> result) {
                if (result.stateCode == SC.OK) {
                    callback.onSuccess(result.entity);
                } else {
                    callback.onFail(result.stateCode);
                }
            }
        }.execute();
    }

    private void sleep() {
        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }

}
