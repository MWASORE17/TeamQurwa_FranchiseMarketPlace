package com.williamsumitromytextview.qurwateam.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.privacy_viewpager) ViewPager viewPager;
    @BindView(R.id.privacy_dots) LinearLayout dotslayout;
    @BindView(R.id.privacy_back) Button back;
    @BindView(R.id.privacy_next) Button next;
    private TextView[] dots;
    private int[] layouts;
    private PrivacyAdapter privacyAdapter;
    private int current;
    public PrivacyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy, container, false);
        ButterKnife.bind(this,view);

        layouts = new int[]{
                R.layout.terms1,
                R.layout.terms2,
                R.layout.terms3,
                R.layout.terms4,
                R.layout.terms5
        };
        addBottomDots(0);
        privacyAdapter = new PrivacyAdapter();
        viewPager.setAdapter(privacyAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        back.setVisibility(view.GONE);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        return view;
    }
    private void addBottomDots(int currentPage){
        dots = new TextView[layouts.length];

        dotslayout.removeAllViews();
        for(int i = 0;i<dots.length;i++){
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.GRAY);
            dotslayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[currentPage].setTextColor(Color.BLACK);
        }
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if(position==layouts.length - 1){
                next.setText("Finish");
                next.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            }
                        }
                );
            }
            else if(position==0){
                back.setVisibility(View.GONE);
            }
            else{
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.privacy_back:
                current = getItem(-1);
                if(current>-1){
                    viewPager.setCurrentItem(current);
                }
                break;
            case R.id.privacy_next:
                current = getItem(+1);
                if(current<layouts.length){
                    viewPager.setCurrentItem(current);
                }
                break;

            default:
                break;
        }
    }

    public class PrivacyAdapter extends PagerAdapter{
        private LayoutInflater layoutInflater;
        public PrivacyAdapter(){

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
