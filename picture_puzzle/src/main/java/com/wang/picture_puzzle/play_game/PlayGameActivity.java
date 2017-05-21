package com.wang.picture_puzzle.play_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.android_lib.util.DialogUtil;
import com.wang.android_lib.util.ImageViewUtil;
import com.wang.data_structure.Pair;
import com.wang.picture_puzzle.R;
import com.wang.picture_puzzle.bean.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * by wangrongjun on 2017/3/29.
 */
public class PlayGameActivity extends Activity implements AdapterView.OnItemClickListener, PlayGameContract.View, View.OnClickListener {

    private TextView tvTime;
    private TextView tvCurrentLevel;
    private TextView tvMaxLevel;
    private LinearLayout llPlaygroundBox;
    private GridView gvPlayground;

    private PlayGameContract.Presenter presenter;
    private int row;
    private int column;
    private Picture picture;
    private Bitmap bitmap;
    private List<Bitmap> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        initVariable();
        initView();
        initTotalBitmap();// 加载原图到bitmap
        initItemBitmap();// 对原图进行row*column的分隔，并保存到itemList
        presenter = new PlayGamePresenter(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        presenter.onMeasureEnable();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerItemBitmap();
    }

    private void recyclerItemBitmap() {
        for (Bitmap bitmap : itemList) {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        bitmap.recycle();
    }

    private void initView() {
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvCurrentLevel = (TextView) findViewById(R.id.tv_current_level);
        tvMaxLevel = (TextView) findViewById(R.id.tv_max_level);
        llPlaygroundBox = (LinearLayout) findViewById(R.id.ll_playground_box);
        gvPlayground = (GridView) findViewById(R.id.gv_playground);
        Button btnShowTotalImage = (Button) findViewById(R.id.btn_show_total_image);
        Button btnChooseLevel = (Button) findViewById(R.id.btn_choose_level);
        Button btnPlayAgain = (Button) findViewById(R.id.btn_play_again);
        Button btnPlayNew = (Button) findViewById(R.id.btn_play_new);

        btnShowTotalImage.setOnClickListener(this);
        btnChooseLevel.setOnClickListener(this);
        btnPlayAgain.setOnClickListener(this);
        btnPlayNew.setOnClickListener(this);

        gvPlayground.setNumColumns(column);
        gvPlayground.setOnItemClickListener(this);
    }

    private void initVariable() {
        row = getIntent().getIntExtra("row", 3);
        column = getIntent().getIntExtra("column", 3);
        picture = (Picture) getIntent().getSerializableExtra("picture");
    }

    private void initTotalBitmap() {
        try {
            switch (picture.getType()) {
                case FILE_PATH:
                    String filePath = (String) picture.getValue();
                    bitmap = BitmapFactory.decodeFile(filePath);
                    break;
                case RES_ID:
                    int redId = (int) picture.getValue();
                    bitmap = BitmapFactory.decodeResource(getResources(), redId);
                    break;
                case URL:
                    // TODO 使用图片缓存框架
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initItemBitmap() {
        itemList = new ArrayList<>();
        if (bitmap == null) {
            Toast.makeText(PlayGameActivity.this, "bitmap == null", Toast.LENGTH_SHORT).show();
            return;
        }
        int itemWidth = bitmap.getWidth() / column;
        int itemHeight = bitmap.getHeight() / row;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i == row - 1 && j == column - 1) {// 最后一块填入空白
                    itemList.add(null);
                } else {
                    int x = j * itemWidth;
                    int y = i * itemHeight;
                    Bitmap bitmap = Bitmap.createBitmap(this.bitmap, x, y, itemWidth, itemHeight);
                    itemList.add(bitmap);
                }
            }
        }
    }

    public static void start(Context context, int row, int column, Picture picture) {
        //先清除上次留下的排列数据
        PlayGamePresenter.controller = null;

        Intent intent = new Intent(context, PlayGameActivity.class);
        intent.putExtra("row", row);
        intent.putExtra("column", column);
        intent.putExtra("picture", picture);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.onClickItem(position);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_show_total_image) {
            presenter.onClickShowTotalImage();
        } else if (view.getId() == R.id.btn_choose_level) {
            presenter.onClickChooseLevel();
        } else if (view.getId() == R.id.btn_play_again) {
            presenter.onClickPlayAgain();
        } else if (view.getId() == R.id.btn_play_new) {
            DialogUtil.showConfirmDialog(this, "确认新局", "确实要开新一局吗？",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.onClickPlayNew();
                        }
                    });
        }
    }

    @Override
    public void restart(int row, int column) {
        finish();
        start(this, row, column, picture);
    }

    @Override
    public void chooseRowAndColumn(final OnRowAndColumnChooseListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择难度");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_number_picker, null);
        final NumberPicker npRow = (NumberPicker) view.findViewById(R.id.np_row);
        final NumberPicker npColumn = (NumberPicker) view.findViewById(R.id.np_column);
        npRow.setMinValue(3);
        npRow.setMaxValue(20);
        npColumn.setMinValue(3);
        npColumn.setMaxValue(20);
        builder.setView(view);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int row = npRow.getValue();
                int column = npColumn.getValue();
                listener.onRowAndColumnChoose(row, column);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    public void showTime(String time) {
        tvTime.setText(time);
    }

    @Override
    public void showLevel(String level) {
        tvCurrentLevel.setText(level);
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public void showBitmapItemListOnPlayground(List<Integer> itemPositionList) {
        // 根据bitmap的宽高比计算gvPlayground的宽高，宽高均不能超过父布局lPlaygroundBox的宽高
        int parentWidth = llPlaygroundBox.getWidth();
        int parentHeight = llPlaygroundBox.getHeight();
        double imageScale = 1.0 * bitmap.getWidth() / bitmap.getHeight();
        Pair<Integer, Integer> widthHeightPair =
                ImageViewUtil.fixCenter(parentWidth, parentHeight, imageScale);
        ViewGroup.LayoutParams params = gvPlayground.getLayoutParams();
        params.width = widthHeightPair.first;
        params.height = widthHeightPair.second;
        gvPlayground.setLayoutParams(params);

        // 计算每个Item(小图片)的宽高
        int itemWidth = widthHeightPair.first / column;
        int itemHeight = widthHeightPair.second / row;

        // 根据itemPositionList数字序列创建新的itemList来显示
        List<Bitmap> itemList = new ArrayList<>();
        for (Integer position : itemPositionList) {
            if (position == null) {
                itemList.add(null);
            } else {
                itemList.add(this.itemList.get(position));
            }
        }

        // 创建adapter
        ItemListAdapter adapter = new ItemListAdapter(this, itemList, row, column, itemWidth, itemHeight);
        gvPlayground.setAdapter(adapter);
    }

    @Override
    public void showTotalImage() {
        ShowPictureActivity.start(this, picture);
    }

    @Override
    public void showProgressIndicator(String hint) {
        DialogUtil.showProgressDialog(this, hint);
    }

    @Override
    public void dismissProgressIndicator() {
        DialogUtil.cancelProgressDialog();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(PlayGameActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
