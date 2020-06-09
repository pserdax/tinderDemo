package com.example.tiderdemo;

import android.content.Intent;
import android.os.Bundle;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class registerFragment extends Fragment {



     EditText mEmail,mPass, mConfPass, mFullname;
     Button mReg_button;
     TextView tvLog;
     FirebaseAuth mAuth;
     ProgressBar progressBar1;
     FirebaseFirestore fStore;
     String userID;

    public registerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);


        mEmail =  view.findViewById(R.id.regEmail);
        mFullname  = view.findViewById(R.id.regFullname);
        mPass =  view.findViewById(R.id.regPass);
        mConfPass =  view.findViewById(R.id.regPassConfirm);
        mReg_button =  view.findViewById(R.id.btnRegister);
        tvLog =  view.findViewById(R.id.tvSign);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar1 = view.findViewById(R.id.progressBar1);


        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        mReg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email, pass, confPass, fullname;

                email = mEmail.getText().toString().trim();
                pass = mPass.getText().toString().trim();
                confPass = mConfPass.getText().toString().trim();
                fullname = mFullname.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    mPass.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(confPass)){
                    mConfPass.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(fullname)){
                    mConfPass.setError("Full name is Required.");
                    return;
                }
                if(!confPass.equals(pass)){

                    Toast.makeText(getActivity(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                }


                    progressBar1.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        userID = mAuth.getCurrentUser().getUid();

                                        DocumentReference documentReference = fStore.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("fName", fullname);
                                        user.put("email", email);
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("HEY: ", "user profile is created for: "+userID);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error: ","onFailture: "+e.toString());
                                            }
                                        });

                                      //  task.getException().printStackTrace();
                                        //Log.i("errorrr: ", "t"+task.getException());


                                        navController.navigate(R.id.action_registerFragment_to_mainmenuFragment);

                                    }


                                      //  task.getException().printStackTrace();


                                        //Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        progressBar1.setVisibility(View.GONE);

                                }
                            });








                }



        });

    }
}
