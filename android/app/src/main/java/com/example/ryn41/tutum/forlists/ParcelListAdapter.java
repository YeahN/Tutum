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

public class ParcelListAdapter extends BaseAdapter {
    private Context context;
    private List<Parcel> parcelList;

    public ParcelListAdapter(Context context, List<Parcel> parcelList) {
        this.context = context;
        this.parcelList = parcelList;
    }

    @Override
    public int getCount() {
        return parcelList.size();
    }

    @Override
    public Object getItem(int i) {
        return parcelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.parcel, null);
//        TextView item_name_text = (TextView) v.findViewById(R.id.item_name_text);
        TextView company_name_text = (TextView) v.findViewById(R.id.company_name_text);
        TextView invoice_no_text = (TextView) v.findViewById(R.id.invoice_no_text);

//        item_name_text.setText(parcelList.get(i).getItemName());
        company_name_text.setText(parcelList.get(i).getCompanyName());
        invoice_no_text.setText(parcelList.get(i).getInvoiceNo());

        v.setTag(parcelList.get(i).getInvoiceNo());
        return v;
    }
}