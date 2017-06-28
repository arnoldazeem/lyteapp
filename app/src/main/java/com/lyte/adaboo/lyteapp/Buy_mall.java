package com.lyte.adaboo.lyteapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
 * Created by adaboo on 6/7/17.
 */

public class Buy_mall extends AppCompatActivity {

    TextView username;
    SessionManager session;

    CircleImageView image;
    JSONArray newarray;

    ArrayList<Friend_List_Bulk> aryFriendList;


    AQuery aq;
    ProgressDialog pDialog;
    user_items_adapter adapteritem;
    companyItemsAdapter adapter;
    private  RecyclerView recyclerView;
    JSONArray products = null;
    String Id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_from_mall);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        pDialog = new ProgressDialog(this);
        aq = new AQuery(this);

        image  = ((CircleImageView) findViewById(R.id.profileImage));

        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // image
        String imageUrl = user.get(SessionManager.KEY_IMAGEURL);

        //String array = user.get(SessionManager.KEY_FRIENDS);

        String id = user.get(SessionManager.KEY_ID);

        //need to fix first page of company side


        Picasso.with(this)
                .load(imageUrl)
                .placeholder( R.drawable.progress_animation )
                .into(image);


        LoadEachCompany(id);

    }


    //function to load each company on click
    private void LoadEachCompany(String id) {

        // TODO Auto-generated method stub
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        Map<String, Object> params = new HashMap<String, Object>();
        aq.progress(pDialog).ajax(
                StaticVariables.requestcompData + "requestcompData&user_id=" + id, params, JSONObject.class,

                new AjaxCallback<JSONObject>() {

                    @Override
                    public void callback(String url, JSONObject json,
                                         AjaxStatus status) {

                        ArrayList<Friend_List_Bulk> friends = new ArrayList<Friend_List_Bulk>();

                        try {

                            int success = json.getInt(StaticVariables.SUCCESS);
                            products = json.getJSONArray("markers");

                            // saved in database as String
                            if (success == 1) {


                                for (int i = 0; i <= products.length();  i++) {

                                    JSONObject c = products.getJSONObject(i);


                                    String product = c.getString("company_name");
                                    String qnty = c.getString("location");
                                    String price = c.getString("contact");
                                    String img = c.getString("company_image");


                                    friends.add(new Friend_List_Bulk(qnty, product,img));
                                    //friends.add(new user_items (product, price,qnty,img));


                                }


                            } else {
                                Toast.makeText(
                                        Buy_mall.this,
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
                            Toast.makeText(Buy_mall.this,
                                    ex.toString(), Toast.LENGTH_LONG)
                                    .show();
                        }

                        recyclerView = (RecyclerView)findViewById(R.id.list);
                        recyclerView.setHasFixedSize(true);
                        recyclerView
                                .setLayoutManager(new LinearLayoutManager(Buy_mall.this, LinearLayoutManager.HORIZONTAL, false));
                        //adapter = new companyItemsAdapter(friends, getApplicationContext());
                        comapny_item_recycle  adapter = new comapny_item_recycle(Buy_mall.this, friends);
                        recyclerView.setAdapter(adapter);// set adapter on recyclerview
                        adapter.notifyDataSetChanged();// Notify the adapter

                    }
                });
    }

}
