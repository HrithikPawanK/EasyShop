package com.example.eazyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eazyshop.Model.Products;
import com.example.eazyshop.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {
//    private FloatingActionButton f;
    private TextView name,des,price,quantity;
    private Button decrement,increment,scan;
    private ImageView image;
    private String productID="";
    private int present=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productID=getIntent().getStringExtra("barcode");
        image=(ImageView)findViewById(R.id.product_image_details);
        name=(TextView)findViewById(R.id.product_name_details);
        des=(TextView)findViewById(R.id.product_description_details);
        price=(TextView)findViewById(R.id.product_price_details);
        quantity=(TextView)findViewById(R.id.item_quantity);
        decrement=(Button)findViewById(R.id.decrement_quantity);
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String present_value=quantity.getText().toString();
                int present=Integer.parseInt(present_value);
                if(present>0){
                    --present;
                    quantity.setText(String.valueOf(present));
                }
            }
        });
        increment=(Button)findViewById(R.id.increment_quantity);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String present_value=quantity.getText().toString();
                present=Integer.parseInt(present_value);
                if(present<10){
                    present++;
                    quantity.setText(String.valueOf(present));
                }
            }
        });
        scan=(Button)findViewById(R.id.scanitem);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ProductDetails.this,ScanItem.class);
                i.putExtra("Shop","Item");
                startActivity(i);
            }
        });
        getProductDetails(productID);
    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);
                    name.setText(products.getName());
                    des.setText(products.getDescription());
                    price.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void click(View view){

        addingToCart();
    }

    private void addingToCart() {
        if(present>0) {
            String saveCurrentDate;
            Calendar date = Calendar.getInstance();
            SimpleDateFormat currentData = new SimpleDateFormat("MM dd,yyyy");
            saveCurrentDate = currentData.format(date.getTime());
            DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("barcode", productID);
            cartMap.put("name", name.getText().toString());
            cartMap.put("price", price.getText().toString());
            cartMap.put("quantity", quantity.getText().toString());
            cartListRef.child("User View").child(Prevalent.onlineUser.getPhone())
                    .child("Products").child(productID).updateChildren(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProductDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                
                            }
                        }
                    });
        }
        else{
            Toast.makeText(ProductDetails.this,"Quantity not specified",Toast.LENGTH_SHORT).show();
        }
    }
}