package com.lyte.adaboo.lyteapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by adaboo on 5/5/17.
 */

public class user_friend_recycle extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<Friend_List_Bulk> arrayList;
    Context mContext;

    public LayoutInflater inflater;

    public user_friend_recycle(Context context, ArrayList<Friend_List_Bulk> arrayList) {
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
        final Friend_List_Bulk model = arrayList.get(position);

        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder


        // setting title
       // mainHolder.name.setText(model.getName());

        //mainHolder.imageview.setImageBitmap(image);

        Picasso.with(mContext)
                .load(model.getImageUrl())
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



