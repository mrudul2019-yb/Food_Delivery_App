package com.example.fooddeliveryapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FoodDetailsFragment extends Fragment {
    View RootView;
    ImageView mFoodImage;
    TextView mFoodName,mFoodPrice;
    EditText mQuantity;
    Button mButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView=inflater.inflate(R.layout.fragment_food_details,container,false);
        mFoodName=RootView.findViewById(R.id.foodName);
        mFoodPrice=RootView.findViewById(R.id.foodPrice);
        mQuantity=RootView.findViewById(R.id.Quantity);
        mFoodImage=RootView.findViewById(R.id.DetailImage);
        mButton=RootView.findViewById(R.id.Add);
        Bundle bundle=getArguments();
        assert bundle != null;
        mFoodName.setText(bundle.getString("Name"));
        mFoodPrice.setText(bundle.getInt("Price")+"");
        Context context=getContext();
        int id=getResources().getIdentifier(bundle.getString("resName"),"drawable",context.getPackageName());
        mFoodImage.setImageDrawable(context.getResources().getDrawable(id));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Add button","working");
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
// Add the buttons
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        AddFood();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
// Set other dialog properties
                builder.setMessage("This will be Rs "+mFoodPrice.getText()+" X "+mQuantity.getText()+" = Rs "+Integer.parseInt(mFoodPrice.getText().toString())*Integer.parseInt(mQuantity.getText().toString()));


// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return RootView;
    }
    public void AddFood(){
        DatabaseReference user=FirebaseDatabase.getInstance().getReference("user");
        Food newFood= new Food(Integer.parseInt(mFoodPrice.getText().toString()),Integer.parseInt(mQuantity.getText().toString()),mFoodName.getText().toString());
        String Key=user.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( Key, newFood);
        user.updateChildren(childUpdates);
        Toast.makeText(getContext(),"Added to cart",Toast.LENGTH_SHORT).show();
    }
}
