package com.example.tiderdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerAc extends AppCompatActivity {




     private EditText mEmail,mPass, mConfPass;
     private Button mReg_button;
     private TextView tvLog;
     private FirebaseAuth mAuth;
     private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail =  findViewById(R.id.regEmail);
        mPass =  findViewById(R.id.regPass);
        mConfPass =  findViewById(R.id.regPassConfirm);
        mReg_button =  findViewById(R.id.btnRegister);
        tvLog =  findViewById(R.id.tvSign);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);



        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(registerAc.this, MainActivity.class);
            startActivity(intent);
            finish();


        }





        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inti = new Intent(registerAc.this, loginAc.class);
                startActivity(inti);
            }
        });


        mReg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, pass, confPass;

                     email = mEmail.getText().toString();
                     pass = mPass.getText().toString();
                     confPass = mConfPass.getText().toString();

                if(email.equals("")){

                    Toast.makeText(registerAc.this, "Email Required", Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals("")){
                    Toast.makeText(registerAc.this, "Password Required", Toast.LENGTH_SHORT).show();



                }
                else if(confPass.equals("")){

                    Toast.makeText(registerAc.this, "Confirmation password Required", Toast.LENGTH_SHORT).show();
                }
                else if(!confPass.equals(pass)){

                    Toast.makeText(registerAc.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }


                else if(confPass.equals(pass)){
                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(registerAc.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(registerAc.this, "Registered Successfully", Toast.LENGTH_SHORT).show();



                                Intent intent = new Intent(registerAc.this, MainActivity.class);
                                startActivity(intent);


                            }
                            else{

                                task.getException().printStackTrace();

                                        Toast.makeText(registerAc .this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                            }
                        }
                    });








            }}
        });
    }
}
