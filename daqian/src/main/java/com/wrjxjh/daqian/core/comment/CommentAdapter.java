package com.wrjxjh.daqian.core.comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.result.CommentListResult;
import com.wrjxjh.daqian.bean.result.SignListResult;

import java.util.List;

/**
 * by wangrongjun on 2017/3/14.
 */
public class CommentAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;

    private CommentContract.View commentView;
    private Context context;
    private SignListResult signListResult;
    private List<CommentListResult> commentListResultList;

    public CommentAdapter(CommentContract.View commentView, Context context,
                          SignListResult signListResult,
                          List<CommentListResult> commentListResultList) {
        this.commentView = commentView;
        this.context = context;
        this.signListResult = signListResult;
        this.commentListResultList = commentListResultList;
    }

    public void setThumbUpCount(int thumbUpCount) {
        signListResult.setThumbUpCount(thumbUpCount);
    }

    public void addItemToTop(CommentListResult commentListResult) {
        commentListResultList.add(0, commentListResult);
        notifyItemInserted(1);
        notifyDataSetChanged();//为了显示出新的commentCount
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEAD : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return 1 + commentListResultList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_commend_head, parent,
                    false);
            return new HeadViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.rv_comment, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position == 0) {
            final HeadViewHolder holder = (HeadViewHolder) viewHolder;
            holder.tvUsername.setText(signListResult.getSigner().getUsername());
            holder.tvSignText.setText(signListResult.getSign().getSignText());
            String s = signListResult.getSign().getSignDate() + "   " +
                    signListResult.getSign().getSignTime();
            holder.tvDateTime.setText(s);
            holder.tvThumbUpCount.setText(signListResult.getThumbUpCount() + "");
            // TODO delete
//            holder.tvCommentCount.setText(signListResult.getCommentCount() + "");
            holder.tvCommentCount.setText(commentListResultList.size() + "");
            holder.btnThumbUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentView.clickThumbUp();
                }
            });
            holder.btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentView.showInputMethod();
                }
            });

        } else {
            //对于item，position从1开始。0是head
            CommentListResult result = commentListResultList.get(position - 1);
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            holder.tvName.setText(result.getCommenter().getUsername());
            holder.tvComment.setText(result.getComment().getCommentText());
            holder.tvDate.setText(result.getComment().getCommentDate());
            holder.tvTime.setText(result.getComment().getCommentTime());
        }
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        TextView tvDateTime;
        TextView tvSignText;
        LinearLayout btnThumbUp;
        TextView tvThumbUpCount;
        LinearLayout btnComment;
        TextView tvCommentCount;

        HeadViewHolder(View view) {
            super(view);
            tvUsername = (TextView) view.findViewById(R.id.tv_username);
            tvDateTime = (TextView) view.findViewById(R.id.tv_date_time);
            tvSignText = (TextView) view.findViewById(R.id.tv_sign_text);
            btnThumbUp = (LinearLayout) view.findViewById(R.id.btn_thumb_up);
            tvThumbUpCount = (TextView) view.findViewById(R.id.tv_thumb_up_count);
            btnComment = (LinearLayout) view.findViewById(R.id.btn_comment);
            tvCommentCount = (TextView) view.findViewById(R.id.tv_comment_count);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHead;
        TextView tvName;
        TextView tvDate;
        TextView tvTime;
        TextView tvComment;

        ItemViewHolder(View view) {
            super(view);
            ivHead = (ImageView) view.findViewById(R.id.iv_head);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvComment = (TextView) view.findViewById(R.id.tv_comment);
        }
    }
}
