package com.example.ryn41.tutum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryn41.tutum.R;

/**
 * Created by ryn41 on 2017-08-20.
 */

public class SettingFragment extends Fragment {
    private View wholeView= null;

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle instance){
        wholeView= inflater.inflate(R.layout.fragment_setting, null);
        return wholeView;
    }
}
