package com.example.ryn41.tutum.activities;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.etc.TempData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewInvoiceActivity extends AppCompatActivity {

    private ArrayAdapter companyAdapter;
    private Spinner companySpinner;

    private String companyCode = "";
    private String invoiceNo = "";
    private int payment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_invoice);
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
        companySpinner = (Spinner) findViewById(R.id.activity_new_invoice_company_spinner);
        companyAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.company_name_array, android.R.layout.simple_spinner_dropdown_item);
        companySpinner.setAdapter(companyAdapter);
        companySpinner.setOnItemSelectedListener(itemSelect);

        ((RadioGroup) findViewById(R.id.activity_new_invoice_payment_radiogroup)).setOnCheckedChangeListener(check);
        ((Button) findViewById(R.id.activity_new_invoice_register_button)).setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.activity_new_invoice_register_button) {
                invoiceNo = ((EditText) findViewById(R.id.activity_new_invoice_invoice_no_edittext)).getText().toString();
                if(!invoiceNo.isEmpty()) {
                    (new NewAsync()).execute();
                }
                else {
                    Toast.makeText(getApplicationContext(), "운송장 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    RadioGroup.OnCheckedChangeListener check = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton paymentButton = (RadioButton) findViewById(checkedId);
            if(paymentButton.getId() == 0)
                payment = 0;
            else payment = 1;
        }
    };
    AdapterView.OnItemSelectedListener itemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Resources res = getResources();
            companyCode = res.getStringArray(R.array.company_code_array)[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private class NewAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String str = "http://13.59.135.92/newinvoice.php?userID=" + TempData.getID() + "&companyCode=" + companyCode + "&invoiceNo=" + invoiceNo + "&payment=" + payment;
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
                Log.e("new invoice", line);

                if(line.equals("success")) {
                    finish();
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "다시 입력해주세요", Toast.LENGTH_SHORT).show();
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
