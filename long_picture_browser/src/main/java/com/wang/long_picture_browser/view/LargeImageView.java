package com.wang.long_picture_browser.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import com.wang.long_picture_browser.example.multiply_touch.MoveGestureDetector;

import java.io.IOException;

/**
 * by wangrongjun on 2017/5/15.
 */
public class LargeImageView extends View {

    private BitmapRegionDecoder decoder;
    /**
     * 图片的宽度和高度
     */
    private int imageWidth, imageHeight;
    /**
     * 绘制的区域
     */
    private volatile Rect rect = new Rect();

    private MoveGestureDetector detector;

    public LargeImageView(Context context) {
        super(context);
        initMoveGestureDetector();
    }

    private void initMoveGestureDetector() {
        detector = new MoveGestureDetector(getContext(), new MoveGestureDetector.SimpleOnMoveGestureListener() {
            @Override
            public boolean onMove(MoveGestureDetector detector) {
                return true;
            }
        });
    }

    public void setImage(String imagePath) throws IOException {
        decoder = BitmapRegionDecoder.newInstance(imagePath, false);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        imageWidth = options.outWidth;
        imageHeight = options.outHeight;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (decoder != null) {
            Bitmap bitmap = decoder.decodeRegion(rect, null);
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}