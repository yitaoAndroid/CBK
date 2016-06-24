package com.example.administrator.cbkproject.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.cbkproject.fragment.ContentFragment;

/**
 * Created by Administrator on 2016/6/21.
 */
public class ContentPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment fragment;

    private String[] titles = {"企业要闻","医疗新闻","生活贴士","药品新闻","食品新闻","社会热点","疾病快讯"};
    public ContentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
