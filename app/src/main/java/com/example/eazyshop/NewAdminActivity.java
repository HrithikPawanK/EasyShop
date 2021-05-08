package com.example.eazyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewAdminActivity extends AppCompatActivity {
    private Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin);
        b1=(Button)findViewById(R.id.adda);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NewAdminActivity.this,AdminCategory.class);
                startActivity(i);
            }
        });
        b2=(Button)findViewById(R.id.removea);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NewAdminActivity.this,InventoryActivity.class);
                i.putExtra("Admin","Admin");
                startActivity(i);
            }
        });
        b3=(Button)findViewById(R.id.transcationsa);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(NewAdminActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}