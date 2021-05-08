package com.example.eazyshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Add_Items extends AppCompatActivity {
    private String categoryName,iname,idescription,iprice,ibarcode,iavailable,cat;
    private ImageView img;
    private Button b;
    private EditText name,description,price,available,barcode,icategory;
    private static final int pic=1;
    private Uri uri;
    private StorageReference productImages;
    private String saveCurrentDate,saveCurrentTime,productKey,downloadImageUrl;
    private DatabaseReference productRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__items);
        categoryName=getIntent().getExtras().get("category").toString();
        icategory=(EditText)findViewById(R.id.item_category);
        icategory.setText(categoryName);
        productImages= FirebaseStorage.getInstance().getReference().child("Product Images");
        productRef=FirebaseDatabase.getInstance().getReference().child("Products");
        img=(ImageView)findViewById(R.id.item_img);
        b=(Button)findViewById(R.id.adding);
        name=(EditText)findViewById(R.id.item_name);
        description=(EditText)findViewById(R.id.item_description);
        price=(EditText)findViewById(R.id.item_price);
        available=(EditText)findViewById(R.id.item_available);
        barcode=(EditText)findViewById(R.id.item_code);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openGallery();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }
    private void validateData() {
        iname=name.getText().toString();
        cat=icategory.getText().toString();
        idescription=description.getText().toString();
        iprice=price.getText().toString();
        iavailable=available.getText().toString();
        ibarcode=barcode.getText().toString();
        if(iname==null ){
            Toast.makeText(Add_Items.this,"Fill the name",Toast.LENGTH_SHORT).show();
        }
        else if(idescription==null ){
            Toast.makeText(Add_Items.this,"Fill description",Toast.LENGTH_SHORT).show();
        }
        else if(iprice==null ){
            Toast.makeText(Add_Items.this,"Fill the price",Toast.LENGTH_SHORT).show();
        }
        else if(iavailable==null ){
            Toast.makeText(Add_Items.this,"Fill available",Toast.LENGTH_SHORT).show();
        }
        else if(ibarcode==null ){
            Toast.makeText(Add_Items.this,"Fill the barcode",Toast.LENGTH_SHORT).show();
        }
        else{
            storeInfo();
        }
    }
    private void openGallery() {
        Intent gallery=new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,pic);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pic && resultCode==RESULT_OK && data!=null){
            uri=data.getData();
            img.setImageURI(uri);
        }

    }
    private void storeInfo() {
        Calendar calendar=Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate=new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime=currentTime.format(calendar.getTime());
        productKey=saveCurrentDate+saveCurrentTime;
        StorageReference filepath=productImages.child(uri.getLastPathSegment()+productKey+".jpeg");
        final UploadTask uploadTask=filepath.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(Add_Items.this,message,Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Add_Items.this,"Image uploaded",Toast.LENGTH_LONG).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl=task.getResult().toString();
                            Toast.makeText(Add_Items.this,"Prouct Image added successfully",Toast.LENGTH_SHORT).show();
                            HashMap<String, Object> productMap=new HashMap<>();
                            productMap.put("pid",productKey);
                            productMap.put("name",iname);
                            productMap.put("description",idescription);
                            productMap.put("price",iprice);
                            productMap.put("image",downloadImageUrl);
                            productMap.put("available",iavailable);
                            productMap.put("barcode",ibarcode);
                            productMap.put("category",cat);
//                            this
                            productRef.child(ibarcode).updateChildren(productMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Add_Items.this,"Successfully added",Toast.LENGTH_SHORT).show();
                                                Intent i=new Intent(Add_Items.this,AdminActivity.class);
                                                startActivity(i);
                                            }
                                            else{
                                                String message=task.getException().toString();
                                                Toast.makeText(Add_Items.this,message,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

}