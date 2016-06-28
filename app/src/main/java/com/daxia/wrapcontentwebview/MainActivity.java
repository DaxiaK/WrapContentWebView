package com.daxia.wrapcontentwebview;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.daxia.wrapcontentwebview.verticalviewpager.ContentFragment;
import com.daxia.wrapcontentwebview.verticalviewpager.VerticalViewPagerSingleton;
import com.daxia.wrapcontentwebview.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    VerticalViewPagerSingleton VVPSInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VVPSInstance = VerticalViewPagerSingleton.getInstance();
        initViewPager();
    }

    private void initViewPager() {
        VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.vertical_viewpager);
        VVPSInstance.initViewPager(getSupportFragmentManager());
        VVPSInstance.getFragmentList().add(ContentFragment.newInstance(0, "http://www.apple.com/tw/"));
        viewPager.setAdapter(VVPSInstance.getmAdapter());
        viewPager.addOnPageChangeListener(this);
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //top scroll
        if (position + 1 == VVPSInstance.getCurrentPage()) {
            VVPSInstance.getFragmentList().get(position).ScrollToBottom();
        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("test", "nowpage = " + VVPSInstance.getCurrentPage() + " , pos = " + position);
        Log.e("test", VVPSInstance.getFragmentList().get(position).getUrl());
        VVPSInstance.setCurrentPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //do nothing
    }
}
