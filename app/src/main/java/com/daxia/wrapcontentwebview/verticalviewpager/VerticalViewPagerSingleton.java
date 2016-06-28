package com.daxia.wrapcontentwebview.verticalviewpager;

import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daxia on 2016/6/28.
 */
public class VerticalViewPagerSingleton {

    private int currentPage = 0;
    private static VerticalViewPagerSingleton instance = null;
    private List<ContentFragment> mFragmentList = new ArrayList<>();
    private ContentFragmentAdapter mAdapter;

    private VerticalViewPagerSingleton() {
    }

    public static VerticalViewPagerSingleton getInstance() {
        if (instance == null) {
            synchronized (VerticalViewPagerSingleton.class) {
                if (instance == null) {
                    instance = new VerticalViewPagerSingleton();
                }
            }
        }
        return instance;
    }

    public void initViewPager(FragmentManager fm) {
        mAdapter = new ContentFragmentAdapter(fm, mFragmentList);
    }

    public List<ContentFragment> getFragmentList() {
        return mFragmentList;
    }

    public ContentFragmentAdapter getmAdapter() {
        return mAdapter;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean canLoadMore() {
        return mFragmentList.size() <= currentPage + 1;
    }
}
