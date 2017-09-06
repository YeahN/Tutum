package com.example.ryn41.tutum.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.forlists.Parcel;
import com.example.ryn41.tutum.forlists.ParcelListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class ParcelListFragment extends Fragment {
    private View wholeView= null;
    private ListView parcelListView;
    private ParcelListAdapter adapter;
    private List<Parcel> parcelList;

    public static ParcelListFragment newInstance(){
        return new ParcelListFragment();
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
//        mList= (ListView)wholeView.findViewById(R.id.fragment_list_listview);
//        mList.setAdapter(mAdapter);
//        mList.setOnItemClickListener(itemClick);

        parcelListView = (ListView) wholeView.findViewById(R.id.fragment_list_listview);
        parcelList = new ArrayList<Parcel>();
        adapter = new ParcelListAdapter(getContext(), parcelList);
        parcelListView.setAdapter(adapter);
        (new ListAsync()).execute();
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

    private class ListAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params){
            try {
                String str = "http://13.59.135.92/list.php?id=0JTQ3PNVOT";
                URL url = new URL(str);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = rd.readLine()) != null) {
                    sb.append(line + "\n");
                }
                line = sb.toString();
                Log.e("Sohyeon", line);

                JSONObject jsonObject = new JSONObject(line);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String companyName, invoiceNo;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    companyName = object.getString("companyName");
                    invoiceNo = object.getString("invoiceNo");
                    Parcel parcel = new Parcel(companyName, invoiceNo);
                    parcelList.add(parcel);
                    count++;
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
    }
}
