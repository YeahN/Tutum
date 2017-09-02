package com.example.ryn41.tutum.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.forview.MyFragmentAdapter;

public class MainActivity extends FragmentActivity {
    private MyFragmentAdapter mAdapter = null;
    private ViewPager mPager = null;
    private TabLayout mTabs = null;

    //택배 회사 리스트 띄우기
    static final String[] LIST_DELIVERY_COMPANY = {"CJ대한통운","한진택배","롯데택배","우체국택배","로젠택배"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mAdapter == null) mAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.activity_main_pager);
        mPager.setAdapter(mAdapter);

        mTabs = (TabLayout) findViewById(R.id.activity_main_tabs);
        mTabs.setupWithViewPager(mPager);

        //택배 회사 리스트 띄우기
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_DELIVERY_COMPANY);

        ListView listview = (ListView) findViewById(R.id.list_delivery_company);
        listview.setAdapter(arrayAdapter);


    }
}
