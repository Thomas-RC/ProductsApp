package com.rosdesign.productsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * KLasa głównego adaptera widoku za pomocą listy dynamicznej RecyclerView
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    Context context;
    List<Product> productList;

    public CustomAdapter(Context context, List<Product> productList)
    {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, final int position)
    {
        final Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        holder.timestamp.setText(product.getCreated_at());
    }

    public int getItemCount()
    {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, price, timestamp;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView2);
            price = itemView.findViewById(R.id.priceTextView);
            timestamp = itemView.findViewById(R.id.timeTextView);
        }
    }

}
