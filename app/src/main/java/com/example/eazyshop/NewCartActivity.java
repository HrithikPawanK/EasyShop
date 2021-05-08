package com.example.eazyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eazyshop.Prevalent.Prevalent;
import com.example.eazyshop.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewCartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button b;
    private TextView t;
    private int price=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);
        recyclerView=findViewById(R.id.newcartlist);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        b=(Button)findViewById(R.id.proceed);
        t=(TextView)findViewById(R.id.displayprice);
        t.setText("Your Cart");
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NewCartActivity.this,ConfirmActivity.class);
                i.putExtra("price",price);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<NewCart> options=new FirebaseRecyclerOptions.Builder<NewCart>()
                .setQuery(reference.child("User View")
                        .child(Prevalent.onlineUser.getPhone()).child("Products"),NewCart.class)
                .build();
        FirebaseRecyclerAdapter<NewCart, CartViewHolder> adapter=
                new FirebaseRecyclerAdapter<NewCart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull NewCart model) {
                        holder.txtPrice.setText(model.getPrice());
                        holder.txtName.setText(model.getName());
                        holder.txtQuantity.setText(model.getQuantity());
                        holder.edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i=new Intent(NewCartActivity.this,ProductDetails.class);
                                i.putExtra("barcode",model.getBarcode());
                                startActivity(i);
                            }
                        });
                        holder.remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reference.child("User View").child(Prevalent.onlineUser.getPhone())
                                        .child("Products").child(model.getBarcode())
                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(NewCartActivity.this,"Item Deleted",Toast.LENGTH_SHORT).show();
                                            Intent i= new Intent(NewCartActivity.this,InventoryActivity.class);
                                            startActivity(i);
                                        }
                                    }
                                });
                            }
                        });
                        int onepro=(Integer.valueOf(model.getPrice()))*(Integer.valueOf(model.getQuantity()));
                        price=price+onepro;
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newcart_items_layout,parent,false);
                        CartViewHolder holder=new CartViewHolder(view);
                        return  holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}