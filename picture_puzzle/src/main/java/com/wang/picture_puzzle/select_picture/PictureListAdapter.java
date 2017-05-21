package com.wang.picture_puzzle.select_picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wang.picture_puzzle.R;
import com.wang.picture_puzzle.bean.Picture;
import com.wang.picture_puzzle.play_game.PlayGameActivity;

import java.util.List;

/**
 * by wangrongjun on 2017/3/29.
 */
public class PictureListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Picture> pictureList;

    public PictureListAdapter(Context context, List<Picture> pictureList) {
        this.context = context;
        this.pictureList = pictureList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_picture_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Picture picture = pictureList.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;

        holder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayGameActivity.start(context, 3, 3, picture);
            }
        });

        try {
            switch (picture.getType()) {
                case FILE_PATH:
                    String filePath = (String) picture.getValue();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    holder.ivPicture.setImageBitmap(bitmap);
                    break;
                case RES_ID:
                    int redId = (int) picture.getValue();
                    holder.ivPicture.setImageResource(redId);
                    break;
                case URL:
                    // TODO 使用图片缓存框架
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPicture = (ImageView) itemView.findViewById(R.id.iv_picture);
        }
    }

}
