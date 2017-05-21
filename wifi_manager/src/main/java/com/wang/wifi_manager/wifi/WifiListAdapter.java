package com.wang.wifi_manager.wifi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wang.wifi_manager.R;
import com.wang.wifi_manager.bean.WifiInfo;

import java.util.List;

/**
 * by wangrongjun on 2017/2/28.
 */
public class WifiListAdapter extends BaseAdapter {

    private Context context;
    private List<WifiInfo> wifiInfoList;

    public WifiListAdapter(Context context, List<WifiInfo> wifiInfoList) {
        this.context = context;
        this.wifiInfoList = wifiInfoList;
    }

    @Override
    public int getCount() {
        return wifiInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return wifiInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_wifi, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        updateView(holder, position);
        return convertView;
    }

    private void updateView(ViewHolder holder, int position) {
        WifiInfo wifiInfo = wifiInfoList.get(position);
        holder.tvSsid.setText(wifiInfo.getSsid());
        holder.tvPsk.setText(wifiInfo.getPsk());
        holder.tvKeyMgmt.setText(wifiInfo.getKeyMgmt());
    }

    static class ViewHolder {
        TextView tvSsid;
        TextView tvPsk;
        TextView tvKeyMgmt;

        ViewHolder(View view) {
            tvSsid = (TextView) view.findViewById(R.id.tv_ssid);
            tvPsk = (TextView) view.findViewById(R.id.tv_psk);
            tvKeyMgmt = (TextView) view.findViewById(R.id.tv_key_mgmt);
        }
    }
}
