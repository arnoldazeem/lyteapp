package com.lyte.adaboo.lyteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by adaboo on 4/23/17.
 */

public class GraphApiTry extends AppCompatActivity {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    private String firstName,lastName, email,birthday,gender;
    private URL profilePicture;
    private String userId;
    private String TAG = "GraphApiTry";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_start);

        TextView tx = (TextView) findViewById(R.id.allows);

        TextView top = (TextView) findViewById(R.id.welcome);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "ARCHRISTY.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(),  "ARESSENCE.ttf");


        top.setTypeface(custom_font);
        tx.setTypeface(custom_font2);



        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setHeight(100);
        loginButton.setTextColor(Color.WHITE);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        loginButton.setCompoundDrawablePadding(0);

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        FacebookRequestError error = response.getError();
                        if (error != null) {
                            // handle your error
                            Log.e("Error", error + "");
                            return;
                        }


                        try {

                            userId = object.getString("id");

                            profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");

                            if(object.has("first_name"))
                                firstName = object.getString("first_name");
                            if(object.has("last_name"))
                                lastName = object.getString("last_name");
                            if (object.has("email"))
                                email = object.getString("email");
                            if (object.has("birthday"))
                                birthday = object.getString("birthday");
                            if (object.has("gender"))
                                gender = object.getString("gender");

                            Intent main = new Intent(GraphApiTry.this, BuyPage.class);
                            //main.putExtra("name",firstName);
                            //main.putExtra("surname",lastName);
                            //main.putExtra("imageUrl",profilePicture.toString());

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("name", firstName);
                            editor.putString("surname", lastName);
                            //editor.putInt("surname", lastName);
                            editor.putString("imageUrl",profilePicture.toString());
                            editor.commit();



                            startActivity(main);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //Here we put the requested fields to be returned from the JSONObject
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, birthday, gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        };

        loginButton.setReadPermissions("email", "user_birthday","user_posts");
        loginButton.registerCallback(callbackManager, callback);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Intent main = new Intent(GraphApiTry.this, BuyPage.class);
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("name", firstName);
        editor.putString("surname", lastName);
        //editor.putInt("surname", lastName);
        editor.putString("imageUrl",profilePicture.toString());

        editor.commit();

        startActivity(main);

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
        callbackManager.onActivityResult(requestCode, responseCode, intent);
    }
}