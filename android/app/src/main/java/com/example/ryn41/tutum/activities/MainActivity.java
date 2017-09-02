package com.example.ryn41.tutum.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.fragments.TutumPagerAdapter;

public class MainActivity extends FragmentActivity {
    private TutumPagerAdapter mAdapter= null;
    private ViewPager mPager= null;
    private TabLayout mTabs= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume(){
        super.onResume();
        makeView();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView(){
        mAdapter= new TutumPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        mPager= (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mTabs= (TabLayout)findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mPager);
    }
}
