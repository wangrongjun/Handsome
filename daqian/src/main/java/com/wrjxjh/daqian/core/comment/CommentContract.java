package com.wrjxjh.daqian.core.comment;

import com.wrjxjh.daqian.BasePresenter;
import com.wrjxjh.daqian.BaseView;
import com.wrjxjh.daqian.bean.result.CommentListResult;
import com.wrjxjh.daqian.bean.result.SignListResult;

import java.util.List;

/**
 * by wangrongjun on 2017/3/14.
 */
public class CommentContract {

    interface Presenter extends BasePresenter {
        void comment(String commentText);

        void thumbUp();

        void refreshList();
    }

    interface View extends BaseView<Presenter> {
        void showList(SignListResult signListResult,
                      List<CommentListResult> commentListResultList);

        SignListResult getSignListResult();

        void hideInputMethodAndClearEditText();

        void addLatestItem(CommentListResult commentListResult);

        void showRefresher();

        void dismissRefresher();

        void showInputMethod();

        void clickThumbUp();

        void showThumbUpCountAndNotifySignPage(int signId, int thumbUpCount);
    }
}
