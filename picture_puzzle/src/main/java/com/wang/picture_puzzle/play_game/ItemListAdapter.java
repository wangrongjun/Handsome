package com.wang.picture_puzzle.play_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * by wangrongjun on 2017/3/30.
 */
public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private List<Bitmap> bitmapList;
    private int row;
    private int column;
    private int itemWidth;
    private int itemHeight;

    public ItemListAdapter(Context context, List<Bitmap> bitmapList,
                           int row, int column, int itemWidth, int itemHeight) {
        this.context = context;
        this.bitmapList = bitmapList;
        this.row = row;
        this.column = column;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    @Override
    public int getCount() {
        return bitmapList.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(itemWidth, itemHeight));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            setPadding(imageView, position, row, column);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(bitmapList.get(position));
        return imageView;
    }

    /**
     * 1.左上角Item：设置right,bottom
     * 2.右上角Item：设置bottom
     * 3.左下角Item：设置right
     * 4.右下角Item：不设置
     * 5.左边界Item（除首尾）：设置right,bottom
     * 6.上边界Item（除首尾）：设置right,bottom
     * 7.右边界Item（除首尾）：设置bottom
     * 8.下边界Item（除首尾）：设置right
     * 9.中间Item：设置right,bottom
     */
    private static void setPadding(ImageView imageView, int position, int row, int column) {
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
        int i = position / row;
        int j = position % column;

        if (i == 0 && j == 0) {//1
            right = bottom = 1;
        } else if (i == 0 && j == column - 1) {//2
            bottom = 1;
        } else if (i == 0 && j == column - 1) {//3
            right = 1;
        } else if (i == 0 && j == column - 1) {//4

        } else if (i == 0 && j == column - 1) {//5
            right = bottom = 1;
        } else if (i == 0 && j == column - 1) {//6
            right = bottom = 1;
        } else if (i == 0 && j == column - 1) {//7
            bottom = 1;
        } else if (i == 0 && j == column - 1) {//8
            right = 1;
        } else {//9
            right = bottom = 1;
        }

        imageView.setPadding(left, top, right, bottom);
    }

}
