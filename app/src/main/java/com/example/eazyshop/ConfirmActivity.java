package com.example.eazyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ConfirmActivity extends AppCompatActivity {
    private  String price;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        price=getIntent().getExtras().get("price").toString();
        textView=(TextView)findViewById(R.id.showPrice);
        textView.setText("Bill: "+price);
    }
}