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
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by adaboo on 4/14/17.
 */

public class StartActivity extends AppCompatActivity{

    LoginButton fbLogin;

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private AccessToken accessToken;

    //Intent Actions
    private static final String HOME_ACTIVITIES = "com.lyte.adaboo.lyteapp";
    // Request Code
    private static final int HomePage_REQUEST_CODE = 10;



    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            nextActivity(profile);
        }
        @Override
        public void onCancel() {        }
        @Override
        public void onError(FacebookException e) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //should be place exactly before setcontentView

        setContentView(R.layout.activity_start);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);

            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();






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



        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                nextActivity(profile);
                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();    }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        };
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, callback);

    }


    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
       // callbackManager.onActivityResult(requestCode, responseCode, intent);

        if (callbackManager.onActivityResult(requestCode, responseCode, intent)) {
            return;
        }
    }


    private void nextActivity(Profile profile){

        //Toast.makeText(getApplicationContext(), profile.getFirstName() + "", Toast.LENGTH_SHORT).show();

        if(profile != null){
            Intent main = new Intent(StartActivity.this, BuyPage.class);
            main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
            startActivity(main);
        }
    }

}
