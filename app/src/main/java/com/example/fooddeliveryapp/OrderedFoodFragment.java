package com.example.fooddeliveryapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderedFoodFragment extends Fragment {
    private View RootView;
    private RecyclerView rv;
    private List<Food> ListOfOrderedFood;
    private TextView grandtotal;
    private Button PlaceOrder;
    private int total=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView=inflater.inflate(R.layout.fragment_order_details,container,false);
        rv=RootView.findViewById(R.id.recyclerView2);
        grandtotal=RootView.findViewById(R.id.grandTotal);
        PlaceOrder=RootView.findViewById(R.id.placeOrder);

        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        ListOfOrderedFood=new ArrayList<>();
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        getFood();
        return RootView;
    }

    private void placeOrder() {
        DatabaseReference Dr=FirebaseDatabase.getInstance().getReference("user");
        Dr.setValue(null);
        Toast.makeText(getContext(),"Order Placed Successfully",Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }


    private void getFood() {
//        FirebaseApp.initializeApp(getContext());
        total=0;
        DatabaseReference DatabaseRoot= FirebaseDatabase.getInstance().getReference("user");
        DatabaseRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                total=0;
                if(dataSnapshot.exists()){
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        Food food=child.getValue(Food.class);
                        assert food != null;
                        total+=food.getPrice()*food.getQuantity();
                        ListOfOrderedFood.add(food);
                    }
                    Log.v("onDataChange","database change occurred with new list size "+ListOfOrderedFood.size());
//                    for(int i=0;i<ListOfOrderedFood.size();i++) Log.v("entry "+i, ListOfOrderedFood.get(i).getName());
                    OrderedFoodAdapter adapter=new OrderedFoodAdapter(ListOfOrderedFood);
                    rv.setAdapter(adapter);
                    grandtotal.setText(total+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Failed to fetch from database",Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("onChildAdded","database change occurred with new list size "+ListOfOrderedFood.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("onChildChange","database change occurred with new list size "+ListOfOrderedFood.size());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.v("onChildRemoved","database change occurred with new list size "+ListOfOrderedFood.size());
                total=0;
                ListOfOrderedFood.clear();
                OrderedFoodAdapter adapter=new OrderedFoodAdapter(ListOfOrderedFood);
                rv.setAdapter(adapter);
                grandtotal.setText(total+"");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("onChildMoved","database change occurred with new list size "+ListOfOrderedFood.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
