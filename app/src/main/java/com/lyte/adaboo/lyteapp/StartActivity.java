package com.lyte.adaboo.lyteapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //should be place exactly before setcontentView
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_start);

        fbLogin = (LoginButton) findViewById(R.id.fblogin);

        callbackManager = CallbackManager.Factory.create();

        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                Intent intent = new Intent(StartActivity.this, HomePage.class);
                        startActivity(intent);

                Toast.makeText(StartActivity.this, loginResult.getAccessToken().getUserId() + loginResult.getAccessToken().getToken(),
                        Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });





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

        Log.e("data",data.toString());
    }
}
