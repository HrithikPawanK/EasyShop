package com.example.eazyshop.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eazyshop.Interface.ItemClickListener;
import com.example.eazyshop.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtName,txtPrice,txtQuantity,edit,remove;
    private ItemClickListener itemClickListener;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName=itemView.findViewById(R.id.productsname);
        txtPrice=itemView.findViewById(R.id.productsprice);
        txtQuantity=itemView.findViewById(R.id.productsquantity);
        edit=itemView.findViewById(R.id.productsedit);
        remove=itemView.findViewById(R.id.productsremove);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
