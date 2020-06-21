package com.example.fooddeliveryapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements FragmentActionListener{
    FragmentManager  mFragmentManager;
//    FirebaseDatabase mFirebaseInstance;
    FragmentTransaction mFragmentTransaction;
    FloatingActionButton fab;
    DrawerLayout mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager=getSupportFragmentManager();
        fab=(FloatingActionButton) findViewById(R.id.fab);
        mDrawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navView=findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.v("NavItemSelected","Something Was Picked");
                switch(item.getItemId()){
                    case R.id.close:
                        Log.v("NavView","close");
                        finish();
                }
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentTransaction=mFragmentManager.beginTransaction();
                Fragment detailedFragment=new OrderedFoodFragment();
//                fab.setVisibility(View.GONE);
                mFragmentTransaction.replace(R.id.fragContainer,detailedFragment,"CurrentList");
                mFragmentTransaction.addToBackStack("CurrentList");
                mFragmentTransaction.commit();
            }
        });
         mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener(){

            @Override
            public void onBackStackChanged() {
                String tag=getTopFragmentTag();
                if(tag!=null&&tag.equals("CurrentList")){
                    fab.setVisibility(View.GONE);
                }
                else{
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });
        Log.v("onCreate(MainActivity)","called");
        addFoodList();
    }

    private void addFoodList() {
        mFragmentTransaction=mFragmentManager.beginTransaction();
        FoodlistFragment FoodList=new FoodlistFragment();
        mFragmentTransaction.add(R.id.fragContainer,FoodList);
        FoodList.setFragListener(MainActivity.this);

        mFragmentTransaction.commit();
    }

    @Override
    public void showDetailsOf(Bundle bundle) {
        mFragmentTransaction=mFragmentManager.beginTransaction();
        Fragment detailedFragment=new FoodDetailsFragment();
        detailedFragment.setArguments(bundle);
        mFragmentTransaction.replace(R.id.fragContainer,detailedFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }
    public String getTopFragmentTag() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return fragmentTag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_clear_cart:
                clearCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearCart() {
        DatabaseReference Dr=FirebaseDatabase.getInstance().getReference("user");
        Dr.setValue(null);
    }

}
