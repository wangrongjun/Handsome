package com.wang.long_picture_browser.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.wang.android_lib.util.M;
import com.wang.long_picture_browser.R;
import com.wang.long_picture_browser.view.LargeImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * by wangrongjun on 2017/4/12.
 */
public class LargeImageBrowserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_browser);


        String imagePath = getImagePath();
        System.out.println(imagePath + "");
        if (imagePath == null || !new File(imagePath).exists()) {
            M.t(this, "图片路径为空");
            return;
        }


        LargeImageView largeImageView = (LargeImageView) findViewById(R.id.large_image_view);
        try {
            largeImageView.setImage(imagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            M.t(this, e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getImagePath() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if ("file".equals(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

}
