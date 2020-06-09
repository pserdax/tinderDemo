package com.example.tiderdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


//    ChipNavigationBar bottomNav;
//    FragmentManager fragmentManager;
//    private static final String TAG = MainActivity.class.getSimpleName();
//
//
//
//    public void logout(View view){
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(getApplicationContext(), loginAc.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        bottomNav = findViewById(R.id.bottom_nav);
//
//        if(savedInstanceState==null){
//            bottomNav.setItemSelected(R.id.home, true);
//            fragmentManager = getSupportFragmentManager();
//            homeFragment homeFragment = new homeFragment();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, homeFragment)
//                    .commit();
//        }
//
//        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(int id) {
//                Fragment fragment = null;
//                switch (id) {
//                    case R.id.home:
//                        fragment = new homeFragment();
//                        break;
//
//                    case R.id.discover:
//                        fragment = new discoverFragment();
//                        break;
//
//                    case R.id.account:
//                        fragment = new profileFragment();
//                        break;
//
//                    case R.id.message:
//                        fragment = new messageFragment();
//                        break;
//                }
//
//                if(fragment!=null){
//                    fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.fragment_container, fragment)
//                            .commit();
//
//                }
//                else{
//                    Log.e(TAG, "Error in creating fragment");
//                }
//            }
//        });







    }

