package com.influentials.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.influentials.kiranastore.R;



public class HomePageCategoryAdapter extends BaseAdapter {

    private Context context;
    private String [] groupNames;

    public HomePageCategoryAdapter(Context context,String [] groupNames)
    {
        this.context=context;
        this.groupNames= groupNames;
    }
    @Override
    public int getCount() {
        return groupNames.length;
    }

    @Override
    public Object getItem(int i) {
        return groupNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View row;
        LayoutInflater inflater;

        if (convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(R.layout.home_list_row,parent,false);
            convertView=row;

        }
        else
        {
            row=convertView;
        }

        TextView groupName= (TextView) convertView.findViewById(R.id.group_name);
        ImageView groupIcon= (ImageView) convertView.findViewById(R.id.group_header_icon);

        groupName.setText(groupNames[i]);
        groupIcon.setImageResource(R.drawable.ic_basket);


        return row;
    }
}
