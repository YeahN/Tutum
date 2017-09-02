package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
    }

    @Override
    public void onResume(){
        super.onResume();
        makeView();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView(){

    }

    View.OnClickListener click= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            int id= v.getId();
        }
    };

    private class SignupAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params){
            try {
                URL url= new URL("http://asokoya.jp/wp-json/jwt-auth/v1/token");
                HttpURLConnection conn= (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os= conn.getOutputStream();
//                os.write(postData.toString().getBytes("utf-8"));
                os.flush();
                conn.connect();

                BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line= rd.readLine()) != null){
                    sb.append(line);
                }
                line= sb.toString();
//                Log.e("onface player", "onface_player login info : " + line);
//                JSONObject obj= new JSONObject(line);
//                if(obj.getString("token") != null && obj.getString("token").length() > 10){
//
//                    reqLogin(obj.getString("token"));
//                } else {
//                    Toast.makeText(getApplicationContext(), "There\'s no matched user!", Toast.LENGTH_SHORT).show();
//                }
//                reqLogin();
            } catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
    }
}
