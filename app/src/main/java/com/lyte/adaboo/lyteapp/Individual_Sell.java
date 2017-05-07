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
import android.util.Log;
import android.view.View;
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

public class Individual_Sell extends Activity implements View.OnClickListener {

    EditText password, userName, confirmPassword;
    String passwordString, userNameString, confirmPasswordString;
    Button upload, cancel;
    AQuery aq;
    ImageView prev;
    ProgressDialog pDialog;
    String url_all_products = "";
    Spinner spinner1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_sell);


       // password = (EditText) findViewById(R.id.etRegisterPassword);
       // userName = (EditText) findViewById(R.id.etRegisterUserName);
       // confirmPassword = (EditText) findViewById(R.id.etRegisterPasswordConfirm);

        prev = (ImageView) findViewById(R.id.img_prev);
        upload = (Button) findViewById(R.id.upload);



        aq = new AQuery(this);
        pDialog = new ProgressDialog(this);
      //  cancel.setOnClickListener(this);
        upload.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.upload:
                selectImage();
                break;


            case R.id.submit:
                userNameString = userName.getText().toString();
                passwordString = MD5(password.getText().toString());
                confirmPasswordString = MD5(confirmPassword.getText().toString());

                if (userNameString.trim().contentEquals("")) {
                    Toast.makeText(this, "User Name can not be empty",
                            Toast.LENGTH_LONG).show();
                } else if (passwordString.contentEquals("")) {
                    Toast.makeText(this, "Password can not be empty",
                            Toast.LENGTH_LONG).show();
                } else if (confirmPasswordString.contentEquals("")) {
                    Toast.makeText(this, "Confirm your Password", Toast.LENGTH_LONG)
                            .show();
                } else if (!passwordString.contentEquals(confirmPasswordString)) {
                    Toast.makeText(this, "Password does not match",
                            Toast.LENGTH_LONG).show();
                } else {
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
            }
        }
    }








    private void doStaff() {
        // TODO Auto-generated method stub
        pDialog.setMessage("Registering..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        Map<String, Object> params = new HashMap<String, Object>();
        aq.progress(pDialog).ajax(
                StaticVariables.registerUrl + "register&username="
                        + userNameString + "&password=" + passwordString,
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
                                userName.setText("");
                                password.setText("");
                                confirmPassword.setText("");
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

}
