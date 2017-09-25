package com.example.ryn41.tutum.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.activities.MainActivity;
import com.example.ryn41.tutum.etc.TempData;

/**
 * Created by ryn41 on 2017-08-20.
 */

public class MastercodeFragment extends Fragment {

    private View wholeView = null;
    private Bitmap mastercode;

    public static MastercodeFragment newInstance(){
        return new MastercodeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        wholeView= inflater.inflate(R.layout.fragment_mastercode, null);
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
        generateViewIfNotExisting();

        mastercode = ((MainActivity) getActivity()).getMastercode();
        ((ImageView) wholeView.findViewById(R.id.fragment_mastercode_image)).setImageBitmap(mastercode);
        ((TextView) wholeView.findViewById(R.id.fragment_mastercode_id_text)).setText(TempData.getID());
    }

    private void generateViewIfNotExisting(){
        if(wholeView == null)
            wholeView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mastercode, null);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
        }
    };
}
