package com.wang.picture_puzzle.play_game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wang.picture_puzzle.R;
import com.wang.picture_puzzle.bean.Picture;

/**
 * by wangrongjun on 2017/4/1.
 */
public class ShowPictureActivity extends Activity {

    private ImageView ivPicture;

    private Picture picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        initVariable();
        initView();
    }

    private void initVariable() {
        picture = (Picture) getIntent().getSerializableExtra("picture");
    }

    private void initView() {
        findViewById(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivPicture = (ImageView) findViewById(R.id.iv_picture);
        try {
            switch (picture.getType()) {
                case FILE_PATH:
                    String filePath = (String) picture.getValue();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    ivPicture.setImageBitmap(bitmap);
                    break;
                case RES_ID:
                    int redId = (int) picture.getValue();
                    ivPicture.setImageResource(redId);
                    break;
                case URL:
                    // TODO 使用图片缓存框架
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Context context, Picture picture) {
        Intent intent = new Intent(context, ShowPictureActivity.class);
        intent.putExtra("picture", picture);
        context.startActivity(intent);
    }
}
