package com.wang.video_downloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.java_util.TextUtil;

import java.util.List;

/**
 * by 王荣俊 on 2016/9/26.
 */
public class DownloadAdapter extends BaseAdapter {

    private Context context;
    private List<DownloadListItem> items;

    public DownloadAdapter(Context context, List<DownloadListItem> items) {
        this.context = context;
        this.items = items;
    }

    public List<DownloadListItem> getDownloadListItems() {
        return items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_download, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        updateView(holder, position);
        return convertView;
    }

    private void updateView(ViewHolder holder, int position) {
        DownloadListItem item = items.get(position);

        holder.tvFileName.setText(item.getFileName());

        switch (item.getType()) {
            case DownloadListItem.TYPE_UNKNOWN:
                holder.ivFileIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case DownloadListItem.TYPE_MP4:
                holder.ivFileIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case DownloadListItem.TYPE_TXT:
                holder.ivFileIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case DownloadListItem.TYPE_APK:
                holder.ivFileIcon.setImageResource(R.mipmap.ic_launcher);
                break;
        }

        switch (item.getState()) {
            case DownloadListItem.STATE_DOWNLOADING:
                holder.tvCurrentSize.setText(toSizeString(item.getCurrentSize()));
                holder.tvTotalSize.setText("/" + toSizeString(item.getTotalSize()));
                holder.tvRemainTime.setText("剩余" + item.getRemainTime() + "s");
                holder.tvSpeedOrState.setText(toSizeString(item.getSpeed()) + "/s");
                break;
            case DownloadListItem.STATE_WAITING:
                holder.tvCurrentSize.setText(toSizeString(item.getCurrentSize()));
                holder.tvTotalSize.setText("/" + toSizeString(item.getTotalSize()));
                holder.tvRemainTime.setText("");
                holder.tvSpeedOrState.setText("等待中");
                break;
            case DownloadListItem.STATE_PAUSE:
                holder.tvCurrentSize.setText(toSizeString(item.getCurrentSize()));
                holder.tvTotalSize.setText("/" + toSizeString(item.getTotalSize()));
                holder.tvRemainTime.setText("");
                holder.tvSpeedOrState.setText("暂停");
                break;
            case DownloadListItem.STATE_FINISH:
                holder.tvCurrentSize.setText("");
                holder.tvTotalSize.setText(toSizeString(item.getTotalSize()));
                holder.tvRemainTime.setText("");
                holder.tvSpeedOrState.setText("下载成功");
                break;
            case DownloadListItem.STATE_FAILED:
                holder.tvCurrentSize.setText(toSizeString(item.getCurrentSize()));
                holder.tvTotalSize.setText("/" + toSizeString(item.getTotalSize()));
                holder.tvRemainTime.setText("");
                holder.tvSpeedOrState.setText("下载失败");
                break;
            case DownloadListItem.STATE_READING:
                holder.tvCurrentSize.setText(toSizeString(item.getCurrentSize()));
                holder.tvTotalSize.setText(toSizeString(item.getTotalSize()));
                holder.tvRemainTime.setText("");
                holder.tvSpeedOrState.setText("读取中");
                break;
        }
    }

    static class ViewHolder {
        ImageView ivFileIcon;
        TextView tvFileName;
        TextView tvCurrentSize;
        TextView tvTotalSize;
        TextView tvRemainTime;
        TextView tvSpeedOrState;

        ViewHolder(View view) {
            ivFileIcon = (ImageView) view.findViewById(R.id.iv_file_icon);
            tvFileName = (TextView) view.findViewById(R.id.tv_file_name);
            tvCurrentSize = (TextView) view.findViewById(R.id.tv_current_size);
            tvTotalSize = (TextView) view.findViewById(R.id.tv_total_size);
            tvRemainTime = (TextView) view.findViewById(R.id.tv_remain_time);
            tvSpeedOrState = (TextView) view.findViewById(R.id.tv_speed_or_state);
        }
    }

    private static String toSizeString(int kb) {
        return TextUtil.transferFileLength(kb, 2);
    }

}
