package com.example.ryn41.tutum.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.activities.MainActivity;
import com.example.ryn41.tutum.activities.ReloadActivity;
import com.example.ryn41.tutum.etc.TempData;
import com.example.ryn41.tutum.forlists.Payment;
import com.example.ryn41.tutum.forlists.PaymentListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class PayFragment extends Fragment {

    private View wholeView= null;
    private ListView paymentListView;
    private PaymentListAdapter adapter;
    private List<Payment> paymentList;

    private String payments;

    public static PayFragment newInstance(){
        return new PayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        wholeView = inflater.inflate(R.layout.fragment_pay, null);
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

        paymentListView = (ListView) wholeView.findViewById(R.id.fragment_pay_listview);
        paymentList = new ArrayList<Payment>();
        try {
            payments = ((MainActivity) getActivity()).getPayments();
            String sign, amount, detail, time;
            JSONObject jsonObject = new JSONObject(payments);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            if(jsonArray != null) {
                int count = jsonArray.length();
                while (count > 0) {
                    count--;
                    JSONObject object = jsonArray.getJSONObject(count);
                    sign = object.getString("sign");
                    amount = String.format("%,d", object.getInt("amount"));
                    detail = object.getString("detail");
                    time = object.getString("time");

                    if(sign.equals("+")) {
                        Resources res = getResources();

                        for(int i = 0; i < 4; i++) {
                            if(detail.equals(res.getStringArray(R.array.pay_method_value_array)[i])) {
                                detail = res.getStringArray(R.array.pay_method_name_array)[i].concat("충전");
                                break;
                            }
                        }

                        amount = sign.concat(amount);
                    }
                    Payment payment = new Payment(amount, detail, time);
                    paymentList.add(payment);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        adapter = new PaymentListAdapter(getContext(), paymentList);
        paymentListView.setAdapter(adapter);

        ((TextView) wholeView.findViewById(R.id.fragment_pay_name_text)).setText(TempData.getName().concat("님"));
        ((TextView) wholeView.findViewById(R.id.fragment_pay_balance_text)).setText((String.format("%,d", TempData.getPoint())).concat("원"));
        ((Button) wholeView.findViewById(R.id.fragment_pay_reload_button)).setOnClickListener(click);
    }

    private void generateViewIfNotExisting(){
        if(wholeView == null)
            wholeView= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
    }

    View.OnClickListener click= new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int id= v.getId();

            if(id == R.id.fragment_pay_reload_button) {
                startActivity(new Intent(getActivity(), ReloadActivity.class));
            }
        }
    };
}
