package com.example.administrator.cbkproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.cbkproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class WelecomeActivity extends Activity {
    private ViewPager viewPager;
    private List<ImageView> imageViews;
    private int[] imageRes = {R.mipmap.slide1, R.mipmap.slide2, R.mipmap.slide3};
    private LinearLayout linearLayout;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_activity);
        init();
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(adapter);
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.welcom_vp);
        linearLayout = (LinearLayout) findViewById(R.id.welcome_ll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        imageViews = new ArrayList<ImageView>();
        params.leftMargin = 10;
        for (int i = 0; i < imageRes.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imageRes[i]);
            imageViews.add(imageView);
            imageView = null;


            View view = new View(this);
            view.setLayoutParams(params);
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.page_now);
            } else {
                view.setBackgroundResource(R.mipmap.page);
            }
            linearLayout.addView(view);
            view = null;
        }
        params = null;
    }

    class MyPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                if (i==position) {
                    linearLayout.getChildAt(i).setBackgroundResource(R.mipmap.page_now);
                } else {
                    linearLayout.getChildAt(i).setBackgroundResource(R.mipmap.page);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
