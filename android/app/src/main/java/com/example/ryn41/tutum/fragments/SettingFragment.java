package com.example.ryn41.tutum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.etc.TempData;

/**
 * Created by ryn41 on 2017-08-20.
 */

public class SettingFragment extends Fragment {

    private View wholeView= null;

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        wholeView= inflater.inflate(R.layout.fragment_setting, null);
        return wholeView;
    }

    @Override
    public void onResume() {
        super.onResume();
        makeView();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView() {
        ((TextView) wholeView.findViewById(R.id.fragment_setting_name_text)).setText(TempData.getName());
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            int id = v.getId();
        }
    };
}

