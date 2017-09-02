package com.example.ryn41.tutum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ryn41.tutum.R;

/**
 * Created by godjakoo on 2017. 8. 20..
 */

public class ListFragment extends Fragment {
    private View wholeView= null;

    private TextView mTextView= null;

    public static ListFragment newInstance(){
        return new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle instance) {

        wholeView = inflater.inflate(R.layout.fragment_list, null);
        return wholeView;

    }

}
