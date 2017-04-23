package com.lyte.adaboo.lyteapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by adaboo on 4/16/17.
 */

public class HomePage extends AppCompatActivity implements View.OnClickListener{

    Button peopleyouknow;
    Button shoppingmall;
    Button buy;
    LinearLayout buybuttoms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_lay);

        peopleyouknow = ((Button) this.findViewById(R.id.ppl_know));

        shoppingmall = ((Button) this.findViewById(R.id.mall));

        buy = ((Button) this.findViewById(R.id.client_buy));

        buybuttoms = ((LinearLayout) this.findViewById(R.id.appear_buy));

        peopleyouknow.setOnClickListener(this);

        shoppingmall.setOnClickListener(this);

        buy.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            Bundle inBundle = getIntent().getExtras();
            String name = inBundle.get("name").toString();
            String surname = inBundle.get("surname").toString();
            String imageUrl = inBundle.get("imageUrl").toString();

            Toast.makeText(HomePage.this, name + surname + imageUrl, Toast.LENGTH_LONG).show();
        }


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ppl_know:
                /** Start a new Activity MyCards.java */

                //Intent main = new Intent(HomePage.this, facebooklogin_example.class);
                // main.putExtra("name", profile.getFirstName());
                // main.putExtra("surname", profile.getLastName());
                // main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
                //startActivity(main);

                break;
            case R.id.mall:
                /** AlerDialog when click on Exit */

                break;

            case R.id.client_buy:
                /** AlerDialog when click on Exit */

                buybuttoms.setVisibility(View.VISIBLE);

                buy.setVisibility(View.INVISIBLE);

                break;
        }



    }



}