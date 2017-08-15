package com.lyte.adaboo.lyteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
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
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by adaboo on 5/6/17.
 */


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import static android.R.attr.path;
import static android.content.ContentValues.TAG;

public class Individual_Sell extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {

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
    Button edit, prevthem;

    JSONArray products = null;

    //for the arraylist
    ArrayList<String> friends = new ArrayList<String>();
    ArrayList<String> mylist = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_sell);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        prev = (ImageView) findViewById(R.id.img_prev);
        upload = (Button) findViewById(R.id.upload);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinnercompany = (Spinner) findViewById(R.id.spinnercompany);


        edit = (Button) findViewById(R.id.clear);
        prevthem = (Button) findViewById(R.id.prev_them);
        submit = (Button) findViewById(R.id.submit);


        descrip = (EditText) findViewById(R.id.editText2);
        price = (EditText) findViewById(R.id.editText3);
        qty = (EditText) findViewById(R.id.editText4);


        pr = (TextView) findViewById(R.id.prev_product);
        p = (TextView) findViewById(R.id.prev_price);
        q = (TextView) findViewById(R.id.prev_qty);

        clickable = (LinearLayout) findViewById(R.id.editable);

       // android:background="@drawable/editback"
        HashMap<String, String> user = session.getUserDetails();

        id = user.get(SessionManager.KEY_ID);
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // image
        String imageUrl = user.get(SessionManager.KEY_IMAGEURL);

        friend_array = user.get(SessionManager.KEY_FRIENDS);



        String[] years = {"Choose Category","Electronics","Clothing","Accommodation","Stationery","Automobile"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);


        spinner1.setAdapter(langAdapter);

        spinner1.setOnItemSelectedListener(this);

        spinnercompany.setOnItemSelectedListener(this);


        aq = new AQuery(this);
        pDialog = new ProgressDialog(this);

      //  cancel.setOnClickListener(this);
        upload.setOnClickListener(this);
        submit.setOnClickListener(this);
        prevthem.setOnClickListener(this);
        edit.setOnClickListener(this);

        //load the spinner with company
        getCompany(id);
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.upload:

                picture();

                break;


            case R.id.prev_them:

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
                }else if (mBitmap == null) {

                    Toast.makeText(this, "Please select picture", Toast.LENGTH_LONG)
                            .show();
                }
                else {

                    preview();
                    edit.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    prevthem.setVisibility(View.INVISIBLE);
                    clickable.setEnabled(false);
                }



                break;


            case R.id.clear:
                submit.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.INVISIBLE);
                prevthem.setVisibility(View.VISIBLE);
                clickable.setEnabled(true);

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

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Individual_Sell.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //called after the user submits his item to the server
    void dialogShow() {
        AlertDialogFragment cdd = new AlertDialogFragment(Individual_Sell.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();


        //check again
        submit.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        prevthem.setVisibility(View.VISIBLE);
        clickable.setClickable(true);
    }



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

    /**
     * Shows the progress UI and hides the login form.
     */


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

     //send to items without comapny
     private void donormalStaff() {
        // TODO Auto-generated method stub
        pDialog.setMessage("Submitting Item..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        Map<String, Object> params = new HashMap<String, Object>();

        // params.put("company", comp);
         params.put("type", "submit");
         params.put("category", cate);
         params.put("description", desc);
         params.put("price", amt);
         params.put("quantity", qtny);
         params.put("user_id", id);
         params.put("objurl", encoded);


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
                            //Toast.makeText(Individual_Sell.this,
                            //        ex.toString(), Toast.LENGTH_LONG)
                            //        .show();
                        }

                    }
                });

    }

    public void preview() {

        try {

            pr.setText("Product:" + desc);
            p.setText("Price:" + "GHS " + amt);
            q.setText("Qty:" + qtny);
            prev.setImageBitmap(mBitmap);

            }

         catch (Exception e) {
        }
        ;
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

                            ArrayAdapter<CharSequence> langAdapters = new ArrayAdapter<CharSequence>(getApplicationContext(), R.layout.spinner_text2, arr);
                            langAdapters.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            spinnercompany.setAdapter(langAdapters);



                        //String[] years = {"Choose Category","Electronics","Clothing","Stationery","Automobile"};



                    }
                });

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        switch (parent.getId()){
            case R.id.spinner1:
                cate = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnercompany:

                comp = parent.getItemAtPosition(position).toString();
                //Toast.makeText(this, comp, Toast.LENGTH_LONG).show();
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
