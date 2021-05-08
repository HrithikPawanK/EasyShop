package com.example.eazyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Redirect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);
        starting();
    }

    private void starting() {
        Intent i=new Intent(Redirect.this,InventoryActivity.class);
        i.putExtra("Admin","Admin");
        startActivity(i);
    }
}