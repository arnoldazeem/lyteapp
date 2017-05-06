package com.lyte.adaboo.lyteapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

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
    Button register, cancel;
    AQuery aq;
    ProgressDialog pDialog;
    String url_all_products = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_sell);

        password = (EditText) findViewById(R.id.etRegisterPassword);
        userName = (EditText) findViewById(R.id.etRegisterUserName);
        confirmPassword = (EditText) findViewById(R.id.etRegisterPasswordConfirm);


        cancel = (Button) findViewById(R.id.Cancel);
        register = (Button) findViewById(R.id.Register);


        aq = new AQuery(this);
        pDialog = new ProgressDialog(this);
        cancel.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.Cancel:
                finish();
                break;

            case R.id.Register:
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
                                        Register.this,
                                        json.getString(StaticVariables.MESSAGE),
                                        Toast.LENGTH_LONG).show();
                                userName.setText("");
                                password.setText("");
                                confirmPassword.setText("");
                            } else {
                                Toast.makeText(
                                        Register.this,
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
                            Toast.makeText(Register.this,
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
