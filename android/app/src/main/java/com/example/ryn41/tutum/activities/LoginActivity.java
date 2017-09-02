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

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.etc.TempData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class LoginActivity extends Activity {

    private JSONObject postData= null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
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
        ((Button)findViewById(R.id.activity_login_login_button)).setOnClickListener(click);
        ((Button)findViewById(R.id.activity_login_signup_button)).setOnClickListener(click);
    }

    View.OnClickListener click= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            int id= v.getId();

            if(id == R.id.activity_login_login_button) {
                String idval= ((EditText)findViewById(R.id.activity_login_id_edittext)).getText().toString();
                String pwval= ((EditText)findViewById(R.id.activity_login_password_edittext)).getText().toString();

                if(!idval.isEmpty() && !pwval.isEmpty()) {
                    TempData.setID(idval);
                    TempData.setPW(pwval);

                    request();
                }
                else {
                    // make toast
                }
            }
            else if(id == R.id.activity_login_signup_button)
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        }
    };

    private void request(){
        Log.e("tutum", "username : " + TempData.getID() + ", password : " + TempData.getPW());
        try{
            postData= new JSONObject();
            postData.accumulate("username", TempData.getID());
            postData.accumulate("password", TempData.getPW());
            (new LoginAsync()).execute();
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void loginSuccess(){
        // data save (id, pw ...)
        // if saving data, is Saved = true;

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private class LoginAsync extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params){
            try {
                URL url= new URL("https://m.naver.com");
                HttpURLConnection conn= (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
//                conn.setDoOutput(true);
//                OutputStream os= conn.getOutputStream();
//                os.write(postData.toString().getBytes("utf-8"));
//                os.flush();
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
                Log.e("tutum", line);
                loginSuccess();
            } catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
    }
}
