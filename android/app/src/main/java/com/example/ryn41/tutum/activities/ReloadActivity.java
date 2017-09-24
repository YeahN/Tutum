package com.example.ryn41.tutum.activities;

import android.content.Intent;
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
import com.example.ryn41.tutum.etc.Constants;
import com.example.ryn41.tutum.etc.TempData;

public class ReloadActivity extends AppCompatActivity {

    private ArrayAdapter methodAdapter;
    private Spinner methodSpinner;

//    private String userID = "";
    private String payMethod = "";
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload);

//        Intent intent = getIntent();
//        userID = intent.getStringExtra("userID");
    }

    @Override
    public void onResume() {
        super.onResume();
        makeView();
        TempData.setFragmentIndex(Constants.FRAGMENT_PAY);
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView() {
        methodSpinner = (Spinner) findViewById(R.id.activity_reload_pay_method_spinner);
        methodAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.pay_method_name_array, android.R.layout.simple_spinner_dropdown_item);
        methodSpinner.setAdapter(methodAdapter);
        methodSpinner.setOnItemSelectedListener(itemSelect);

        ((Button) findViewById(R.id.activity_reload_reload_button)).setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.activity_reload_reload_button) {
                amount = Integer.parseInt(((EditText) findViewById(R.id.activity_reload_amount_edittext)).getText().toString());
                if(amount > 0) {
                    Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                    intent.putExtra("userID", TempData.getID());
                    intent.putExtra("payMethod", payMethod);
                    intent.putExtra("amount", amount);
                    startActivity(intent);
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
}
