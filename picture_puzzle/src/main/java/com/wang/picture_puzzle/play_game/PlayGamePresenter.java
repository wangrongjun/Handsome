package com.wang.picture_puzzle.play_game;

/**
 * by wangrongjun on 2017/3/30.
 */
public class PlayGamePresenter implements PlayGameContract.Presenter {

    private PlayGameContract.View view;
    public static PictureController controller;

    public PlayGamePresenter(PlayGameContract.View view) {
        this.view = view;
        if (controller == null) {
            controller = new PictureController(view.getRow(), view.getColumn());
            controller.random();
        }
    }

    public void setView(PlayGameContract.View view) {
        this.view = view;
    }

    @Override
    public void onMeasureEnable() {
        view.showTime("00:00");
        view.showLevel(view.getRow() + " X " + view.getColumn());
        view.showBitmapItemListOnPlayground(controller.getItemList());
    }

    @Override
    public void onClickItem(int position) {
        boolean success = controller.move(position);
        if (success) {
            view.showBitmapItemListOnPlayground(controller.getItemList());
        } else {
            view.showToastMessage("不能移动");
        }
    }

    @Override
    public void onClickShowTotalImage() {
        view.showTotalImage();
    }

    @Override
    public void onClickChooseLevel() {
        view.chooseRowAndColumn(new PlayGameContract.View.OnRowAndColumnChooseListener() {
            @Override
            public void onRowAndColumnChoose(int row, int column) {
                view.restart(row, column);
            }
        });
    }

    @Override
    public void onClickPlayAgain() {

    }

    @Override
    public void onClickPlayNew() {
        controller.random();
        view.showBitmapItemListOnPlayground(controller.getItemList());
    }

}