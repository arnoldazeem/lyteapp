package com.lyte.adaboo.lyteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by adaboo on 5/5/17.
 */

        import java.util.ArrayList;
        import java.util.List;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import de.hdodenhof.circleimageview.CircleImageView;

public class companyItemsAdapter extends ArrayAdapter<Friend_List_Bulk> {

    private ArrayList<Friend_List_Bulk> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        CircleImageView info;
    }

    public companyItemsAdapter(ArrayList<Friend_List_Bulk> data, Context context) {
        super(context, R.layout.freinditem, data);
        this.dataSet = data;
        this.mContext = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Friend_List_Bulk dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.comapnyitem, parent, false);

           // viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);

            viewHolder.info = (CircleImageView) convertView.findViewById(R.id.profileImage);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


       // viewHolder.txtName.setText(dataModel.getName());

       String url =  "http://lyteapp.site40.net"+dataModel.getImageUrl() ;

        Picasso.with(getContext())
                .load(url)
                .placeholder( R.drawable.progress_animation )
                .into(viewHolder.info);

        // Return the completed view to render on screen
        return convertView;
    }
}