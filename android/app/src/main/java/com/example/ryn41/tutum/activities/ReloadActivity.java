package com.example.ryn41.tutum.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ryn41.tutum.R;

public class ReloadActivity extends AppCompatActivity {

    private ArrayAdapter methodAdapter;
    private Spinner methodSpinner;

    private String payMethod = "";
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload);
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
        methodSpinner = (Spinner)findViewById(R.id.activity_reload_pay_method_spinner);
        methodAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pay_method_name_array, android.R.layout.simple_spinner_dropdown_item);
        methodSpinner.setAdapter(methodAdapter);
        methodSpinner.setOnItemSelectedListener(itemSelect);

        ((Button)findViewById(R.id.activity_reload_reload_button)).setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.activity_reload_reload_button) {
                amount = Integer.parseInt(((EditText)findViewById(R.id.activity_reload_amount_edittext)).getText().toString());
                if(amount > 0) {
                    String str = "payMethod: " + payMethod + ", amount: " + amount;
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
//                    (new PayAsync()).execute();
                }
                else {
                    Toast.makeText(getApplicationContext(), "충전금액을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    AdapterView.OnItemSelectedListener itemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Resources res = getResources();
            payMethod = res.getStringArray(R.array.pay_method_value_array)[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

//    private class PayAsync extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                String str = "http://";
//                URL url = new URL(str);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setDoInput(true);
//                conn.connect();
//
//                BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while((line = rd.readLine()) != null){
//                    sb.append(line);
//                }
//                line = sb.toString();
//                Log.e("request pay", line);
//
//                if(line.equals("success")) {
//                    finish();
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//            catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            return null;
//        }
//    }
}
