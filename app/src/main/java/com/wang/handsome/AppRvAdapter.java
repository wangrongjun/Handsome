package com.wang.handsome;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * by wangrongjun on 2017/3/3.
 */
public class AppRvAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<AppBean> appBeanList;

    public AppRvAdapter(Context context, List<AppBean> appBeanList) {
        this.context = context;
        this.appBeanList = appBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AppBean appBean = appBeanList.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.ivAppIcon.setImageResource(appBean.getAppIconId());
        holder.tvAppName.setText(appBean.getAppName());
        holder.tvAppDescription.setText(appBean.getAppDescription());
        holder.btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppBean currAppBean = appBeanList.get(holder.getAdapterPosition());
                context.startActivity(new Intent(context, currAppBean.getStartActivityClass()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return appBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout btnStartActivity;
        ImageView ivAppIcon;
        TextView tvAppName;
        TextView tvAppDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            btnStartActivity = (LinearLayout) itemView.findViewById(R.id.btn_start_activity);
            ivAppIcon = (ImageView) itemView.findViewById(R.id.iv_app_icon);
            tvAppName = (TextView) itemView.findViewById(R.id.tv_app_name);
            tvAppDescription = (TextView) itemView.findViewById(R.id.tv_app_description);
        }
    }
}
