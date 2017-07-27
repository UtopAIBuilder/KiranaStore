package com.influentials.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.influentials.kiranastore.MainActivity;
import com.influentials.kiranastore.R;
import com.influentials.listadapters.HomePageCategoryAdapter;



public class HomePageCategoryListFragment extends Fragment {

    private MainActivity mainActivity;
    private String [] groupNames;
    private ListView listView;
    private HomePageCategoryAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity= (MainActivity) getActivity();
       groupNames=getResources().getStringArray(R.array.group_names);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.category_home_page,container,false);
              listView= (ListView) rootView.findViewById(R.id.catListView);
        adapter=new HomePageCategoryAdapter(mainActivity,groupNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainActivity.displayView(2);
            }
        });
        return rootView;
    }
}
