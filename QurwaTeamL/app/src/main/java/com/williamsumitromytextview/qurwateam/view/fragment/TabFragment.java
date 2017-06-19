package com.williamsumitromytextview.qurwateam.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.view.adapter.TabAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;


    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);
        ButterKnife.bind(this,view);
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
