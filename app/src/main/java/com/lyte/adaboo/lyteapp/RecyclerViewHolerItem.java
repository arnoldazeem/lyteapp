package com.lyte.adaboo.lyteapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by adaboo on 5/28/17.
 */


public class RecyclerViewHolerItem extends RecyclerView.ViewHolder  {
    // View holder for gridview recycler view as we used in listview
    public TextView product;
    public TextView price;
    public TextView qnty;
    public ImageView imageview;


    public RecyclerViewHolerItem(View view) {
        super(view);

        // Find all views ids
        this.product = (TextView) view
          .findViewById(R.id.prev_product);

        this.price = (TextView) view
                .findViewById(R.id.prev_price);

        this.qnty = (TextView) view
                .findViewById(R.id.prev_qty);

        this.imageview = (ImageView) view
                .findViewById(R.id.img_prev);


    }



}