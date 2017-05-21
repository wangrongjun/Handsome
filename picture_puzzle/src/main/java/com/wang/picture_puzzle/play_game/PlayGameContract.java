package com.wang.picture_puzzle.play_game;

import com.wang.picture_puzzle.BaseView;

import java.util.List;

/**
 * by wangrongjun on 2017/3/30.
 */
public class PlayGameContract {

    interface Presenter {

        /**
         * 当View可以获取测量后的宽高的时候
         * 该方法需要在onWindowFocusChanged调用，实现显示Item图片的功能
         */
        void onMeasureEnable();

        void onClickItem(int position);

        void onClickShowTotalImage();

        void onClickChooseLevel();

        void onClickPlayAgain();

        void onClickPlayNew();
    }

    interface View extends BaseView {
        void restart(int newRow, int newColumn);

        void chooseRowAndColumn(OnRowAndColumnChooseListener listener);

        void showTime(String time);

        void showLevel(String level);

        int getRow();

        int getColumn();

        void showBitmapItemListOnPlayground(List<Integer> itemPositionList);

        void showTotalImage();

        interface OnRowAndColumnChooseListener {
            void onRowAndColumnChoose(int row, int column);
        }
    }

}
