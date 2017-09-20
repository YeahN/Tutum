package com.example.ryn41.tutum.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.activities.MainActivity;
import com.example.ryn41.tutum.activities.NewInvoiceActivity;
import com.example.ryn41.tutum.activities.TrackingInfoActivity;
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

    private String parcels;
    private String userID;
    private String companyCode, invoiceNo;

    public static ParcelListFragment newInstance(){
        return new ParcelListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        wholeView= inflater.inflate(R.layout.fragment_list, null);
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
        parcelListView = (ListView) wholeView.findViewById(R.id.fragment_list_listview);
        parcelList = new ArrayList<Parcel>();
        try {
            String str= ((MainActivity) getActivity()).getParcels();
            String companyCode, companyName, invoiceNo;
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            if(jsonArray != null) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    companyCode = object.getString("companyCode");
                    companyName = object.getString("companyName");
                    invoiceNo = object.getString("invoiceNo");
                    Parcel parcel = new Parcel(companyCode, companyName, invoiceNo);
                    parcelList.add(parcel);
                    count++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        adapter = new ParcelListAdapter(getContext(), parcelList);
        parcelListView.setAdapter(adapter);
        parcelListView.setOnItemClickListener(itemClick);

        ((ImageButton) wholeView.findViewById(R.id.fragment_list_add_imagebutton)).setOnClickListener(click);
    }

    private void generateViewIfNotExisting(){
        if(wholeView == null)
            wholeView= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
    }

//    public void setParcelData(String jsonString){
//        try {
//            String companyCode, companyName, invoiceNo;
//            JSONObject jsonObject = new JSONObject(jsonString);
//            JSONArray jsonArray = jsonObject.getJSONArray("response");
//            int count = 0;
//            while (count < jsonArray.length()) {
//                JSONObject object = jsonArray.getJSONObject(count);
//                companyCode = object.getString("companyCode");
//                companyName = object.getString("companyName");
//                invoiceNo = object.getString("invoiceNo");
//                Parcel parcel = new Parcel(companyCode, companyName, invoiceNo);
//                parcelList.add(parcel);
//                count++;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        adapter.notifyDataSetChanged();
//    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.fragment_list_add_imagebutton) {
                userID = ((MainActivity) getActivity()).getUserID();
                Intent intent = new Intent(getActivity(), NewInvoiceActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        }
    };

    AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            companyCode = parcelList.get(position).getCompanyCode();
            invoiceNo = parcelList.get(position).getInvoiceNo();
            (new TrackAsync()).execute();
        }
    };

    private class TrackAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.e("parcel", "company code : " + companyCode + ", invoice no : " + invoiceNo);
                String str = "http://info.sweettracker.co.kr/api/v1/trackingInfo?t_key=qLBsAYIWlzUJ0ojCZR6DDA&t_code=" + companyCode + "&t_invoice=" + invoiceNo;
                URL url = new URL(str);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = rd.readLine()) != null) {
                    sb.append(line + "\n");
                }
                line = sb.toString();
                Log.e("trackingInfo", line);

                Intent intent = new Intent(getActivity(), TrackingInfoActivity.class);
                intent.putExtra("trackingInfo", line);
                startActivity(intent);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
