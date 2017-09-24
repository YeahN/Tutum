package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.ryn41.tutum.R;

/**
 * Created by godjakoo on 2017. 9. 24..
 */

public class CompleteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        Intent intent= getIntent();
        Uri data= intent.getData();

        if(data != null){

        }
    }
}
