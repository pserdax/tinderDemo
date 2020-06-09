package com.example.tiderdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;





/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {

    private TextView fullName, email, phoneNumber, twitter, location;
    private ImageView profilePic, icontwitter, iconLocation, iconPhone, iconEmail;

    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;



    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     fullName = view.findViewById(R.id.fullName);
     email = view.findViewById(R.id.email);
     phoneNumber = view.findViewById(R.id.phoneNumber);
     twitter = view.findViewById(R.id.twitter);
     location = view.findViewById(R.id.location);
     iconEmail = view.findViewById(R.id.iconEmail);
     iconPhone = view.findViewById(R.id.iconPhone);
     icontwitter = view.findViewById(R.id.iconTwitter);
     iconLocation = view.findViewById(R.id.iconLocation);

    }
}
