package com.lyte.adaboo.lyteapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by adaboo on 4/23/17.
 */

public class GraphApiTry extends FragmentActivity {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    private String firstName,lastName, name,birthday,gender;
    private URL profilePicture;
    private String userId;
    private String TAG = "GraphApiTry";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SessionManager session;
    Bitmap profilePic;
    JSONObject data;
    String rawName;



    private LoginManager loginManager;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);

        callbackManager = CallbackManager.Factory.create();

        //loginManager = LoginManager.getInstance();

        setContentView(R.layout.activity_start);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        TextView tx = (TextView) findViewById(R.id.allows);

        TextView top = (TextView) findViewById(R.id.welcome);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "ARCHRISTY.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "ARESSENCE.ttf");


        top.setTypeface(custom_font);
        tx.setTypeface(custom_font2);




        loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setHeight(100);
       // loginButton.setTextColor(Color.WHITE);
       // loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
       // loginButton.setCompoundDrawablePadding(0);
        loginButton.setReadPermissions("user_friends");




        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_friends","public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loginButton.setVisibility(View.INVISIBLE);
                //loginButton.setVisibility(View.GONE);
                OnDone(loginResult);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });





    }



        /**
        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


               // Toast.makeText(GraphApiTry.this, "here", Toast.LENGTH_LONG).show();
               // Request();
                OnDone(loginResult);
            }




            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        };


         **/



        //loginButton.setReadPermissions("user_friends");
        //loginButton.registerCallback(callbackManager, callback);


    private void Request() {


        Bundle params = new Bundle();
        params.putString("fields", "id,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {

                            try {

                                data = response.getJSONObject();


                                userId = data.getString("id");

                                if (data.has("picture"))

                                    if(data.has("name"))
                                        name = data.getString("name");

                                   final String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");

                                    new Thread() {
                                        @Override
                                        public void run() {
                                            // Do your network stuff

                                           // Toast.makeText(GraphApiTry.this, data.toString() + "", Toast.LENGTH_LONG).show();

                                            profilePic = getFacebookProfilePicture(profilePicUrl);

                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            profilePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                            byte[] byteArray = stream.toByteArray();

                                            session.createLoginSession(userId, name , profilePicture.toString());

                                            Intent main = new Intent(GraphApiTry.this, BuyPage.class);
                                            //main.putExtra("name_of_extra", byteArray);
                                            startActivity(main);

                                            finish();

                                        }
                                    }.start();



                                    //needfriendslist
                                   // session.createLoginSession(userId, name ,profilePicture.toString(), gethem.toString() );

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
        //Here we put the requested fields to be returned from the JSONObject
    }



    public  Bitmap getFacebookProfilePicture(String src) {
        try {

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


    //get all friends here
    public void Freinds(){

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {

                        JSONObject newresponse, totlfrndcount;
                        final JSONArray array;

                        try {

                            newresponse = object
                                    .getJSONObject("friends");

                            Log.e("array", newresponse + "");

                             array = newresponse
                                    .getJSONArray("data");
                            Log.e("array", array + "");


                            /**
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject res = array.getJSONObject(i);
                                Log.e("name frnd",
                                        res.getString("name"));
                                Log.e("id frnd", res.getString("id"));

                            }
                            totlfrndcount = newresponse
                                    .getJSONObject("summary");

                           **/

                          //  Log.e("Total fb frnds", totlfrndcount
                          //          .getString("total_count"));

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,friends,name");
        request.setParameters(parameters);
        request.executeAsync();

    }



    public void OnDone(final LoginResult loginResult){

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.e(TAG,object.toString());
                Log.e(TAG,response.toString());

                try {

                    Freinds();

                   /// Toast.makeText(GraphApiTry.this, gethem + " worked", Toast.LENGTH_LONG).show();


                    userId = object.getString("id");

                    profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");


                    if(object.has("name"))
                        name = object.getString("name");


                    session.createLoginSession(userId, name , profilePicture.toString());

                    Intent main = new Intent(GraphApiTry.this, HomePage.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(main);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        //Here we put the requested fields to be returned from the JSONObject
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(getApplication());
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }



    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        //super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);



    }
}