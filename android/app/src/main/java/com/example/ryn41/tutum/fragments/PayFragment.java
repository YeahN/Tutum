package com.example.ryn41.tutum.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.activities.MainActivity;
import com.example.ryn41.tutum.activities.ReloadActivity;
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

//    private String userID = "";

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
            String str= ((MainActivity) getActivity()).getPayments();
            String sign, amount, detail, time;
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            if(jsonArray != null) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    sign = object.getString("sign");
                    amount = object.getString("amount");
                    detail = object.getString("detail");
                    time = object.getString("time");
                    Payment payment = new Payment(amount, detail, time);
                    paymentList.add(payment);
                    count++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        adapter = new PaymentListAdapter(getContext(), paymentList);
        paymentListView.setAdapter(adapter);

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
//                userID = ((MainActivity) getActivity()).getUserID();
//                Intent intent = new Intent(getActivity(), ReloadActivity.class);
//                intent.putExtra("userID", userID);
                startActivity(new Intent(getActivity(), ReloadActivity.class));
            }
        }
    };
}
