package com.lyte.adaboo.lyteapp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by adaboo on 4/22/17.
 */

public class BuyPage extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    String name;
    String imageUrl;
    int surname;

    Button logout;

    SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_layout);

        logout = (Button) findViewById(R.id.logout);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // image
        String imageUrl = user.get(SessionManager.KEY_IMAGEURL);

        new DownloadImage((ImageView) findViewById(R.id.profileImage)).execute(imageUrl);

       // Toast.makeText(BuyPage.this, name  + imageUrl, Toast.LENGTH_LONG).show();



        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                session.logoutUser();

            }

        });


    }

}
