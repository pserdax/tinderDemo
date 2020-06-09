package com.example.tiderdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class mainmenuFragment extends Fragment {

    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;
    private static final String TAG = MainActivity.class.getSimpleName();




    public mainmenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mainmenu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onCreate(savedInstanceState);
        bottomNav = view.findViewById(R.id.bottom_nav);

        if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.home, true);
            fragmentManager = getChildFragmentManager();
            homeFragment homeFragment = new homeFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();


        }
        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id) {
                    case R.id.home:
                        fragment = new homeFragment();
                        break;

                    case R.id.discover:
                        fragment = new discoverFragment();
                        break;

                    case R.id.account:
                        fragment = new profileFragment();
                        break;

                    case R.id.message:
                        fragment = new messageFragment();
                        break;
                }

                if(fragment!=null){
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();

                }
                else{
                    Log.e(TAG, "Error in creating fragment");
                }
            }
        });

    }
}
