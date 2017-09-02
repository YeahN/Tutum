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

public class MastercodeFragment extends Fragment {
    private View wholeView= null;

    public static MastercodeFragment newInstance(){
        return new MastercodeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle instance){
        wholeView= inflater.inflate(R.layout.fragment_mastercode, null);
        return wholeView;
    }
}
