package com.example.fooddeliveryapp;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FoodlistFragment extends Fragment {
    View RootView;
    RecyclerView rv;
    List<Food> ListOfFood;
    FragmentActionListener fragmentListener;
    FoodAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView=inflater.inflate(R.layout.fragment_food,container,false);
        rv=RootView.findViewById(R.id.recyclerView);
        ListOfFood=new ArrayList<>();
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        getFood();
        return RootView;
    }

    private void getFood() {
//        FirebaseApp.initializeApp(getContext());
        DatabaseReference DatabaseRoot= FirebaseDatabase.getInstance().getReference("Food");
        DatabaseRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        ListOfFood.add(child.getValue(Food.class));
                    }
                    adapter=new FoodAdapter(ListOfFood,fragmentListener,getContext());
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Failed to fetch from database",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setFragListener(FragmentActionListener fragmentListener){
        this.fragmentListener=fragmentListener;
    }

}
