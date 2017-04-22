package com.lyte.adaboo.lyteapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by adaboo on 4/16/17.
 */

public class HomePage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_lay);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            Bundle inBundle = getIntent().getExtras();
            String name = inBundle.get("name").toString();
            String surname = inBundle.get("surname").toString();
            String imageUrl = inBundle.get("imageUrl").toString();

            Toast.makeText(HomePage.this, name + surname + imageUrl , Toast.LENGTH_LONG).show();
        }






    }
}
