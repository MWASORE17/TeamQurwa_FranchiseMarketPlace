package com.williamsumitromytextview.qurwateam.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.williamsumitromytextview.qurwateam.view.fragment.DaftarfranchiseFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.EventFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.HomeFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.NewsFragment;

/**
 * Created by william on 17/05/2017.
 */

public class TabAdapter extends FragmentPagerAdapter {
    public static int int_items = 3;
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new NewsFragment();
            case 2:
                return new EventFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return int_items;
    }
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "News";
            case 2:
                return "Event";
        }
        return null;
    }
}
