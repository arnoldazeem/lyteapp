package com.lyte.adaboo.lyteapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by adaboo on 4/14/17.
 */

public class StartActivity extends AppCompatActivity{

    LoginButton fbLogin;
    CallbackManager callbackManager;

    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;

    //Intent Actions
    private static final String HOME_ACTIVITIES = "com.lyte.adaboo.lyteapp";
    // Request Code
    private static final int HomePage_REQUEST_CODE = 10;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //should be place exactly before setcontentView
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_start);


        b = new Bundle();

        fbLogin = (LoginButton) findViewById(R.id.fblogin);

        callbackManager = CallbackManager.Factory.create();

        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("LOGIN_SUCCESS", "Success");
                fbLogin.setVisibility(View.INVISIBLE); //<- IMPORTANT

                Profile profile = Profile.getCurrentProfile();


                if(profile != null){

                    Intent main = new Intent(StartActivity.this, HomePage.class);
                    main.putExtra("name", profile.getFirstName());
                    main.putExtra("surname", profile.getLastName());
                    main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
                    Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
                    startActivity(main);
                };




            //finish();//<- IMPORTANT





            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        // If already logged in show the home view
        if (accessToken != null) {//<- IMPORTANT
            Intent main = new Intent(StartActivity.this, HomePage.class);
            startActivity(main);
           // finish();//<- IMPORTANT
        }





        /** was for generating hashes for facebook
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.lyte.adaboo.lyteapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Your Tag", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

         */

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        try {

        if (requestCode == HomePage_REQUEST_CODE  && resultCode  == RESULT_OK) {

            String result = data.getStringExtra("result");

            String requiredValue = data.getStringExtra("Key");
        }
    } catch (Exception ex) {

            Log.e("data",ex.toString());

    }




    }
}
