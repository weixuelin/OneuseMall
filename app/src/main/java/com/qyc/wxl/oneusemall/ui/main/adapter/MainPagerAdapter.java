package com.qyc.wxl.oneusemall.ui.main.adapter;


import com.qyc.wxl.oneusemall.base.FragmentFactory;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private String type;

    public MainPagerAdapter(FragmentManager fm, String type) {
        super(fm);
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createForMain(position, type);
    }

    @Override
    public int getCount() {
        return 5;
    }

}
