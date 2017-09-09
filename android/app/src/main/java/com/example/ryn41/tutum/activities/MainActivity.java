package com.example.ryn41.tutum.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.etc.Constants;
import com.example.ryn41.tutum.fragments.ParcelListFragment;
import com.example.ryn41.tutum.fragments.TutumPagerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends FragmentActivity {
    private TutumPagerAdapter mAdapter = null;
    private ViewPager mPager = null;
    private TabLayout mTabs = null;

    private String parcels;
    private String userID;

    private ProgressDialog mDialog= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
    }

    @Override
    public void onResume(){
        super.onResume();

//        makeView();
        (new ListAsync()).execute();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView() {
        mAdapter = new TutumPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mTabs = (TabLayout)findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mPager);
    }

    public String getParcels() {
        return parcels;
    }

    private class ListAsync extends AsyncTask<Void, Void, Void> {

        @Override
        public void onPreExecute(){
            super.onPreExecute();

            if(mDialog == null){
                mDialog= new ProgressDialog(MainActivity.this);
                mDialog.setMessage("get data");
                mDialog.setIndeterminate(false);
                mDialog.setCancelable(false);
                mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            }
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String str = "http://13.59.135.92/list.php?id=" + userID;
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
                parcels = line;
                Log.e("parcels", parcels);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(Void result){
            super.onPostExecute(result);
            if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();
            Log.e("parcel", "list async");
//            ((ParcelListFragment)mAdapter.getItem(Constants.FRAGMENT_LIST)).makeViewWithData(parcels);
            makeView();
        }
    }
}
