package com.example.fooddeliveryapp;

import java.util.HashMap;

public class Food {
    private int price,quantity;
    private String name,resName;
    Food(){

    }
    Food(int price, int quantity, String name){
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }
    Food(String name,int price,String resName){
        this.price=price;
        this.name=name;
        this.resName=resName;
    }
    public String getName() {
        return name;
    }
    public String getResName(){return resName;}
    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

//    public HashMap<String,String> toFirebaseObject(){
//        HashMap<String,String> newFood= new HashMap<>();
//        newFood.put("name",this.name);
//        newFood.put("price",this.price+"");
//        newFood.put("quantity",this.quantity+"");
//        return newFood;
//    }
}
