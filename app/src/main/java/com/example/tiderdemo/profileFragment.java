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
import android.content.Intent;

import com.example.tiderdemo.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Queue;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {

//    private TextView fullName, email, phoneNumber, twitter, location;
//    private ImageView profilePic, icontwitter, iconLocation, iconPhone, iconEmail;

    FirebaseAuth fAuth;
    FirebaseUser user;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FragmentProfileBinding binding;
    private String userId;
    private FirebaseFirestore fStore;

//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;



    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View view = inflater.inflate(R.layout.fragment_profile, container, false);
       // return view;

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        View view =  binding.getRoot();
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





//        Intent intent  = getActivity().getIntent();
//        final String email  = intent.getStringExtra("email");
//        firebaseDatabase  = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference(USERS);
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    if(ds.child("email").getValue().equals(email)){
//
//                        binding.fullName.setText(ds.child("fname").getValue(String.class));
//                        binding.emaill.setText(email);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//


//
//     fullName = view.findViewById(R.id.fullName);
//     email = view.findViewById(R.id.email);
//     phoneNumber = view.findViewById(R.id.phoneNumber);
//     twitter = view.findViewById(R.id.twitter);
//     location = view.findViewById(R.id.location);
//     iconEmail = view.findViewById(R.id.iconEmail);
//     iconPhone = view.findViewById(R.id.iconPhone);
//     icontwitter = view.findViewById(R.id.iconTwitter);
//     iconLocation = view.findViewById(R.id.iconLocation);


        fAuth = FirebaseAuth.getInstance();
        user  = fAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String fNamee = ""+ ds.child("fName").getValue();


                    //set data

                    binding.fullNamee.setText(fNamee);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //fStore = FirebaseFirestore.getInstance();

//        userId = fAuth.getCurrentUser().getUid();
//
//        final DocumentReference documentReference = fStore.collection("users").document(userId);
//
//        documentReference.addSnapshotListener((Executor) this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
//
//                binding.fullNamee.setText(documentSnapshot.getString("fName"));
//
//
//            }
//        });



    }
}
