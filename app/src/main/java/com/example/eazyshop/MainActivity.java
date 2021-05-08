package com.example.eazyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void register(View view){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
    public void signin(View view){
        Intent i = new Intent(this,ScanItem.class);
        i.putExtra("Shop","Shop");
        startActivity(i);
    }
}