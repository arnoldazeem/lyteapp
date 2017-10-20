package com.lyte.adaboo.lyteapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.mvc.imagepicker.ImagePicker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.lyte.adaboo.lyteapp.R.id.imageView;

/**
 * Created by adaboo on 6/4/17.
 */

public class CreateCompany extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {

    Button upload, submit;
    AQuery aq;
    ImageView prev;
    ProgressDialog pDialog;
    String url_all_products = "";


    File imgFile;
    File imgFile1;
    File imgFile2;
    EditText location;
    EditText company_name;
    EditText contact;

    String fname,nation,locat,addre,conta1,conta2;
    String lname,pass1,comfirmpass;
    String dobb;

    Context context;

    TextView prev_compname,prev_category;
    TextView prev_loca;
    TextView pics1,pics2,pics3;

    String cate,friend_array,user_id;
    String item1,item2,item3;

    EditText firstname,lastname,dob,nationality,businame,confirmpass,passw,emailadd,idnum,spinnerid,locate,address,cont1,cont2;
    Spinner idtype,countryspin,state,industry;

    CheckBox checkyes,checkno;

    String encodedstring;

    byte[] imgurl;
    SessionManager session;
    TextView pr,p,q;
    String encodedImage = "";
    File encoded;
    RequestQueue requestQueue;
    Bitmap  bitmapprofile, bitmapid, bitmapcover= null;

    Button edit, browseID,uploadBusiness,uploadcover;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcompany);


        //context = getApplicationContext();
        // width and height will be at least 600px long (optional).
        ImagePicker.setMinQuality(600, 600);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        dob = (EditText) findViewById(R.id.dob);
        nationality = (EditText) findViewById(R.id.nationality);
        dob = (EditText) findViewById(R.id.idnumber);
        businame = (EditText) findViewById(R.id.businame);
        locate = (EditText) findViewById(R.id.location);
        address = (EditText) findViewById(R.id.address);
        cont1 = (EditText) findViewById(R.id.contact1);
        cont2 = (EditText) findViewById(R.id.contact2);
        idtype = (Spinner) findViewById(R.id.spinidtype);
        countryspin = (Spinner) findViewById(R.id.countryspinner);
        industry = (Spinner) findViewById(R.id.industrypinner);
        emailadd = (EditText) findViewById(R.id.emailaddress);
        passw = (EditText) findViewById(R.id.passww);
        confirmpass = (EditText) findViewById(R.id.confirmpass);


        pics1 = (TextView) findViewById(R.id.pic1);
        pics2 = (TextView) findViewById(R.id.pic2);
        pics3 = (TextView) findViewById(R.id.pic3);

        checkyes  = (CheckBox) findViewById(R.id.checkyes);
        checkno  = (CheckBox) findViewById(R.id.checkno);


        browseID = (Button) findViewById(R.id.uploadID);
        uploadBusiness = (Button) findViewById(R.id.uploadBussinespic);
        uploadcover = (Button) findViewById(R.id.uploadCoverPic);
        submit = (Button) findViewById(R.id.submit);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countries, R.layout.spinner_text);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        countryspin.setAdapter(adapter);


        countryspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item1 = parent.getItemAtPosition(position).toString();

                Toast.makeText(parent.getContext(), "Android Simple Spinner Example Output..." + item1, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //for id cards
        String[] years = {"Choose Type","Passport","Driver's License","Nationl Id","National Health Insurance"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        idtype.setAdapter(langAdapter);

        idtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item2 = parent.getItemAtPosition(position).toString();

                Toast.makeText(parent.getContext(), "Android Simple Spinner Example Output..." + item2, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //for id cards
        String[] indus = {"Choose Category","Clothing","Food","Electronics","Beauty Products"," Automobile & Parts",
                "Books & Stationery","Entertainment","Education","Accommodation","Others"};

        ArrayAdapter<CharSequence> langAdapters = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, indus );
        langAdapters.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        industry.setAdapter(langAdapters);

        industry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item3 = parent.getItemAtPosition(position).toString();
               // Toast.makeText(parent.getContext(), "Android Simple Spinner Example Output..." + item3, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Clothing,Food,Electronics,Beauty Products,Automobile & Parts,Books & Stationery
        //Entertainment, Education,Accommodation,Others;
        aq = new AQuery(this);
        pDialog = new ProgressDialog(this);

        //idtype.setOnItemSelectedListener(this);
        browseID.setOnClickListener(this);
        uploadBusiness.setOnClickListener(this);
        uploadcover.setOnClickListener(this);
        submit.setOnClickListener(this);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){
            case 0: // Do your stuff here...
                 bitmapid = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);

                String path = ImagePicker.getImagePathFromResult(this, requestCode, resultCode, data);
                String filename = path.substring(path.lastIndexOf("/")+1);

                if (bitmapid != null) {

                     imgFile = new  File(path);

                    if(imgFile.exists()){

                    pics1.setText("Picture Uploaded!");

                    }


                    Toast.makeText(this, path, Toast.LENGTH_LONG)
                           .show();
                  //  Log.d(TAG, ConvertImage );
                } InputStream is = ImagePicker.getInputStreamFromResult(this, requestCode, resultCode, data);
                if (is != null) {
                    //textView.setText("Got input stream!");
                    try {
                        is.close();
                    } catch (IOException ex) {
                        // ignore
                    }
                } else {
                    // textView.setText("Failed to get input stream!");
                }

                break;
            case 1: // Do your other stuff here...
                 bitmapprofile = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
                String path1 = ImagePicker.getImagePathFromResult(this, requestCode, resultCode, data);
               // String filename1 = path1.substring(path1.lastIndexOf("/")+1);
                if (bitmapprofile != null) {

                    imgFile1 = new  File(path1);

                    if(imgFile1.exists()){

                        pics2.setText("Picture Uploaded!");

                    }
                    // imageView.setImageBitmap(bitmap2);
                }
                InputStream is2 = ImagePicker.getInputStreamFromResult(this, requestCode, resultCode, data);
                if (is2 != null) {
                    //textView.setText("Got input stream!");
                    try {
                        is2.close();
                    } catch (IOException ex) {
                        // ignore
                    }
                } else {
                    // textView.setText("Failed to get input stream!");
                }
                break;
            case 2:

                 bitmapcover = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);

                String path2 = ImagePicker.getImagePathFromResult(this, requestCode, resultCode, data);
                String filename2 = path2.substring(path2.lastIndexOf("/")+1);

                if (bitmapcover != null) {

                    imgFile2 = new  File(path2);
                    pics3.setText("Picture Uploaded!");
                }
                InputStream is3 = ImagePicker.getInputStreamFromResult(this, requestCode, resultCode, data);
                if (is3 != null) {
                    //textView.setText("Got input stream!");
                    try {
                        is3.close();
                    } catch (IOException ex) {
                        // ignore
                    }
                } else {
                    // textView.setText("Failed to get input stream!");
                }
                break;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.uploadID:

                ImagePicker.pickImage(this, 0);
               // ImagePicker.pickImage(this, "Select your image:");
              break;

            case R.id.uploadBussinespic:

                ImagePicker.pickImage(this, 1);
                // ImagePicker.pickImage(this, "Select your image:");
                break;


            case R.id.uploadCoverPic:

                ImagePicker.pickImage(this, 2);
                // ImagePicker.pickImage(this, "Select your image:");
                break;

            case R.id.submit:

                //check all editboxes
                fname = firstname.getText().toString();
                lname = lastname.getText().toString();
                dobb = dob.getText().toString();
                nation = nationality.getText().toString();
                locat = locate.getText().toString();
                addre = address.getText().toString();
                conta1 = cont1.getText().toString();
                conta2 = cont2.getText().toString();
                pass1 = passw.getText().toString();
                comfirmpass = confirmpass.getText().toString();

                if (fname.trim().contentEquals("")) {
                    Toast.makeText(this, "Please provide your First name",
                            Toast.LENGTH_LONG).show();
                } else if (lname.contentEquals("")) {
                    Toast.makeText(this, "Please provide your Last name",
                            Toast.LENGTH_LONG).show();
                } else if (dobb.contentEquals("")) {
                    Toast.makeText(this, "Please provide Date of Birth", Toast.LENGTH_LONG)
                            .show();
                }else if (dobb.contentEquals("")){
                    Toast.makeText(this, "Please provide Nationality", Toast.LENGTH_LONG)
                            .show();
                } else if (item1 == "Choose Country") {
                    Toast.makeText(this, "Please provide Country", Toast.LENGTH_LONG)
                            .show();
                } else if (item2 == "Choose Type") {
            Toast.makeText(this, "Please provide Id Type", Toast.LENGTH_LONG)
                    .show();
                }else if (item3 == "Choose Category") {
                    Toast.makeText(this, "Please provide Industry", Toast.LENGTH_LONG)
                            .show();
                }else if (bitmapid == null) {

                    Toast.makeText(this, "Please select Id picture", Toast.LENGTH_LONG)
                            .show();
                }else if (bitmapcover == null) {

                    Toast.makeText(this, "Please select Cover picture", Toast.LENGTH_LONG)
                            .show();
                }else if (bitmapprofile == null) {

                    Toast.makeText(this, "Please select Profile picture", Toast.LENGTH_LONG)
                            .show();
                }// Check if both password should be equal
                else if (!pass1.equals(comfirmpass)) {
                    Toast.makeText(this, "Passwords dont match", Toast.LENGTH_LONG)
                            .show();
                }else if (!checkyes.isChecked() ||!checkno.isChecked() )
                    Toast.makeText(this, "Please check delivery or no delivery", Toast.LENGTH_LONG)
                            .show();
                else {

                    try {
                        signupRequest();
                    }catch (Exception E){
                        E.printStackTrace();
                    }

                }
                break;
        }

    }

    private void signupRequest() {

            // TODO Auto-generated method stub
            pDialog.setMessage("Submitting Item..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            Map<String, Object> params = new HashMap<String, Object>();



            params.put("type", "submit");
            params.put("category", locat);
            params.put("description", addre);
            params.put("price", conta1);
            params.put("quantity", conta1);
            params.put("user_id", conta1);
            params.put("objurl", imgFile);
            params.put("objurl1", imgFile1);
            params.put("objurl2", imgFile2);

            /**
             *
             *  aq.progress(pDialog).ajax(
             StaticVariables.sendItemVolUrl + "submit&category="
             + cate + "&description=" + desc + "&price="
             + amt+ "&quantity=" + qtny + "&user_id=" + id
             +"&objurl=" + encoded,
             *
             *
             *
             */
            aq.progress(pDialog).ajax(
                    StaticVariables.sendItemVolUrl,
                    params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject json,
                                             AjaxStatus status) {

                            try {

                                Toast.makeText(
                                        CreateCompany.this,
                                        json.toString() + "",
                                        Toast.LENGTH_LONG).show();


                                System.out.println(json.toString());
                                int success = json.getInt(StaticVariables.SUCCESS);

                                if (success == 1) {
                                    Toast.makeText(
                                            CreateCompany.this,
                                            "work",
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(
                                            CreateCompany.this,
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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
