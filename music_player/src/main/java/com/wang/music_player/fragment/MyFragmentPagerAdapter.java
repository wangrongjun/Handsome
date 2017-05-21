package com.wang.music_player.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragList;
    private List<String> fragTitle;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragList,
                                  List<String> fragTitle) {
        super(fm);
        this.fragList = fragList;
        this.fragTitle = fragTitle;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

}
