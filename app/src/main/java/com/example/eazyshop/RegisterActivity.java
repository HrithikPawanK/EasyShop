package com.example.eazyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText e1,e2,e3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1=(EditText)findViewById(R.id.register_username);
        e2=(EditText)findViewById(R.id.register_number);
        e3=(EditText)findViewById(R.id.register_password);
    }
    public void createAccount(View view){
        account();
    }
    private void account(){
        String name=e1.getText().toString();
        String number=e2.getText().toString();
        String password=e3.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter name",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(number)){
            Toast.makeText(this,"Enter phone number",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
        }
        else{
            validatePhoneNUmber(name,number,password);
        }
    }

    private void validatePhoneNUmber(String name, String number, String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(number).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("user",name);
                    userDataMap.put("phone",number);
                    userDataMap.put("password",password);
                    RootRef.child("Users").child(number).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Successfully Registered!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"ERROR: please try again after some time...",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this,"User already exists!",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(RegisterActivity.this,MainActivity.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}