package com.lyte.adaboo.lyteapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by adaboo on 4/22/17.
 */

public class BuyPage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_layout);

       // new DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            Bundle inBundle = getIntent().getExtras();
            String name = inBundle.get("name").toString();
            String surname = inBundle.get("surname").toString();
            String imageUrl = inBundle.get("imageUrl").toString();

            Toast.makeText(BuyPage.this, name + surname + imageUrl, Toast.LENGTH_LONG).show();


            TextView nameView = (TextView)findViewById(R.id.nameAndSurname);
            nameView.setText("" + name + " " + surname);

            new DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);
          //  new BuyPage().DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);
        }

    }

}
