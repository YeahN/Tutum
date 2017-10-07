package com.example.ryn41.tutum.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.activities.LoginActivity;
import com.example.ryn41.tutum.etc.TempData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ryn41 on 2017-08-20.
 */

public class SettingFragment extends Fragment {

    private View wholeView= null;

    private String pushValid = "n";

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        wholeView= inflater.inflate(R.layout.fragment_setting, null);
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
        ((TextView) wholeView.findViewById(R.id.fragment_setting_name_text)).setText(TempData.getName().concat("님"));
        ((Button) wholeView.findViewById(R.id.fragment_setting_logout_button)).setOnClickListener(click);
        ((Switch) wholeView.findViewById(R.id.fragment_setting_push_switch)).setOnCheckedChangeListener(check);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            int id = v.getId();

            if(id == R.id.fragment_setting_logout_button) {
                Toast.makeText(getContext(), "로그아웃되었습니다", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        }
    };
    CompoundButton.OnCheckedChangeListener check = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton button, boolean isChecked) {
            if (isChecked) {
                pushValid = "y";
            } else {
                pushValid = "n";
            }
            (new PushAsync()).execute();
        }
    };

    private class PushAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.e("pushValid", pushValid);
                String str = "http://13.59.135.92/update.php?userId=" + TempData.getID() + "&valid=" + pushValid;
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
                Log.e("PushAsync", line);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}