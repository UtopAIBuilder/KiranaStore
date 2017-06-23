package com.influentials.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.influentials.kiranastore.R;

/**
 * Created by bazil on 5/23/17.
 */

public class NavDrawerListAdapter extends BaseAdapter {
    private Context context;
    String [] navDrawerTitles;
    int [] navDrawerIcons;
    public NavDrawerListAdapter(Context context, String [] navDrawerTitles,int[] navDrawerIcons){
        this.context = context;
        this.navDrawerTitles = navDrawerTitles;
        this.navDrawerIcons=navDrawerIcons;
    }
    @Override
    public int getCount() {
        return navDrawerTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return navDrawerTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.drawer_list_item,parent,false);

        }
        else
        {
            row=convertView;
        }

         TextView dTitle= (TextView) row.findViewById(R.id.title);
        ImageView dImage= (ImageView) row.findViewById(R.id.icon);
        dTitle.setText(navDrawerTitles[position]);
        dImage.setImageResource(navDrawerIcons[position]);

       return row;
    }
}
