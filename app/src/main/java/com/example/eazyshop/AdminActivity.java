package com.example.eazyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    private Button inventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toast.makeText(this,"Welcome Admin",Toast.LENGTH_LONG).show();
        inventory=(Button) findViewById(R.id.remove);
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminActivity.this,ChangeNow.class);
                startActivity(i);
            }
        });
    }
    public void addItem(View view){
        Intent i=new Intent(AdminActivity.this,AdminCategory.class);
        startActivity(i);
    }
    public void logout(View view){
        finish();
        Intent i=new Intent(AdminActivity.this,MainActivity.class);
        startActivity(i);
    }
}