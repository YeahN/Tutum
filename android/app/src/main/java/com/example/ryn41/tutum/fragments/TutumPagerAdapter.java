package com.example.ryn41.tutum.fragments;

/**
 * Created by ryn41 on 2017-09-02.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.example.ryn41.tutum.etc.Constants;

import com.example.ryn41.tutum.R;


/**
 * Created by godjakoo on 2017. 8. 24..
 */

public class TutumPagerAdapter extends FragmentPagerAdapter {
    private Context mContext= null;

    public TutumPagerAdapter(FragmentManager fm){
        super(fm);
    }
    public TutumPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        mContext= context;
    }

    @Override
    public CharSequence getPageTitle(int position){
        if(mContext != null){
            switch(position){
                case Constants.FRAGMENT_LIST:
                    return mContext.getResources().getString(R.string.fragment_list);
                case Constants.FRAGMENT_MASTER:
                    return mContext.getResources().getString(R.string.fragment_mastercode);
                case Constants.FRAGMENT_PAY:
                    return mContext.getResources().getString(R.string.fragment_pay);
                case Constants.FRAGMENT_SETTING:
                    return mContext.getResources().getString(R.string.fragment_setting);
            }
        }
        return null;
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case Constants.FRAGMENT_LIST:
                return TutumListFragment.newInstance();
            case Constants.FRAGMENT_MASTER:
                return MastercodeFragment.newInstance();
            case Constants.FRAGMENT_PAY:
                return PayFragment.newInstance();
            case Constants.FRAGMENT_SETTING:
                return SettingFragment.newInstance();
        }
        return null;
    }
    @Override
    public int getCount(){ return 4; }
}

