package com.lyte.adaboo.lyteapp;

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
import android.support.v7.app.AppCompatActivity;
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

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.mvc.imagepicker.ImagePicker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by adaboo on 6/4/17.
 */

public class CreateCompany extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

    Button upload, submit;
    AQuery aq;
    ImageView prev;
    ProgressDialog pDialog;
    String url_all_products = "";


    EditText location;
    EditText company_name;
    EditText contact;

    String fname,nation,locat,addre,conta1,conta2;
    String lname;
    String dobb;


    TextView prev_compname,prev_category;
    TextView prev_loca;
    TextView prev_contact;

    String cate,id,friend_array,user_id;

    EditText firstname,lastname,dob,nationality,businame,confirmpass,passw,emailadd,idnum,spinnerid,locate,address,cont1,cont2;
    Spinner idtype,country,state,industry;

    CheckBox checkyes,checkno;


    byte[] imgurl;
    SessionManager session;
    TextView pr,p,q;
    String encodedImage = "";
    File encoded;

    LinearLayout clickable;


    Bitmap mBitmap = null;

    Button edit, browseID,uploadBusiness,uploadcover;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcompany);

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
        country = (Spinner) findViewById(R.id.countryspinner);
        state = (Spinner) findViewById(R.id.statespinner);
        industry = (Spinner) findViewById(R.id.industrypinner);

        emailadd = (EditText) findViewById(R.id.emailaddress);
        passw = (EditText) findViewById(R.id.passww);
        confirmpass = (EditText) findViewById(R.id.confirmpass);

        checkyes  = (CheckBox) findViewById(R.id.checkyes);
        checkno  = (CheckBox) findViewById(R.id.checkno);


        browseID = (Button) findViewById(R.id.uploadID);
        uploadBusiness = (Button) findViewById(R.id.uploadBussinespic);
        uploadcover = (Button) findViewById(R.id.uploadCoverPic);

        submit = (Button) findViewById(R.id.submit);


        String[] years = {"Choose Type","Passport","Driver's License","Nationl Id","National Health Insurance"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        idtype.setAdapter(langAdapter);


        Clothing,Food,Electronics,Beauty Products,Automobile & Parts,Books & Stationery
        Entertainment, Education,Accommodation,Others;


        aq = new AQuery(this);
        pDialog = new ProgressDialog(this);

        idtype.setOnItemSelectedListener(this);
        browseID.setOnClickListener(this);
        uploadBusiness.setOnClickListener(this);
        uploadcover.setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){
            case 0: // Do your stuff here...
                Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);

                if (bitmap != null) {

                    //  imageView.setImageBitmap(bitmap);
                }
                InputStream is = ImagePicker.getInputStreamFromResult(this, requestCode, resultCode, data);
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
                break;
            case 2:
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

                if (fname.trim().contentEquals("")) {
                    Toast.makeText(this, "Please provide your First name",
                            Toast.LENGTH_LONG).show();
                } else if (lname.contentEquals("")) {
                    Toast.makeText(this, "Please provide your Last name",
                            Toast.LENGTH_LONG).show();
                } else if (dobb.contentEquals("")) {
                    Toast.makeText(this, "Please provide Date of Birth", Toast.LENGTH_LONG)
                            .show();
                }else if (nation == "Choose Category") {
                    Toast.makeText(this, "Please provide Nationality", Toast.LENGTH_LONG)
                            .show();
                }else if (mBitmap == null) {

                    Toast.makeText(this, "Please select picture", Toast.LENGTH_LONG)
                            .show();
                }
                else {

                    doStaff();

                }
                break;
        }

    }

    private void uploadIdPic() {


    }



    //image file is saved to be sent to server later
    private File savebitmap(String filename) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        // File file = new File(filename + ".png");
        File file = new File(filename);

        Log.e("file", "" + file);

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }






    //image file of image is created here
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //	mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }



    //spinner happens here
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cate = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "The planet is " +
                cate, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void doStaff() {
        // TODO Auto-generated method stub
        pDialog.setMessage("Submitting Item..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("type", "createcomp");
        params.put("category", cate);
        params.put("fname", fname);
        params.put("dobb", dobb);
        params.put("nationality", nation);
        params.put("user_id", id);
        params.put("objurl", encoded);


        aq.progress(pDialog).ajax(
                StaticVariables.companycreate,
                params, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject json,
                                         AjaxStatus status) {

                        try {

                            Toast.makeText(
                                    CreateCompany.this,
                                    json.getInt(StaticVariables.SUCCESS) + "",
                                    Toast.LENGTH_LONG).show();


                            System.out.println(json.toString());
                            int success = json.getInt(StaticVariables.SUCCESS);

                            if (success == 1) {


                                location.setText("");
                                company_name.setText("");
                                contact.setText("");

                                dialogShow();

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
                            //Toast.makeText(Individual_Sell.this,
                            //        ex.toString(), Toast.LENGTH_LONG)
                            //        .show();
                        }

                    }
                });

    }



    //called after the user submits his item to the server
    void dialogShow() {
        AlertdialogfragmentComp cdd = new AlertdialogfragmentComp(CreateCompany.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();

        //check again
        submit.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        //prevthem.setVisibility(View.VISIBLE);
        //clickable.setOnTouchListener();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
