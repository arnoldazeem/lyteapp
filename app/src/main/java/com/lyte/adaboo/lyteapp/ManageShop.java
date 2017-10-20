package com.lyte.adaboo.lyteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


import static android.content.ContentValues.TAG;

/**
 * Created by adaboo on 10/20/17.
 */



public class ManageShop extends Activity implements View.OnClickListener{

    Button upload, submit;
    AQuery aq;
    ImageView prev;
    ProgressDialog pDialog;
    String url_all_products = "";
    Spinner spinner1,spinnercompany;
    EditText descrip;
    EditText price;
    EditText qty;
    String desc;
    String amt;
    String qtny;
    String cate,id,friend_array,comp;


    JSONArray products = null;

    //for the arraylist
    ArrayList<String> friends = new ArrayList<String>();
    ArrayList<String> mylist = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageshop);

        aq = new AQuery(this);
        pDialog = new ProgressDialog(this);






        

        //  cancel.setOnClickListener(this);
        upload.setOnClickListener(this);
        submit.setOnClickListener(this);

        getCompany(id);
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.upload:

                picture();

                break;

            case R.id.submit:

                desc = descrip.getText().toString();
                amt = price.getText().toString();
                qtny = qty.getText().toString();

                if (desc.trim().contentEquals("")) {
                    Toast.makeText(this, "Please provide Decription of Item",
                            Toast.LENGTH_LONG).show();
                } else if (amt.contentEquals("")) {
                    Toast.makeText(this, "Please provide Item Price",
                            Toast.LENGTH_LONG).show();
                } else if (qtny.contentEquals("")) {
                    Toast.makeText(this, "Please provide Quantity", Toast.LENGTH_LONG)
                            .show();
                }else if (cate == "Choose Category") {
                    Toast.makeText(this, "Please choose Category", Toast.LENGTH_LONG)
                            .show();
                }
                else {

                    if (comp == "NONE"){

                        //  Toast.makeText(this, comp, Toast.LENGTH_LONG).show();
                        // sendToCompany();

                    }else
                        Toast.makeText(this, comp, Toast.LENGTH_LONG).show();

                    donormalStaff();

                }
                break;
        }

    }



    //send to company
    private void sendToCompany() {
        // TODO Auto-generated method stub
        pDialog.setMessage("Submitting Item..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("company", comp);
        params.put("type", "submit");
        params.put("category", cate);
        params.put("description", desc);
        params.put("price", amt);
        params.put("quantity", qtny);
        params.put("user_id", id);
        params.put("objurl", encoded);

        aq.progress(pDialog).ajax(
                StaticVariables.sendCompData,
                params, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject json,
                                         AjaxStatus status) {

                        try {

                            Toast.makeText(
                                    Individual_Sell.this,
                                    json.getInt(StaticVariables.SUCCESS) + "",
                                    Toast.LENGTH_LONG).show();


                            System.out.println(json.toString());
                            int success = json.getInt(StaticVariables.SUCCESS);

                            if (success == 1) {


                                descrip.setText("");
                                price.setText("");
                                qty.setText("");

                                dialogShow();

                            } else {
                                Toast.makeText(
                                        Individual_Sell.this,
                                        json.getString(StaticVariables.MESSAGE),
                                        Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        } catch (Exception ex) {
                            // TODO: handle exception
                            ex.printStackTrace();
                            System.out.println("********************* "
                                    + ex.toString());

                        }

                    }
                });

    }


    //function to load each company to spinner
    private void getCompany(String id) {

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



                        try {

                            int success = json.getInt(StaticVariables.SUCCESS);
                            products = json.getJSONArray("markers");

                            // saved in database as String
                            if (success == 1) {

                                mylist.add("NONE");
                                for (int i = 0; i <= products.length();  i++) {

                                    JSONObject c = products.getJSONObject(i);

                                    String product = c.getString("company_name");
                                    String qnty = c.getString("location");
                                    String price = c.getString("contact");
                                    String img = c.getString("company_image");


                                    mylist.add(product);
                                    //friends.add(new user_items (product, price,qnty,img));

                                }




                            } else {
                                Toast.makeText(
                                        Individual_Sell.this,
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
                            Toast.makeText(Individual_Sell.this,
                                    ex.toString(), Toast.LENGTH_LONG)
                                    .show();
                        }


                        String[] arr =  mylist.toArray(new String[mylist.size()]);

                        //Toast.makeText(Individual_Sell.this,
                        //        mylist + "", Toast.LENGTH_LONG).show();

                        ArrayAdapter<CharSequence> langAdapters = new ArrayAdapter<CharSequence>(getApplicationContext(), R.layout.spinner_text, arr);
                        langAdapters.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        spinnercompany.setAdapter(langAdapters);



                        //String[] years = {"Choose Category","Electronics","Clothing","Stationery","Automobile"};



                    }
                });

    }

}
