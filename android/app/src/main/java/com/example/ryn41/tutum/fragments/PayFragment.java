package com.example.ryn41.tutum.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.activities.ReloadActivity;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class PayFragment extends Fragment {

    private View wholeView= null;

    public static PayFragment newInstance(){
        return new PayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        wholeView = inflater.inflate(R.layout.fragment_pay, null);
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
        ((Button) wholeView.findViewById(R.id.fragment_pay_reload_button)).setOnClickListener(click);
    }

    View.OnClickListener click= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int id= v.getId();

            if(id == R.id.fragment_pay_reload_button) {
                startActivity(new Intent(getActivity(), ReloadActivity.class));
            }
        }
    };
}
