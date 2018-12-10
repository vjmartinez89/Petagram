package com.vjmartinez.petagram.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {

    /**
     * List of fragments to paginate
     */
    private List<Fragment> fragments;


    public PageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
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
        switch (position)
        {
            case 0:
                return "Usuarios";
            case 1:
                return "Llamadas";

        }
        return super.getPageTitle(position);
    }
}
