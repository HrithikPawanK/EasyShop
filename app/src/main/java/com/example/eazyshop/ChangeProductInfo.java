package com.example.eazyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ChangeProductInfo extends AppCompatActivity {
    private EditText pname,pdes,pprice,pbarcode;
    private Button button,deleteButton;
    private String ID;
    private ImageView imageView;
    private DatabaseReference productsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_product_info);
        ID=getIntent().getExtras().get("barcode").toString();
        imageView=(ImageView)findViewById(R.id.aproduct_image);
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products").child(ID);
        pname=(EditText)findViewById(R.id.aproduct_name);
        pdes=(EditText)findViewById(R.id.aproduct_description);
        pprice=(EditText)findViewById(R.id.aproduct_price);
        pbarcode=(EditText)findViewById(R.id.aproduct_barcode);
        button=(Button)findViewById(R.id.apply_changes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyCHanges();
            }
        });
        deleteButton=(Button)findViewById(R.id.delete_item);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
        displayInfo();
    }

    private void deleteItem() {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ChangeProductInfo.this,"Item Deleted",Toast.LENGTH_SHORT).show();
                finish();
                Intent i =new Intent(ChangeProductInfo.this,AdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void applyCHanges() {
        String newName=pname.getText().toString();
        String newDes=pdes.getText().toString();
        String newPrice=pprice.getText().toString();
        String newBarcode=pbarcode.getText().toString();
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("barcode",newBarcode);
        productMap.put("price",newPrice);
        productMap.put("name",newName);
        productMap.put("description",newDes);
        productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ChangeProductInfo.this,"Chnages updated",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ChangeProductInfo.this,AdminActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void displayInfo() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name =dataSnapshot.child("name").getValue().toString();
                    String des =dataSnapshot.child("description").getValue().toString();
                    String price =dataSnapshot.child("price").getValue().toString();
                    String image =dataSnapshot.child("image").getValue().toString();
                    String barcode =dataSnapshot.child("barcode").getValue().toString();
                    pname.setText(name);
                    pdes.setText(des);
                    pprice.setText(price);
                    pbarcode.setText(barcode);
                    Picasso.get().load(image).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}