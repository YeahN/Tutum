package com.example.ryn41.tutum.forlists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ryn41.tutum.R;

import java.util.List;

import static com.example.ryn41.tutum.R.id.trans_kind_text;
import static com.example.ryn41.tutum.R.id.trans_time_text;
import static com.example.ryn41.tutum.R.id.trans_where_text;

/**
 * Created by stg32 on 2017-09-06.
 */

public class PaymentListAdapter extends BaseAdapter {

    private Context context;
    private List<Payment> paymentList;

    public PaymentListAdapter(Context context, List<Payment> paymentList) {
        this.context = context;
        this.paymentList = paymentList;
    }

    @Override
    public int getCount() {
        return paymentList.size();
    }

    @Override
    public Object getItem(int i) {
        return paymentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.payment, null);
        TextView payment_detail_text = (TextView) v.findViewById(R.id.payment_detail_text);
        TextView payment_time_text = (TextView) v.findViewById(R.id.payment_time_text);
        TextView payment_amount_text = (TextView) v.findViewById(R.id.payment_amount_text);

        payment_detail_text.setText(paymentList.get(i).getDetail());
        payment_time_text.setText(paymentList.get(i).getTime());
        payment_amount_text.setText(paymentList.get(i).getAmount());

        v.setTag(paymentList.get(i).getDetail());
        return v;
    }
}