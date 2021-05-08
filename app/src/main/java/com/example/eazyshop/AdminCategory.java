package com.example.eazyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminCategory extends AppCompatActivity {
    private ImageView vegetables,junk_foods,dairy_products;
    private ImageView pastes,soaps,perfumes;
    private ImageView pens,books,others;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        vegetables=(ImageView) findViewById(R.id.add_edibles_vegetables);
        junk_foods=(ImageView) findViewById(R.id.add_edibles_junkfood);
        dairy_products=(ImageView) findViewById(R.id.add_edibles_dairy);
        pastes=(ImageView) findViewById(R.id.add_toiletries_pastes);
        soaps=(ImageView) findViewById(R.id.add_toiletries_soaps);
        perfumes=(ImageView) findViewById(R.id.add_toiletries_perfumes);
        pens=(ImageView) findViewById(R.id.add_stationary_pen);
        books=(ImageView) findViewById(R.id.add_stationary_books);
        others=(ImageView) findViewById(R.id.add_stationary_others);
        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","Vegetables and Fruits");
                        startActivity(i);
            }
        });
        junk_foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","Junk_foods");
                startActivity(i);
            }
        });
        dairy_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","Diary Products");
                startActivity(i);
            }
        });
        pastes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","pastes");
                startActivity(i);
            }
        });
        soaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","Face-Wash,soaps and shampoos");
                startActivity(i);
            }
        });
        perfumes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","perfumes and creams");
                startActivity(i);
            }
        });
        pens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","Pens,Pencils and Others");
                startActivity(i);
            }
        });
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","Papers,Charts and Books");
                startActivity(i);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategory.this,Add_Items.class);
                i.putExtra("category","Others");
                startActivity(i);
            }
        });
    }

}