package com.wang.picture_puzzle.play_game;

import com.wang.java_util.MathUtil;

import java.util.List;

/**
 * by wangrongjun on 2017/3/30.
 */
public class PictureController {

    private int row;
    private int column;
    private List<Integer> itemList;

    public PictureController(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * 随机打乱序列
     */
    public void random() {
        int lastPosition = row * column - 1;
        while (true) {
            // 随机获取0-14中15个不重复随机数
            List<Integer> list = MathUtil.randomList(0, lastPosition - 1, lastPosition);
            list.add(null);// 最后填空格
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i) + "");
            }
            System.out.println("------------------------------------------------");
            if (nPuzzle(column, list)) {
                itemList = list;
                break;
            }
        }
    }

    /**
     * N Puzzle算法判断该序列的拼图是否有解，详见Android群英传第315页
     */
    private static boolean nPuzzle(int column, List<Integer> integerList) {
        int TSum = 0;
        for (int i = 0; i < integerList.size() - 1; i++) {
            Integer n = integerList.get(i);
            if (n != null) {
                int smallerCount = 0;
                for (int j = i + 1; j < integerList.size(); j++) {
                    Integer compareN = integerList.get(j);
                    if (compareN != null && n > compareN) {
                        smallerCount++;
                    }
                }
                TSum += smallerCount;
            }
        }

        if (column % 2 == 1) {// 如果列数为奇数，则TSum必须为偶数才有解
            return TSum % 2 == 0;
        } else {// 如果列数为偶数
            // 如果空格所在的行从下到上为奇数行（从1数起），则TSum必须为偶数才有解；
            // 如果空格所在的行从下到上为偶数行（从1数起），则TSum必须为奇数才有解；
            int blankPosition = 0;
            for (int i = 0; i < integerList.size(); i++) {
                if (integerList.get(i) == null) {
                    blankPosition = i;
                }
            }
            int row = integerList.size() / column;
            int blankReverseRow = row - blankPosition / column;// 从1数起
            if (blankReverseRow % 2 == 1) {// 如果空格所在的行从下到上为奇数行
                return TSum % 2 == 0;
            } else {// 如果空格所在的行从下到上为偶数行
                return TSum % 2 == 1;
            }
        }
    }

    /**
     * @param position 需要移动的块的位置
     * @return 是否成功移动
     */
    public boolean move(int position) {
        int blankPosition = getBlankPosition();
        int xDistance = Math.abs(blankPosition / column - position / column);//如果同列，xDistance=0
        int yDistance = Math.abs(blankPosition % column - position % column);//如果同行，yDistance=0

        // 如果需要移动的空白块不在邻边或者就是空白块，无法移动
        if (xDistance + yDistance >= 2 || blankPosition == position) {
            return false;
        }

        // 交换position与blankPosition的Item对象
        itemList.set(blankPosition, itemList.get(position));
        itemList.set(position, null);

        return true;
    }

    public int getBlankPosition() {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    public List<Integer> getItemList() {
        return itemList;
    }

    public void setItemList(List<Integer> itemList) {
        this.itemList = itemList;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
