package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.etc.TempData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class LoginActivity extends Activity {

    private String idstr = "";
    private String pwstr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        ((Button) findViewById(R.id.activity_login_login_button)).setOnClickListener(click);
        ((Button) findViewById(R.id.activity_login_signup_button)).setOnClickListener(click);

        checkSaved();
    }

    private void checkSaved(){
        SharedPreferences pref= getSharedPreferences("tutum", Context.MODE_PRIVATE);
        idstr= pref.getString("userid", "");
        if(!idstr.isEmpty() && idstr.length() > 0){
            pwstr= pref.getString("password", "");

            TempData.setID(idstr);
            TempData.setPW(pwstr);
            TempData.setUserLogin(1);
            request();
        }
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.activity_login_login_button) {
                idstr = ((EditText) findViewById(R.id.activity_login_id_edittext)).getText().toString();
                pwstr = ((EditText) findViewById(R.id.activity_login_password_edittext)).getText().toString();

                if(!idstr.isEmpty() && !pwstr.isEmpty()) {
                    TempData.setID(idstr);
                    TempData.setPW(pwstr);
                    TempData.setUserLogin(1);

                    request();
                }
                else {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
            else if(id == R.id.activity_login_signup_button)
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        }
    };

    private void request() {
        Log.e("TempData", "ID: " + TempData.getID() + ", PW: " + TempData.getPW());
        (new LoginAsync()).execute();
    }

    private class LoginAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String str = "http://13.59.135.92/auth/login.php?id=" + idstr + "&pw=" + pwstr;
                URL url = new URL(str);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();
                Log.e("login", line);

                if(line.equals("success")) {
                    //godjakoo
                    SharedPreferences.Editor edit= getSharedPreferences("tutum", Context.MODE_PRIVATE).edit();
                    edit.putString("userid", idstr);
                    edit.putString("password", pwstr);
                    edit.commit();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 다시 확인하세요", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
