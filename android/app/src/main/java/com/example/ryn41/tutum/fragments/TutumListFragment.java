package com.example.ryn41.tutum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.forlists.MainAdapter;
import com.example.ryn41.tutum.forlists.MainItem;

import java.util.ArrayList;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class TutumListFragment extends Fragment {
    private View wholeView= null;
    private MainAdapter mAdapter= null;
    private ArrayList<MainItem> mContent= null;
    private ListView mList= null;

    public static TutumListFragment newInstance(){
        return new TutumListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        wholeView= inflater.inflate(R.layout.fragment_list, null);
        return wholeView;
    }

    @Override
    public void onResume(){
        super.onResume();
        makeView();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView(){
        mList= (ListView)wholeView.findViewById(R.id.fragment_list_listview);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(itemClick);
    }

    View.OnClickListener click= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            int id= v.getId();
        }
    };
    AdapterView.OnItemClickListener itemClick= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };
}
