package com.lyte.adaboo.lyteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by adaboo on 5/16/17.
 */

/**
 * Created by adaboo on 5/5/17.
 */



public class user_items_adapter extends ArrayAdapter<user_items> {

    private ArrayList<user_items> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtProduct ;
        TextView txtPrice;
        TextView txtQnty;
        ImageView info;
    }

    public user_items_adapter(ArrayList<user_items> data, Context context) {

        super(context, R.layout.user_item, data);
        this.dataSet = data;
        this.mContext = context;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        user_items dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_item, parent, false);


            viewHolder.txtProduct = (TextView) convertView.findViewById(R.id.prev_product);

            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.prev_price);

            viewHolder.txtQnty = (TextView) convertView.findViewById(R.id.prev_qty);

            viewHolder.info = (ImageView) convertView.findViewById(R.id.img_prev);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtProduct.setText("Product: " + dataModel.getProduct());
        viewHolder.txtPrice.setText(dataModel.getPrice());
        viewHolder.txtQnty.setText(dataModel.getQnty());
       // viewHolder.info.setText(dataModel.getImg());

        //Picasso.with(getContext())
        //        .load(dataModel.getImg())
        //        .placeholder( R.drawable.progress_animation )
        //        .into(viewHolder.info);

        // Return the completed view to render on screen
        return convertView;
    }
}