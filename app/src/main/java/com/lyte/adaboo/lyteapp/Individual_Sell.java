package com.lyte.adaboo.lyteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

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
import java.util.HashMap;
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
    Boolean bild;

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

       // android:background="@drawable/editback"
        HashMap<String, String> user = session.getUserDetails();

        id = user.get(SessionManager.KEY_ID);
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // image
        String imageUrl = user.get(SessionManager.KEY_IMAGEURL);

        friend_array = user.get(SessionManager.KEY_FRIENDS);


         bild = false;

        String[] years = {"Electronics","Clothing","Accommodation","Stationery","Automobile"};
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
                selectImage();
                break;

            case R.id.send:

                desc = descrip.getText().toString();
                amt = price.getText().toString();
                qtny = qty.getText().toString();
              //  passwordString = MD5(password.getText().toString());
                if (desc.trim().contentEquals("")) {
                    Toast.makeText(this, "Please provide Decription of Item",
                            Toast.LENGTH_LONG).show();
                } else if (amt.contentEquals("")) {
                    Toast.makeText(this, "Please provide Item Price",
                            Toast.LENGTH_LONG).show();
                } else if (qtny.contentEquals("")) {
                    Toast.makeText(this, "Please provide Quantity", Toast.LENGTH_LONG)
                            .show();
                }else if (bild != true) {
                    Toast.makeText(this, "Please provide an Image", Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    doStaff();
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

    void checkbit( byte[] arr){

        byte[] array = new byte[4096];
        for (byte b : array) {

            if (b != 0) {
                bild = false;
            }else{
                bild = true;
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    prev.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image..****..", picturePath+"");
                prev.setImageBitmap(thumbnail);

                //image converted to file
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                imgurl = bos.toByteArray();
               // String file = Base64.encodeBytes(data);

                checkbit(imgurl);

            }
        }
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
                        +"&objurl=" + imgurl,


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
