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

public class user_item_recycle extends RecyclerView.Adapter<RecyclerViewHolder> {

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
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final user_items model = arrayList.get(position);

        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder


        //mainHolder.txt

        // setting title
       // mainHolder..setText(model.getProduct());
       // mainHolder.name.setText(model.getPrice());
       // mainHolder.name.setText(model.getQnty());



        //mainHolder.imageview.setImageBitmap(image);


       /// need to convert array byte back

        Picasso.with(mContext)
                .load(model.getImg())
                .placeholder( R.drawable.progress_animation )
                .into(mainHolder.imageview);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.freinditem, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;

    }

}



