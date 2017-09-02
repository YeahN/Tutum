package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ryn41.tutum.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class SignupActivity extends Activity {
    private String idstr = "";
    private String pwstr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @Override
    public void onResume() {
        super.onResume();
        makeView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void makeView() {
        ((Button) findViewById(R.id.activity_signup_signup_button)).setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.activity_signup_signup_button) {
                idstr = ((EditText) findViewById(R.id.activity_signup_id_edittext)).getText().toString();
                pwstr = ((EditText) findViewById(R.id.activity_signup_password_edittext)).getText().toString();

                if (!idstr.isEmpty() && !pwstr.isEmpty()) {
                    request();
                } else {
                    Toast.makeText(getApplicationContext(), "입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void request() {
        (new SignupAsync()).execute();
    }

    private class SignupAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String str = "http://13.59.135.92/register.php?id=" + idstr + "&pw=" + pwstr;
                URL url = new URL(str);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
//                os.write(postData.toString().getBytes("utf-8"));
                os.flush();
                conn.connect();

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();

                Log.e("tutum", line);
                Toast.makeText(getApplicationContext(), "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}