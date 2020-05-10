package com.example.tiderdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginAc extends AppCompatActivity {

   private  EditText mEmail,mPass;
    private Button mlog_button;
    private TextView tvReg;
     FirebaseAuth mFirebaseAuth;
     FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mEmail = findViewById(R.id.logEmail);
        mPass = findViewById(R.id.logPass);
        tvReg = findViewById(R.id.tvReg);
        mlog_button  =(Button) findViewById(R.id.btnLogin);
        mFirebaseAuth = FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthStateListener  = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){

                    Toast.makeText(loginAc.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginAc.this, MainActivity.class);
                    startActivity(intent);


                }
                else{
                    Toast.makeText(loginAc.this, "Please log in!", Toast.LENGTH_SHORT).show();



                }
            }
        };

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginAc.this, registerAc.class);
                startActivity(intent);
            }
        });



        mlog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;

                email = mEmail.getText().toString();
                pass = mPass.getText().toString();

                if(email.equals("")){

                    Toast.makeText(loginAc.this, "Email Required", Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals("")){
                    Toast.makeText(loginAc.this, "Password Required", Toast.LENGTH_SHORT).show();
                }

                else if(!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(loginAc.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(loginAc.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();


                            }
                            else{

                                Intent intent = new Intent(loginAc.this, MainActivity.class);
                                startActivity(intent);

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(loginAc.this, "Error occurred", Toast.LENGTH_SHORT).show();

                }


            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}


//note: youtubedaki 4/4 videony gor we login pageni tazeden etmane synans sebabi yalnyslyk kan. registerAc alright ,,, registerAc-daky 42-nji setire uns ber currentgetUser(), sol yalnysdyrmasyn