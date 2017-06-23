package com.influentials.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.influentials.kiranastore.MainActivity;
import com.influentials.kiranastore.R;
import com.influentials.listadapters.HomePageListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pesin on 6/14/17.
 */

public class HomePageListFragment extends Fragment {

    private MainActivity mainActivity;
    private List<String> groupNames;
    private ArrayList<Object> childItem = new ArrayList<>();
    private List<String> child;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity= (MainActivity) getActivity();
        groupNames= Arrays.asList(getResources().getStringArray(R.array.group_names));
        setChildItem();

    }

    private void setChildItem()
    {

            child= Arrays.asList(getResources().getStringArray(R.array.Vegetables));
            childItem.add(child);
            child=Arrays.asList(getResources().getStringArray(R.array.Fruits));
            childItem.add(child);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rowView= (ViewGroup) inflater.inflate(R.layout.home_page,container,false);
        ExpandableListView expandableListView= (ExpandableListView) rowView.findViewById(R.id.expandable_list_view);
        HomePageListAdapter adapter=new HomePageListAdapter(mainActivity,groupNames,childItem);
        expandableListView.setAdapter(adapter);

        return rowView;

    }
}
