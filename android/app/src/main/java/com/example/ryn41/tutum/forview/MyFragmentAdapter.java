package com.example.ryn41.tutum.forview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ryn41.tutum.Constants;
import com.example.ryn41.tutum.fragments.ListFragment;
import com.example.ryn41.tutum.fragments.MastercodeFragment;
import com.example.ryn41.tutum.fragments.SettingFragment;
import com.example.ryn41.tutum.fragments.TutumpayFragment;

import java.util.List;


/**
 * Created by godjakoo on 2017. 8. 20..
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    public MyFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int pos){
        switch(pos){
            case Constants.FRAGMENT_LIST:
                return ListFragment.newInstance();
            case Constants.FRAGMENT_TUTUMPAY:
                return TutumpayFragment.newInstance();
            case Constants.FRAGMENT_MASTERCODE:
                return MastercodeFragment.newInstance();
            case Constants.FRAGMENT_SETTING:
                return SettingFragment.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount(){
        return 4;
    }

}

