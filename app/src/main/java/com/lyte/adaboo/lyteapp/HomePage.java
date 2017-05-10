package com.lyte.adaboo.lyteapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by adaboo on 4/16/17.
 */

public class HomePage extends AppCompatActivity implements View.OnClickListener{

    Button peopleyouknow;
    Button shoppingmall;
    ImageButton buy;
    LinearLayout buybuttoms;
    private ProgressDialog progressDialog;

    TextView just,want;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_lay);

        peopleyouknow = ((Button) this.findViewById(R.id.ppl_know));

        shoppingmall = ((Button) this.findViewById(R.id.mall));

        buy = ((ImageButton) this.findViewById(R.id.client_buy));

        buybuttoms = ((LinearLayout) this.findViewById(R.id.appear_buy));


        just = ((TextView) this.findViewById(R.id.just));

        want = ((TextView) this.findViewById(R.id.want_to));

       // Typeface custom_font = Typeface.createFromAsset(getAssets(), "ARCHRISTY.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "ARESSENCE.ttf");


        just.setTypeface(custom_font2);

        want.setTypeface(custom_font2);


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

                Intent intent = new Intent(HomePage.this,
                        BuyPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                break;
            case R.id.mall:
                /** AlerDialog when click on Exit */

                Intent in = new Intent(HomePage.this,
                        Individual_Sell.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(in);

                break;

            case R.id.client_buy:
                /** AlerDialog when click on Exit */

                progressDialog = new ProgressDialog(HomePage.this);
                progressDialog.setMessage("Loading......");
                progressDialog.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        buybuttoms.setVisibility(View.VISIBLE);

                        buy.setVisibility(View.INVISIBLE);
                        progressDialog.dismiss();
                    }
                }, 2000); // 3000 milliseconds delay




                break;
        }



    }



}