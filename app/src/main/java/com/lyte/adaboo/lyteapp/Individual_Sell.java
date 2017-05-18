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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

import static android.content.ContentValues.TAG;

public class Individual_Sell extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {

    EditText password, userName, confirmPassword;
    String passwordString, userNameString, confirmPasswordString;
    Button upload, submit;
    AQuery aq;
    ImageView prev;
    ProgressDialog pDialog;
    String url_all_products = "";
    Spinner spinner1;
    EditText descrip;
    EditText price;
    EditText qty;
    String desc;
    String amt;
    String qtny;
    String cate,id,friend_array,user_id;

    byte[] imgurl;
    SessionManager session;
    TextView pr,p,q;
    String encodedImage = "";


    public static final int REQUEST_CAMERA = 110;
    public static final int SELECT_FILE = 120;
    String code;
    File mPhotoFile = null;
    Bitmap mBitmap = null;
    String gotten,send;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_sell);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

       // password = (EditText) findViewById(R.id.etRegisterPassword);
       // userName = (EditText) findViewById(R.id.etRegisterUserName);
       // confirmPassword = (EditText) findViewById(R.id.etRegisterPasswordConfirm);

        prev = (ImageView) findViewById(R.id.img_prev);
        upload = (Button) findViewById(R.id.upload);
        submit = (Button) findViewById(R.id.send);
        spinner1= (Spinner) findViewById(R.id.spinner1);


        descrip = (EditText) findViewById(R.id.editText2);
        price = (EditText) findViewById(R.id.editText3);
        qty = (EditText) findViewById(R.id.editText4);

        pr = (TextView) findViewById(R.id.prev_product);
        p = (TextView) findViewById(R.id.prev_price);
        q = (TextView) findViewById(R.id.prev_qty);


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

        aq = new AQuery(this);
        pDialog = new ProgressDialog(this);
      //  cancel.setOnClickListener(this);
        upload.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.upload:
                picture();
                break;

            case R.id.send:

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
                }else if (encodedImage.contentEquals("")) {
                    Toast.makeText(this, "Please provide an Image", Toast.LENGTH_LONG)
                            .show();
                }else if (cate == "Choose Category") {
                    Toast.makeText(this, "Please choose Category", Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    uploadImage();
                    //doStaff();
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
            prev.setImageBitmap(mBitmap);
            savebitmap(currentPhotoPath);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    private File savebitmap(String filename) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            // make a new bitmap from your file
            Bitmap bitmap = BitmapFactory.decodeFile(file.getName());

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
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

    private void uploadImage() {

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest postRequest = new StringRequest(Request.Method.POST, StaticVariables.sendItemVolUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        loading.dismiss();
                        Log.d("Error.Response", error.getMessage().toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
               // params.put("name", "Alif");
                //params.put("image", "http://itsalif.info");

                //Adding parameters
                params.put("photo", "encodedImage");
                params.put("name", desc);
                params.put("price", amt);
                params.put("quantity", qtny);


                return params;
            }
        };

        //Creating a Request Queue
       //RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        //requestQueue.add(stringRequest);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
       // CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, StaticVariables.sendItemVolUrl, params, this., this.createRequestErrorListener());
         requestQueue.add(postRequest);


    }





    private void doStaff() {
        // TODO Auto-generated method stub
        pDialog.setMessage("Submitting Item..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        Map<String, Object> params = new HashMap<String, Object>();
        aq.progress(pDialog).ajax(
                StaticVariables.sendItemUrl + "submit&category="
                        + cate + "&description=" + desc + "&price="
                        + amt+ "&quantity=" + qtny + "&user_id=" + id
                        +"&objurl=" + encodedImage,

                params, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject json,
                                         AjaxStatus status) {

                        try {

                            System.out.println(json.toString());
                            int success = json.getInt(StaticVariables.SUCCESS);

                            if (success == 1) {
                                Toast.makeText(
                                        Individual_Sell.this,
                                        json.getString(StaticVariables.MESSAGE),
                                        Toast.LENGTH_LONG).show();

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
                            Toast.makeText(Individual_Sell.this,
                                    "Server cannot be found", Toast.LENGTH_LONG)
                                    .show();
                        }

                    }
                });

    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cate = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "The planet is " +
                cate, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
