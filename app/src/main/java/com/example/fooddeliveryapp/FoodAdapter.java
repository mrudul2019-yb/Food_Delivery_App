package com.example.fooddeliveryapp;

import android.content.Context;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
import java.util.zip.Inflater;

//import static androidx.core.graphics.drawable.IconCompat.getResources;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {
    int mItemCount;
    List<Food> ListOfFood;
    Context context;
    FragmentActionListener mClickLister;
    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
//        View view=View.inflate(context,R.layout.foodcard,parent);
        View view= LayoutInflater.from(context).inflate(R.layout.foodcard,parent,false);
        return new FoodHolder(view);
    }
    FoodAdapter(List<Food> ListOfFood, FragmentActionListener ClickListener,Context context){
        this.ListOfFood=ListOfFood;
        mItemCount=ListOfFood.size();
        this.context=context;
        mClickLister=ClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodHolder holder, int position) {
        Food food=ListOfFood.get(position);
        holder.mPrice.setText(food.getPrice()+"");
        holder.mName.setText(food.getName());
        Log.v("resId",food.getResName()+" ");
        int id = context.getResources().getIdentifier(food.getResName(), "drawable", context.getPackageName());
        Drawable drawable=context.getResources().getDrawable(id);
        holder.mImg.setImageDrawable(drawable);
        final Bundle bundle =new Bundle();
        bundle.putString("Name",holder.getName());
        bundle.putInt("Price",food.getPrice());
        bundle.putString("resName",food.getResName());
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                Toast.makeText(holder.itemView.getContext(),"Show details",Toast.LENGTH_SHORT).show();
                mClickLister.showDetailsOf(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }
    class FoodHolder extends RecyclerView.ViewHolder{
        TextView mName,mPrice;
        ImageView mImg;
        public FoodHolder(@NonNull View itemView) {

            super(itemView);
            mName=(TextView) itemView.findViewById(R.id.name);
            mPrice=(TextView)itemView.findViewById(R.id.price);
            mImg=(ImageView)itemView.findViewById(R.id.imgId);
        }
        public String getName(){

           return mName.getText().toString();
        }
    }
}
