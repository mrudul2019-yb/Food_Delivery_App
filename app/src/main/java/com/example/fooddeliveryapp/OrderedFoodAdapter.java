package com.example.fooddeliveryapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderedFoodAdapter extends RecyclerView.Adapter<OrderedFoodAdapter.OrderedFoodHolder> {
    int mItemCount;
    List<Food> ListOfFood;
    @NonNull
    @Override
    public OrderedFoodAdapter.OrderedFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
//        View view=View.inflate(context,R.layout.foodcard,parent);
        View view= LayoutInflater.from(context).inflate(R.layout.ordercard,parent,false);
        return new OrderedFoodAdapter.OrderedFoodHolder(view);
    }
    OrderedFoodAdapter(List<Food> ListOfFood){
        this.ListOfFood=ListOfFood;
        mItemCount=ListOfFood.size();
    }


    @Override
    public void onBindViewHolder(@NonNull final OrderedFoodAdapter.OrderedFoodHolder holder, int position) {
        //calc quant*price
        Food foodItem=ListOfFood.get(position);
        holder.mName.setText(foodItem.getName());
        holder.mPrice.setText("Rs "+foodItem.getPrice()+" x "+ foodItem.getQuantity()+" = Rs "+foodItem.getPrice()*foodItem.getQuantity()+"");
        holder.mQuantity.setText(foodItem.getQuantity()+"");

    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }
    class OrderedFoodHolder extends RecyclerView.ViewHolder{
        TextView mName,mPrice,mQuantity;
        ImageView mImg;
        public OrderedFoodHolder(@NonNull View itemView) {

            super(itemView);
            mName=(TextView) itemView.findViewById(R.id.finalName);
            mPrice=(TextView)itemView.findViewById(R.id.finalPrice);
            mQuantity=(TextView) itemView.findViewById(R.id.finalQuantity);
        }
    }


}
