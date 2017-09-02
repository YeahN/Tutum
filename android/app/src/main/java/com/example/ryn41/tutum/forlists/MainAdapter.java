package com.example.ryn41.tutum.forlists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * Created by godjakoo on 2017. 8. 20..
 */

public class MainAdapter extends ArrayAdapter<MainItem> {
    private Context mContext= null;
    private ArrayList<MainItem> mContent= null;

    public MainAdapter(Context v1, int v2, ArrayList<MainItem> v3){
        super(v1, v2, v3);
        mContext= v1;
        mContent= v3;
    }

    @Override
    public View getView(int position, View v, ViewGroup container){
        //if(v == null) v= LayoutInflater.from(mContext).inflate(R.layout.deleteuser_item, null);
        return v;
    }
    @Override
    public int getCount(){
        return -1;
    }

    @Override
    public MainItem getItem(int position){
        return null;
    }
}

