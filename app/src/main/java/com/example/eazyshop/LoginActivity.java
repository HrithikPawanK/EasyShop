package com.example.eazyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eazyshop.Model.Users;
import com.example.eazyshop.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText e1,e2;
    private TextView t1,t2;
    Button b;
    String parentDBName="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=(EditText)findViewById(R.id.login_number);
        e2=(EditText)findViewById(R.id.login_password);
        t1=(TextView)findViewById(R.id.admin);
        t2=(TextView)findViewById(R.id.not_admin);
        b=(Button)findViewById(R.id.login);
    }
    public void login(View view){
        loginUser();
    }
    public  void  adminUser(View view){
        b.setText("Login Admin");
        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.VISIBLE);
        parentDBName="Admins";
    }
    public void notAdminUser(View view){
        b.setText("Login");
        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.INVISIBLE);
        parentDBName="Users";
    }

    private void loginUser() {
        String phone=e1.getText().toString();
        String password=e2.getText().toString();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(LoginActivity.this,"Enter phone number",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Enter password",Toast.LENGTH_LONG).show();
        }
        else{
            AllowAccessAccount(phone,password);
        }
    }

    private void AllowAccessAccount(String phone, String passwd) {
        final DatabaseReference RootRef;
        RootRef=FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBName).child(phone).exists()){
                    Users userData=dataSnapshot.child(parentDBName).child(phone).getValue(Users.class);
                    if(userData.getPhone().equals(phone)){
                        if(userData.getPassword().equals(passwd)){
                            if(parentDBName.equals("Admins")){
                                Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(LoginActivity.this,AdminActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(LoginActivity.this,InventoryActivity.class);
                                intent.putExtra("Admin","NOTAdmin");
                                Prevalent.onlineUser=userData;
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Incorrect password",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"User doesnt not Exist",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}