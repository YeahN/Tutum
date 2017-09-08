package com.example.ryn41.tutum.forlists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ryn41.tutum.R;

import java.util.List;

/**
 * Created by stg32 on 2017-09-06.
 */

public class TrackingDetailListAdapter extends BaseAdapter {
    private Context context;
    private List<TrackingDetail> trackingDetailList;

    public TrackingDetailListAdapter(Context context, List<TrackingDetail> trackingDetailList) {
        this.context = context;
        this.trackingDetailList = trackingDetailList;
    }

    @Override
    public int getCount() {
        return trackingDetailList.size();
    }

    @Override
    public Object getItem(int i) {
        return trackingDetailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.tracking_detail, null);
        TextView trans_time_text = (TextView) v.findViewById(R.id.trans_time_text);
        TextView trans_where_text = (TextView) v.findViewById(R.id.trans_where_text);
        TextView trans_kind_text = (TextView) v.findViewById(R.id.trans_kind_text);

        trans_time_text.setText(trackingDetailList.get(i).getTransTime());
        trans_where_text.setText(trackingDetailList.get(i).getTransWhere());
        trans_kind_text.setText(trackingDetailList.get(i).getTransKind());

        v.setTag(trackingDetailList.get(i).getTransKind());
        return v;
    }
}