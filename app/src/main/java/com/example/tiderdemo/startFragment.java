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
//import com.socratesss.goodapp.utils.OnboardingUtils;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//importing objects from constants class;

import static com.example.tiderdemo.Constants.EMAIL_FB;
import static com.example.tiderdemo.Constants.FULL_NAME;
import static com.example.tiderdemo.Constants.EMAIL;
import static com.example.tiderdemo.Constants.FULL_NAME_FB;
//import static com.example.tiderdemo.Constants.LAST_NAME_FB;


import static android.content.ContentValues.TAG;


public class startFragment extends Fragment {

    private FragmentStartBinding binding;

    FirebaseAuth fAuth;
    CallbackManager callbackManager;
    //private LoginManager loginManager;
    private String TAG = "facebookAuthentication";
    LoginButton loginButton;
    String usernameFb;
    private NavController navController;


    private String imgUrl;
    private String userEmail;
    private String fName;
   // private String lastName;

    //private LoginButton fbSign;

    private AccessToken token;





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

    // i think i wont use this since there are not a lot of buttons there!!! so im gonna comment them

//    public void onClick(View view){
//        navController = Navigation.findNavController(view);
//
//        switch (view.getId()) {
//            case (R.id.fbSign):
//
//                authenticateWithFacebook();
//
//            case (R.id.ggSign):
//
//                authenticateWithFacebook();
//
//            case(R.id.tvReg):
//
//                navController.navigate(R.id.action_startFragment_to_registerFragment);
//
//
//
//
//
//
//
//        }
//    }


    // im gonna use this method when i press to the fbSign. binding.fbSign.setOnClickListener(new On......
//
//    private void authenticateWithFacebook() {
//       // ((OnboardingUtils) requireActivity()).showProgressBar();
//        loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", EMAIL_FB));
//        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                ((OnboardingUtils) requireActivity()).hideProgressBar();
//                token = loginResult.getAccessToken();
//                GraphRequest request = GraphRequest.newMeRequest(token,
//                        (object, response) -> {
//                            try {
//                                URL fbProfilePicture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=500&height=500");
//                                imgUrl = fbProfilePicture.toString();
//                                userEmail = object.getString(EMAIL_FB);
//                                fName = object.getString(FULL_NAME);
//                               // lastName = object.getString(LAST_NAME_FB);
//                            } catch (JSONException | MalformedURLException e) {
//                                e.printStackTrace();
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id, first_name, last_name, email");
//                request.setParameters(parameters);
//                request.executeAsync();
//                FacebookSdk.setAutoLogAppEventsEnabled(true);
//                FacebookSdk.setAdvertiserIDCollectionEnabled(true);
//                handleFacebookAccessToken(token);
//            }
//
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });
//    }
//
//
//    private void handleFacebookAccessToken(AccessToken token) {
//        AuthCredential fbCredential = FacebookAuthProvider.getCredential(token.getToken());
//        FirebaseAuth.getInstance().signInWithCredential(fbCredential)
//                .addOnCompleteListener(requireActivity(), task -> {
//                    if (task.isSuccessful()) {
//                        boolean isNew = false;
//                        String uId = "";
//                        if (task.getResult() != null && task.getResult().getAdditionalUserInfo() != null) {
//                            isNew = task.getResult().getAdditionalUserInfo().isNewUser();
//                            if (task.getResult().getUser() != null)
//                                uId = task.getResult().getUser().getUid();
//                        }
//                        if (!isNew) {
//                            gotoHomeScreen();
//                        } else {
//                            saveUserFbData(uId);
//                            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_welcome_to_phoneAdd);
//                        }
//                    } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        if (user != null) {
//                            user.delete()
//                                    .addOnCompleteListener(task12 -> {
//                                        if (task12.isSuccessful()) {
//                                            FirebaseAuth.getInstance().getCurrentUser().linkWithCredential(fbCredential)
//                                                    .addOnCompleteListener(task1 -> {
//                                                        if (task1.isSuccessful()) {
//                                                            DataManager.getInstance().saveLoggedInStatus(true);
//                                                            gotoHomeScreen();
//                                                        } else {
//                                                            if (task1.getException() != null) {
//                                                                ((OnboardingUtils) requireActivity()).showToastMessage(task1.getException().getLocalizedMessage());
//                                                            } else {
//                                                                ((OnboardingUtils) requireActivity()).showToastMessage(getString(R.string.toast_create_user_fail));
//                                                            }
//                                                        }
//                                                    });
//                                        }
//                                    });
//                        } else {
//                            ((OnboardingUtils) requireActivity()).showToastMessage(task.getException().getLocalizedMessage());
//                        }
//                    }
//                });
//    }
//
//
//    private void saveUserFbData(String uId) {
//        SharedPreferences mPreferences = requireActivity().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
//        preferencesEditor.putString(FULL_NAME, fName);
//        preferencesEditor.putString(EMAIL, userEmail);
//        preferencesEditor.putString(UID, uId);
//        Set<String> picSet = new HashSet<>();
//        picSet.add(imgUrl);
//        preferencesEditor.putStringSet(IMG_REFS, picSet);
//        preferencesEditor.putString(AUTH_PROVIDER, FACEBOOK_PROVIDER);
//        preferencesEditor.apply();
//    }

//    private void handleFacebookToken(AccessToken token){
//
//        Log.d(TAG, "handleFacebookToken"+token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        fAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//           if(task.isSuccessful()){
//               Log.d(TAG, "sign in with credential: successful");
//               FirebaseUser user = fAuth.getCurrentUser();
//               updateUI();
//           }
//           else{
//
//           }
//            }
//        });
//    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         navController = Navigation.findNavController(view);

        fAuth = FirebaseAuth.getInstance();
        // FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager  = CallbackManager.Factory.create();

        loginButton = binding.fbSign;
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               // handleFacebookAccessToken(loginResult.getAccessToken());
                navController.navigate(R.id.action_startFragment_to_mainmenuFragment);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });





        //fbSign  = view.findViewById(R.id.fbSign);

       // fbSign.setReadPermissions("email", "public_profile


//        fbSign.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                Log.d(TAG, "facebookOnSuccess:"+loginResult);
//                //handleFacebookAccessToken(loginResult.getAccessToken());
//
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });




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
    private void signIn(){


    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
       // progressDialog.show();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = fAuth.getCurrentUser();

                            navController.navigate(R.id.action_startFragment_to_mainmenuFragment);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "" + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                      //  progressDialog.dismiss();
                    }
                });
    }
    //private void updateUI(FirebaseUser ise)


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}