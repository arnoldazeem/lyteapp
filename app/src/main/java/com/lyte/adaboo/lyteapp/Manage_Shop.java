package com.lyte.adaboo.lyteapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by adaboo on 8/15/17.
 */

public class Manage_Shop extends AppCompatActivity implements View.OnClickListener {

    TextView create;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_shop);

        create = ((TextView) this.findViewById(R.id.create));

        create.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.create:
                /** Start a new Activity MyCards.java */

                Intent intent = new Intent(Manage_Shop.this,
                        CreateCompany.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                break;
        }

    }
}