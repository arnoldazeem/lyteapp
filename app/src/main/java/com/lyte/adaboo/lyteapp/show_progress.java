package com.lyte.adaboo.lyteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by adaboo on 4/23/17.
 */

public class show_progress extends AppCompatActivity implements View.OnClickListener {


    Button facedem;
    Button shoppingmall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_dem);

        facedem = ((Button) this.findViewById(R.id.facedem));

        shoppingmall = ((Button) this.findViewById(R.id.shopdem));

        facedem.setOnClickListener(this);

        shoppingmall.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.facedem:
                /** Start a new Activity MyCards.java */

                Intent main = new Intent(show_progress.this, GraphApiTry.class);
                // main.putExtra("name", profile.getFirstName());
                // main.putExtra("surname", profile.getLastName());
                // main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
                startActivity(main);

                break;
            case R.id.shopdem:
                /** AlerDialog when click on Exit */

                Intent mains = new Intent(show_progress.this, HomePage.class);
                // main.putExtra("name", profile.getFirstName());
                // main.putExtra("surname", profile.getLastName());
                // main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
                startActivity(mains);

                break;


        }



    }
}
