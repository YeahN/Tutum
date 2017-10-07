package com.example.ryn41.tutum.activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.etc.TempData;
import com.example.ryn41.tutum.fragments.TutumPagerAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends FragmentActivity {
    private TutumPagerAdapter mAdapter = null;
    private ViewPager mPager = null;
    private TabLayout mTabs = null;

    private ProgressDialog mDialog= null;

    private String parcels;
    private String payments;
    private Bitmap mastercode;
    public String getParcels() {
        return parcels;
    }
    public String getPayments() {
        return payments;
    }
    public Bitmap getMastercode() { return  mastercode; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        (new GetAsync()).execute();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView() {
        mAdapter = new TutumPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mTabs = (TabLayout) findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mPager);

        mAdapter.notifyDataSetChanged();
        mPager.setCurrentItem(TempData.getFragmentIndex(), true);
    }

    private class GetAsync extends AsyncTask<Void, Void, Void> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();

            if(mDialog == null) {
                mDialog= new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Get data");
                mDialog.setIndeterminate(false);
                mDialog.setCancelable(false);
                mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            }
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String str = "http://13.59.135.92/parcellist.php?id=" + TempData.getID();
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
                conn.disconnect();

                str = "http://13.59.135.92/payhistory.php?id=" + TempData.getID();
                url = new URL(str);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();
                rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                while((line = rd.readLine()) != null) {
                    sb.append(line + "\n");
                }
                line = sb.toString();
                payments = line;
                Log.e("payments", payments);
                conn.disconnect();

                str = "http://13.59.135.92/auth/userinfo.php?id=" + TempData.getID();
                url = new URL(str);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();
                rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                while((line = rd.readLine()) != null) {
                    sb.append(line + "\n");
                }
                line = sb.toString();
                Log.e("user info", line);
                conn.disconnect();

                JSONObject jsonObject = new JSONObject(line);
                TempData.setName(jsonObject.getString("name"));
                TempData.setPoint(jsonObject.getInt("point"));

                str = "http://13.59.135.92/mastercode.php?id=" + TempData.getID();
                url = new URL(str);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                mastercode = BitmapFactory.decodeStream(is);
                conn.disconnect();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(mDialog != null && mDialog.isShowing()) mDialog.dismiss();
            Log.e("parcel", "list async");
            makeView();
        }
    }
}
