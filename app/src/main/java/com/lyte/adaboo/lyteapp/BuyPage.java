package com.lyte.adaboo.lyteapp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    Friend_List_Bulk friendBean;
    ArrayList<Friend_List_Bulk> aryFriendList;

    user_friends_adapter adapter;

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





    }





    public void friendlist(JSONArray friendslist){

        ArrayList<Friend_List_Bulk> friends = new ArrayList<Friend_List_Bulk>();


        try {

            for (int l=0; l < friendslist.length(); l++) {


                String name = friendslist.getJSONObject(l).getString("name");

                String userId = friendslist.getJSONObject(l).getString("id");

                URL  profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");

               // Toast.makeText(BuyPage.this,  friendslist.getJSONObject(l).getString("id") , Toast.LENGTH_LONG).show();

               // profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");


                friends.add(new Friend_List_Bulk(userId, name,profilePicture.toString()));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {

        }


        adapter = new user_friends_adapter(friends, getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // friends dataModel= friends.get(position);

                //Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                //        .setAction("No action", null).show();
            }
        });


    }



    /**

     for (int i = 0; i < newarray.length(); i++) {

     JSONObject res = array.getJSONObject(i);

     Log.e("name frnd",
     res.getString("name"));
     Log.e("id frnd", res.getString("id"));

     }
     totlfrndcount = newresponse
     .getJSONObject("summary");


    //  Log.e("Total fb frnds", totlfrndcount
    //          .getString("total_count"));


        */

    }


