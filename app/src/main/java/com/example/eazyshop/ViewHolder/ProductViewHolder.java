package com.example.eazyshop.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eazyshop.Interface.ItemClickListener;
import com.example.eazyshop.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name,des,price;
    public ImageView image;
    public  ItemClickListener listener;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        image=(ImageView) itemView.findViewById(R.id.product_image);
        name=(TextView) itemView.findViewById(R.id.product_name);
        des=(TextView) itemView.findViewById(R.id.product_description);
        price=(TextView) itemView.findViewById(R.id.product_price);
    }
    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        listener.OnClick(v, getAdapterPosition(),false);
    }
}
