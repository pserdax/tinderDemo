package com.example.tiderdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.tiderdemo.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class loginFragment extends Fragment  {

//    EditText mEmail,mPassword;
//    Button mLoginBtn;
//    TextView tvReg,forgotTextLink;
//    ProgressBar progressBar;

    FirebaseAuth fAuth;
    private FragmentLoginBinding binding;

    public loginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login, container, false);

         binding = FragmentLoginBinding.inflate(inflater, container, false);

         return binding.getRoot();
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

//        Button btnLogin  = view.findViewById(R.id.btnLogin);
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //      navController.navigate(R.id.action_third_to_main);
//
//               // navController.navigate(R.id.action_loginFragment_to_registerFragment);
//            }
//        });

//        tvReg = view.findViewById(R.id.tvReg);
//        mEmail = view.findViewById(R.id.logEmail);
//        mPassword = view.findViewById(R.id.logPass);
//        progressBar = view.findViewById(R.id.progressBar);
//        mLoginBtn = view.findViewById(R.id.btnLogin);

          fAuth = FirebaseAuth.getInstance();



         binding.tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.logEmail.getText().toString().trim();
                String password = binding.logPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    binding.logEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    binding.logPass.setError("Password is Required.");
                    return;
                }

                binding.progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Logged in Successfully!",Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.action_loginFragment_to_mainmenuFragment);

                        }else {
                            Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }

                    }
                });


            }
        });




    }
}
