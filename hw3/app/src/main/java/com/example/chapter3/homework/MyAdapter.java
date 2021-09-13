package com.example.chapter3.homework;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;                   //存放fragment对象


    public MyAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        //返回当前条目
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        //返回数组长度
        return fragmentList.size();
    }
}
