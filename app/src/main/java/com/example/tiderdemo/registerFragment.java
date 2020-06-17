package com.example.tiderdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.tiderdemo.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class registerFragment extends Fragment {



//     EditText mEmail,mPass, mConfPass, mFullname;
//     Button mReg_button;
//     TextView tvLog;
//     ProgressBar progressBar1;

     FirebaseAuth mAuth;
     FirebaseFirestore fStore;
     String userID;

     private FragmentRegisterBinding binding;

    public registerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_register, container, false);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
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

        final NavController navController = Navigation.findNavController(view);


//        mEmail =  view.findViewById(R.id.regEmail);
//        mFullname  = view.findViewById(R.id.regFullname);
//        mPass =  view.findViewById(R.id.regPass);
//        mConfPass =  view.findViewById(R.id.regPassConfirm);
//        mReg_button =  view.findViewById(R.id.btnRegister);
//        tvLog =  view.findViewById(R.id.tvSign);
          mAuth = FirebaseAuth.getInstance();
          fStore = FirebaseFirestore.getInstance();
//        progressBar1 = view.findViewById(R.id.progressBar1);


        binding.tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email, pass, confPass, firstname, lastname, phoneNumber;

                email = binding.regEmail.getText().toString().trim();
                pass = binding.regPass.getText().toString().trim();
                confPass = binding.regPassConfirm.getText().toString().trim();
                firstname = binding.regFirstName.getText().toString().trim();
                lastname = binding.lastName.getText().toString().trim();
                phoneNumber = binding.phoneNumber.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    binding.regEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    binding.btnRegister.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(confPass)){
                    binding.regPassConfirm.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(firstname)){
                    binding.regFirstName.setError("First name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(lastname)){
                    binding.lastName.setError("Last name is Required.");
                    return;
                }
                if(!confPass.equals(pass)){

                    Toast.makeText(getActivity(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                }


                    binding.progressBar1.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        userID = mAuth.getCurrentUser().getUid();

                                        DocumentReference documentReference = fStore.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("fName", firstname);
                                        user.put("lName", lastname);
                                        user.put("email", email);
                                        user.put("phoneNumber", phoneNumber);
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

                                        navController.navigate(R.id.action_registerFragment_to_mainmenuFragment);

                                    }


                                      //  task.getException().printStackTrace();


                                        //Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        binding.progressBar1.setVisibility(View.GONE);

                                }
                            });








                }



        });

    }
}
