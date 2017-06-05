package com.lyte.adaboo.lyteapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
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
    TextView username;
    AQuery aq;
    ProgressDialog pDialog;
    String id;

    ArrayList<user_items> aryFriendList;
    user_items_adapter adapter;
    private  RecyclerView recyclerView;

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


       // recyclerView = (RecyclerView)findViewById(R.id.list);
        //recyclerView.setHasFixedSize(true);
        //recyclerView
       //         .setLayoutManager(new LinearLayoutManager(myProfile.this, LinearLayoutManager.HORIZONTAL, false));
       // adapter = new user_items_adapter(friends, getApplicationContext());
       // user_item_recycle  adapter = new user_item_recycle(myProfile.this, friends);
       // recyclerView.setAdapter(adapter);// set adapter on recyclerview
      //  adapter.notifyDataSetChanged();// Notify the adapter
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

                        ArrayList<user_items> friends = new ArrayList<user_items>();

                        try {

                            int success = json.getInt(StaticVariables.SUCCESS);
                            products = json.getJSONArray("markers");

                            // saved in database as String
                            if (success == 1) {



                                for (int i = 0; i <= products.length();  i++) {

                                    JSONObject c = products.getJSONObject(i);


                                    String product = c.getString("product_name");
                                    String qnty = c.getString("product_price");
                                    String price = c.getString("product_type");
                                    String img = c.getString("product_image");

                                    //convert bytearray to Bitmap
                                  //  byte[] theByteArray = img.getBytes();

                                  //  Bitmap bitmap = BitmapFactory.decodeByteArray(theByteArray , 0, theByteArray .length);


                                   // images.setImageBitmap(bitmap);

                                    // /byte[] decodedString = Base64.decode(encodedImage, Base64.URL_SAFE);
                                    //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                     friends.add(new user_items (product, price,qnty,img));


                                }


                            } else {
                                Toast.makeText(
                                        myProfile.this,
                                        json.getString(StaticVariables.MESSAGE),
                                        Toast.LENGTH_LONG).show();

                            }

                        }catch (JSONException e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        } catch (Exception ex) {
                            // TODO: handle exception
                            ex.printStackTrace();
                            System.out.println("********************* "
                                    + ex.toString());
                            Toast.makeText(myProfile.this,
                                   ex.toString(), Toast.LENGTH_LONG)
                                    .show();
                        }


                        recyclerView = (RecyclerView)findViewById(R.id.list_them);

                        recyclerView.setHasFixedSize(true);

                        recyclerView
                                .setLayoutManager(new LinearLayoutManager(myProfile.this, LinearLayoutManager.VERTICAL, false));

                        adapter = new user_items_adapter(friends, getApplicationContext());

                        user_item_recycle  adapter = new user_item_recycle(myProfile.this, friends);

                        recyclerView.setAdapter(adapter);// set adapter on recyclerview

                        adapter.notifyDataSetChanged();// Notify the adapter


                    }
                });

    }











}
