package com.wrjxjh.daqian.core.comment;

import com.wang.java_util.DateUtil;
import com.wrjxjh.daqian.bean.Comment;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.bean.result.CommentListResult;
import com.wrjxjh.daqian.bean.result.SignListResult;
import com.wrjxjh.daqian.constant.SC;
import com.wrjxjh.daqian.data.local.ILocalData;
import com.wrjxjh.daqian.data.local.LocalDataImpl;
import com.wrjxjh.daqian.data.remote.IRemoteData;
import com.wrjxjh.daqian.data.remote.RemoteDataImpl;

import java.util.List;

/**
 * by wangrongjun on 2017/3/14.
 */
public class CommentPresenter implements CommentContract.Presenter {

    private IRemoteData remoteData;
    private ILocalData localData;
    private CommentContract.View commentView;
    private SignListResult signListResult;

    public CommentPresenter(CommentContract.View commentView) {
        this.commentView = commentView;
        signListResult = commentView.getSignListResult();
        remoteData = new RemoteDataImpl();
        localData = new LocalDataImpl();
        refreshList();
    }

    @Override
    public void refreshList() {
        commentView.showRefresher();
        int signId = signListResult.getSign().getSignId();
        remoteData.getCommentList(signId, new IRemoteData.Callback<List<CommentListResult>>() {
            @Override
            public void onSuccess(List<CommentListResult> commentListResultList) {
                commentView.dismissRefresher();
                for (CommentListResult result : commentListResultList) {// 避免空指针
                    if (result.getCommenter() == null) {
                        result.setCommenter(new User());
                    }
                    if (result.getComment() == null) {
                        result.setComment(new Comment());
                    }
                }
                commentView.showList(signListResult, commentListResultList);
            }

            @Override
            public void onFail(int errorCode) {
                commentView.dismissRefresher();
                commentView.showToastMessage(SC.getErrorMessage(errorCode));
            }
        });
    }

    @Override
    public void comment(final String commentText) {
        commentView.showProgressIndicator("正在加载");
        final User user = localData.getUser();
        final int signId = signListResult.getSign().getSignId();
        remoteData.comment(user.getUserId(), signId, commentText,
                new IRemoteData.Callback<Integer>() {
                    @Override
                    public void onSuccess(Integer entity) {
                        commentView.dismissProgressIndicator();
                        //不想再次联网刷新，所以直接构造一个新的item，commentTime会与服务器的迟几秒
                        Comment comment = new Comment(user.getUserId(), signId,
                                DateUtil.getCurrentDate(), DateUtil.getCurrentTime(), commentText);
                        commentView.hideInputMethodAndClearEditText();
                        commentView.addLatestItem(new CommentListResult(comment, user));
                    }

                    @Override
                    public void onFail(int errorCode) {
                        commentView.dismissProgressIndicator();
                        commentView.showToastMessage(SC.getErrorMessage(errorCode));
                    }
                });
    }

    @Override
    public void thumbUp() {
        commentView.showProgressIndicator("正在加载");
        int userId = localData.getUser().getUserId();
        final int signId = signListResult.getSign().getSignId();
        remoteData.thumbUp(userId, signId, new IRemoteData.Callback<Integer>() {
            @Override
            public void onSuccess(Integer thumbUpCount) {
                commentView.dismissProgressIndicator();
                commentView.showThumbUpCountAndNotifySignPage(signId, thumbUpCount);
            }

            @Override
            public void onFail(int errorCode) {
                commentView.dismissProgressIndicator();
                commentView.showToastMessage(SC.getErrorMessage(errorCode));
            }
        });
    }

}
