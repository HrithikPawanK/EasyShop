package com.example.eazyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eazyshop.Model.Products;
import com.example.eazyshop.Prevalent.Prevalent;
import com.example.eazyshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

public class InventoryActivity extends AppCompatActivity {
    private TextView user;
    private ImageView im1,im2,im3,im4;
    private DatabaseReference productsRef;
    private RecyclerView recyclerView;
    private String type="";
    private RelativeLayout l;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
//        type=getIntent().getExtras().get("Admin").toString();
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        user=(TextView)findViewById(R.id.user_name);
//        user.setText(Prevalent.onlineUser.getName());
        im1=(ImageView)findViewById(R.id.scan);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(InventoryActivity.this,ScanItem.class);
                i.putExtra("Shop","Item");
                startActivity(i);
            }
        });
        im2=(ImageView)findViewById(R.id.cart);
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InventoryActivity.this,NewCartActivity.class);
                startActivity(intent);
            }
        });
        im3=(ImageView)findViewById(R.id.category);
        im4=(ImageView)findViewById(R.id.logout);
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(InventoryActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        if(type.equals("Admin")){
            im1.setVisibility(View.INVISIBLE);
            im2.setVisibility(View.INVISIBLE);
            im3.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(productsRef,Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.name.setText(model.getName());
                        holder.des.setText(model.getDescription());
                        holder.price.setText("Price" + model.getPrice() + "rupees");
                        Picasso.get().load(model.getImage()).into(holder.image);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                if(type.equals("Admin")){
//                                    Intent i=new Intent(InventoryActivity.this,ChangeProductInfo.class);
//                                    i.putExtra("barcode",model.getBarcode());
//                                    startActivity(i);
//                                }
//                                if(type.equals("NOTAdmin")){
//                                    Intent i=new Intent(InventoryActivity.this,ProductDetails.class);
//                                    i.putExtra("barcode",model.getBarcode());
//                                    startActivity(i);
//                                }
                                   Intent i=new Intent(InventoryActivity.this,ProductDetails.class);
                                   i.putExtra("barcode",model.getBarcode());
                                    startActivity(i);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}