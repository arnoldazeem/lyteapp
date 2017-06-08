package com.lyte.adaboo.lyteapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by adaboo on 5/16/17.
 */


/**
 * Created by adaboo on 5/5/17.
 */

public class user_item_recycle extends RecyclerView.Adapter<RecyclerViewHolerItem> {

    private ArrayList<user_items> arrayList;

    Context mContext;

    public LayoutInflater inflater;

    public user_item_recycle(Context context, ArrayList<user_items> arrayList) {
        super();
        this.arrayList = arrayList;
        this.mContext = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }


    @Override
    public void onBindViewHolder(RecyclerViewHolerItem holder, int position) {
        final user_items model = arrayList.get(position);

        RecyclerViewHolerItem mainHolder = (RecyclerViewHolerItem) holder;// holder


        //check RecyclerViewHolerItem class

        mainHolder.product.setText("Product :" + model.getProduct());
        mainHolder.price.setText("Price :" + model.getPrice());
        mainHolder.qnty.setText("Quantity :" + model.getQnty());


       //String url = "http://lyteapp.site40.net" + model.getImg();

        String url = "http://lyteapp.site40.net/uploads/2017-06-08_17_36_5159398b338d5c5.png";


        Picasso.with(mContext)
                .load(url)
                .placeholder( R.drawable.progress_animation )
                .into(mainHolder.imageview);



        //mainHolder.imageview.setImageBitmap(image);

       /// need to convert array byte back

       // Picasso.with(mContext)
        //        .load(model.getImg())
        //        .placeholder( R.drawable.progress_animation )
       //         .into(mainHolder.imageview);
    }

    @Override
    public RecyclerViewHolerItem onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.user_item, viewGroup, false);

        RecyclerViewHolerItem listHolder = new RecyclerViewHolerItem(mainGroup);

        return listHolder;

    }

}



