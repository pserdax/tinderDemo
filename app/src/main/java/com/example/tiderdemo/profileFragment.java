package com.example.tiderdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
    private NavController navController;
    private static final String USERS = "users";


//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;



    public profileFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        if(fAuth.getCurrentUser() == null){
//            navController.navigate(R.id.action_mainmenuFragment_to_startFragment);
//        }
//
//    }

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
        navController = Navigation.findNavController(view);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId  = fAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(USERS);

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(requireActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                binding.emaill.setText(documentSnapshot.getString("email"));
                binding.phoneNumber.setText(documentSnapshot.getString("phoneNumber"));
                binding.fName.setText(documentSnapshot.getString("fName"));
                binding.lName.setText(documentSnapshot.getString("lName"));
               // binding.emaill.setText(documentSnapshot.getString("email"));

            }
        });




//        binding.emaill.setText(getArguments().getString("email"));
//        String email = binding.emaill.getText().toString().trim();
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//
//                    if(ds.child("email").getValue().equals(email)){
//                        binding.fName.setText(ds.child("fName").getValue(String.class));
//                        binding.lName.setText(ds.child("lName").getValue(String.class));
//                        binding.emaill.setText(ds.child("email").getValue(String.class));
//                        binding.phoneNumber.setText(ds.child("phoneNumber").getValue(String.class));
//
//
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


//        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    String fNamee = ""+ ds.child("fName").getValue();
//
//
//                    //set data
//
//                    binding.fullNamee.setText(fNamee);
//
//                }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        binding.logoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                navController.navigate(R.id.action_mainmenuFragment_to_startFragment);


            }
        });


    }
}
