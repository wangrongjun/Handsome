package com.wrjxjh.daqian.core.sign_history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.Sign;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.bean.result.SignListResult;
import com.wrjxjh.daqian.core.comment.CommentActivity;

import java.util.List;

/**
 * by wangrongjun on 2017/3/5.
 */
public class SignHistoryListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SignListResult> signListResultList;

    public SignHistoryListAdapter(Context context, List<SignListResult> signListResultList) {
        this.context = context;
        this.signListResultList = signListResultList;
    }

    public void addData(List<SignListResult> signListResultList) {
        for (SignListResult result : signListResultList) {
            this.signListResultList.add(result);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_sign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        SignListResult result = signListResultList.get(position);
        Sign sign = result.getSign();
        User signer = result.getSigner();
        if (sign == null) {
            sign = new Sign();
        }
        if (signer == null) {
            signer = new User();
        }

        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignListResult currResult = signListResultList.get(holder.getAdapterPosition());
                CommentActivity.start(context, currResult);
            }
        });
        holder.tvSignerName.setText(signer.getUsername());
        holder.tvSignText.setText(sign.getSignText());
        holder.tvSignTime.setText(sign.getSignDate() + "   " + sign.getSignTime());
        holder.tvThumbUpCount.setText(result.getThumbUpCount() + "");
        holder.tvCommentCount.setText(result.getCommentCount() + "");
    }

    @Override
    public int getItemCount() {
        return signListResultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout btnSign;
        ImageView ivSignerHead;
        TextView tvSignerName;
        TextView tvThumbUpCount;
        TextView tvCommentCount;
        TextView tvSignText;
        TextView tvSignTime;

        public ViewHolder(View view) {
            super(view);
            btnSign = (LinearLayout) view.findViewById(R.id.btn_sign);
            ivSignerHead = (ImageView) view.findViewById(R.id.iv_signer_head);
            tvSignerName = (TextView) view.findViewById(R.id.tv_signer_name);
            tvThumbUpCount = (TextView) view.findViewById(R.id.tv_thumb_up_count);
            tvCommentCount = (TextView) view.findViewById(R.id.tv_comment_count);
            tvSignText = (TextView) view.findViewById(R.id.tv_sign_text);
            tvSignTime = (TextView) view.findViewById(R.id.tv_sign_time);
        }
    }
}
