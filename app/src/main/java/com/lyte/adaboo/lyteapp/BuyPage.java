package com.lyte.adaboo.lyteapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by adaboo on 4/22/17.
 */

public class BuyPage extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    String name;
    String imageUrl;
    int surname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_layout);

       // new DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

            String restoredText = prefs.getString("text", null);

            if (restoredText != null) {
                name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
                imageUrl = prefs.getString("imageUrl", "url");//"No name defined" is the default value.
                surname = prefs.getInt("surname", 0); //0 is the default value.
            }

            //Bundle inBundle = getIntent().getExtras();
            //String name = inBundle.get("name").toString();
           // String surname = inBundle.get("surname").toString();
           // String imageUrl = inBundle.get("imageUrl").toString();

            //editor.clear();
            //editor.commit(); // commit changes

            new DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);

            Toast.makeText(BuyPage.this, name + surname + imageUrl, Toast.LENGTH_LONG).show();


            TextView nameView = (TextView)findViewById(R.id.nameAndSurname);
            nameView.setText("" + name + " " + surname);


          //  new BuyPage().DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);
        }

    }

}
