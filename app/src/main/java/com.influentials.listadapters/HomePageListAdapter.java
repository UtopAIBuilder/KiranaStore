package com.influentials.listadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.influentials.kiranastore.R;

import java.util.ArrayList;
import java.util.List;


public class HomePageListAdapter extends BaseExpandableListAdapter {

    private List<String> groupNames;
    private LayoutInflater inflater;
    private ArrayList<Object> childItem;
    private List<String> child;

    public HomePageListAdapter(Context context, List<String> groupNames, ArrayList<Object> childItem)
    {
      this.groupNames=groupNames;
        this.childItem=childItem;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getGroupCount() {
        return groupNames.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((List<String>)childItem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupNames.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((List<String>) childItem.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView==null)
        {

            convertView=inflater.inflate(R.layout.home_list_row,parent,false);

        }

        TextView groupName= (TextView) convertView.findViewById(R.id.group_name);
       // TextView groupSubCategoryTitle= (TextView) convertView.findViewById(R.id.subgroup_titles);
        ImageView groupIcon= (ImageView) convertView.findViewById(R.id.group_header_icon);

        groupName.setText(groupNames.get(groupPosition));
        Log.d("Bz","groupNames:"+groupNames.get(groupPosition));

        child= (List<String>) childItem.get(groupPosition);
        //groupSubCategoryTitle.setText(getGroupSubCategoryTitles(child));
       groupIcon.setImageResource(R.drawable.ic_basket);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView=inflater.inflate(R.layout.home_child_grid,parent,false);
        }
        ImageView childImage= (ImageView) convertView.findViewById(R.id.child_image);
        TextView childItemName= (TextView) convertView.findViewById(R.id.text_view_child);

        childImage.setImageResource(R.drawable.ic_basket);
        child= (List<String>) childItem.get(groupPosition);
        childItemName.setText(child.get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private String getGroupSubCategoryTitles(List<String> child)
    {
       String groupSubCategoryTitles="";
        for (int i=0;i<child.size();i++)
        {

            groupSubCategoryTitles+=child.get(i)+",";
            Log.d("Bz","child:"+i+":"+child.get(i));
        }
        return groupSubCategoryTitles;
    }
}
