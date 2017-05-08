package com.lyte.adaboo.lyteapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by adaboo on 5/6/17.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder  {
    // View holder for gridview recycler view as we used in listview
  //  public TextView name;
    public CircleImageView imageview;


    public RecyclerViewHolder(View view) {
        super(view);
        // Find all views ids

        //this.name = (TextView) view
              //  .findViewById(R.id.name);
        this.imageview = (CircleImageView) view
                .findViewById(R.id.profileImage);


    }



}