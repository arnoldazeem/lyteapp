package com.lyte.adaboo.lyteapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lyte.adaboo.lyteapp.R;

/**
 * Created by adaboo on 4/16/17.
 */

public class HomePage extends AppCompatActivity implements View.OnClickListener{

    Button shoppingmall,peopleyouknow,sell_ppl_know,sell_mall;
    ImageView buy,sell;
    LinearLayout buybuttoms,sellbuttoms;
    RelativeLayout buy_layout,sell_layout;
    private ProgressDialog progressDialog;
    TextView just,want;

    Boolean clicksell = true;
    Boolean clickbuy = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_lay);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(tool);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setDisplayShowHomeEnabled(false);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setTitle("");

        /**
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.below_toolbar);
        View view =getSupportActionBar().getCustomView();

        Toolbar parent =(Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0,0);

         **/


        //four buttons for navigation
        peopleyouknow = ((Button) this.findViewById(R.id.ppl_know));
        shoppingmall = ((Button) this.findViewById(R.id.mall));
        sell_ppl_know = ((Button) this.findViewById(R.id.sell_know));
        sell_mall = ((Button) this.findViewById(R.id.sell_mall));


        //buy and sell images
        sell = ((ImageView) this.findViewById(R.id.client_sell));
        buy = ((ImageView) this.findViewById(R.id.client_buy));



        //layout here because the hide and show the hidden buttons
        buybuttoms = ((LinearLayout) this.findViewById(R.id.appear_buy));
        sellbuttoms = ((LinearLayout) this.findViewById(R.id.appear_sell));


        //relative layout clickable
        buy_layout = ((RelativeLayout) this.findViewById(R.id.buy_lay));
        sell_layout = ((RelativeLayout) this.findViewById(R.id.sell_lay));


        just = ((TextView) this.findViewById(R.id.just));

        want = ((TextView) this.findViewById(R.id.want_to));

       // Typeface custom_font = Typeface.createFromAsset(getAssets(), "ARCHRISTY.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "ARESSENCE.ttf");
        just.setTypeface(custom_font2);
        want.setTypeface(custom_font2);

        peopleyouknow.setOnClickListener(this);
        shoppingmall.setOnClickListener(this);

        buy_layout.setOnClickListener(this);
        sell_layout.setOnClickListener(this);
        sell_ppl_know.setOnClickListener(this);
        sell_mall.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            Bundle inBundle = getIntent().getExtras();
            String name = inBundle.get("name").toString();
            String surname = inBundle.get("surname").toString();
            String imageUrl = inBundle.get("imageUrl").toString();

            Toast.makeText(HomePage.this, name + surname + imageUrl, Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home)
        {
            return true;
        }
        else if (id == R.id.action_allfriends)
        {
            return true;
        }else if (id == R.id.action_allfriends)
        {
            return true;
        }else if (id == R.id.action_star)
        {
            return true;
        }else if (id == R.id.action_notificarions)
        {
            return true;
        }else if (id == R.id.action_settings)
        {
            Toast.makeText(HomePage.this, "settings", Toast.LENGTH_LONG).show();

        }else if (id == R.id.myself)
        {
            Intent sell_mall = new Intent(HomePage.this, myProfile.class);
            sell_mall.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(sell_mall);
        }

        return super.onOptionsItemSelected(item);
    }





    private void initToolbars() {
        Toolbar toolbarBottom = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarBottom);

        toolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_settings:
                        // TODO
                        break;
                    // TODO: Other cases
                }
                return true;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbarBottom.inflateMenu(R.menu.menu_main);
    }




    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buy_lay:
                /** Start a new Activity MyCards.java */

                /** AlerDialog when click on Exit */
                if(clickbuy) {

                    progressDialog = new ProgressDialog(HomePage.this);
                    progressDialog.setMessage("Loading......");
                    progressDialog.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {


                            //check here again
                            buybuttoms.setVisibility(View.VISIBLE);
                            buy.setVisibility(View.INVISIBLE);
                            sellbuttoms.setVisibility(View.INVISIBLE);
                            sell.setVisibility(View.VISIBLE);

                            progressDialog.dismiss();
                        }
                    }, 2000); // 3000 milliseconds delay


                    clicksell = true;
                    clickbuy = false;
                }

                break;

            case R.id.sell_lay:
                /** Start a new Activity MyCards.java */
                /** AlerDialog when click on Exit */

                if(clicksell) {

                    progressDialog = new ProgressDialog(HomePage.this);
                    progressDialog.setMessage("Loading......");
                    progressDialog.show();

                    Handler handlers = new Handler();
                    handlers.postDelayed(new Runnable() {
                        public void run() {

                            sellbuttoms.setVisibility(View.VISIBLE);
                            sell.setVisibility(View.INVISIBLE);
                            buybuttoms.setVisibility(View.INVISIBLE);
                            buy.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                        }
                    }, 2000); // 3000 milliseconds delay

                    clicksell = false;
                    clickbuy = true;
                }
                break;


            //buy from people you know
            case R.id.ppl_know:
                /** Start a new Activity MyCards.java */
                clickbuy = true;
                Intent intent = new Intent(HomePage.this,
                        BuyPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                break;

            //buy from mall
            case R.id.mall:
                /** AlerDialog when click on Exit */

                clicksell = true;
                Intent in = new Intent(HomePage.this,
                        Buy_mall.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(in);
                break;

            case R.id.sell_know:


               // clicksell = true;
                Intent isn = new Intent(HomePage.this,
                        Individual_Sell.class);
                isn.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(isn);

                break;


            case R.id.sell_mall:
                /** AlerDialog when click on Exit */

                //clicksell = true;
                Intent sell_mall = new Intent(HomePage.this,
                        CreateCompany.class);
                sell_mall.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(sell_mall);
                break;
        }



    }



}