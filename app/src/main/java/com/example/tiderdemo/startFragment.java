package com.example.tiderdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

//import com.example.tiderdemo.utils.Constants;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

//import com.facebook.appevents.AppEventsLogger;
//import static com.example.tiderdemo.utils.Constants.EMAIL_FB;

import com.example.tiderdemo.databinding.FragmentStartBinding;
import com.facebook.GraphRequest;
import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//importing objects from constants class;

import static com.example.tiderdemo.Constants.AUTH_PROVIDER;
import static com.example.tiderdemo.Constants.EMAIL_FB;
import static com.example.tiderdemo.Constants.FACEBOOK_PROVIDER;
import static com.example.tiderdemo.Constants.FIRST_NAME_FB;
import static com.example.tiderdemo.Constants.EMAIL;
import static com.example.tiderdemo.Constants.IMG_REFS;
import static com.example.tiderdemo.Constants.LAST_NAME;
import static com.example.tiderdemo.Constants.LAST_NAME_FB;
import static com.example.tiderdemo.Constants.UID;
//import static com.example.tiderdemo.Constants.LAST_NAME_FB;


import static android.content.ContentValues.TAG;
import static com.example.tiderdemo.Constants.SP_FILE;
import static com.facebook.appevents.UserDataStore.FIRST_NAME;


public class startFragment extends Fragment {

    private FragmentStartBinding binding;

    FirebaseAuth fAuth;
    CallbackManager callbackManager;
    private LoginManager loginManager;
    private String TAG = "facebookAuthentication";
    LoginButton loginButton;
    String usernameFb;
    private NavController navController;
    GoogleSignInClient mGoogleSignInClient;


    private String imgUrl;
    private String userEmail;
    private String fName;
    private String lName;


    private GoogleApiClient mGoogleApiClient;
   // private String lastName;

    //private LoginButton fbSign;

    private AccessToken token;

    private final static int RC_SIGN_IN = 1;





    public startFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStartBinding.inflate(inflater, container, false);
        View view =  binding.getRoot();



        return view;


        // Inflate the layout for this fragment
//        View view =  inflater.inflate(R.layout.fragment_start, container, false);
//        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    @Override
       public void onStart() {
        super.onStart();

        FirebaseUser user =fAuth.getCurrentUser();
        if(user != null){
            navController.navigate(R.id.action_startFragment_to_mainmenuFragment);
        }
    }

    private void authenticateWithFacebook(){
        loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", EMAIL_FB));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              //  ((OnboardingUtils) requireActivity()).hideProgressBar();
                token = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(token,

                        //THIS -> is lambda expression, in order to make it enable you need to upgrade your java to 8. i added some dependencies to build gradle(app)
                        (object, response) -> {
                            try {
                                URL fbProfilePicture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=500&height=500");
                                imgUrl = fbProfilePicture.toString();
                                userEmail = object.getString(EMAIL_FB);
                                fName = object.getString(FIRST_NAME_FB);
                                lName = object.getString(LAST_NAME_FB);


                            } catch (JSONException | MalformedURLException e) {
                                e.printStackTrace();
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email");
                request.setParameters(parameters);
                request.executeAsync();
                FacebookSdk.setAutoLogAppEventsEnabled(true);
                FacebookSdk.setAdvertiserIDCollectionEnabled(true);
                handleFacebookAccessToken(token);
            }


            @Override
            public void onCancel() {

                Toast.makeText(getActivity(), "fb authentication cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();


            }
        });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         navController = Navigation.findNavController(view);

        fAuth = FirebaseAuth.getInstance();
        // FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        loginManager = LoginManager.getInstance();
        callbackManager  = CallbackManager.Factory.create();



        //with GMAIL
//        createRequest();
//
//        binding.ggSign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                signIn();
//            }
//        });




        binding.fbSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateWithFacebook();
            }
        });



        binding.emmSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              navController.navigate(R.id.action_startFragment_to_loginFragment);
            }
        });
        binding.tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_startFragment_to_registerFragment);
            }
        });

    }

//    private void createRequest() {
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//    }

//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                // ...
//            }
//        }
//    }



//    private void handleFacebookAccessToken(AccessToken token) {
////        Log.d(TAG, "handleFacebookAccessToken:" + token);
////       // progressDialog.show();
////
////        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
////        fAuth.signInWithCredential(credential)
////                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        if (task.isSuccessful()) {
////                            // Sign in success, update UI with the signed-in user's information
////                            Log.d(TAG, "signInWithCredential:success");
////                            FirebaseUser user = fAuth.getCurrentUser();
////
////                            navController.navigate(R.id.action_startFragment_to_mainmenuFragment);
////
////
////                        } else {
////                            // If sign in fails, display a message to the user.
////                            Log.w(TAG, "signInWithCredential:failure", task.getException());
////                            Toast.makeText(getActivity(), "" + task.getException().getLocalizedMessage(),
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                      //  progressDialog.dismiss();
////                    }
////                });
////    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential fbCredential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(fbCredential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        boolean isNew = false;
                        String uId = "";
                        if (task.getResult() != null && task.getResult().getAdditionalUserInfo() != null) {
                            isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (task.getResult().getUser() != null)
                                uId = task.getResult().getUser().getUid();
                        }
                        if (!isNew) {
                            gotoHomeScreen();
                        } else {
                            saveUserFbData(uId);
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_startFragment_to_mainmenuFragment);
                        }
                    } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            user.delete()
                                    .addOnCompleteListener(task12 -> {
                                        if (task12.isSuccessful()) {
                                            FirebaseAuth.getInstance().getCurrentUser().linkWithCredential(fbCredential)
                                                    .addOnCompleteListener(task1 -> {
                                                        if (task1.isSuccessful()) {
                                                           // DataManager.getInstance().saveLoggedInStatus(true);
                                                            gotoHomeScreen();
                                                        } else {
                                                            if (task1.getException() != null) {
                                                                ((OnboardingUtils) requireActivity()).showToastMessage(task1.getException().getLocalizedMessage());
                                                            } else {
                                                                Toast.makeText(requireActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                        } else {
                            ((OnboardingUtils) requireActivity()).showToastMessage(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    private void gotoHomeScreen(){

        navController.navigate(R.id.action_startFragment_to_mainmenuFragment);


    }
    private void saveUserFbData(String uId){

        SharedPreferences mPreferences = requireActivity().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(FIRST_NAME, fName);
        preferencesEditor.putString(LAST_NAME, lName);
        preferencesEditor.putString(EMAIL, userEmail);
        preferencesEditor.putString(UID, uId);
        Set<String> picSet = new HashSet<>();
        picSet.add(imgUrl);
        preferencesEditor.putStringSet(IMG_REFS, picSet);
        preferencesEditor.putString(AUTH_PROVIDER, FACEBOOK_PROVIDER);
        preferencesEditor.apply();


    }

    //private void updateUI(FirebaseUser ise)


    //THIS IS FOR FACEBOOK AUTHENTICATION

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}