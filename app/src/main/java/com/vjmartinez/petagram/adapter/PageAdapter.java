package com.vjmartinez.petagram.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class PageAdapter extends FragmentPagerAdapter {

    /**
     * List of fragments to paginate
     */
    private List<Fragment> fragments;
    private List<String> titleList;


    public PageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titleList) {
        super(fm);
        this.fragments = fragments;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(titleList != null && !titleList.isEmpty() && titleList.size() >= position){
            return titleList.get(position);
        }else {
            return "No title";
        }
    }
}