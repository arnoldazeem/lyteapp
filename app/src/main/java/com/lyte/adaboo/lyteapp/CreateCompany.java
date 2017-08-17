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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

    String fname,nation;
    String lname;
    String dobb;


    TextView prev_compname,prev_category;
    TextView prev_loca;
    TextView prev_contact;

    String cate,id,friend_array,user_id;

    EditText firstname,lastname,dob,nationality,idnum,spinnerid;
    Spinner idtype;



    byte[] imgurl;
    SessionManager session;
    TextView pr,p,q;
    String encodedImage = "";
    File encoded;

    LinearLayout clickable;

    public static final int REQUEST_CAMERA = 110;
    public static final int SELECT_FILE = 120;
    String code;
    File mPhotoFile = null;
    Bitmap mBitmap = null;
    String gotten,send;


    Button edit, browseID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcompany);


        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        dob = (EditText) findViewById(R.id.dob);
        nationality = (EditText) findViewById(R.id.nationality);
        dob = (EditText) findViewById(R.id.idnumber);
        idtype = (Spinner) findViewById(R.id.spinidtype);


        browseID = (Button) findViewById(R.id.uploadID);
        submit = (Button) findViewById(R.id.submit);



        String[] years = {"Choose Type","Passport","Driver's License","Nationl Id","National Health Insurance"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        idtype.setAdapter(langAdapter);


        aq = new AQuery(this);
        pDialog = new ProgressDialog(this);

        idtype.setOnItemSelectedListener(this);
        browseID.setOnClickListener(this);

        /**

        location = (EditText) findViewById(R.id.editText2);
        company_name = (EditText) findViewById(R.id.editText3);
        contact = (EditText) findViewById(R.id.editText4);
        prev_compname = (TextView) findViewById(R.id.prev_product);
        prev_loca = (TextView) findViewById(R.id.prev_price);
        prev_contact = (TextView) findViewById(R.id.prev_qty);
        prev_category = (TextView) findViewById(R.id.prev_cate);


        clickable = (LinearLayout) findViewById(R.id.editable);

        // android:background="@drawable/editback"
        HashMap<String, String> user = session.getUserDetails();
        id = user.get(SessionManager.KEY_ID);
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // image
        String imageUrl = user.get(SessionManager.KEY_IMAGEURL);

        //friend_array = user.get(SessionManager.KEY_FRIENDS);


        //  cancel.setOnClickListener(this);
        upload.setOnClickListener(this);
        submit.setOnClickListener(this);
        prevthem.setOnClickListener(this);
        edit.setOnClickListener(this);

        clickable.setOnTouchListener(this);

         */
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.uploadID:
               picture();
              break;


            /*
            case R.id.prev_them:



                loc = location.getText().toString();
                name = company_name.getText().toString();
                conta = contact.getText().toString();


                if (loc.trim().contentEquals("")) {
                    Toast.makeText(this, "Please provide Decription of Item",
                            Toast.LENGTH_LONG).show();
                } else if (name.contentEquals("")) {
                    Toast.makeText(this, "Please provide Item Price",
                            Toast.LENGTH_LONG).show();
                } else if (conta.contentEquals("")) {
                    Toast.makeText(this, "Please provide Quantity", Toast.LENGTH_LONG)
                            .show();
                }else if (cate == "Choose Category") {
                    Toast.makeText(this, "Please choose Category", Toast.LENGTH_LONG)
                            .show();
                }else if (mBitmap == null) {

                    Toast.makeText(this, "Please select picture", Toast.LENGTH_LONG)
                            .show();
                }
                else {

                    //preview();
                    edit.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    prevthem.setVisibility(View.INVISIBLE);
                    clickable.setEnabled(false);
                }



                break;

             */


            case R.id.submit:

                //check all editboxes
                fname = firstname.getText().toString();
                lname = lastname.getText().toString();
                dobb = dob.getText().toString();
                nation = nationality.getText().toString();

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




    //activity results after you go  into the camera


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA &&  resultCode == RESULT_OK) {

            if (mPhotoFile != null) {
                send  =  (mPhotoFile.getAbsolutePath());
                decodeBitmap(mPhotoFile.getAbsolutePath());

            } else
                Toast.makeText(this, "Error Retrieving Picture", Toast.LENGTH_SHORT).show();

        } else if (requestCode == SELECT_FILE &&  resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                    null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);
            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;

            options.inJustDecodeBounds = false;

            bm = BitmapFactory.decodeFile(selectedImagePath, options);

            send  = (selectedImagePath);
            decodeBitmap(selectedImagePath);


        }
    }


    //where Image is Decoded
    private void decodeBitmap(String filepath) {
        Log.i(TAG, "Decoding Bitmap");

        String currentPhotoPath;
        currentPhotoPath = filepath;

        int targetW = 400;
        int targetH = 400;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap resizedBitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        resizedBitmap = ThumbnailUtils.extractThumbnail(resizedBitmap, 400, 400);

        ExifInterface exif;
        try {
            exif = new ExifInterface(currentPhotoPath);

            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) resizedBitmap.getWidth() / 2, (float) resizedBitmap.getHeight() / 2);
            mBitmap = Bitmap.createBitmap(resizedBitmap, 0, 0, targetW, targetH, matrix, true);

            //where image is saved
            // prev.setImageBitmap(mBitmap);

            String name = filepath.substring(filepath.lastIndexOf("/")+1);
            String nameagain = name.substring(0, name.lastIndexOf('.'));

            encoded =  savebitmap(filepath);

            //remember to delete file after or else file stays on user sdcard

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
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



    //here is where the dialog was created
    void picture(){
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Ensure that there's a camera activity to handle the intent
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        mPhotoFile = null;
                        try {
                            mPhotoFile = createImageFile();
                        } catch (IOException ex) {
                            Log.e(TAG, ex.toString());
                        }
                        // Continue only if the File was successfully created
                        if (mPhotoFile != null) {
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(mPhotoFile));
                            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                        }
                    }

                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

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


/*
    //preview of input
    public void preview() {

        try {

            prev_compname.setText("Company Name:" + " "+ name );
            prev_loca.setText("Location " + " "+ loc);
            prev_contact.setText("Contact" +" "+ conta);
            prev_category.setText("Category" + " " + cate);
            prev.setImageBitmap(mBitmap);

        }

        catch (Exception e) {
        }

    }

*/

    //send to server


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
