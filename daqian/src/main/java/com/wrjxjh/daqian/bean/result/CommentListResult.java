package com.wrjxjh.daqian.bean.result;

import com.wrjxjh.daqian.bean.Comment;
import com.wrjxjh.daqian.bean.User;

/**
 * by wangrongjun on 2017/3/14.
 */
public class CommentListResult {

    private Comment comment;
    private User commenter;

    public CommentListResult() {
    }

    public CommentListResult(Comment comment, User commenter) {
        this.comment = comment;
        this.commenter = commenter;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }
}
