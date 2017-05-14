package com.lyte.adaboo.lyteapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by adaboo on 5/14/17.
 */

public class myProfile extends AppCompatActivity{

    SessionManager session;
    JSONArray products = null;
    CircleImageView image;
    JSONArray newarray;
    ArrayList<Friend_List_Bulk> aryFriendList;
    user_friends_adapter adapter;
    private RecyclerView recyclerView;
    TextView username;
    AQuery aq;
    ProgressDialog pDialog;
    String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);

        pDialog = new ProgressDialog(this);
        aq = new AQuery(this);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        username = (TextView) findViewById(R.id.name);
        image  = ((CircleImageView) findViewById(R.id.profileImage));

        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // image
        String imageUrl = user.get(SessionManager.KEY_IMAGEURL);

        id = user.get(SessionManager.KEY_ID);


        Picasso.with(this)
                .load(imageUrl)
                .placeholder( R.drawable.progress_animation )
                .into(image);
        username.setText(name);

        Request();
    }


    private void Request() {
        // TODO Auto-generated method stub
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        Map<String, Object> params = new HashMap<String, Object>();
        aq.progress(pDialog).ajax(
                StaticVariables.requestUserData + "requestUserData&user_id=" + id, params, JSONObject.class,

                new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject json,
                                         AjaxStatus status) {

                        try {

                            int success = json.getInt(StaticVariables.SUCCESS);

                            products = json.getJSONArray("markers");

                            // saved in database as String
                            if (success == 1) {

                                Toast.makeText(
                                        myProfile.this,
                                        json.getString(StaticVariables.MESSAGE),
                                        Toast.LENGTH_LONG).show();


                            } else {

                            }

                            pDialog.dismiss();

                        } catch (Exception ex) {
                            // TODO: handle exception
                            ex.printStackTrace();
                            System.out.println("********************* "
                                    + ex.toString());
                        }

                    }
                });

    }











}
