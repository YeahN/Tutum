package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.forlists.TrackingDetail;
import com.example.ryn41.tutum.forlists.TrackingDetailListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrackingInfoActivity extends Activity {

    private ListView trackingDetailListView;
    private TrackingDetailListAdapter adapter;
    private List<TrackingDetail> trackingDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_info);
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
        trackingDetailListView = (ListView) findViewById(R.id.activity_tracking_info_listview);
        trackingDetailList = new ArrayList<TrackingDetail>();
        adapter = new TrackingDetailListAdapter(getApplicationContext(), trackingDetailList);
        trackingDetailListView.setAdapter(adapter);

        String trackingInfo, timeString, where, kind;
        Intent intent = getIntent();
        trackingInfo = intent.getStringExtra("trackingInfo");

        try {
            JSONObject jsonObject = new JSONObject(trackingInfo);
            ((TextView) findViewById(R.id.sender_name_text)).setText(jsonObject.getString("senderName"));
            ((TextView) findViewById(R.id.receiver_name_text)).setText(jsonObject.getString("receiverName"));
            ((TextView) findViewById(R.id.item_name_text)).setText(jsonObject.getString("itemName"));

            JSONArray jsonArray = jsonObject.getJSONArray("trackingDetails");
            int count = jsonArray.length();
            while (count > 0) {
                count--;
                JSONObject object = jsonArray.getJSONObject(count);
                timeString = object.getString("timeString");
                where = object.getString("where");
                kind = object.getString("kind");
                TrackingDetail trackingDetail = new TrackingDetail(timeString, where, kind);
                trackingDetailList.add(trackingDetail);
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
