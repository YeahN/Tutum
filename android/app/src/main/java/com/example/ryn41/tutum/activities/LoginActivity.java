package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.etc.TempData;

import org.json.JSONObject;

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

    private JSONObject postData= null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
                idstr= ((EditText)findViewById(R.id.activity_login_id_edittext)).getText().toString();
                pwstr= ((EditText)findViewById(R.id.activity_login_password_edittext)).getText().toString();

                if(!idstr.isEmpty() && !pwstr.isEmpty()) {
                    TempData.setID(idstr);
                    TempData.setPW(pwstr);

                    request();
                }
                else {
                    Toast.makeText(getApplicationContext(), "입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
            else if(id == R.id.activity_login_signup_button)
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        }
    };

    private void request(){
        Log.e("tutum", "username : " + TempData.getID() + ", password : " + TempData.getPW());
        (new LoginAsync()).execute();
//        try{
//            postData= new JSONObject();
//            postData.accumulate("username", TempData.getID());
//            postData.accumulate("password", TempData.getPW());
//            (new LoginAsync()).execute();
//        } catch(JSONException e){
//            e.printStackTrace();
//        }
    }

//    private void loginSuccess(){
//        // data save (id, pw ...)
//        // if saving data, is Saved = true;
//
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        finish();
//    }

    private class LoginAsync extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params){
            try
            {
                String str = "http://13.59.135.92/login.php?id=" + idstr + "&pw=" + pwstr;
                URL url = new URL(str);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
//                if(bj.getString("token"o) != null && obj.getString("token").length() > 10){
//
//                    reqLogin(obj.getString("token"));
//                } else {
//                    Toast.makeText(getApplicationContext(), "There\'s no matched user!", Toast.LENGTH_SHORT).show();
//                }
                if(line.equals("success")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "환영합니다", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i= new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "ID/PW를 확인해주세요", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Log.e("login", line);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
