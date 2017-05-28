package com.lyte.adaboo.lyteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by adaboo on 4/22/17.
 */

public class BuyPage extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";


    String imageUrl;
    int surname;

    Button logout;
    TextView username;
    SessionManager session;

    CircleImageView image;
    JSONArray newarray;

    ArrayList<Friend_List_Bulk> aryFriendList;

    user_friends_adapter adapter;

    private  RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_layout);

        logout = (Button) findViewById(R.id.logout);
        username = (TextView) findViewById(R.id.name);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

       image  = ((CircleImageView) findViewById(R.id.profileImage));

        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // image
        String imageUrl = user.get(SessionManager.KEY_IMAGEURL);

        String array = user.get(SessionManager.KEY_FRIENDS);



        Picasso.with(this)
                .load(imageUrl)
                .placeholder( R.drawable.progress_animation )
                .into(image);


        try {

            if (TextUtils.isEmpty(array)) {

                return; // or break, continue, throw
            }else
                 newarray = new JSONArray(array);
                friendlist(newarray);
        }catch (JSONException e){

            Log.e("array", array + "");

        }

        // Toast.makeText(BuyPage.this, array, Toast.LENGTH_LONG).show();

       // new DownloadImage((CircleImageView) findViewById(R.id.profileImage)).execute(imageUrl);
       // Toast.makeText(BuyPage.this, name  + imageUrl, Toast.LENGTH_LONG).show();

        //in your OnCreate() method

        //Toast.makeText(BuyPage.this, array + " worked", Toast.LENGTH_LONG).show();

        username.setText(name);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

        session.logoutUser();

            }

        });



        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(BuyPage.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        try {
                            String id = newarray.getJSONObject(position).getString("id");
                            Toast.makeText(BuyPage.this, id ,Toast.LENGTH_LONG).show();

                        }catch (Exception ex){

                        }

                    }
                })
        );
    }





    public void friendlist(JSONArray friendslist){

        ArrayList<Friend_List_Bulk> friends = new ArrayList<Friend_List_Bulk>();

        try {

            for (int l=0; l < friendslist.length(); l++) {


                String name = friendslist.getJSONObject(l).getString("name");

                String userId = friendslist.getJSONObject(l).getString("id");

                URL  profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");

                friends.add(new Friend_List_Bulk(userId, name,profilePicture.toString()));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {

        }

        recyclerView = (RecyclerView)findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        recyclerView
                .setLayoutManager(new LinearLayoutManager(BuyPage.this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new user_friends_adapter(friends, getApplicationContext());

        user_friend_recycle  adapter = new user_friend_recycle(BuyPage.this, friends);

        recyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter
    }

    }


